
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Stream;

import cryptos.exchanges.ExchangeStreamer;
import cryptos.historical.HistoricalData;
import cryptos.historical.TimestampedData;
import cryptos.historical.HistoricalMinute;
import exceptions.InvalidArgumentException;

public class HistoricalDataStreamExample {

	/**
	 * This Example demonstrates how to use the Historical Daily OHLCV end point to
	 * stream TimestampedData. The same approach is used for Historical Hourly OHLCV
	 * and Historical Minute OHLCV Take a look at Cryptocompare's Documentation for
	 * more info:
	 * 
	 * https://min-api.cryptocompare.com/documentation?key=Historical&cat=dataHistoday
	 * 
	 * parameters: 
	 *   fsym = BTC 
	 *   tsym = USDT 
	 *   e = Binance
	 */
	public static void main(String[] args) {

		try {

			System.out.println("Sorted TimestampedData by high of day:");

			HistoricalMinute histoMinute = HistoricalData.generateHistoMinute("USDT", "BTC",
					ExchangeStreamer.exchanges.Binance);
			histoMinute.update();

			Stream<TimestampedData> steam = histoMinute.getDataStream();
			steam = steam.sorted(new Comparator<TimestampedData>() {

				@Override
				public int compare(TimestampedData t1, TimestampedData t2) {
					if (t1.getHigh() < t2.getHigh())
						return -1;
					else if (t1.getHigh() == t2.getHigh())
						return 0;
					else
						return 1;
				}

			});

			Iterator<TimestampedData> it = steam.iterator();
			while (it.hasNext()) {
				System.out.print(it.next().datatoString());
			}

		} catch (InvalidArgumentException e) {
			System.out.println(e.getMessage());
		}

	}

}
