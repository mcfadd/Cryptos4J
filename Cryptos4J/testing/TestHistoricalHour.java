
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Test;

import cryptos.exchanges.CryptoExchanges;
import cryptos.historical.HistoricalData;
import cryptos.historical.HistoricalHourly;
import cryptos.historical.TimestampedData;
import exceptions.InvalidArgumentException;
import exceptions.TimestampOutOfBoundsException;
import utils.Util;

public class TestHistoricalHour {

	@Test
	public void testHistoHourIntegrety() {

		System.out.println("Starting testHistoHourIntegrety()");

		TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
		Calendar inputCalendar = Calendar.getInstance(gmtTimeZone);
		HistoricalHourly histoHour = HistoricalData.generateHistoHourly("USDT", "BTC", CryptoExchanges.exchanges.Binance);
		int yearMin = 2018;
		int yearMax = inputCalendar.get(Calendar.YEAR);
		int monthMax = 11;
		int dayMax = 28;
		int hourMax = 22;
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
				int randomHour = ThreadLocalRandom.current().nextInt(0, hourMax + 1);
				int randomLimit = ThreadLocalRandom.current().nextInt(1, limitMax + 1);

				inputCalendar.set(randomYear, randomMonth, randomDay, randomHour, 0, 0);

				System.out.println("Iteration: " + i + "\nDate = " + Util.calendarToString(inputCalendar) + ", Limit = "
						+ randomLimit);

				histoHour.setLimit(randomLimit);
				histoHour.setLastUnixTimestamp(inputCalendar.getTimeInMillis() / 1000);
				histoHour.update();

				// check time from
				assertEquals(inputCalendar.getTimeInMillis() / 1000,
						histoHour.getTimeFrom() + histoHour.getLimit() * 60 * 60);
				// check time to
				assertEquals(inputCalendar.getTimeInMillis() / 1000, histoHour.getTimeTo());
				
				long timetoGet = histoHour.getTimeFrom();
				Iterator<TimestampedData> DataIterator = histoHour.getDataStream().iterator();

				while (DataIterator.hasNext()) {

					TimestampedData t = DataIterator.next();
					assertEquals(timetoGet, t.getTime());
					timetoGet += (60 * 60);

				}
			}

		} catch (AssertionError | InvalidArgumentException e) {
			System.out.println("Input Time: " + inputCalendar.getTimeInMillis() / 1000);
			System.out.println("Input Calendar: " + Util.calendarToString(inputCalendar));
			System.out.println("Time From: " + histoHour.getTimeFrom());
			System.out.println(
					"Calendar From: " + Util.calendarToString(Util.createGMTzoneCalendar(histoHour.getTimeFrom())));
			System.out.println("Time To: " + histoHour.getTimeTo());
			System.out.println("Calendar To: " + Util.calendarToString(Util.createGMTzoneCalendar(histoHour.getTimeTo())));
			System.out.println(histoHour.getURL());
			e.printStackTrace();
			assertTrue(false);
		}

	}

	@Test
	public void testHistoHourGetDataAtTime() {

		System.out.println("Starting testHistoHourGetDataAtTime()");

		TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
		Calendar inputCalendar = Calendar.getInstance(gmtTimeZone);
		HistoricalHourly histoHour = HistoricalData.generateHistoHourly("USDT", "BTC", CryptoExchanges.exchanges.Binance);
		int yearMin = 2018;
		int yearMax = inputCalendar.get(Calendar.YEAR);
		int monthMax = 11;
		int dayMax = 28;
		int hourMax = 22;
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
				int randomHour = ThreadLocalRandom.current().nextInt(0, hourMax + 1);
				int randomLimit = ThreadLocalRandom.current().nextInt(1, limitMax + 1);

				inputCalendar.set(randomYear, randomMonth, randomDay, randomHour, 0, 0);

				System.out.println("Iteration: " + i + "\nDate = " + Util.calendarToString(inputCalendar) + ", Limit = "
						+ randomLimit);

				histoHour.setLimit(randomLimit);
				histoHour.setLastUnixTimestamp(inputCalendar.getTimeInMillis() / 1000);
				histoHour.update();

				for (int j = 0; j < inneriteration; j++) {

					int randomNumHourstoAdd = -1 * (ThreadLocalRandom.current().nextInt(1, randomLimit + 1) - 1);
					inputCalendar.add(Calendar.HOUR, randomNumHourstoAdd);

					timestampData = histoHour.getDataAtTime(inputCalendar.getTimeInMillis() / 1000);
					assertEquals(timestampData.getTime(), inputCalendar.getTimeInMillis() / 1000);

					inputCalendar.add(Calendar.HOUR, -1 * randomNumHourstoAdd);
				}

			}

		} catch (AssertionError | InvalidArgumentException | TimestampOutOfBoundsException e) {
			System.out.println("Time to get: " + inputCalendar.getTimeInMillis() / 1000);
			System.out.println("Calendar to get: " + Util.calendarToString(inputCalendar));
			System.out.println("Time got: " + timestampData.getTime());
			System.out.println("Calendar got: " + Util.calendarToString(Util.createGMTzoneCalendar(timestampData.getTime())));
			System.out.println(histoHour.getURL());
			e.printStackTrace();
			assertTrue(false);
		}

	}

	@Test
	public void testNastyGetDataAtTime() {

		System.out.println("Starting testNastyGetDataAtTime()");

		TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
		Calendar inputCalendar = Calendar.getInstance(gmtTimeZone);
		HistoricalHourly histoHour = HistoricalData.generateHistoHourly("USDT", "BTC", CryptoExchanges.exchanges.Binance);
		int yearMin = 2018;
		int yearMax = inputCalendar.get(Calendar.YEAR);
		int monthMax = 11;
		int dayMax = 28;
		int hourMax = 22;
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
				int randomHour = ThreadLocalRandom.current().nextInt(0, hourMax + 1);
				int randomLimit = ThreadLocalRandom.current().nextInt(1, limitMax + 1);

				inputCalendar.set(randomYear, randomMonth, randomDay, randomHour, 0, 0);

				System.out.println("Iteration: " + i + "\nDate = " + Util.calendarToString(inputCalendar) + ", Limit = "
						+ randomLimit);

				histoHour.setLimit(randomLimit);
				histoHour.setLastUnixTimestamp(inputCalendar.getTimeInMillis() / 1000);
				histoHour.update();

				for (int j = 0; j < inneriteration; j++) {

					int randomNumHourtoAdd = -1 * (ThreadLocalRandom.current().nextInt(1, randomLimit + 1));
					int randomNumMintoAdd = (ThreadLocalRandom.current().nextInt(0, 59 + 1));
					int randomNumSectoAdd = (ThreadLocalRandom.current().nextInt(0, 59 + 1));

					inputCalendar.add(Calendar.HOUR, randomNumHourtoAdd); // add a random number of hours
					inputCalendar.add(Calendar.MINUTE, randomNumMintoAdd); // add a random number of minutes
					inputCalendar.add(Calendar.SECOND, randomNumSectoAdd); // add a random number of seconds

					timestampData = histoHour.getDataAtTime(inputCalendar.getTimeInMillis() / 1000);

					if ((offset = ((inputCalendar.getTimeInMillis() / 1000) % (60 * 60))) != 0) {
						offset = (60 * 60) - offset;
					}

					assertEquals(timestampData.getTime(), (inputCalendar.getTimeInMillis() / 1000) + offset);

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
			System.out.println(histoHour.getURL());
			e.printStackTrace();
			assertTrue(false);
		}

	}

	@Test
	public void testHistoHourAggregated() {

		System.out.println("Starting testHistoHourAggregated()");

		TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
		Calendar inputCalendar = Calendar.getInstance(gmtTimeZone);
		HistoricalHourly histoHour = HistoricalData.generateHistoHourly("USDT", "BTC", CryptoExchanges.exchanges.Binance);
		int yearMin = 2018;
		int yearMax = inputCalendar.get(Calendar.YEAR);
		int monthMax = 11;
		int dayMax = 28;
		int hourMax = 22;
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
				int randomHour = ThreadLocalRandom.current().nextInt(0, hourMax + 1);
				int randomLimit = ThreadLocalRandom.current().nextInt(1, limitMax + 1);
				int randomAggregate = ThreadLocalRandom.current().nextInt(1, aggregateMax + 1);

				inputCalendar.set(randomYear, randomMonth, randomDay, randomHour, 0, 0);

				System.out.println("Iteration: " + i + "\nDate = " + Util.calendarToString(inputCalendar) + ", Limit = "
						+ randomLimit + ", Aggregate = " + randomAggregate);

				histoHour.setLimit(randomLimit);
				histoHour.setAggregate(randomAggregate);
				histoHour.setLastUnixTimestamp(inputCalendar.getTimeInMillis() / 1000);
				histoHour.update();

				// check time from
				assertEquals(inputCalendar.getTimeInMillis() / 1000,
						histoHour.getTimeFrom() + 60 * 60 * histoHour.getLimit() * histoHour.getAggregate());
				
				// check time to
				assertEquals(inputCalendar.getTimeInMillis() / 1000, histoHour.getTimeTo());

				// check Data array
				long timetoGet = histoHour.getTimeFrom();
				Iterator<TimestampedData> DataIterator = histoHour.getDataStream().iterator();

				while (DataIterator.hasNext()) {

					TimestampedData t = DataIterator.next();
					assertEquals(timetoGet, t.getTime());
					timetoGet += (60 * 60 * histoHour.getAggregate());

				}

				// test getDataAtTime()
				try {

					for (int j = 0; j < inneriteration; j++) {

						int randomNumHourtoAdd = -1 * randomAggregate
								* (ThreadLocalRandom.current().nextInt(1, randomLimit + 1) - 1);
						inputCalendar.add(Calendar.HOUR, randomNumHourtoAdd);

						timestampData = histoHour.getDataAtTime(inputCalendar.getTimeInMillis() / 1000);
						assertEquals(timestampData.getTime(), inputCalendar.getTimeInMillis() / 1000);

						inputCalendar.add(Calendar.HOUR, -1 * randomNumHourtoAdd);
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
			System.out.println("Time From: " + histoHour.getTimeFrom());
			System.out.println(
					"Calendar From: " + Util.calendarToString(Util.createGMTzoneCalendar(histoHour.getTimeFrom())));
			System.out.println("Time To: " + histoHour.getTimeTo());
			System.out.println("Calendar To: " + Util.calendarToString(Util.createGMTzoneCalendar(histoHour.getTimeTo())));
			System.out.println(histoHour.getURL());
			e.printStackTrace();
			assertTrue(false);
		}

	}

}
