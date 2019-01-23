import java.util.Iterator;

import cryptos.currencies.CryptocurrencyStreamer;

public class CryptoPricesExample {

	/**
	 * This Example demonstrates how to use the Single Symbol Price end point and
	 * the Multiple Symbols Price end point. 
	 * Take a look at Cryptocompare's Documentation for more info:
	 * 
	 * https://min-api.cryptocompare.com/documentation?key=Price&cat=SingleSymbolPriceEndpoint
	 * https://min-api.cryptocompare.com/documentation?key=Price&cat=multipleSymbolsPriceEndpoint
	 * 
	 * parameters: 
	 *   single crypto: 
	 *   fsym = BTC 
	 *   tsyms = USD, EUR
	 * 
	 * multi crypto: 
	 *   fsym = BTC, ETC 
	 *   tsyms = USD, EUR
	 */
	public static void main(String[] args) {

		System.out.println("Get single crypto prices:");
		String[] fiats = { "USD", "EUR" };

		Iterator<Double> it = CryptocurrencyStreamer.streamCryptoPrice("BTC", fiats).iterator();

		for (String f : fiats) {
			System.out.println(f + ": " + it.next());
		}

		System.out.println("\nGet multiple crypto prices:");
		String[] cryptos = { "BTC", "ETC" };

		it = CryptocurrencyStreamer.streamCryptoPrice(cryptos, fiats).iterator();

		int i = 0;
		while (it.hasNext()) {
			System.out.println(fiats[i++] + ": " + it.next());
			if (i == fiats.length)
				i = 0;
		}

	}

}
