package exceptions;

/**
 * @author Matt - github.com/mcfadd
 * @since Cryptos4J v1.0
 */
public class HttpsConnectionException extends Error {

	private static final long serialVersionUID = 1L;

	public HttpsConnectionException(String response) {
		super("Failed to connect with error: " + response);
	}
	
}
