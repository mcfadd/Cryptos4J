# Cryptos4J v1.0
**Java API for https://min-api.cryptocompare.com/**  
This API retrieves Cryptocurrency information such as price and historical data from Cryptocomapre's Restful API.
<br>
<br>
**(optional)**  
After creating an account and api key with Cryptocompare, Cryptos4J allows you to use it.  
Take a look at Cryptocompare's [how-to-use-our-api](https://www.cryptocompare.com/coins/guides/how-to-use-our-api/) guide for more info on api keys and how to use them.  
### Dependencies
* [Google gson](https://github.com/google/gson) for parsing json - simply add [gson.jar](https://github.com/mcfadd/Cryptos4J/blob/master/Cryptos4J/gson-2.2.4.jargson.jar) to your build path  
### Documentation
* [API Doc](https://min-api.cryptocompare.com/documentation): Cryptocompare's documentation on all of their end points
* [API Javadoc](https://mcfadd.github.io/Cryptos4J/): Cryptos4J documentation

### Supported end points
* [Single Symbol Price](https://min-api.cryptocompare.com/documentation?key=Price&cat=SingleSymbolPriceEndpoint):
Get the current price of any cryptocurrency in any other currency that you need.
* [Multiple Symbols Price](https://min-api.cryptocompare.com/documentation?key=Price&cat=multipleSymbolsPriceEndpoint):
Same as Single Symbol Price but with multiple currency from symbols.
* [Generate Custom Average](https://min-api.cryptocompare.com/documentation?key=Price&cat=generateAverageEndpoint):
Compute the current trading info (price, vol, open, high, low etc) of the requested pair as a volume weighted average based on the exchanges requested.
* [Historical Daily OHLCV](https://min-api.cryptocompare.com/documentation?key=Historical&cat=dataHistoday):
Get open, high, low, close, volumefrom and volumeto from daily historical data.
* [Historical Hourly OHLCV](https://min-api.cryptocompare.com/documentation?key=Historical&cat=dataHistohour):
Get open, high, low, close, volumefrom and volumeto from hourly historical data.
* [Historical Minute OHLCV](https://min-api.cryptocompare.com/documentation?key=Historical&cat=dataHistominute):
Get open, high, low, close, volumefrom and volumeto from minute historical data.
* [Coins General Info](https://min-api.cryptocompare.com/data/coin/generalinfo?fsyms=BTC,MLN,DASH&tsym=USD):
Get combinations of subs and pricing info in order to know what needs to be streamed and how to connect to the streamers.
* [All Exchanges and Trading Pairs](https://min-api.cryptocompare.com/documentation?key=Other&cat=allExchangesEndpoint):
Returns all the exchanges that CryptoCompare has integrated with.
* [All the Coins](https://min-api.cryptocompare.com/documentation?key=Other&cat=allCoinsWithContentEndpoint):
Returns all the coins that CryptoCompare has added to the website.  

Take a look at some [Examples](https://github.com/mcfadd/Cryptos4J/tree/master/Cryptos4J/examples) for how to use Cryptos4J.  

### License

Cryptos4J is released under the [MIT License](LICENSE).
```
MIT License

Copyright (c) 2018 Matthew McFadden

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```  
