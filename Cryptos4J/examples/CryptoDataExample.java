import cryptos.CryptoData;
import cryptos.exchanges.CryptoExchanges;
import exceptions.InvalidArgumentException;

public class CryptoDataExample {

	public static void main(String[] args) {

		try {

			CryptoData cryptoData = CryptoData.getInstance("USDT", "BTC", CryptoExchanges.exchanges.Binance);

			cryptoData.update();
			System.out.println(cryptoData.dataToString());

		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
	}

}
