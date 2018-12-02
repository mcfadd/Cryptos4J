package cryptos;

/**
 * Object equivalent to the 'ConversionInfo' json object returned by the <i>min-api.cryptocompare.com/data/coin/generalinfo</i> end point.
 * 
 * @author Matt - github.com/mcfadd
 * @since Cryptos4J v1.0
 * @see CryptoInfo
 */
public class ConversionInfo {

	private final String Conversion;
	private final String ConversionSymbol;
	private final String CurrencyFrom;
	private final String CurrencyTo;
	private final String Market;
	private final float Supply;
	private final float TotalVolume24H;
	private final String SubBase;
	private final String[] SubsNeeded;
	private final String[] RAW;

	protected ConversionInfo(String conversion, String conversionSymbol, String currencyFrom, String currencyTo,
			String market, float supply, float totalVolume24H, String subBase, String[] subsNeeded, String[] raw) {

		Conversion = conversion;
		ConversionSymbol = conversionSymbol;
		CurrencyFrom = currencyFrom;
		CurrencyTo = currencyTo;
		Market = market;
		Supply = supply;
		TotalVolume24H = totalVolume24H;
		SubBase = subBase;
		SubsNeeded = subsNeeded;
		RAW = raw;
		
	}

	public String getConversion() {
		return Conversion;
	}

	public String getConversionSymbol() {
		return ConversionSymbol;
	}

	public String getCurrencyFrom() {
		return CurrencyFrom;
	}

	public String getCurrencyTo() {
		return CurrencyTo;
	}

	public String getMarket() {
		return Market;
	}

	public float getSupply() {
		return Supply;
	}

	public float getTotalVolume24H() {
		return TotalVolume24H;
	}

	public String getSubBase() {
		return SubBase;
	}

	public String getSubsNeeded() {
		return SubsNeeded[0];
	}

	public String getRawConversion() {
		return RAW[0];
	}

	/**
	 * returns a string representation of the 'ConversionInfo' object retrieved from the <i>min-api.cryptocompare.com/data/coin/generalinfo</i> end point.
	 * @return dataToString
	 * @since Cryptos4J v1.0
	 * @see CryptoInfo#dataToString()
	 */
	public String dataToString() {
		
		return "Conversion:  \"" + Conversion + "\"\r\n" + "ConversionSymbol:  \"" + ConversionSymbol + "\"\r\n"
				+ "CurrencyFrom:  \"" + CurrencyFrom + "\"\r\n" + "CurrencyTo: " + CurrencyTo + "\r\n" + "Market: "
				+ Market + "\r\n" + "Supply: " + Supply + "\r\n" + "TotalVolume24H: " + TotalVolume24H + "\r\n"
				+ "SubBase:  \"" + SubBase + "\"\r\n" + "SubsNeeded: "
				+ ((SubsNeeded.length == 0) ? "" : SubsNeeded[0]) + "\r\n" + "RAW: "
				+ ((RAW.length == 0) ? "" : RAW[0]) + "\n";
		
	}
	
}
