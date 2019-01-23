package cryptos.exchanges;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Object that contains currency pairs returned by the <a href=
 * "https://min-api.cryptocompare.com/documentation?key=Other&cat=allExchangesEndpoint">
 * All the Exchanges and Trading Pairs</a> end point.
 * 
 * @author Matt - <a href="https://github.com/mcfadd">mcfadd</a>
 * @since Cryptos4J v1.0
 * @see ExchangeStreamer
 */
public final class Exchange {

	private final ExchangeStreamer.exchanges name;
	private final ConcurrentHashMap<String, String[]> pairs; // <currencyFrom, currenciesTo>

	protected Exchange(ExchangeStreamer.exchanges exchange) {

		this.name = exchange;
		this.pairs = new ConcurrentHashMap<String, String[]>();

	}

	/**
	 * @return name of this Exchange
	 */
	public ExchangeStreamer.exchanges getName() {
		return this.name;
	}

	/**
	 * @return cryptocurrency pairs for this Exchange
	 */
	public ConcurrentHashMap<String, String[]> getPairs() {
		return pairs;
	}

	/**
	 * @param key currency symbol
	 * @return true if exchange trades key parameter
	 */
	public boolean contains(String key) {
		return pairs.containsKey(key);
	}

	/**
	 * @param key currency symbol
	 * @return array of currency symbols that the key parameter is traded with on
	 *         this exchange
	 */
	public String[] getValue(String key) {
		return pairs.get(key);
	}

	protected void put(String key, String[] val) {
		pairs.put(key, val);
	}

}