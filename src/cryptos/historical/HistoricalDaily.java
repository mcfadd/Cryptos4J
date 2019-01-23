package cryptos.historical;

import java.net.MalformedURLException;
import java.net.URL;

import cryptos.exchanges.ExchangeStreamer;
import exceptions.TimestampOutOfBoundsException;
import utils.Util;

/**
 * Historical data class that is equivalent to the json object returned by the
 * <a href=
 * "https://min-api.cryptocompare.com/documentation?key=Historical&cat=dataHistoday">
 * Historical Daily OHLCV</a> end point.
 * 
 * @author Matt - <a href="https://github.com/mcfadd">mcfadd</a>
 * @since Cryptos4J v1.0
 * @see TimestampedData
 */
public class HistoricalDaily extends HistoricalData {

	protected HistoricalDaily(String currencyTo, String currencyFrom, ExchangeStreamer.exchanges exchange) {
		super(currencyTo, currencyFrom, exchange);
	}

	@Override
	public TimestampedData getDataAtTime(long timestamp) throws TimestampOutOfBoundsException {

		try {

			TimestampedData[] Data = this.getDataArray();

			long offset;
			if ((offset = (timestamp % (60 * 60 * 24))) != 0) {
				offset = (24 * 60 * 60) - offset;
			}

			return Data[(Data.length - 1
					- (int) (this.getTimeTo() - (timestamp + offset)) / (60 * 60 * 24 * this.getAggregate()))];

		} catch (IndexOutOfBoundsException e) {
			throw new TimestampOutOfBoundsException(timestamp);
		}
	}

	@Override
	protected void updateURL() {
		try {

			URL temp = new URL("https://min-api.cryptocompare.com/data/histoday?fsym=" + this.getCurrencyFrom()
					+ "&tsym=" + this.getCurrencyTo() + "&e=" + this.getExchange() + "&limit=" + this.getLimit()
					+ "&toTs=" + this.getLastUnixTimestamp() + "&extraParams=Cryptos4J");

			if (this.getAggregate() != 1)
				temp = new URL(temp + "&aggregate=" + this.getAggregate() + "&aggregatePredictableTimePeriods=false");

			if (Util.getApiKey() != null)
				temp = new URL(temp + "&api_key=" + Util.getApiKey());

			this.setURL(temp);

		} catch (MalformedURLException e) {
		}
	}

}
