package cryptos.historical;

import java.net.URL;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import cryptos.CryptoBaseExchange;
import cryptos.exchanges.CryptoExchanges;
import exceptions.InvalidArgumentException;
import exceptions.TimestampOutOfBoundsException;
import utils.HttpsConnector;

/**
 * Historical data super/factory class for HistoricalDaily, HistoricalHourly, and HistoricalMinute.
 * 
 * @author Matt - github.com/mcfadd
 * @since Cryptos4J v1.0
 * @see TimestampedData
 */
public abstract class HistoricalData implements CryptoBaseExchange, HistoricalBase {

	// mulatble fields
	private String currencyFrom;
	private String currencyTo;
	private CryptoExchanges.exchanges exchange;
	private long lastUnixTimestamp;
	private int limit;
	private int aggregate;
	private URL url;
	
	// immutable fields
	private String Response;
	private float Type;
	private boolean Aggregated;
	private TimestampedData[] Data;
	private long TimeTo;
	private long TimeFrom;
	private boolean FirstValueInArray;
	private ConversionType ConversionType;

	/**
	 * gets a new instance of HistoricalDaily with the following parameters
	 * @param currencyTo - currency symbol to convert to
	 * @param currencyFrom - currency symbol to convert from
	 * @param exchange - exchange to get data from
	 * @return new instance of HistoricalDaily
	 * @since Cryptos4J v1.0
	 */
	public static HistoricalDaily generateHistoDaily(String currencyTo, String currencyFrom, CryptoExchanges.exchanges exchange) {
		return new HistoricalDaily(currencyTo, currencyFrom, exchange);
	}
	
	/**
	 * gets a new instance of HistoricalHourly with the following parameters
	 * @param currencyTo - currency symbol to convert to
	 * @param currencyFrom - currency symbol to convert from
	 * @param exchange - exchange to get data from
	 * @return new instance of HistoricalHourly
	 * @since Cryptos4J v1.0
	 */
	public static HistoricalHourly generateHistoHourly(String currencyTo, String currencyFrom, CryptoExchanges.exchanges exchange) {
		return new HistoricalHourly(currencyTo, currencyFrom, exchange);
	}
	
	/**
	 * gets a new instance of HistoricalMinute with the following parameters
	 * @param currencyTo - currency symbol to convert to
	 * @param currencyFrom - currency symbol to convert from
	 * @param exchange - exchange to get data from
	 * @return new instance of HistoricalMinute
	 * @since Cryptos4J v1.0
	 */
	public static HistoricalMinute generateHistoMinute(String currencyTo, String currencyFrom, CryptoExchanges.exchanges exchange) {
		return new HistoricalMinute(currencyTo, currencyFrom, exchange);
	}
	
	protected HistoricalData(String currencyTo, String currencyFrom, CryptoExchanges.exchanges exchange) {

		this.aggregate = 1;
		this.lastUnixTimestamp = System.currentTimeMillis() / 1000;
		this.limit = 10;
		this.currencyFrom = currencyFrom;
		this.currencyTo = currencyTo;
		this.exchange = exchange;
		
	}
	
	protected HistoricalData(String Response, float Type, boolean Aggregated, TimestampedData[] Data, long TimeTo,
			long TimeFrom, boolean FirstValueInArray, ConversionType ConversionTypeObject) {

		this.Response = Response;
		this.Type = Type;
		this.Aggregated = Aggregated;
		this.Data = Data;
		this.TimeTo = TimeTo;
		this.TimeFrom = TimeFrom;
		this.FirstValueInArray = FirstValueInArray;
		this.ConversionType = ConversionTypeObject;
		
	}

	@Override
	public long getLastUnixTimestamp() {
		return this.lastUnixTimestamp;
	}
	
	@Override
	public void setLastUnixTimestamp(long time) {
		this.lastUnixTimestamp = time;
	}
	
	@Override
	public int getAggregate() {
		return aggregate;
	}
	
	@Override
	public void setAggregate(int aggregate) {		
		this.aggregate = aggregate;
	}
	
	@Override
	public int getLimit() {
		return limit;
	}
	
	@Override
	public void setLimit(int limit) {		
		this.limit = limit;
	}
	
	@Override
	public CryptoExchanges.exchanges getExchange() {
		return this.exchange;
	}
	
	@Override
	public void setExchange(CryptoExchanges.exchanges exchange) {
		this.exchange = exchange;
	}

	@Override
	public String getCurrencyFrom() {
		return this.currencyFrom;
	}

	@Override
	public void setCurrencyFrom(String currencyFrom) {
		this.currencyFrom = currencyFrom;
	}

	@Override
	public String getCurrencyTo() {
		return this.currencyTo;
	}

	@Override
	public void setCurrencyTo(String currencyTo) {
		this.currencyTo = currencyTo;
	}
	
	/**
	 * gets the <i>min-api.cryptocompare.com/data/histo</i> end point url with parameters 
	 * currencyTo, currencyFrom, exchange, lastUnixTimestamp, limit, aggregate, and apiKey (if any)
	 * @return URL end point this object connects to
	 */
	@Override
	public URL getURL() {
		return url;
	}
	
	protected void setURL(URL url) {
		this.url = url;
	}

	@Override
	public String getResponse() {
		return Response;
	}

	@Override
	public float getType() {
		return Type;
	}

	@Override
	public boolean isAggregated() {
		return Aggregated;
	}

	@Override
	public long getTimeTo() {
		return TimeTo;
	}
	
	@Override
	public long getTimeFrom() {
		return TimeFrom;
	}
	
	@Override
	public boolean isFirstValueInArray() {
		return FirstValueInArray;
	}

	@Override
	public ConversionType getConversionType() {
		return ConversionType;
	}
	
	protected TimestampedData[] getDataArray() {
		return Data;
	}

	@Override
	public Stream<TimestampedData> getDataStream() {
		return Arrays.stream(Data);
	}
	
	@Override
	public abstract TimestampedData getDataAtTime(long timestamp) throws TimestampOutOfBoundsException;
	
	protected abstract void updateURL();

	/**
	 * updates url with parameters,
	 * then connects and updates this objects fields with the returned json.
	 * @since Cryptos4J v1.0
	 */
	@Override
	public void update() throws InvalidArgumentException {

		String json = "";
		try {

			this.updateURL();

			Gson gson = new Gson();
			json = HttpsConnector.connect(url.toString());
			HistoricalData tmp = gson.fromJson(json,
					HistoricalDaily.class);

			this.Response = tmp.Response;
			this.Type = tmp.Type;
			this.Aggregated = tmp.Aggregated;
			this.Data = tmp.Data;
			this.TimeTo = tmp.TimeTo;
			this.TimeFrom = tmp.TimeFrom;
			this.FirstValueInArray = tmp.FirstValueInArray;
			this.ConversionType = tmp.ConversionType;
			
		} catch (JsonSyntaxException e) {
			Matcher m = Pattern.compile("\"Message\":\"(.*?)\"").matcher(json);
			m.find();
			throw new InvalidArgumentException(m.group());
		}

	}

	/**
	 * returns a string representation of the historical data retrieved from the <i>min-api.cryptocompare.com/data/histo</i> end point.
	 * @return dataToString
	 * @since Cryptos4J v1.0
	 * @see TimestampedData#datatoString()
	 * @see ConversionType#datatoString()
	 */
	@Override
	public String dataToString() {

		StringBuilder sb = new StringBuilder();
		sb.append("Response:  \"" + Response + "\"\r\n" + "Type:  \"" + Type + "\"\r\n" + "Aggregated:  \"" + Aggregated
				+ "\"\r\n");

		for (int i = 0; i < Data.length; i++) {
			sb.append(Data[i].datatoString());
		}

		sb.append("TimeTo: " + TimeTo + "\r\n" + "TimeFrom: " + TimeFrom + "\r\n" + "FirstValueInArray: "
				+ FirstValueInArray + "\r\n" + "ConversionTypeObject: \n" + ConversionType.datatoString() + "\n");

		return sb.toString();

	}

}
