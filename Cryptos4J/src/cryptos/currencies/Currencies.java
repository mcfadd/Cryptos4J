package cryptos.currencies;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import com.google.gson.Gson;

import utils.HttpsConnector;

/**
 * Class for streaming Cryptocurrency objects from the <i>min-api.cryptocompare.com/data/all/coinlist</i> end point<br>
 * and cryptocurrency to fiat prices from the <i>min-api.cryptocompare.com/data/pricemulti</i> end point.
 * 
 * @author Matt - github.com/mcfadd
 * @since Cryptos4J v1.0
 * @see Cryptocurrency
 */
public class Currencies {

	/**
	 * streams Cryptocurrency objects in the order given by connecting to the <i>min-api.cryptocompare.com/data/all/coinlist</i> end point.
	 * @param symbols - array of Cryptocurrency symbols to stream 
	 * @return stream of Cryptocurrency objects
	 * @since Cryptos4J v1.0
	 */
	public static Stream<Cryptocurrency> streamCurrencies(String... symbols) {

		String json = HttpsConnector
				.connect("https://min-api.cryptocompare.com/data/all/coinlist?extraParams=Cryptos4J");
		Builder<Cryptocurrency> streamBuilder = Stream.builder();
		Gson gson = new Gson();

		for (int i = 0; i < symbols.length; i++) {

			Matcher entryMatcher = Pattern.compile("\"" + symbols[i] + "\":\\{(.*?)\\}").matcher(json);

			if (entryMatcher.find()) {

				Matcher currencyMatcher = Pattern.compile("\\{(.*?)\\}").matcher(entryMatcher.group());
				currencyMatcher.find();

				streamBuilder.accept(gson.fromJson(currencyMatcher.group(), Cryptocurrency.class));
			}
		}

		return streamBuilder.build();

	}

	/**
	 * streams all Cryptocurrency objects by connecting to the <i>min-api.cryptocompare.com/data/all/coinlist</i> end point.
	 * @return stream of all Cryptocurrency objects
	 * @since Cryptos4J v.10
	 */
	public static Stream<Cryptocurrency> streamAllCurrencies() {

		String json = HttpsConnector
				.connect("https://min-api.cryptocompare.com/data/all/coinlist?extraParams=Cryptos4J");
		Builder<Cryptocurrency> streamBuilder = Stream.builder();
		Gson gson = new Gson();

		Matcher entryMatcher = Pattern.compile("\"([a-zA-Z0-9])+\":\\{(.*?)\\}").matcher(json);
		entryMatcher.find(); // finds "Data:{"
		while (entryMatcher.find()) {

			Matcher currencyMatcher = Pattern.compile("\\{(.*?)\\}").matcher(entryMatcher.group());
			currencyMatcher.find();

			streamBuilder.accept(gson.fromJson(currencyMatcher.group(), Cryptocurrency.class));
		}

		return streamBuilder.build();

	}

	/**
	 * streams Cryptocurrency fiat prices in the order in which the fiats are listed
	 * @param cryptoFrom - cryptocurrency symbol to convert from
	 * @param fiatsTo - array of fiat currencies to convert to
	 * @return stream of fiat prices
	 * @since Cryptos4J v1.0
	 */
	public static Stream<Double> streamCryptoPrice(String cryptoFrom, String... fiatsTo) {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < fiatsTo.length; i++) {

			if (i == fiatsTo.length - 1)
				sb.append(fiatsTo[i]);
			else
				sb.append(fiatsTo[i] + ",");

		}
		
		String json = HttpsConnector
				.connect("https://min-api.cryptocompare.com/data/price?fsym=" + cryptoFrom + "&tsyms=" + sb.toString() + "&extraParams=Cryptos4J");

		Builder<Double> streamBuilder = Stream.builder();
		Matcher priceMatcher = Pattern.compile(":[0-9]+\\.[0-9]+").matcher(json);
		
		while(priceMatcher.find()) {
			streamBuilder.accept(Double.parseDouble(priceMatcher.group().substring(1)));
		}
		
		return streamBuilder.build();

	}
	
	/**
	 * streams multiple Cryptocurrency/fiat pair prices in the order in which they are listed
	 * @param cryptosFrom - array of cryptocurrency symbols to convert from
	 * @param fiatsTo - array of fiat currencies to convert to
	 * @return stream of fiat prices
	 * @since Cryptos4J v1.0
	 */
	public static Stream<Double> streamCryptoPrice(String[] cryptosFrom, String... fiatsTo) {

		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		
		for (int i = 0; i < fiatsTo.length; i++) {

			if (i == fiatsTo.length - 1)
				sb1.append(fiatsTo[i]);
			else
				sb1.append(fiatsTo[i] + ",");

		}
		
		for (int i = 0; i < cryptosFrom.length; i++) {

			if (i == cryptosFrom.length - 1)
				sb2.append(cryptosFrom[i]);
			else
				sb2.append(cryptosFrom[i] + ",");

		}
		
		String json = HttpsConnector
				.connect("https://min-api.cryptocompare.com/data/pricemulti?fsyms=" + sb2.toString() + "&tsyms=" + sb1.toString() + "&extraParams=Cryptos4J");

		Builder<Double> streamBuilder = Stream.builder();
		Matcher priceMatcher = Pattern.compile(":[0-9]+\\.[0-9]+").matcher(json);
		
		while(priceMatcher.find()) {
			streamBuilder.accept(Double.parseDouble(priceMatcher.group().substring(1)));
		}
		
		return streamBuilder.build();

	}

}
