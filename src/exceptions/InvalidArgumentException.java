package exceptions;

/**
 * @author Matt - <a href="https://github.com/mcfadd">mcfadd</a>
 * @since Cryptos4J v1.0
 */
public class InvalidArgumentException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidArgumentException(String message) {
		super("Bad URL parameters: " + message);
	}

	public InvalidArgumentException(String currencyTo, String currencyFrom) {
		super("Bad URL parameters: Invalid currencyTo = \"" + currencyTo + "\", currencyFrom = \"" + currencyFrom
				+ "\"");
	}

}
