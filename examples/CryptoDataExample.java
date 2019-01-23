import cryptos.CryptoData;
import cryptos.exchanges.ExchangeStreamer;
import exceptions.InvalidArgumentException;

public class CryptoDataExample {

	/**
	 * This Example demonstrates how to use the Generate Custom Average end point.
	 * Take a look at Cryptocompare's Documentation for more info:
	 * 
	 * https://min-api.cryptocompare.com/documentation?key=Price&cat=generateAverageEndpoint
	 * 
	 * parameters: 
	 *   fsym = BTC 
	 *   tsym = USDT
	 *   e = Binance
	 */
	public static void main(String[] args) {

		try {

			CryptoData cryptoData = CryptoData.getInstance("USDT", "BTC", ExchangeStreamer.exchanges.Binance);

			cryptoData.update();
			System.out.println(cryptoData.dataToString());

		} catch (InvalidArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

}
