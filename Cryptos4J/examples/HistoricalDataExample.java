import java.util.Calendar;
import java.util.TimeZone;

import cryptos.exchanges.CryptoExchanges;
import cryptos.historical.HistoricalDaily;
import cryptos.historical.HistoricalData;
import exceptions.InvalidArgumentException;
import exceptions.TimestampOutOfBoundsException;

public class HistoricalDataExample {

	public static void main(String[] args) {

		try {

			// default example
			
			HistoricalDaily histoDay = HistoricalData.generateHistoDaily("USDT", "BTC",
					CryptoExchanges.exchanges.Binance);
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
			
			// get data at timestamp example
			
			System.out.println("Input timestamp (2018/5/11): " + inputCalendar.getTimeInMillis() / 1000 + "\n" + histoDay.getDataAtTime(inputCalendar.getTimeInMillis() / 1000).datatoString());
			

		} catch (InvalidArgumentException | TimestampOutOfBoundsException e) {
			e.printStackTrace();
		}

	}

}
