
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import cryptos.CryptoData;
import cryptos.CryptoInfo;
import cryptos.exchanges.CryptoExchanges;
import cryptos.historical.HistoricalDaily;
import cryptos.historical.HistoricalData;
import cryptos.historical.HistoricalHourly;
import cryptos.historical.HistoricalMinute;
import exceptions.HttpsConnectionException;
import exceptions.InvalidArgumentException;
import utils.HttpsConnector;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMain {

	@Test
	public void testCryptocurrencyFields() {

		try {
			
			CryptoData coinData = CryptoData.getInstance("USDT", "BTC", CryptoExchanges.exchanges.Binance);
			coinData.update();
			assertEquals("USDT", coinData.getCurrencyTo());
			assertEquals("BTC", coinData.getCurrencyFrom());
			assertEquals(CryptoExchanges.exchanges.Binance, coinData.getExchange());

			CryptoInfo coinInfo = CryptoInfo.getInstance("USDT", "BTC");
			coinInfo.update();
			assertEquals("USDT", coinInfo.getCurrencyTo());
			assertEquals("BTC", coinInfo.getCurrencyFrom());

			coinInfo.setCurrencyFrom("ETC");
			coinInfo.setCurrencyTo("BTC");
			coinInfo.update();
			
			coinData.setCurrencyFrom("ETC");
			coinData.setCurrencyTo("BTC");
			coinData.setExchange(CryptoExchanges.exchanges.Bitfinex);
			coinData.update();

			assertEquals("BTC", coinData.getCurrencyTo());
			assertEquals("ETC", coinData.getCurrencyFrom());
			assertEquals(CryptoExchanges.exchanges.Bitfinex, coinData.getExchange());

			assertEquals("BTC", coinInfo.getCurrencyTo());
			assertEquals("ETC", coinInfo.getCurrencyFrom());

		} catch (InvalidArgumentException e) {
			e.printStackTrace();
			assertTrue(false);
		}

	}

	@Test
	public void testCryptoInfoStream() {

		String currencyTo = "BTC";
		String[] currenciesFrom = { "BTC", "ETC", "XRP", "LTC" };

		try {

			Iterator<CryptoInfo> it = CryptoInfo.streamCryptoInfo(currencyTo, currenciesFrom).iterator();
			int i = 0;
			CryptoInfo tmp;
			while (it.hasNext()) {
				tmp = it.next();
				assertEquals(tmp.getCurrencyFrom(), currenciesFrom[i++]);
				assertEquals(tmp.getCurrencyTo(), currencyTo);
			}

		} catch (InvalidArgumentException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void testBadArguments() {

		try {

			CryptoInfo coin = CryptoInfo.getInstance("STD", "BTC");
			coin.update();
			assertTrue(false);

		} catch (InvalidArgumentException e) {
		}

		try {

			CryptoData coin = CryptoData.getInstance("STD", "BTC", CryptoExchanges.exchanges.Binance);
			coin.update();
			assertTrue(false);

		} catch (InvalidArgumentException e) {
		}

		try {

			HistoricalDaily histoDay = HistoricalData.generateHistoDaily("STD", "BTC",
					CryptoExchanges.exchanges.Binance);
			histoDay.update();
			assertTrue(false);

		} catch (InvalidArgumentException e) {
		}

		try {

			HistoricalHourly histoHour = HistoricalData.generateHistoHourly("USDT", "BTC",
					CryptoExchanges.exchanges.Binance);
			histoHour.setAggregate(-10);
			histoHour.update();
			assertTrue(false);

		} catch (InvalidArgumentException e) {
		}

		try {

			HistoricalMinute histoMin = HistoricalData.generateHistoMinute("USDT", "BTC",
					CryptoExchanges.exchanges.Binance);
			histoMin.setLimit(-10);
			histoMin.update();
			assertTrue(false);

		} catch (InvalidArgumentException e) {
		}

	}

	@Test
	public void testHttpsConnection() {

		try {

			HttpsConnector.connect(
					"https://min-api.cryptocompare.com/data/generateAvg?fsym=BTC&tsym=USDT&e=binance&extraParams=Cryptos4J");

		} catch (HttpsConnectionException e) {
			e.printStackTrace();
			assertTrue(false);
		}

		try {

			HttpsConnector.connect(
					"htt://min-api.cryptocompare.com/data/generateAvg?fsym=BTC&tsym=USDT&e=binance&extraParams=Cryptos4J");
			assertTrue(false);

		} catch (HttpsConnectionException e) {
		}

	}

}
