package cryptos;

/**
 * Object equivalent to the 'RAW' json object returned by the <i>min-api.cryptocompare.com/data/generateAvg</i> end point.
 * 
 * @author Matt - github.com/mcfadd
 * @since Cryptos4J v1.0
 * @see CryptoData
 */
public class RawData {

	private final String MARKET;
	private final String FROMSYMBOL;
	private final String TOSYMBOL;
	private final int FLAGS;
	private final double PRICE;
	private final long LASTUPDATE;
	private final double LASTVOLUME;
	private final double LASTVOLUMETO;
	private final String LASTTRADEID;
	private final double VOLUME24HOUR;
	private final double VOLUME24HOURTO;
	private final double OPEN24HOUR;
	private final double HIGH24HOUR;
	private final String LOW24HOUR;
	private final String LASTMARKET;
	private final double CHANGE24HOUR;
	private final double CHANGEPCT24HOUR;
	private final int CHANGEDAY;
	private final int CHANGEPCTDAY;

	protected RawData(String market, String fromSymbol, String toSymbol, int flags, double price, long lastUpdate,
			double lastVolume, double lastVolumeTo, String lastTraded, double volume24Hour, double volume24HourTo,
			double open24Hour, double hight24Hour, String low24Hour, String lastMarket, double change24Hour,
			double changePCT24Hour, int changeDay, int changePCTDay) {

		MARKET = market;
		FROMSYMBOL = fromSymbol;
		TOSYMBOL = toSymbol;
		FLAGS = flags;
		PRICE = price;
		LASTUPDATE = lastUpdate;
		LASTVOLUME = lastVolume;
		LASTVOLUMETO = lastVolumeTo;
		LASTTRADEID = lastTraded;
		VOLUME24HOUR = volume24Hour;
		VOLUME24HOURTO = volume24HourTo;
		OPEN24HOUR = open24Hour;
		HIGH24HOUR = hight24Hour;
		LOW24HOUR = low24Hour;
		LASTMARKET = lastMarket;
		CHANGE24HOUR = change24Hour;
		CHANGEPCT24HOUR = changePCT24Hour;
		CHANGEDAY = changeDay;
		CHANGEPCTDAY = changePCTDay;
		
	}

	public String getMARKET() {
		return MARKET;
	}

	public String getFROMSYMBOL() {
		return FROMSYMBOL;
	}

	public String getTOSYMBOL() {
		return TOSYMBOL;
	}

	public int getFLAGS() {
		return FLAGS;
	}

	public double getPRICE() {
		return PRICE;
	}

	public long getLASTUPDATE() {
		return LASTUPDATE;
	}

	public double getLASTVOLUME() {
		return LASTVOLUME;
	}

	public double getLASTVOLUMETO() {
		return LASTVOLUMETO;
	}

	public String getLASTTRADEID() {
		return LASTTRADEID;
	}

	public double getVOLUME24HOUR() {
		return VOLUME24HOUR;
	}

	public double getVOLUME24HOURTO() {
		return VOLUME24HOURTO;
	}

	public double getOPEN24HOUR() {
		return OPEN24HOUR;
	}

	public double getHIGH24HOUR() {
		return HIGH24HOUR;
	}

	public String getLOW24HOUR() {
		return LOW24HOUR;
	}

	public String getLASTMARKET() {
		return LASTMARKET;
	}

	public double getCHANGE24HOUR() {
		return CHANGE24HOUR;
	}

	public double getCHANGEPCT24HOUR() {
		return CHANGEPCT24HOUR;
	}

	public int getCHANGEDAY() {
		return CHANGEDAY;
	}

	public int getCHANGEPCTDAY() {
		return CHANGEPCTDAY;
	}

	/**
	 * returns a string representation of the 'RAW' data retrieved from the <i>min-api.cryptocompare.com/data/generateAvg</i> end point.
	 * @return dataToString
	 * @since Cryptos4J v1.0
	 * @see CryptoData#dataToString()
	 */
	public String datatoString() {

		return "MARKET:  \"" + MARKET + "\"\r\n" + "FROMSYMBOL:  \"" + FROMSYMBOL + "\"\r\n" + "TOSYMBOL:  \""
				+ TOSYMBOL + "\"\r\n" + "FLAGS: " + FLAGS + "\r\n" + "PRICE: " + PRICE + "\r\n" + "LASTUPDATE: "
				+ LASTUPDATE + "\r\n" + "LASTVOLUME: " + LASTVOLUME + "\r\n" + "LASTVOLUMETO: " + LASTVOLUMETO
				+ "\r\n" + "LASTTRADEID:  \"" + LASTTRADEID + "\"\r\n" + "VOLUME24HOUR: " + VOLUME24HOUR + "\r\n"
				+ "VOLUME24HOURTO: " + VOLUME24HOURTO + "\r\n" + "OPEN24HOUR: " + OPEN24HOUR + "\r\n"
				+ "HIGH24HOUR: " + HIGH24HOUR + "\r\n" + "LOW24HOUR: " + LOW24HOUR + "\r\n" + "LASTMARKET:  \""
				+ LASTMARKET + "\"\r\n" + "CHANGE24HOUR: " + CHANGE24HOUR + "\r\n" + "CHANGEPCT24HOUR: "
				+ CHANGEPCT24HOUR + "\r\n" + "CHANGEDAY: " + CHANGEDAY + "\r\n" + "CHANGEPCTDAY: " + CHANGEPCTDAY
				+ "\n";

	}

}
