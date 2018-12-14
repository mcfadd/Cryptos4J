package cryptos.historical;

import java.util.stream.Stream;

import cryptos.historical.ConversionType;
import exceptions.TimestampOutOfBoundsException;

/**
 * Interface for HistoricalData.
 * 
 * @author Matt - <a href="https://github.com/mcfadd">mcfadd</a>
 * @since Cryptos4J v1.0
 */
public interface HistoricalBase {

	/**
	 * gets the toTs parameter this object uses for the CryptoCompare API
	 * 
	 * @return lastUnixTimestap - the last unix timestamp to get data for
	 * @since Cryptos4J v1.0
	 */
	public long getLastUnixTimestamp();

	/**
	 * sets the toTs parameter this object uses for the CryptoCompare API
	 * 
	 * @param lastUnixTimestamp the last unix timestamp to get data for
	 * @since Cryptos4J v1.0
	 */
	public void setLastUnixTimestamp(long lastUnixTimestamp);

	/**
	 * gets the aggregate parameter this object uses for the CryptoCompare API
	 * 
	 * @return aggregate time period to aggregate the data over
	 * @since Cryptos4J v1.0
	 */
	public int getAggregate();

	/**
	 * sets the aggregate parameter this object uses for the CryptoCompare API
	 * 
	 * @param aggregate time period to aggregate the data over
	 * @since Cryptos4J v1.0
	 */
	public void setAggregate(int aggregate);

	/**
	 * gets the limit parameter this object uses for the CryptoCompare API
	 * 
	 * @return limit the number of TimestampedData to return
	 * @since Cryptos4J v1.0
	 */
	public int getLimit();

	/**
	 * sets the limit parameter this object uses for the CryptoCompare API
	 * 
	 * @param limit The number of TimestampedData to return
	 * @since Cryptos4J v1.0
	 */
	public void setLimit(int limit);

	/**
	 * @return response message<br>
	 *         Example: "Success" if data was successfully retrieved
	 * @since Cryptos4J v1.0
	 */
	public String getResponse();

	/**
	 * @return type
	 * @since Cryptos4J v1.0
	 */
	public float getType();

	/**
	 * @return true if data is aggregated
	 * @since Cryptos4J v1.0
	 */
	public boolean isAggregated();

	/**
	 * @return unix timestamp to for this Historical object
	 * @since Cryptos4J v1.0
	 */
	public long getTimeTo();

	/**
	 * @return unix timestamp from for this Historical object
	 * @since Cryptos4J v1.0
	 */
	public long getTimeFrom();

	/**
	 * @return true if there is a first value in the array of TimestampedData
	 * @since Cryptos4J v1.0
	 */
	public boolean isFirstValueInArray();

	/**
	 * gets the ConversionType for this object
	 * 
	 * @return ConversionType
	 * @see ConversionType
	 * @since Cryptos4J v1.0
	 */
	public ConversionType getConversionType();

	/**
	 * streams TimestampedData from this Historical object
	 * 
	 * @return data stream
	 * @see TimestampedData
	 * @since Cryptos4J v1.0
	 */
	public Stream<TimestampedData> getDataStream();

	/**
	 * gets the TimestampedData with unix timestamp equal to unixTimestamp.<br>
	 * If the unixTimestamp is in between two unix timestamps then the latter
	 * TimestampedData is returned. <br>
	 * <br>
	 * Note: this method expects the unixTimestamp parameter to be in GMT time zone
	 * 
	 * @param unixTimestamp unix timestamp of TimestampedData to get
	 * @return TimestampedData with unix timestamp equal to or greater than
	 *         unixTimestamp
	 * @throws TimestampOutOfBoundsException
	 * @see TimestampedData
	 * @since Cryptos4J v1.0
	 */
	public TimestampedData getDataAtTime(long unixTimestamp) throws TimestampOutOfBoundsException;
}
