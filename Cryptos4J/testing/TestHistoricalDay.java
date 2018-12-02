
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Test;

import cryptos.exchanges.CryptoExchanges;
import cryptos.historical.HistoricalDaily;
import cryptos.historical.HistoricalData;
import cryptos.historical.TimestampedData;
import exceptions.InvalidArgumentException;
import exceptions.TimestampOutOfBoundsException;
import utils.Util;

public class TestHistoricalDay {

	@Test
	public void testHistoDayIntegrety() {

		System.out.println("Starting testHistoDayIntegrety()");

		TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
		Calendar inputCalendar = Calendar.getInstance(gmtTimeZone);
		HistoricalDaily histoDay = HistoricalData.generateHistoDaily("USDT", "BTC", CryptoExchanges.exchanges.Binance);
		int yearMin = 2018;
		int yearMax = inputCalendar.get(Calendar.YEAR);
		int monthMax = 11;
		int dayMax = 28;
		int limitMax = 50;
		int iterations = 250;

		try {

			for (int i = 0; i < iterations; i++) {

				// generate random construction data
				// this is to prevent dates in the future
				int randomYear;
				if ((randomYear = ThreadLocalRandom.current().nextInt(yearMin, yearMax + 1)) == yearMax) {
					monthMax = Calendar.getInstance().get(Calendar.MONTH);
					dayMax = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1;
				}
				int randomMonth = ThreadLocalRandom.current().nextInt(0, monthMax + 1);
				int randomDay = ThreadLocalRandom.current().nextInt(0, dayMax + 1);
				int randomLimit = ThreadLocalRandom.current().nextInt(1, limitMax + 1);

				inputCalendar.set(randomYear, randomMonth, randomDay, 0, 0, 0);

				System.out.println("Iteration: " + i + "\nDate = " + Util.calendarToString(inputCalendar) + ", Limit = "
						+ randomLimit);

				histoDay.setLimit(randomLimit);
				histoDay.setLastUnixTimestamp(inputCalendar.getTimeInMillis() / 1000);
				histoDay.update();

				// check time from
				assertEquals(inputCalendar.getTimeInMillis() / 1000,
						histoDay.getTimeFrom() + histoDay.getLimit() * 24 * 60 * 60);

				// check time to
				assertEquals(inputCalendar.getTimeInMillis() / 1000, histoDay.getTimeTo());

				// check Data array
				long timetoGet = histoDay.getTimeFrom();

				Iterator<TimestampedData> DataIterator = histoDay.getDataStream().iterator();

				while (DataIterator.hasNext()) {

					TimestampedData t = DataIterator.next();
					assertEquals(timetoGet, t.getTime());
					timetoGet += (24 * 60 * 60);
				}

			}

		} catch (AssertionError | InvalidArgumentException e) {
			System.out.println("Input Time: " + inputCalendar.getTimeInMillis() / 1000);
			System.out.println("Input Calendar: " + Util.calendarToString(inputCalendar));
			System.out.println("Time From: " + histoDay.getTimeFrom());
			System.out.println(
					"Calendar From: " + Util.calendarToString(Util.createGMTzoneCalendar(histoDay.getTimeFrom())));
			System.out.println("Time To: " + histoDay.getTimeTo());
			System.out.println("Calendar To: " + Util.calendarToString(Util.createGMTzoneCalendar(histoDay.getTimeTo())));
			System.out.println(histoDay.getURL());
			e.printStackTrace();
			assertTrue(false);
		}

	}

	@Test
	public void testHistoDayGetDataAtTime() {

		System.out.println("Starting testHistoDayGetDataAtTime()");

		TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
		Calendar inputCalendar = Calendar.getInstance(gmtTimeZone);
		HistoricalDaily histoDay = HistoricalData.generateHistoDaily("USDT", "BTC", CryptoExchanges.exchanges.Binance);
		int yearMin = 2018;
		int yearMax = inputCalendar.get(Calendar.YEAR);
		int monthMax = 11;
		int dayMax = 28;
		int limitMax = 50;
		int outteriterations = 100;
		int inneriteration = 50;
		TimestampedData timestampData = null;

		try {

			for (int i = 0; i < outteriterations; i++) {

				// generate random construction data
				// this is to prevent dates in the future
				int randomYear;
				if ((randomYear = ThreadLocalRandom.current().nextInt(yearMin, yearMax + 1)) == yearMax) {
					monthMax = Calendar.getInstance().get(Calendar.MONTH);
					dayMax = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1;
				}
				int randomMonth = ThreadLocalRandom.current().nextInt(0, monthMax + 1);
				int randomDay = ThreadLocalRandom.current().nextInt(0, dayMax + 1);
				int randomLimit = ThreadLocalRandom.current().nextInt(1, limitMax + 1);

				inputCalendar.set(randomYear, randomMonth, randomDay, 0, 0, 0);

				System.out.println("Iteration: " + i + "\nDate = " + Util.calendarToString(inputCalendar) + ", Limit = "
						+ randomLimit);

				histoDay.setLimit(randomLimit);
				histoDay.setLastUnixTimestamp(inputCalendar.getTimeInMillis() / 1000);
				histoDay.update();

				for (int j = 0; j < inneriteration; j++) {

					int randomNumDaytoAdd = -1 * (ThreadLocalRandom.current().nextInt(1, randomLimit + 1) - 1);
					inputCalendar.add(Calendar.DAY_OF_YEAR, randomNumDaytoAdd);

					timestampData = histoDay.getDataAtTime(inputCalendar.getTimeInMillis() / 1000); //
					assertEquals(timestampData.getTime(), inputCalendar.getTimeInMillis() / 1000);

					inputCalendar.add(Calendar.DAY_OF_YEAR, -1 * randomNumDaytoAdd);
				}

			}

		} catch (AssertionError | InvalidArgumentException | TimestampOutOfBoundsException e) {
			System.out.println("Time to get: " + inputCalendar.getTimeInMillis() / 1000);
			System.out.println("Calendar to get: " + Util.calendarToString(inputCalendar));
			System.out.println("Time got: " + timestampData.getTime());
			System.out.println("Calendar got: " + Util.calendarToString(Util.createGMTzoneCalendar(timestampData.getTime())));
			System.out.println(histoDay.getURL());
			e.printStackTrace();
			assertTrue(false);
		}

	}

	@Test
	public void testNastyGetDataAtTime() {

		System.out.println("Starting testNastyGetDataAtTime()");

		TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
		Calendar inputCalendar = Calendar.getInstance(gmtTimeZone);
		HistoricalDaily histoDay = HistoricalData.generateHistoDaily("USDT", "BTC", CryptoExchanges.exchanges.Binance);
		int yearMin = 2018;
		int yearMax = inputCalendar.get(Calendar.YEAR);
		int monthMax = 11;
		int dayMax = 28;
		int limitMax = 50;
		int outteriterations = 100;
		int inneriteration = 50;
		TimestampedData timestampData = null;
		long offset = 0;

		try {

			for (int i = 0; i < outteriterations; i++) {

				// generate random construction data
				// this is to prevent dates in the future
				int randomYear;
				if ((randomYear = ThreadLocalRandom.current().nextInt(yearMin, yearMax + 1)) == yearMax) {
					monthMax = Calendar.getInstance().get(Calendar.MONTH);
					dayMax = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1;
				}
				int randomMonth = ThreadLocalRandom.current().nextInt(0, monthMax + 1);
				int randomDay = ThreadLocalRandom.current().nextInt(0, dayMax + 1);
				int randomLimit = ThreadLocalRandom.current().nextInt(1, limitMax + 1);

				inputCalendar.set(randomYear, randomMonth, randomDay, 0, 0, 0);

				System.out.println("Iteration: " + i + "\nDate = " + Util.calendarToString(inputCalendar) + ", Limit = "
						+ randomLimit);

				histoDay.setLimit(randomLimit);
				histoDay.setLastUnixTimestamp(inputCalendar.getTimeInMillis() / 1000);
				histoDay.update();

				Calendar tmpCal = Calendar.getInstance(gmtTimeZone);
				for (int j = 0; j < inneriteration; j++) {

					int randomNumDaytoAdd = -1 * (ThreadLocalRandom.current().nextInt(1, randomLimit + 1));
					int randomNumHourtoAdd = (ThreadLocalRandom.current().nextInt(1, 22 + 1));
					int randomNumMintoAdd = (ThreadLocalRandom.current().nextInt(0, 59 + 1));
					int randomNumSectoAdd = (ThreadLocalRandom.current().nextInt(0, 59 + 1));

					inputCalendar.add(Calendar.DAY_OF_YEAR, randomNumDaytoAdd); // subtract a random number of days
					inputCalendar.add(Calendar.HOUR, randomNumHourtoAdd); // add a random number of hours
					inputCalendar.add(Calendar.MINUTE, randomNumMintoAdd); // add a random number of minutes
					inputCalendar.add(Calendar.SECOND, randomNumSectoAdd); // add a random number of seconds

					timestampData = histoDay.getDataAtTime(inputCalendar.getTimeInMillis() / 1000);

					if ((offset = ((inputCalendar.getTimeInMillis() / 1000) % (60 * 60 * 24))) != 0) {
						offset = (24 * 60 * 60) - offset;
					}

					assertEquals(timestampData.getTime(), (inputCalendar.getTimeInMillis() / 1000) + offset);

					inputCalendar.add(Calendar.DAY_OF_YEAR, 1);
					tmpCal.setTimeInMillis(timestampData.getTime() * 1000);
					assertEquals(tmpCal.get(Calendar.DAY_OF_YEAR), inputCalendar.get(Calendar.DAY_OF_YEAR));

					inputCalendar.add(Calendar.DAY_OF_YEAR, -1 * (randomNumDaytoAdd + 1)); // restore number of days
																							// plus one
					inputCalendar.add(Calendar.HOUR, -1 * randomNumHourtoAdd); // restore number of hours
					inputCalendar.add(Calendar.MINUTE, -1 * randomNumMintoAdd); // restore number of minutes
					inputCalendar.add(Calendar.SECOND, -1 * randomNumSectoAdd); // restore number of seconds

				}

			}

		} catch (AssertionError | InvalidArgumentException | TimestampOutOfBoundsException e) {
			System.out.println("Time to get: " + inputCalendar.getTimeInMillis() / 1000);
			System.out.println("Calendar to get: " + Util.calendarToString(inputCalendar));
			System.out.println("Time got: " + timestampData.getTime());
			System.out.println("Calendar got: " + Util.calendarToString(Util.createGMTzoneCalendar(timestampData.getTime())));
			System.out.println("offset: " + offset);
			System.out.println(histoDay.getURL());
			e.printStackTrace();
			assertTrue(false);
		}

	}

	@Test
	public void testHistoDayAggregated() {

		System.out.println("Starting testHistoDayAggregated()");

		TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
		Calendar inputCalendar = Calendar.getInstance(gmtTimeZone);
		HistoricalDaily histoDay = HistoricalData.generateHistoDaily("USDT", "BTC", CryptoExchanges.exchanges.Binance);
		int yearMin = 2018;
		int yearMax = inputCalendar.get(Calendar.YEAR);
		int monthMax = 11;
		int dayMax = 28;
		int limitMax = 50;
		int aggregateMax = 20;
		int iterations = 100;
		int inneriteration = 50;
		TimestampedData timestampData = null;

		try {

			for (int i = 0; i < iterations; i++) {

				// generate random construction data
				// this is to prevent dates in the future
				int randomYear;
				if ((randomYear = ThreadLocalRandom.current().nextInt(yearMin, yearMax + 1)) == yearMax) {
					monthMax = Calendar.getInstance().get(Calendar.MONTH);
					dayMax = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1;
				}
				int randomMonth = ThreadLocalRandom.current().nextInt(0, monthMax + 1);
				int randomDay = ThreadLocalRandom.current().nextInt(0, dayMax + 1);
				int randomLimit = ThreadLocalRandom.current().nextInt(1, limitMax + 1);
				int randomAggregate = ThreadLocalRandom.current().nextInt(1, aggregateMax + 1);

				inputCalendar.set(randomYear, randomMonth, randomDay, 0, 0, 0);

				System.out.println("Iteration: " + i + "\nDate = " + Util.calendarToString(inputCalendar) + ", Limit = "
						+ randomLimit + ", Aggregate = " + randomAggregate);

				histoDay.setLimit(randomLimit);
				histoDay.setAggregate(randomAggregate);
				histoDay.setLastUnixTimestamp(inputCalendar.getTimeInMillis() / 1000);
				histoDay.update();

				// check time from
				assertEquals(inputCalendar.getTimeInMillis() / 1000,
						histoDay.getTimeFrom() + 24 * 60 * 60 * histoDay.getLimit() * histoDay.getAggregate());

				// check time to
				assertEquals(inputCalendar.getTimeInMillis() / 1000, histoDay.getTimeTo());

				// check Data array
				long timetoGet = histoDay.getTimeFrom();

				Iterator<TimestampedData> DataIterator = histoDay.getDataStream().iterator();

				while (DataIterator.hasNext()) {

					TimestampedData t = DataIterator.next();
					assertEquals(timetoGet, t.getTime());
					timetoGet += (24 * 60 * 60 * histoDay.getAggregate());

				}

				// test getDataAtTime()
				try {

					for (int j = 0; j < inneriteration; j++) {

						int randomNumDaytoAdd = -1 * randomAggregate
								* (ThreadLocalRandom.current().nextInt(1, randomLimit + 1) - 1);
						inputCalendar.add(Calendar.DAY_OF_YEAR, randomNumDaytoAdd);

						timestampData = histoDay.getDataAtTime(inputCalendar.getTimeInMillis() / 1000);
						assertEquals(timestampData.getTime(), inputCalendar.getTimeInMillis() / 1000);

						inputCalendar.add(Calendar.DAY_OF_YEAR, -1 * randomNumDaytoAdd);
					}

				} catch (AssertionError e) {
					System.out.println("Time to get: " + inputCalendar.getTimeInMillis() / 1000);
					System.out.println("Calendar to get: " + Util.calendarToString(inputCalendar));
					System.out.println("Time got: " + timestampData.getTime());
					System.out.println(
							"Calendar got: " + Util.calendarToString(Util.createGMTzoneCalendar(timestampData.getTime())));
					e.printStackTrace();
					assertTrue(false);
				}

			}

		} catch (AssertionError | InvalidArgumentException | TimestampOutOfBoundsException e) {
			System.out.println("Input Time: " + inputCalendar.getTimeInMillis() / 1000);
			System.out.println("Input Calendar: " + Util.calendarToString(inputCalendar));
			System.out.println("Time From: " + histoDay.getTimeFrom());
			System.out.println("Round Time From: " + histoDay.getTimeFrom());
			System.out.println(
					"Calendar From: " + Util.calendarToString(Util.createGMTzoneCalendar(histoDay.getTimeFrom())));
			System.out.println("Time To: " + histoDay.getTimeTo());
			System.out.println("Round Time To: " + histoDay.getTimeTo());
			System.out.println("Calendar To: " + Util.calendarToString(Util.createGMTzoneCalendar(histoDay.getTimeTo())));
			System.out.println(histoDay.getURL());
			e.printStackTrace();
			assertTrue(false);
		}

	}
}
