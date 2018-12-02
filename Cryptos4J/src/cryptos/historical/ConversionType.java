package cryptos.historical;

/**
 * Object equivalent to the 'ConversionType' json object returned by the <i>min-api.cryptocompare.com/data/histo</i> end point.
 * 
 * @author Matt - github.com/mcfadd
 * @since Cryptos4J v1.0
 * @see HistoricalData
 */
public class ConversionType {

	private final String type;
	private final String conversionSymbol;

	protected ConversionType(String type, String conversionSymbol) {
		this.type = type;
		this.conversionSymbol = conversionSymbol;
	}

	public String getType() {
		return type;
	}

	public String getConversionSymbol() {
		return conversionSymbol;
	}

	/**
	 * returns a string representation of the 'ConversionType' json object retrieved from the <i>min-api.cryptocompare.com/data/histo</i> end point.
	 * @return dataToString
	 * @since Cryptos4J v1.0
	 * @see HistoricalData#dataToString()
	 */
	public String datatoString() {

		return "type: " + type + "\r\n" + "conversionSymbol: " + conversionSymbol + "\n";

	}
}