package me.ramuta.daycare.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import me.ramuta.daycare.activity.LoginActivity.ResponseReceiver;
import me.ramuta.daycare.data.UrlHelper;
import me.ramuta.daycare.object.Login;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class AuthService extends IntentService {
	private static final String TAG = "AuthService";	

	// connection variables
	private InputStream is = null;
	private StringBuilder sb = null;
	
	// data variables
	private String streamResponse;

	// data for login service
	private Login login = new Login();
	private boolean success = false;
	public static final String SUCCESS_RESPONSE = "successresponse";
	
	public AuthService() {
		super("AuthService");
		Log.i(TAG, "login: "+login.Email+", "+login.Password);
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		
		getAuthToken();
		
		if (!UrlHelper.getAuthCookie().equals("napaka")) { // izbriši klicaj èe hoèeš v Main Activity
			Log.i(TAG, "cookie is not null");
			success = true;
			Intent intentMainService = new Intent(AuthService.this, MainService.class);
			startService(intentMainService);
		} else {
			success = false;
		}
		
		// pošlji linke prek intenta
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(ResponseReceiver.ACTION_RESP);
		broadcastIntent.addCategory(Intent.ACTION_DEFAULT);
		broadcastIntent.putExtra(SUCCESS_RESPONSE, success);
		sendBroadcast(broadcastIntent);	
	}

	// get auth token
	public void getAuthToken() {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("email", login.Email)); // email
		nameValuePairs.add(new BasicNameValuePair("password", login.Password)); // geslo
		HttpResponse response = null;
		
    	// http post 
    	try {
    	     HttpClient httpclient = new DefaultHttpClient();
    	     HttpPost httppost = new HttpPost(UrlHelper.getAuthCookieUrl());
    	     httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    	     response = httpclient.execute(httppost);
    	     HttpEntity entity = response.getEntity();
    	     is = entity.getContent();
    	} catch(Exception e){
    	     Log.e(TAG, "Error in http connection"+e.toString());
    	}
    	
    	//convert response to string
    	try {
    		BufferedReader reader = new BufferedReader(new InputStreamReader(is,HTTP.UTF_8),8);
    		sb = new StringBuilder();
    	    sb.append(reader.readLine() + "\n");
	        String line="0";	        
	        while ((line = reader.readLine()) != null) {
	        	sb.append(line + "\n");
	        }        
	        is.close();
	        streamResponse = sb.toString(); // odgovor (rezultat) ki ga dobimo po poslanem zahtevku
	        Log.i(TAG, "auth token: " + sb.toString());
    	} catch(Exception e){
    		Log.e(TAG, "Error converting result "+e.toString());
    	}
    	
    	try {
			String authCookie = response.getFirstHeader("Set-Cookie").getValue();
			
			int pos = authCookie.indexOf(".ASPXAUTH=");
			if (pos >= 0)
			{
				int pos1 = authCookie.indexOf(";", pos);
				if(pos1 >= pos)
					authCookie = authCookie.substring(pos, pos1 - pos);
			}
			Log.i(TAG, "auth cookie v responsu: "+authCookie);
			
			UrlHelper.setAuthCookie(authCookie); // save auth cookie
			
		} catch (Exception e) {
			Log.e(TAG, "error pri auth cookieju...");
			UrlHelper.setAuthCookie("napaka");
		}
	}
}
