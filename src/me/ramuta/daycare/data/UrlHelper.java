package me.ramuta.daycare.data;

public class UrlHelper {
	private static final String AUTH_COOKIE_URL = "http://api.glii.me/api/AccountLocal/Post";
	private static final String STREAM_URL = "http://api.glii.me/api/Post/GetPostsA";
	private static String authCookie;

	/** Get stream news.
	 * @return the streamUrl
	 */
	public static String getStreamUrl() {
		return STREAM_URL;
	}

	/** Get authentication cookie URL.
	 * @return the authCookie
	 */
	public static String getAuthCookieUrl() {
		return AUTH_COOKIE_URL;
	}

	/**
	 * @return the authCookie
	 */
	public static String getAuthCookie() {
		return authCookie;
	}

	/**
	 * @param authCookie the authCookie to set
	 */
	public static void setAuthCookie(String authCookie) {
		UrlHelper.authCookie = authCookie;
	}
}
