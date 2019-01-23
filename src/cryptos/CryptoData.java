package cryptos;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import cryptos.exchanges.ExchangeStreamer;
import exceptions.InvalidArgumentException;
import utils.HttpsConnector;
import utils.Util;

/**
 * Wrapper class for RawData.
 * 
 * @author Matt - <a href="https://github.com/mcfadd">mcfadd</a>
 * @since Cryptos4J v1.0
 * @see RawData
 */
public final class CryptoData implements CryptoBaseExchange {

	// mutable fields
	private String currencyFrom;
	private String currencyTo;
	private ExchangeStreamer.exchanges exchange;

	// immutable fields
	private RawData rawData;
	private URL url;

	/**
	 * gets a new instance of CryptoData with the following parameters
	 * 
	 * @param currencyTo   currency symbol to convert to
	 * @param currencyFrom currency symbol to convert from
	 * @param exchange     exchange to get data from
	 * @return new instance of CryptoData
	 */
	public static CryptoData getInstance(String currencyTo, String currencyFrom, ExchangeStreamer.exchanges exchange) {
		return new CryptoData(currencyTo, currencyFrom, exchange);
	}

	private CryptoData(String currencyTo, String currencyFrom, ExchangeStreamer.exchanges exchange) {
		this.currencyFrom = currencyFrom;
		this.currencyTo = currencyTo;
		this.exchange = exchange;
	}

	@Override
	public ExchangeStreamer.exchanges getExchange() {
		return this.exchange;
	}

	@Override
	public void setExchange(ExchangeStreamer.exchanges exchange) {
		this.exchange = exchange;
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
	 * gets the <i>min-api.cryptocompare.com/data/generateAvg</i> end point url with
	 * parameters currencyTo, currencyFrom, exchange, and apiKey (if any)
	 * 
	 * @return URL end point this object connects to
	 */
	@Override
	public URL getURL() {
		return url;
	}

	/**
	 * returns RawData object equivalent to the 'RAW' json object returned by the
	 * <a href=
	 * "https://min-api.cryptocompare.com/documentation?key=Price&cat=generateAverageEndpoint">
	 * Generate Custom Average</a> end point.
	 * 
	 * @return rawData
	 */
	public RawData getRawData() {
		return rawData;
	}

	/**
	 * updates url with parameters, then connects and updates this objects RawData
	 * aggregate with the returned json.
	 * 
	 * @see CryptoData#getRawData()
	 * @see CryptoData#getURL()
	 */
	@Override
	public void update() throws InvalidArgumentException {

		String json = "";
		try {

			url = new URL("https://min-api.cryptocompare.com/data/generateAvg?fsym=" + currencyFrom + "&tsym="
					+ currencyTo + "&e=" + exchange + "&extraParams=Cryptos4J");

			if (Util.getApiKey() != null)
				url = new URL(url + "&api_key=" + Util.getApiKey());

			Gson gson = new Gson();
			json = HttpsConnector.connect(url.toString());

			rawData = gson.fromJson(json.substring(7, json.indexOf(",\"DISPLAY")), RawData.class);

		} catch (JsonSyntaxException | IOException | StringIndexOutOfBoundsException e) {
			Matcher m = Pattern.compile("\"Message\":\"(.*?)\"").matcher(json);
			m.find();
			throw new InvalidArgumentException(m.group());
		}

	}

	/**
	 * @see RawData#datatoString()
	 */
	@Override
	public String dataToString() {
		return rawData.datatoString();
	}

}
