import cryptos.CryptoInfo;
import exceptions.InvalidArgumentException;

public class CryptoInfoExample {

	public static void main(String[] args) {

		try {

			CryptoInfo cryptoInfo = CryptoInfo.getInstance("USDT", "BTC");

			cryptoInfo.update();
			System.out.println(cryptoInfo.dataToString());

		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
	}
	
}
