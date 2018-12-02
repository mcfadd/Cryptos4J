import java.util.Iterator;
import java.util.Map.Entry;

import cryptos.exchanges.CryptoExchanges;
import cryptos.exchanges.Exchange;

public class StreamExchangeExample {

	public static void main(String[] args) {

		Iterator<Exchange> it = CryptoExchanges.streamExchanges(CryptoExchanges.exchanges.Binance).iterator();
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

	}

}
