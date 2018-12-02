import java.util.Iterator;

import cryptos.currencies.Currencies;
import cryptos.currencies.Cryptocurrency;

public class StreamCurrenciesExample {

	public static void main(String[] args) {

		Iterator<Cryptocurrency> it = Currencies.streamCurrencies("BTC").iterator();
		while (it.hasNext()) {

			Cryptocurrency currency = it.next();
			
			System.out.println(currency.dataToString());

		}

	}
	
}
