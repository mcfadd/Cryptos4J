package utils;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Utility class for setting a static api key as well as converting timestamps
 * to calendars and visa-versa.
 * 
 * @author Matt - <a href="https://github.com/mcfadd">mcfadd</a>
 * @since Cryptos4J v1.0
 */
public class Util {

	private static String apiKey;

	/**
	 * gets the api key parameter (if any) to be used - see <a href=
	 * "https://www.cryptocompare.com/coins/guides/how-to-use-our-api/">cryptocompare.com/coins/guides/how-to-use-our-api/</a>
	 * for more info.
	 * 
	 * @return apiKey
	 * @since Cryptos4J v1.0
	 */
	public static String getApiKey() {
		return apiKey;
	}

	/**
	 * sets the api key parameter to be used - see <a href=
	 * "https://www.cryptocompare.com/coins/guides/how-to-use-our-api/">cryptocompare.com/coins/guides/how-to-use-our-api/</a>
	 * for more info.
	 * 
	 * @param api_key api key parameter to use
	 * @since Cryptos4J v1.0
	 */
	public static void setApiKey(String api_key) {
		apiKey = api_key;
	}

	/**
	 * returns calendar as a string in the format "YYYY-MM-DD HH:MM:SS"
	 * 
	 * @param calendar calendar to convert
	 * @return formatted string of calendar parameter
	 * @since Cryptos4J v1.0
	 */
	public static String calendarToString(Calendar calendar) {
		return String.format("%04d-%02d-%02d %02d:%02d:%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),
				calendar.get(Calendar.SECOND));
	}

	/**
	 * returns a GMT time zone calendar as a string with time equal to unixTimestamp
	 * 
	 * @param unixTimestamp timestamp to convert
	 * @return formatted string
	 * @since Cryptos4J v1.0
	 */
	public static String timestampToGMTCalendarString(long unixTimestamp) {
		return calendarToString(createGMTzoneCalendar(unixTimestamp));
	}

	/**
	 * returns a GMT time zone calendar with time equal to unixTimestamp
	 * 
	 * @param unixTimestamp timestamp to convert
	 * @return GMT calendar with unixTimestamp
	 * @since Cryptos4J v1.0
	 */
	public static Calendar createGMTzoneCalendar(long unixTimestamp) {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		c.setTimeInMillis(unixTimestamp * 1000);
		return c;
	}

}
