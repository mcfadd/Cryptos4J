package cryptos;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import exceptions.InvalidArgumentException;
import utils.HttpsConnector;
import utils.Util;

/**
 * Wrapper class for GeneralInfo and ConversionInfo.
 * 
 * @author Matt - <a href="https://github.com/mcfadd">mcfadd</a>
 * @since Cryptos4J v1.0
 * @see GeneralInfo
 * @see ConversionInfo
 */
public final class CryptoInfo implements CryptoBase {

	// mutable fields
	private String currencyFrom;
	private String currencyTo;

	// immutable fields
	private GeneralInfo generalInfo;
	private ConversionInfo conversionInfo;
	private URL url;

	/**
	 * streams CryptoInfo objects by connecting to the <a href=
	 * "https://min-api.cryptocompare.com/documentation?key=Streaming&cat=coinsGeneralInfoEndpoint">Coins
	 * General Info</a> end point.<br>
	 * The number of objects streamed is equal to the length of currenciesFrom.
	 * 
	 * @param currencyTo     currency to convert to
	 * @param currenciesFrom array of currencies to convert from
	 * @return a stream of CryptoInfo
	 * @throws InvalidArgumentException
	 * @since Cryptos4J v1.0
	 */
	public static Stream<CryptoInfo> streamCryptoInfo(String currencyTo, String... currenciesFrom)
			throws InvalidArgumentException {

		int i = 0;
		try {

			Builder<CryptoInfo> streamBuilder = Stream.builder();
			Gson gson = new Gson();
			StringBuilder sb = new StringBuilder();

			for (i = 0; i < currenciesFrom.length; i++) {

				if (i == currenciesFrom.length - 1)
					sb.append(currenciesFrom[i]);
				else
					sb.append(currenciesFrom[i] + ",");

			}

			String json = HttpsConnector.connect("https://min-api.cryptocompare.com/data/coin/generalinfo?fsyms="
					+ sb.toString() + "&tsym=" + currencyTo + "&extraParams=Cryptos4J"
					+ ((Util.getApiKey() != null) ? "&api_key=" + Util.getApiKey() : ""));

			Matcher gneralInfoMatcher = Pattern.compile("CoinInfo\":(.*?),\"ConversionInfo\"").matcher(json);
			Matcher conversionInfoMatcher = Pattern.compile("\"ConversionInfo\":(.*?)\\}\\}").matcher(json);

			i = 0;
			while (gneralInfoMatcher.find() && conversionInfoMatcher.find()) {

				String gInfo = gneralInfoMatcher.group();
				String cInfo = conversionInfoMatcher.group();

				GeneralInfo generalInfo = gson.fromJson(gInfo.substring(10, gInfo.indexOf(",\"ConversionInfo\"")),
						GeneralInfo.class);
				ConversionInfo conversionInfo = gson.fromJson(cInfo.substring(17, cInfo.indexOf("}}") + 1),
						ConversionInfo.class);

				CryptoInfo tmp = new CryptoInfo(generalInfo, conversionInfo);
				tmp.setCurrencyFrom(tmp.getConversionInfo().getCurrencyFrom());
				tmp.setCurrencyTo(tmp.getConversionInfo().getCurrencyTo());
				streamBuilder.accept(tmp);
				i++;

			}

			return streamBuilder.build();

		} catch (JsonSyntaxException | StringIndexOutOfBoundsException e) {
			throw new InvalidArgumentException(currencyTo, currenciesFrom[i]);
		}

	}

	/**
	 * gets a new instance of CryptoInfo with the following parameters
	 * 
	 * @param currencyTo   currency symbol to convert to
	 * @param currencyFrom currency symbol to convert from
	 * @return new instance of CryptoInfo
	 * @since Cryptos4J v1.0
	 */
	public static CryptoInfo getInstance(String currencyTo, String currencyFrom) {
		return new CryptoInfo(currencyTo, currencyFrom);
	}

	private CryptoInfo(String currencyTo, String currencyFrom) {
		this.currencyFrom = currencyFrom;
		this.currencyTo = currencyTo;
	}

	private CryptoInfo(GeneralInfo generalInfo, ConversionInfo conversionInfo) {
		this.generalInfo = generalInfo;
		this.conversionInfo = conversionInfo;
	}

	@Override
	public String getCurrencyFrom() {
		return this.currencyFrom;
	}

	@Override
	public void setCurrencyFrom(String currencyFrom) {
		this.currencyFrom = currencyFrom;
	}

	@Override
	public String getCurrencyTo() {
		return this.currencyTo;
	}

	@Override
	public void setCurrencyTo(String currencyTo) {
		this.currencyTo = currencyTo;
	}

	/**
	 * gets the <i>min-api.cryptocompare.com/data/generalinfo</i> end point url with
	 * parameters currencyTo, currencyFrom, and apiKey (if any)
	 * 
	 * @return URL end point this object connects to
	 */
	@Override
	public URL getURL() {
		return url;
	}

	/**
	 * returns GeneralInfo object equivalent to the 'GeneralInfo' json object
	 * returned by the <a href=
	 * "https://min-api.cryptocompare.com/documentation?key=Streaming&cat=coinsGeneralInfoEndpoint">Coins
	 * General Info</a>. end point.
	 * 
	 * @return generalInfo
	 */
	public GeneralInfo getGeneralInfo() {
		return generalInfo;
	}

	/**
	 * returns ConversionInfo object equivalent to the 'ConversionInfo' json object
	 * returned by the <a href=
	 * "https://min-api.cryptocompare.com/documentation?key=Streaming&cat=coinsGeneralInfoEndpoint">Coins
	 * General Info</a> end point.
	 * 
	 * @return conversionInfo
	 */
	public ConversionInfo getConversionInfo() {
		return conversionInfo;
	}

	/**
	 * updates url with parameters, then connects and updates this objects
	 * GeneralInfo and ConversionInfo aggregates with the returned json.
	 * 
	 * @see CryptoInfo#getConversionInfo()
	 * @see CryptoInfo#getGeneralInfo()
	 * @see CryptoInfo#getURL()
	 */
	@Override
	public void update() throws InvalidArgumentException {

		String json = "";
		try {

			url = new URL("https://min-api.cryptocompare.com/data/coin/generalinfo?fsyms=" + currencyFrom + "&tsym="
					+ currencyTo + "&extraParams=Cryptos4J");

			if (Util.getApiKey() != null)
				url = new URL(url + "&api_key=" + Util.getApiKey());

			Gson gson = new Gson();
			json = HttpsConnector.connect(url.toString());

			generalInfo = gson.fromJson(
					json.substring(json.indexOf("CoinInfo\":") + 10, json.indexOf(",\"ConversionInfo\"")),
					GeneralInfo.class);
			conversionInfo = gson.fromJson(
					json.substring(json.indexOf("\"ConversionInfo\":") + 17, json.indexOf("}}") + 1),
					ConversionInfo.class);

		} catch (JsonSyntaxException | IOException | StringIndexOutOfBoundsException e) {
			Matcher m = Pattern.compile("\"Message\":\"(.*?)\"").matcher(json);
			m.find();
			throw new InvalidArgumentException(m.group());
		}

	}

	/**
	 * @see GeneralInfo#dataToString()
	 * @see ConversionInfo#dataToString()
	 */
	@Override
	public String dataToString() {
		return generalInfo.dataToString() + "\n" + conversionInfo.dataToString();
	}

}
