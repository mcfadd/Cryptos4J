package cryptos.historical;

import java.net.MalformedURLException;
import java.net.URL;

import cryptos.exchanges.CryptoExchanges;
import exceptions.TimestampOutOfBoundsException;
import utils.Util;

/**
 * Historical data class that is equivalent to the json object returned by the <i>min-api.cryptocompare.com/data/histohour</i> end point.
 * 
 * @author Matt - github.com/mcfadd
 * @since Cryptos4J v1.0
 * @see TimestampedData
 */
public class HistoricalHourly extends HistoricalData {

	protected HistoricalHourly(String currencyTo, String currencyFrom, CryptoExchanges.exchanges exchange) {
		super(currencyTo, currencyFrom, exchange);
	}

	@Override
	public TimestampedData getDataAtTime(long timestamp) throws TimestampOutOfBoundsException {

		try {

			TimestampedData[] Data = this.getDataArray();

			long offset;
			if ((offset = (timestamp % (60 * 60))) != 0) {
				offset = (60 * 60) - offset;
			}

			return Data[(Data.length - 1
					- (int) (this.getTimeTo() - (timestamp + offset)) / (60 * 60 * this.getAggregate()))];

		} catch (IndexOutOfBoundsException e) {
			throw new TimestampOutOfBoundsException(timestamp);
		}
	}

	@Override
	protected void updateURL() {
		try {

			URL temp = new URL("https://min-api.cryptocompare.com/data/histohour?fsym=" + this.getCurrencyFrom()
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
