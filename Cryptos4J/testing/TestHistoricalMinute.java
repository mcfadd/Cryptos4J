
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

import cryptos.exchanges.ExchangeStreamer;
import cryptos.historical.HistoricalData;
import cryptos.historical.HistoricalMinute;
import cryptos.historical.TimestampedData;
import exceptions.InvalidArgumentException;
import exceptions.TimestampOutOfBoundsException;
import utils.Util;

public class TestHistoricalMinute {

	@Test
	public void testHistoHourIntegrety() {

		System.out.println("Starting testHistoHourIntegrety()");

		TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
		Calendar inputCalendar = Calendar.getInstance(gmtTimeZone);
		HistoricalMinute histoMinute = HistoricalData.generateHistoMinute("USDT", "BTC",
				ExchangeStreamer.exchanges.Binance);
		int dayMax = 5;
		int hourMax = 22;
		int minuteMax = 59;
		int limitMax = 50;
		int iterations = 250;

		try {

			for (int i = 0; i < iterations; i++) {

				// generate random construction data
				int randomDay = ThreadLocalRandom.current().nextInt(0, dayMax + 1);
				int randomHour = ThreadLocalRandom.current().nextInt(0, hourMax + 1);
				int randomMinute = ThreadLocalRandom.current().nextInt(0, minuteMax + 1);
				int randomLimit = ThreadLocalRandom.current().nextInt(1, limitMax + 1);

				inputCalendar = Calendar.getInstance(gmtTimeZone);
				inputCalendar.set(Calendar.SECOND, 0);
				inputCalendar.set(Calendar.MILLISECOND, 0);
				inputCalendar.add(Calendar.DAY_OF_MONTH, -1 * randomDay);
				inputCalendar.add(Calendar.HOUR, -1 * randomHour);
				inputCalendar.add(Calendar.MINUTE, -1 * randomMinute);

				System.out.println("Iteration: " + i + "\nDate = " + Util.calendarToString(inputCalendar) + ", Limit = "
						+ randomLimit);

				histoMinute.setLimit(randomLimit);
				histoMinute.setLastUnixTimestamp(inputCalendar.getTimeInMillis() / 1000);
				histoMinute.update();

				// check time from
				assertEquals(inputCalendar.getTimeInMillis() / 1000,
						histoMinute.getTimeFrom() + histoMinute.getLimit() * 60);

				// check time to
				assertEquals(inputCalendar.getTimeInMillis() / 1000, histoMinute.getTimeTo());

				long timetoGet = histoMinute.getTimeFrom();

				Iterator<TimestampedData> DataIterator = histoMinute.getDataStream().iterator();

				while (DataIterator.hasNext()) {

					TimestampedData t = DataIterator.next();
					assertEquals(timetoGet, t.getTime());
					timetoGet += 60;

				}

			}

		} catch (AssertionError | InvalidArgumentException e) {
			System.out.println("url: " + histoMinute.getURL().toString());
			System.out.println("Input Time: " + inputCalendar.getTimeInMillis() / 1000);
			System.out.println("Input Calendar: " + Util.calendarToString(inputCalendar));
			System.out.println("Time From: " + histoMinute.getTimeFrom());
			System.out.println(
					"Calendar From: " + Util.calendarToString(Util.createGMTzoneCalendar(histoMinute.getTimeFrom())));
			System.out.println("Time To: " + histoMinute.getTimeTo());
			System.out
					.println("Calendar To: " + Util.calendarToString(Util.createGMTzoneCalendar(histoMinute.getTimeTo())));
			System.out.println(histoMinute.getURL());
			e.printStackTrace();
			assertTrue(false);
		}

	}

	@Test
	public void testHistoMinuteGetDataAtTime() {

		System.out.println("Starting testHistoMinuteGetDataAtTime()");

		TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
		Calendar inputCalendar = Calendar.getInstance(gmtTimeZone);
		HistoricalMinute histoMinute = HistoricalData.generateHistoMinute("USDT", "BTC",
				ExchangeStreamer.exchanges.Binance);
		int dayMax = 5;
		int hourMax = 22;
		int minuteMax = 59;
		int limitMax = 50;
		int outteriterations = 100;
		int inneriteration = 50;
		TimestampedData timestampData = null;

		try {

			for (int i = 0; i < outteriterations; i++) {

				// generate random construction data
				int randomDay = ThreadLocalRandom.current().nextInt(0, dayMax + 1);
				int randomHour = ThreadLocalRandom.current().nextInt(0, hourMax + 1);
				int randomMinute = ThreadLocalRandom.current().nextInt(0, minuteMax + 1);
				int randomLimit = ThreadLocalRandom.current().nextInt(1, limitMax + 1);

				inputCalendar = Calendar.getInstance(gmtTimeZone);
				inputCalendar.set(Calendar.SECOND, 0);
				inputCalendar.set(Calendar.MILLISECOND, 0);
				inputCalendar.add(Calendar.DAY_OF_MONTH, -1 * randomDay);
				inputCalendar.add(Calendar.HOUR, -1 * randomHour);
				inputCalendar.add(Calendar.MINUTE, -1 * randomMinute);

				System.out.println("Iteration: " + i + "\nDate = " + Util.calendarToString(inputCalendar) + ", Limit = "
						+ randomLimit);

				histoMinute.setLimit(randomLimit);
				histoMinute.setLastUnixTimestamp(inputCalendar.getTimeInMillis() / 1000);
				histoMinute.update();

				for (int j = 0; j < inneriteration; j++) {

					int randomNumMinutetoAdd = -1 * (ThreadLocalRandom.current().nextInt(1, randomLimit + 1) - 1);
					inputCalendar.add(Calendar.MINUTE, randomNumMinutetoAdd);

					timestampData = histoMinute.getDataAtTime(inputCalendar.getTimeInMillis() / 1000);
					assertEquals(timestampData.getTime(), inputCalendar.getTimeInMillis() / 1000);

					inputCalendar.add(Calendar.MINUTE, -1 * randomNumMinutetoAdd);
				}

			}

		} catch (AssertionError | InvalidArgumentException | TimestampOutOfBoundsException e) {
			System.out.println("Time to get: " + inputCalendar.getTimeInMillis() / 1000);
			System.out.println("Calendar to get: " + Util.calendarToString(inputCalendar));
			System.out.println("Time got: " + timestampData.getTime());
			System.out.println(
					"Calendar got: " + Util.calendarToString(Util.createGMTzoneCalendar(timestampData.getTime())));
			System.out.println(histoMinute.getURL());
			e.printStackTrace();
			assertTrue(false);
		}

	}

	@Test
	public void testNastyGetDataAtTime() {

		System.out.println("Starting testNastyGetDataAtTime()");

		TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
		Calendar inputCalendar = Calendar.getInstance(gmtTimeZone);
		HistoricalMinute histoMinute = HistoricalData.generateHistoMinute("USDT", "BTC",
				ExchangeStreamer.exchanges.Binance);
		int dayMax = 5;
		int hourMax = 22;
		int minuteMax = 59;
		int limitMax = 50;
		int outteriterations = 100;
		int inneriteration = 50;
		TimestampedData timestampData = null;
		long offset = 0;

		try {

			for (int i = 0; i < outteriterations; i++) {

				// generate random construction data
				int randomDay = ThreadLocalRandom.current().nextInt(0, dayMax + 1);
				int randomHour = ThreadLocalRandom.current().nextInt(0, hourMax + 1);
				int randomMinute = ThreadLocalRandom.current().nextInt(0, minuteMax + 1);
				int randomLimit = ThreadLocalRandom.current().nextInt(1, limitMax + 1);

				inputCalendar = Calendar.getInstance(gmtTimeZone);
				inputCalendar.set(Calendar.SECOND, 0);
				inputCalendar.set(Calendar.MILLISECOND, 0);
				inputCalendar.add(Calendar.DAY_OF_MONTH, -1 * randomDay);
				inputCalendar.add(Calendar.HOUR, -1 * randomHour);
				inputCalendar.add(Calendar.MINUTE, -1 * randomMinute);

				System.out.println("Iteration: " + i + "\nDate = " + Util.calendarToString(inputCalendar) + ", Limit = "
						+ randomLimit);

				histoMinute.setLimit(randomLimit);
				histoMinute.setLastUnixTimestamp(inputCalendar.getTimeInMillis() / 1000);
				histoMinute.update();

				for (int j = 0; j < inneriteration; j++) {

					int randomNumMintoAdd = -1 * (ThreadLocalRandom.current().nextInt(1, randomLimit + 1));
					int randomNumSectoAdd = (ThreadLocalRandom.current().nextInt(0, 59 + 1));

					inputCalendar.add(Calendar.MINUTE, randomNumMintoAdd); // add a random number of minutes
					inputCalendar.add(Calendar.SECOND, randomNumSectoAdd); // add a random number of seconds

					timestampData = histoMinute.getDataAtTime(inputCalendar.getTimeInMillis() / 1000);

					if ((offset = ((inputCalendar.getTimeInMillis() / 1000) % 60)) != 0) {
						offset = 60 - offset;
					}

					assertEquals(timestampData.getTime(), (inputCalendar.getTimeInMillis() / 1000) + offset);

					inputCalendar.add(Calendar.MINUTE, -1 * randomNumMintoAdd); // restore number of minutes
					inputCalendar.add(Calendar.SECOND, -1 * randomNumSectoAdd); // restore number of seconds

				}

			}

		} catch (AssertionError | InvalidArgumentException | TimestampOutOfBoundsException e) {
			System.out.println("Time to get: " + inputCalendar.getTimeInMillis() / 1000);
			System.out.println("Calendar to get: " + Util.calendarToString(inputCalendar));
			System.out.println("Time got: " + timestampData.getTime());
			System.out.println(
					"Calendar got: " + Util.calendarToString(Util.createGMTzoneCalendar(timestampData.getTime())));
			System.out.println("offset: " + offset);
			System.out.println(histoMinute.getURL());
			e.printStackTrace();
			assertTrue(false);
		}

	}

	@Test
	public void testHistoMinuteAggregated() {

		System.out.println("Starting testHistoMinuteAggregated()");

		TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
		Calendar inputCalendar = Calendar.getInstance(gmtTimeZone);
		HistoricalMinute histoMinute = HistoricalData.generateHistoMinute("USDT", "BTC",
				ExchangeStreamer.exchanges.Binance);
		int dayMax = 5;
		int hourMax = 22;
		int minuteMax = 59;
		int limitMax = 50;
		int aggregateMax = 20;
		int iterations = 100;
		int inneriteration = 50;
		TimestampedData timestampData = null;

		try {

			for (int i = 0; i < iterations; i++) {

				// generate random construction data
				int randomDay = ThreadLocalRandom.current().nextInt(0, dayMax + 1);
				int randomHour = ThreadLocalRandom.current().nextInt(0, hourMax + 1);
				int randomMinute = ThreadLocalRandom.current().nextInt(0, minuteMax + 1);
				int randomLimit = ThreadLocalRandom.current().nextInt(1, limitMax + 1);
				int randomAggregate = ThreadLocalRandom.current().nextInt(1, aggregateMax + 1);

				inputCalendar = Calendar.getInstance(gmtTimeZone);
				inputCalendar.set(Calendar.SECOND, 0);
				inputCalendar.set(Calendar.MILLISECOND, 0);
				inputCalendar.add(Calendar.DAY_OF_MONTH, -1 * randomDay);
				inputCalendar.add(Calendar.HOUR, -1 * randomHour);
				inputCalendar.add(Calendar.MINUTE, -1 * randomMinute);

				System.out.println("Iteration: " + i + "\nDate = " + Util.calendarToString(inputCalendar) + ", Limit = "
						+ randomLimit);

				histoMinute.setLimit(randomLimit);
				histoMinute.setAggregate(randomAggregate);
				histoMinute.setLastUnixTimestamp(inputCalendar.getTimeInMillis() / 1000);
				histoMinute.update();

				// check time from
				assertEquals(inputCalendar.getTimeInMillis() / 1000,
						histoMinute.getTimeFrom() + histoMinute.getLimit() * 60 * histoMinute.getAggregate());

				// check time to
				assertEquals(inputCalendar.getTimeInMillis() / 1000, histoMinute.getTimeTo());

				// check Data array
				long timetoGet = histoMinute.getTimeFrom();
				Iterator<TimestampedData> DataIterator = histoMinute.getDataStream().iterator();

				while (DataIterator.hasNext()) {

					TimestampedData t = DataIterator.next();
					assertEquals(timetoGet, t.getTime());
					timetoGet += (60 * histoMinute.getAggregate());

				}

				// test getDataAtTime()
				try {

					for (int j = 0; j < inneriteration; j++) {

						int randomNumHourtoAdd = -1 * randomAggregate
								* (ThreadLocalRandom.current().nextInt(1, randomLimit + 1) - 1);
						inputCalendar.add(Calendar.MINUTE, randomNumHourtoAdd);

						timestampData = histoMinute.getDataAtTime(inputCalendar.getTimeInMillis() / 1000);
						assertEquals(timestampData.getTime(), inputCalendar.getTimeInMillis() / 1000);

						inputCalendar.add(Calendar.MINUTE, -1 * randomNumHourtoAdd);
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
			System.out.println("Time From: " + histoMinute.getTimeFrom());
			System.out.println(
					"Calendar From: " + Util.calendarToString(Util.createGMTzoneCalendar(histoMinute.getTimeFrom())));
			System.out.println("Time To: " + histoMinute.getTimeTo());
			System.out
					.println("Calendar To: " + Util.calendarToString(Util.createGMTzoneCalendar(histoMinute.getTimeTo())));
			System.out.println(histoMinute.getURL());
			e.printStackTrace();
			assertTrue(false);
		}

	}

}
