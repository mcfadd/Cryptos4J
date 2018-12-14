import java.util.Iterator;
import java.util.Map.Entry;

import cryptos.exchanges.ExchangeStreamer;
import cryptos.exchanges.Exchange;

public class StreamExchangeExample {

	/**
	 * This Example demonstrates how to use the All the Exchanges and Trading Pairs
	 * end point to extract a specific exchange. Take a look at Cryptocompare's
	 * Documentation for more info:
	 * 
	 * https://min-api.cryptocompare.com/documentation?key=Other&cat=allExchangesEndpoint
	 * 
	 * parameters: 
	 *   exchange = Binance
	 */
	public static void main(String[] args) {

		Iterator<Exchange> it = ExchangeStreamer.streamExchanges(ExchangeStreamer.exchanges.Binance).iterator();
		while (it.hasNext()) {

			Exchange exchange = it.next();
			Iterator<Entry<String, String[]>> it2 = exchange.getPairs().entrySet().iterator();
			System.out.println(exchange.getName() + ":\n");

			while (it2.hasNext()) {

				Entry<String, String[]> entry = it2.next();
				System.out.print("    " + entry.getKey() + ": [");
				String[] coins = entry.getValue();

				for (int i = 0; i < coins.length; i++) {
					System.out.print(coins[i] + (i == (coins.length - 1) ? "" : ", "));
				}
				System.out.println("]");
			}
		}

		// stream all exchanges (warning very long output stream)

//		Iterator<Exchange> it3 = ExchangeStreamer.streamExchanges(ExchangeStreamer.exchanges.Binance).iterator();
//		while (it3.hasNext()) {
//
//			Exchange exchange = it3.next();
//			Iterator<Entry<String, String[]>> it4 = exchange.getPairs().entrySet().iterator();
//			System.out.println(exchange.getName() + ":\n");
//
//			while (it4.hasNext()) {
//
//				Entry<String, String[]> entry = it4.next();
//				System.out.print("    " + entry.getKey() + ": [");
//				String[] coins = entry.getValue();
//
//				for (int i = 0; i < coins.length; i++) {
//					System.out.print(coins[i] + (i == (coins.length - 1) ? "" : ", "));
//				}
//				System.out.println("]");
//			}
//		}
	}

}
