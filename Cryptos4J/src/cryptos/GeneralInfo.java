package cryptos;

/**
 * Object equivalent to the 'GeneralInfo' json object returned by the <i>min-api.cryptocompare.com/data/coin/generalinfo</i> end point.
 * 
 * @author Matt - github.com/mcfadd
 * @since Cryptos4J v1.0
 * @see CryptoInfo
 */
public class GeneralInfo {

	private final String Id;
	private final String Name;
	private final String FullName;
	private final String Internal;
	private final String ImageUrl;
	private final String Url;
	private final String Algorithm;
	private final String ProofType;
	private final double NetHashesPerSecond;
	private final float BlockNumber;
	private final float BlockTime;
	private final float BlockReward;
	private final float Type;
	private final String DocumentType;

	protected GeneralInfo(String id, String name, String fullName, String internal, String imageUrl, String url,
			String algorithm, String proofType, double netHashesPerSecond, float blockNumber, float blockTime,
			float blockReward, float type, String documentType) {

		Id = id;
		Name = name;
		FullName = fullName;
		Internal = internal;
		ImageUrl = imageUrl;
		Url = url;
		Algorithm = algorithm;
		ProofType = proofType;
		NetHashesPerSecond = netHashesPerSecond;
		BlockNumber = blockNumber;
		BlockTime = blockTime;
		BlockReward = blockReward;
		Type = type;
		DocumentType = documentType;
	}

	public String getId() {
		return Id;
	}

	public String getName() {
		return Name;
	}

	public String getFullName() {
		return FullName;
	}

	public String getInternal() {
		return Internal;
	}

	public String getImageUrl() {
		return ImageUrl;
	}

	public String getUrl() {
		return Url;
	}

	public String getAlgorithm() {
		return Algorithm;
	}

	public String getProofType() {
		return ProofType;
	}

	public double getNetHashesPerSecond() {
		return NetHashesPerSecond;
	}

	public float getBlockNumber() {
		return BlockNumber;
	}

	public float getBlockTime() {
		return BlockTime;
	}

	public float getBlockReward() {
		return BlockReward;
	}

	public float getType() {
		return Type;
	}

	public String getDocumentType() {
		return DocumentType;
	}

	/**
	 * returns a string representation of the 'GeneralInfo' data retrieved from the <i>min-api.cryptocompare.com/data/coin/generalinfo</i> end point.
	 * @return dataToString
	 * @since Cryptos4J v1.0
	 * @see CryptoInfo#dataToString()
	 */
	public String dataToString() {

		return "Id:  \"" + Id + "\"\r\n" + "Name:  \"" + Name + "\"\r\n" + "FullName:  \"" + FullName + "\"\r\n"
				+ "Internal: " + Internal + "\r\n" + "ImageUrl: " + ImageUrl + "\r\n" + "Url: " + Url + "\r\n"
				+ "Algorithm: " + Algorithm + "\r\n" + "ProofType:  \"" + ProofType + "\"\r\n" + "NetHashesPerSecond: "
				+ NetHashesPerSecond + "\r\n" + "BlockNumber: " + BlockNumber + "\r\n" + "BlockTime: " + BlockTime
				+ "\r\n" + "BlockReward: " + BlockReward + "\r\n" + "Type: " + Type + "\r\n" + "DocumentType: "
				+ DocumentType + "\n";

	}
}
