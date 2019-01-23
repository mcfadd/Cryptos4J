import java.util.Iterator;

import cryptos.currencies.CryptocurrencyStreamer;
import cryptos.currencies.Cryptocurrency;

public class StreamCurrenciesExample {

	/**
	 * This Example demonstrates how to use the All the Coins end point to extract a
	 * specific cryptocurrency. 
	 * Take a look at Cryptocompare's Documentation for more info:
	 * 
	 * https://min-api.cryptocompare.com/documentation?key=Other&cat=allCoinsWithContentEndpoint
	 * 
	 * parameters: 
	 *   symbols = BTC
	 */
	public static void main(String[] args) {

		Iterator<Cryptocurrency> it = CryptocurrencyStreamer.streamCurrencies("BTC").iterator();
		while (it.hasNext()) {

			Cryptocurrency currency = it.next();

			System.out.println(currency.dataToString());

		}

		// stream all cryptocurrencies (warning very long output stream)

//		Iterator<Cryptocurrency> it2 = CryptocurrencyStreamer.streamAllCurrencies().iterator();
//		while (it2.hasNext()) {
//
//			Cryptocurrency currency = it2.next();
//			
//			System.out.println(currency.dataToString());
//
//		}

	}

}
