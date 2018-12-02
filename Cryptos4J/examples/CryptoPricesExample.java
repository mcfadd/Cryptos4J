import java.util.Iterator;

import cryptos.currencies.Currencies;

public class CryptoPricesExample {

	public static void main(String[] args) {
		
		System.out.println("Get single crypto prices:");
		String[] fiats = {"USD", "EUR"};
		
		Iterator<Double> it = Currencies.streamCryptoPrice("BTC", fiats).iterator();
		
		for(String f: fiats) {
			System.out.println(f + ": " + it.next());
		}
		
		System.out.println("\nGet multiple crypto prices:");
		String[] cryptos = {"BTC", "ETC"};
		
		it = Currencies.streamCryptoPrice(cryptos, fiats).iterator();
		
		int i = 0;
		while(it.hasNext()) {
			System.out.println(fiats[i++] + ": " + it.next());
			if(i == fiats.length)
				i = 0;
		}
		
	}
	
}
