import java.util.Iterator;

import cryptos.CryptoInfo;
import exceptions.InvalidArgumentException;

public class CryptoInfoExample {

	/**
	 * This Example demonstrates how to use the Coins General Info end point. Take a
	 * look at Cryptocompare's Documentation for more info:
	 * 
	 * https://min-api.cryptocompare.com/documentation?key=Streaming&cat=coinsGeneralInfoEndpoint
	 * 
	 * parameters: 
	 *   fsym = BTC 
	 *   tsym = USDT
	 */
	public static void main(String[] args) {

		try {

			CryptoInfo cryptoInfo = CryptoInfo.getInstance("USDT", "BTC");

			cryptoInfo.update();
			System.out.println(cryptoInfo.dataToString());

			System.out.println("stream multiple example:");

			Iterator<CryptoInfo> it = CryptoInfo.streamCryptoInfo("USDT", "BTC", "ETC").iterator();

			while (it.hasNext()) {
				System.out.println(it.next().getGeneralInfo().getFullName());
			}

		} catch (InvalidArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

}
