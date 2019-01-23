package cryptos.historical;

/**
 * Object equivalent to the json array elements returned by the <a href=
 * "https://min-api.cryptocompare.com/documentation?key=Historical&cat=dataHistoday">
 * Historical OHLCV</a> end point.
 * 
 * @author Matt - <a href="https://github.com/mcfadd">mcfadd</a>
 * @since Cryptos4J v1.0
 * @see HistoricalData
 */
public class TimestampedData {

	private final long time;
	private final double close;
	private final double high;
	private final double low;
	private final double open;
	private final double volumefrom;
	private final double volumeto;

	protected TimestampedData(long time, double close, double high, double low, double open, double volumefrom,
			double volumeto) {

		this.time = time;
		this.close = close;
		this.high = high;
		this.low = low;
		this.open = open;
		this.volumefrom = volumefrom;
		this.volumeto = volumeto;

	}

	public long getTime() {
		return time;
	}

	public double getClose() {
		return close;
	}

	public double getHigh() {
		return high;
	}

	public double getLow() {
		return low;
	}

	public double getOpen() {
		return open;
	}

	public double getVolumefrom() {
		return volumefrom;
	}

	public double getVolumeto() {
		return volumeto;
	}

	/**
	 * returns a string representation of the timestamped data retrieved from the
	 * <a href=
	 * "https://min-api.cryptocompare.com/documentation?key=Historical&cat=dataHistoday">
	 * Historical OHLCV</a> end point.
	 * 
	 * @return dataToString
	 * @since Cryptos4J v1.0
	 * @see HistoricalData#dataToString()
	 */
	public String datatoString() {

		return "\n[\n  time:  \"" + time + "\"\r\n  " + "close:  \"" + close + "\"\r\n  " + "high:  \"" + high
				+ "\"\r\n  " + "low: " + low + "\r\n  " + "open: " + open + "\r\n  " + "volumefrom: " + volumefrom
				+ "\r\n  " + "volumeto: " + volumeto + "\n]\n";

	}
}
