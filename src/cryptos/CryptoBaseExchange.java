package cryptos;

import cryptos.exchanges.ExchangeStreamer;

/**
 * Interface for CryptoData and HistoricalData.
 * 
 * @author Matt - <a href="https://github.com/mcfadd">mcfadd</a>
 * @since Cryptos4J v1.0
 */
public interface CryptoBaseExchange extends CryptoBase {

	/**
	 * gets the e parameter this object uses for the CryptoCompare API
	 * 
	 * @return exchange exchange to get data from
	 * @since Cryptos4J v1.0
	 */
	public ExchangeStreamer.exchanges getExchange();

	/**
	 * sets the e parameter this object uses for the CryptoCompare API
	 * 
	 * @param exchange exchange to get data from
	 * @since Cryptos4J v1.0
	 */
	public void setExchange(ExchangeStreamer.exchanges exchange);

}
