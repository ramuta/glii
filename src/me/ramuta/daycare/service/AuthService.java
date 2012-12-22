package me.ramuta.daycare.service;

import me.ramuta.daycare.activity.LoginActivity.ResponseReceiver;
import me.ramuta.daycare.data.UrlHelper;
import me.ramuta.daycare.object.Login;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.google.gson.Gson;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class AuthService extends IntentService {
	private static final String TAG = "AuthService";

	// data for login service
	private Login login = new Login();
	private boolean success = false;
	public static final String SUCCESS_RESPONSE = "successresponse";
	
	// connection timeout, in milliseconds (waiting to connect)
	private static final int CONN_TIMEOUT = 3000;

	// socket timeout, in milliseconds (waiting for data)
	private static final int SOCKET_TIMEOUT = 5000;
	
	public AuthService() {
		super("AuthService");
		Log.i(TAG, "login: "+login.Email+", "+login.Password);
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		try {
			UrlHelper.setAuthCookie(postLogin(login));
			Log.i(TAG, "auth cookie v url helperju: "+UrlHelper.getAuthCookie());
		} catch (Exception e) {
			Log.e(TAG, "Problem setting auth cookie.");
		}
		
		if (UrlHelper.getAuthCookie().equals("napaka")) { // izbriši klicaj èe hoèeš v Main Activity
			Log.i(TAG, "cookie is not null");
			success = true;
			Intent intentMainService = new Intent(AuthService.this, MainService.class);
			startService(intentMainService);
		}
		
		// pošlji linke prek intenta
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(ResponseReceiver.ACTION_RESP);
		broadcastIntent.addCategory(Intent.ACTION_DEFAULT);
		broadcastIntent.putExtra(SUCCESS_RESPONSE, success);
		sendBroadcast(broadcastIntent);	
	}
	
	public String postLogin(Login login) throws Exception {
		try {
			String url = UrlHelper.getAuthCookieUrl() + "/accountlocal"; // TODO: URL

			HttpResponse response = doResponse(url, RequestMethod.POST, login);

			if (response == null || response.getStatusLine().getStatusCode() != 200) {
				throw new Exception("Unable to post login.");
			}
			
			String authCookie = response.getFirstHeader("Set-Cookie").getValue();
			
			int pos = authCookie.indexOf(".ASPXAUTH=");
			if (pos >= 0)
			{
				int pos1 = authCookie.indexOf(";", pos);
				if(pos1 >= pos)
					authCookie = authCookie.substring(pos, pos1 - pos);
			}
			Log.i(TAG, "auth cookie v responsu: "+authCookie);
			return authCookie;
		} catch (Exception e) {
			return "napaka";
		}
	}
	
	private HttpResponse doResponse(String url, RequestMethod method, Object data) {

		HttpClient httpclient = new DefaultHttpClient(getHttpParams());

		HttpResponse response = null;

		try {
			switch (method) {

			case POST:
			{
				HttpPost httppost = new HttpPost(url);
				HttpParams params = httppost.getParams();
				HttpConnectionParams.setConnectionTimeout(params, 30000);
				httppost.setParams(params);
				
				Gson gson = new Gson();
				String message = gson.toJson(data);

				StringEntity s = new StringEntity(message);
				s.setContentEncoding("UTF-8");
				s.setContentType("application/json");
				httppost.setEntity(s);

				response = httpclient.execute(httppost);
				break;
			}
			case GET:
				HttpGet httpget = new HttpGet(url);
				httpget.addHeader("Cookie", data.toString());
				response = httpclient.execute(httpget);
				break;
			case PUT:
			{
				HttpPut httpput = new HttpPut(url);
				Gson gson = new Gson();
				String message = gson.toJson(data);

				StringEntity s = new StringEntity(message);
				s.setContentEncoding("UTF-8");
				s.setContentType("application/json");
				httpput.setEntity(s);

				response = httpclient.execute(httpput);
				break;
			}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}
		return response;
	}

	private HttpParams getHttpParams() {
		HttpParams htpp = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
		HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

		return htpp;
	}
	
	public enum RequestMethod {
		GET, POST, PUT
	}
}
