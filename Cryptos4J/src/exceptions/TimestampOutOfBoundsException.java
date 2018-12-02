package exceptions;

/**
 * @author Matt - github.com/mcfadd
 * @since Cryptos4J v1.0
 */
public class TimestampOutOfBoundsException extends Exception{

	private static final long serialVersionUID = 1L;

	public TimestampOutOfBoundsException(long timestamp) {
		super("Timestamp out of bounds! ts = " + timestamp);
	}
	
}
