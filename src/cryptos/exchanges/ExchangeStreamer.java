package cryptos.exchanges;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import com.google.gson.Gson;

import utils.HttpsConnector;

/**
 * Class for streaming Exchange objects from the <a href=
 * "https://min-api.cryptocompare.com/documentation?key=Other&cat=allExchangesEndpoint">
 * All the Exchanges and Trading Pairs</a> end point.
 * 
 * @author Matt - <a href="https://github.com/mcfadd">mcfadd</a>
 * @since Cryptos4J v1.0
 * @see Exchange
 */
public class ExchangeStreamer {

	/**
	 * streams Exchange objects by connecting to the <a href=
	 * "https://min-api.cryptocompare.com/documentation?key=Other&cat=allExchangesEndpoint">
	 * All the Exchanges and Trading Pairs</a> end point.
	 * 
	 * @param exchanges array of exchanges to stream
	 * @return stream of Exchange objects
	 * @since Cryptos4J v1.0
	 */
	public static Stream<Exchange> streamExchanges(ExchangeStreamer.exchanges... exchanges) {

		String json = HttpsConnector
				.connect("https://min-api.cryptocompare.com/data/all/exchanges?extraParams=Cryptos4J");
		Builder<Exchange> streamBuilder = Stream.builder();
		Gson gson = new Gson();

		for (int i = 0; i < exchanges.length; i++) {

			Matcher entryMatcher = Pattern.compile("\"" + exchanges[i] + "\":\\{(.*?)\\}").matcher(json);
			if (entryMatcher.find()) {

				String tmp = entryMatcher.group();
				Matcher coinFromMatcher = Pattern.compile("(\\{|\\],)\"(.*?)\":").matcher(tmp);
				Matcher arrayMatcher = Pattern.compile("\\[(.*?)\\]").matcher(tmp);
				Exchange exchangeObject = new Exchange(exchanges[i]);

				while (coinFromMatcher.find() & arrayMatcher.find()) {

					exchangeObject.put(coinFromMatcher.group().replaceAll("(\\{|\\]|,|\"|:)", ""),
							gson.fromJson(arrayMatcher.group(), String[].class));

				}
				streamBuilder.accept(exchangeObject);
			}
		}

		return streamBuilder.build();

	}

	/**
	 * streams all Exchange objects by connecting to the <a href=
	 * "https://min-api.cryptocompare.com/documentation?key=Other&cat=allExchangesEndpoint">
	 * All the Exchanges and Trading Pairs</a> end point.
	 * 
	 * @return stream of all Exchange objects
	 * @since Cryptos4J v1.0
	 */
	public static Stream<Exchange> streamAllExchanges() {

		String json = HttpsConnector
				.connect("https://min-api.cryptocompare.com/data/all/exchanges?extraParams=Cryptos4J");
		Builder<Exchange> streamBuilder = Stream.builder();
		Gson gson = new Gson();

		Matcher entryMatcher = Pattern.compile("\"([a-zA-Z0-9])+\":\\{(.*?)\\}").matcher(json);
		while (entryMatcher.find()) {

			String tmp = entryMatcher.group();
			Matcher exchangeMatcher = Pattern.compile("\"(.*?)\":").matcher(tmp);
			Matcher coinFromMatcher = Pattern.compile("(\\{|\\],)\"(.*?)\":").matcher(tmp);
			Matcher arrayMatcher = Pattern.compile("\\[(.*?)\\]").matcher(tmp);
			exchangeMatcher.find();

			Exchange ex = new Exchange(
					ExchangeStreamer.exchanges.valueOf(exchangeMatcher.group().replaceAll("(\"|:)", "")));

			while (coinFromMatcher.find() & arrayMatcher.find()) {

				ex.put(coinFromMatcher.group().replaceAll("(\\{|\\]|,|\"|:)", ""),
						gson.fromJson(arrayMatcher.group(), String[].class));

			}
			streamBuilder.accept(ex);
		}
		return streamBuilder.build();

	}

	/**
	 * enum for all of the supported Exchanges.<br>
	 * That is, all the exchanges returned by the
	 * <i>https://min-api.cryptocompare.com/data/all/exchanges</i> end point.
	 * 
	 * @author Matt - github.com/mcfadd
	 * @since Cryptos4J v1.0
	 */
	public enum exchanges {
		Cryptsy, BTCChina, Bitstamp, BTER, OKCoin, Coinbase, Poloniex, Cexio, BTCE, BitTrex, Kraken, Bitfinex, Yacuna,
		LocalBitcoins, Yunbi, itBit, HitBTC, btcXchange, BTC38, Coinfloor, Huobi, CCCAGG, LakeBTC, Bit2C, Coinsetter,
		CCEX, Coinse, MonetaGo, Gatecoin, Gemini, CCEDK, Cryptopia, Exmo, Yobit, Korbit, BitBay, BTCMarkets, Coincheck,
		QuadrigaCX, BitSquare, Vaultoro, MercadoBitcoin, Bitso, Unocoin, BTCXIndia, Paymium, TheRockTrading, bitFlyer,
		Quoine, Luno, EtherDelta, bitFlyerFX, TuxExchange, CryptoX, Liqui, MtGox, BitMarket, LiveCoin, Coinone, Tidex,
		Bleutrade, EthexIndia, Bithumb, CHBTC, ViaBTC, Jubi, Zaif, Novaexchange, WavesDEX, Binance, Lykke, Remitano,
		Coinroom, Abucoins, BXinth, Gateio, HuobiPro, OKEX, EXX, Kucoin, TrustDEX, BitGrail, BitZ, TradeSatoshi,
		BitFlip, Foxbit, Surbitcoin, ChileBit, VBTC, Coincap, Coinnest, Velox, LAToken, BitBank, Graviex, ExtStock,
		Upbit, IDEX, DSX, CoinEx, Braziliex, Bitlish, AidosMarket, TokenStore, CoinDeal, Ethfinex, Buda, CoinsBank,
		CoinCorner, BitMart, BTCTurk, Neraex, ZB, LBank, Bibox, Bitmex, DDEX, SingularityX, Nebula, Simex, RightBTC,
		WEX, Bluebelt, ABCC, OpenLedger, Kuna, CryptoCarbon, BitexBook, WorldCryptoCap, FCoin, BigONE, CoinBene,
		IndependentReserve, Qryptos, HADAX, BTCBOX, Hikenex, Nexchange, CryptoBulls, IDAX, Tokenomy, CoinHub, DEx,
		Ironex, Cryptagio, Globitex, Nlexch, Ore, CoinFalcon, Bitsane, TDAX, ACX, BTCAlpha, FCCE, Zecoex, InstantBitex,
		CoinJar, CoinPulse, Cryptonit, Bitinfi, Gneiss, Liqnet, BCEX, Bitforex, IQFinex, P2PB2B, StocksExchange,
		iCoinbay, Everbloom, CoinTiger, Liquid, Threexbit, Switcheo, Coinsbit, EXRATES, Bitkub, DigiFinex, Gnosis,
		Bitshares, NDAX, Coinmate, Nuex, Incorex, Ethermium, Blackturtle, Catex, Minebit, Exenium, StocksExchangeio,
		CBX, ZBG, Codex, SafeCoin;

	}

}
