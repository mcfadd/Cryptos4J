package cryptos;

import java.net.URL;

import exceptions.InvalidArgumentException;

/**
 * Interface for CryptoData, CryptoInfo, and HistoricalData.
 * 
 * @author Matt - github.com/mcfadd
 * @since Cryptos4J v1.0
 */
public interface CryptoBase {
	
	/**
	 * gets the fsym(s) parameter this object uses for the CryptoCompare API
	 * @return currencyFrom - currency symbol to convert from
	 * @since Cryptos4J v1.0
	 */
	public String getCurrencyFrom();

	/**
	 * sets the fsym(s) parameter this object uses for the CryptoCompare API
	 * @param currencyFrom - currency symbol to convert from
	 * @since Cryptos4J v1.0
	 */
	public void setCurrencyFrom(String currencyFrom);

	/**
	 * gets the tsym(s) parameter this object uses for the CryptoCompare API
	 * @return currencyTo - currency symbol to convert to
	 * @since Cryptos4J v1.0
	 */
	public String getCurrencyTo();

	/**
	 * sets the tsym(s) parameter this object uses for the CryptoCompare API
	 * @param currencyTo - currency symbol to convert to
	 * @since Cryptos4J v1.0
	 */
	public void setCurrencyTo(String currencyTo);
	
	/**
	 * updates url and gets data from the CryptoCompare API
	 * @throws InvalidArgumentException
	 * @since Cryptos4J v1.0
	 */
	public void update() throws InvalidArgumentException;
	
	/**
	 * gets the CryptoCompare API end point url with parameters
	 * @return URL end point this object connects to
	 * @since Cryptos4J v1.0
	 */
	public URL getURL();
	
	/**
	 * returns a string representation of the data retrieved from the CryptoCompare API
	 * @return dataToString
	 * @since Cryptos4J v1.0
	 */
	public String dataToString();
	
}
