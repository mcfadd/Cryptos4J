package utils;

import exceptions.HttpsConnectionException;
import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * Utility class for retrieving json data.
 * 
 * @author Matt - github.com/mcfadd
 * @since Cryptos4J v1.0
 */
public class HttpsConnector {

	private static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36";

	/**
	 * gets json data from the url specified
	 * @param url - url to get json data from
	 * @return json
	 * @throws HttpsConnectionException
	 */
	public static String connect(String url) throws HttpsConnectionException {

		HttpsURLConnection connection;
		String response = "Could not connect";
		try {

			connection = (HttpsURLConnection) (new URL(url)).openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.addRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("charset", "utf-8");
			connection.setRequestProperty("User-Agent", userAgent);
			connection.connect();

			if (connection.getResponseCode() == 200) {
				return extracted(connection.getInputStream()).useDelimiter("\\Z").next();
			}

			response = connection.getResponseMessage();

		} catch (IOException e) {
		}
		throw new HttpsConnectionException(response);

	}

	private static Scanner extracted(InputStream inStream) {
		return new Scanner(inStream, "UTF-8");
	}

	/**
	 * sets the user agent to use for this HttpConnector
	 * @param user_agent - user agent to use
	 */
	public static void setUserAgent(String user_agent) {
		userAgent = user_agent;
	}

}
