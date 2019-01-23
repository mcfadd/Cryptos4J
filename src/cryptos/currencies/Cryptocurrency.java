package cryptos.currencies;

/**
 * Object equivalent to the json Coin object returned by the <a href=
 * "https://min-api.cryptocompare.com/documentation?key=Other&cat=allCoinsWithContentEndpoint">
 * All the Coins</a> end point.
 * 
 * @author Matt - <a href="https://github.com/mcfadd">mcfadd</a>
 * @since Cryptos4J v1.0
 * @see CryptocurrencyStreamer
 */
public class Cryptocurrency {

	private final int Id;
	private final String Url;
	private final String ImageUrl;
	private final String Name;
	private final String CoinName;
	private final String FullName;
	private final String Algorithm;
	private final String ProofType;
	private final int FullyPremined;
	private final String TotalCoinSupply;
	private final String BuiltOn;
	private final String SmartContractAddress;
	private final String PreMinedValue;
	private final String TotalCoinsFreeFloat;
	private final int SortOrder;
	private final boolean Sponsored;
	private final boolean IsTrading;

	protected Cryptocurrency(int id, String url, String imageUrl, String name, String coinName, String fullName,
			String algoritm, String proofType, int fullyPremined, String totalCoinSupply, String builtOn,
			String smartContractAddress, String preMinedValue, String totalCoinsFreeFloat, int sortOrder,
			boolean sponsored, boolean isTrading) {

		Id = id;
		Url = url;
		ImageUrl = imageUrl;
		Name = name;
		CoinName = coinName;
		FullName = fullName;
		Algorithm = algoritm;
		ProofType = proofType;
		FullyPremined = fullyPremined;
		TotalCoinSupply = totalCoinSupply;
		BuiltOn = builtOn;
		SmartContractAddress = smartContractAddress;
		PreMinedValue = preMinedValue;
		TotalCoinsFreeFloat = totalCoinsFreeFloat;
		SortOrder = sortOrder;
		Sponsored = sponsored;
		IsTrading = isTrading;
	}

	public int getId() {
		return Id;
	}

	public String getUrl() {
		return Url;
	}

	public String getImageUrl() {
		return ImageUrl;
	}

	public String getName() {
		return Name;
	}

	public String getCoinName() {
		return CoinName;
	}

	public String getFullName() {
		return FullName;
	}

	public String getAlgorithm() {
		return Algorithm;
	}

	public String getProofType() {
		return ProofType;
	}

	public int getFullyPremined() {
		return FullyPremined;
	}

	public String getTotalCoinSupply() {
		return TotalCoinSupply;
	}

	public String getBuiltOn() {
		return BuiltOn;
	}

	public String getSmartContractAddress() {
		return SmartContractAddress;
	}

	public String getPreMinedValue() {
		return PreMinedValue;
	}

	public String getTotalCoinsFreeFloat() {
		return TotalCoinsFreeFloat;
	}

	public int getSortOrder() {
		return SortOrder;
	}

	public boolean isSponsored() {
		return Sponsored;
	}

	public boolean isIsTrading() {
		return IsTrading;
	}

	/**
	 * returns a string representation of the json Coin data retrieved from the
	 * <a href=
	 * "https://min-api.cryptocompare.com/documentation?key=Other&cat=allCoinsWithContentEndpoint">
	 * All the Coins</a> end point.
	 * 
	 * @return dataToString
	 * @since Cryptos4J v1.0
	 */
	public String dataToString() {

		return "Id:  \"" + Id + "\"\r\n" + "Url:  \"" + Url + "\"\r\n" + "ImageUrl:  \"" + ImageUrl + "\"\r\n"
				+ "Name: " + Name + "\r\n" + "CoinName: " + CoinName + "\r\n" + "FullName: " + FullName + "\r\n"
				+ "Algorithm: " + Algorithm + "\r\n" + "ProofType:  \"" + ProofType + "\"\r\n" + "FullyPremined: "
				+ FullyPremined + "\r\n" + "TotalCoinSupply: " + TotalCoinSupply + "\r\n" + "BuiltOn: " + BuiltOn
				+ "\r\n" + "SmartContractAddress: " + SmartContractAddress + "\r\n" + "PreMinedValue: " + PreMinedValue
				+ "\r\n" + "TotalCoinsFreeFloat: " + TotalCoinsFreeFloat + "\r\n" + "SortOrder: " + SortOrder + "\r\n"
				+ "Sponsored: " + Sponsored + "\r\n" + "IsTrading: " + IsTrading + "\r\n" + "\n";

	}

}
