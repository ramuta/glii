package me.ramuta.daycare.data;

public class UrlHelper {
	private static final String AUTH_COOKIE_URL = "http://api.glii.me/api/AccountLocal/Post";
	private static final String STREAM_URL = "http://api.glii.me/api/Post/Get";
	private static final String GROUP_URL = "http://api.glii.me/api/Group/Get";
	private static final String POST_URL = "http://api.glii.me/api/Post/Post";
	private static final String CHILD_URL = "http://api.glii.me/api/Child/Get";
	private static final String GALLERY_URL = "http://api.glii.me/api/Post/GetGallery";
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

	/**
	 * @return the groupUrl
	 */
	public static String getGroupUrl() {
		return GROUP_URL;
	}

	/**
	 * @return the postUrl
	 */
	public static String getPostUrl() {
		return POST_URL;
	}

	/**
	 * @return the childUrl
	 */
	public static String getChildUrl() {
		return CHILD_URL;
	}

	/**
	 * @return the galleryUrl
	 */
	public static String getGalleryUrl() {
		return GALLERY_URL;
	}
}
