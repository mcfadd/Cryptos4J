import java.util.Calendar;
import java.util.TimeZone;

import cryptos.exchanges.ExchangeStreamer;
import cryptos.historical.HistoricalDaily;
import cryptos.historical.HistoricalData;
import exceptions.InvalidArgumentException;
import exceptions.TimestampOutOfBoundsException;

public class HistoricalDataExample {

	/**
	 * This Example demonstrates how to use the Historical Daily OHLCV end point.
	 * The same approach is used for Historical Hourly OHLCV and Historical Minute OHLCV
	 * Take a look at Cryptocompare's Documentation for more info:
	 * 
	 * https://min-api.cryptocompare.com/documentation?key=Historical&cat=dataHistoday
	 * 
	 * parameters:
	 * 	fsym 	= BTC
	 * 	tsym 	= USDT
	 * 	e 		= Binance
	 */
	public static void main(String[] args) {

		try {

			// default example
			
			HistoricalDaily histoDay = HistoricalData.generateHistoDaily("USDT", "BTC",
					ExchangeStreamer.exchanges.Binance);
			histoDay.update();
			System.out.print("Default HistoricalDay:\n\n" + histoDay.dataToString());
			
			// aggregate example
			
			Calendar inputCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			inputCalendar.set(2018, 5, 5, 0, 0, 0);
			histoDay.setLastUnixTimestamp(inputCalendar.getTimeInMillis() / 1000);
			histoDay.setLimit(10);
			histoDay.setAggregate(2);
			histoDay.update();
			
			inputCalendar.add(Calendar.DAY_OF_YEAR, -5);
			System.out.print("Aggregated HistoricalDay:\n\n" + histoDay.dataToString());
			
			// get data at timestamp example - note the data is aggregated so the TimestampedData returned has a timestamp that is greater than the input timestamp.
			
			System.out.println("Input timestamp: " + inputCalendar.getTimeInMillis() / 1000 + "\n" + histoDay.getDataAtTime(inputCalendar.getTimeInMillis() / 1000).datatoString());
			

		} catch (InvalidArgumentException | TimestampOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}

	}

}
