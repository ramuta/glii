package me.ramuta.daycare.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.ramuta.daycare.data.DataHolder;
import me.ramuta.daycare.data.UrlHelper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;

import android.app.IntentService;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.util.Log;

public class MainService extends IntentService {
	private static final String TAG = "MainService";
	
	// connection variables
	private InputStream is = null;
	private StringBuilder sb = null;
	
	// data variables
	private String streamResponse;
	private DataHolder dataHolder = new DataHolder();
	
	// connection timeout, in milliseconds (waiting to connect)
	private static final int CONN_TIMEOUT = 3000;

	// socket timeout, in milliseconds (waiting for data)
	private static final int SOCKET_TIMEOUT = 5000;
	
	public MainService() {
		super("MainService");
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		// TODO Auto-generated method stub
		Log.i(TAG, "zaèetek servicea");
		
		getStream5();
		
	}
	
	public void getStream5() {		
		try {
			// parametri - niso nujni
			HttpParams myParams = new BasicHttpParams();
			HttpConnectionParams.setSoTimeout(myParams, 10000);
			HttpConnectionParams.setConnectionTimeout(myParams, 10000); // Timeout
			
			// http client in httpget
			HttpClient httpclient = new DefaultHttpClient(myParams);
			HttpGet httpget = new HttpGet(UrlHelper.getStreamUrl());
			
			Log.i(TAG, "auth cookie: "+UrlHelper.getAuthCookie()); // cookie v LogCatu, poglej èe je isti kot tisti ki pride do web apija
						
			httpget.setHeader("Cookie", UrlHelper.getAuthCookie()); // dodamo cookie v header
			
			HttpResponse response = httpclient.execute(httpget); // PROBLEM: v responseu dobimo 401 unauthorized. Preveri ali pride cookie do serverja in kakšen je (primerjaj ga s cookijem v logu)
			
			HttpEntity entity = response.getEntity();
   	     	is = entity.getContent();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//convert response to string
    	try {
    		BufferedReader reader = new BufferedReader(new InputStreamReader(is
    				,HTTP.UTF_8
    				),65728);
    		sb = new StringBuilder();
    	    sb.append(reader.readLine() + "\n");
	        String line="0";	        
	        while ((line = reader.readLine()) != null) {
	        	sb.append(line + "\n");
	        }        
	        is.close();
	        Log.i(TAG, "Stream get response: " + sb.toString());
	        streamResponse = sb.toString(); // odgovor (rezultat) ki ga dobimo po poslanem zahtevku
	        dataHolder.setPostObjects(streamResponse); // make post objects from json response	        
    	} catch(Exception e){
    		Log.e(TAG, "Error converting result "+e.toString());
    	}
	}
	
	/*
	private HttpParams getHttpParams() {
		HttpParams htpp = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
		HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

		return htpp;
	}
	*/

	/*
	// get home stream
	public void getStream() {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		//nameValuePairs.add(new BasicNameValuePair("fb_user_id", user.getFB_ID())); // post values
	   	
    	// http post 
    	try {
    	     HttpClient httpclient = new DefaultHttpClient();
    	     HttpPost httppost = new HttpPost(UrlHelper.getStreamUrl());
    	     httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    	     //httppost.setHeader("Accept","application/json");
    	     //httppost.setHeader("Content-type","application/json");
    	     httppost.setHeader("Cookie", UrlHelper.getAuthCookie());
    	     HttpResponse response = httpclient.execute(httppost);
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
	        Log.i(TAG, "PHOTOS: " + sb.toString());
    	} catch(Exception e){
    		Log.e(TAG, "Error converting result "+e.toString());
    	}
	}
	*/

		/*
		public void getStream4() {
			URL url = null;
			try {
				url = new URL(UrlHelper.getStreamUrl());
			} catch (MalformedURLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			   HttpURLConnection urlConnection = null;
			try {
				urlConnection = (HttpURLConnection) url.openConnection();
				//urlConnection.setDoInput(true);
				//urlConnection.setDoOutput(true);
				urlConnection.setRequestProperty("Cookie", UrlHelper.getAuthCookie());
				
				
				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(in,HTTP.UTF_8),8);
	    		sb = new StringBuilder();
	    	    sb.append(reader.readLine() + "\n");
		        String line="0";	        
		        while ((line = reader.readLine()) != null) {
		        	sb.append(line + "\n");
		        }        
		        in.close();
		        streamResponse = sb.toString(); // odgovor (rezultat) ki ga dobimo po poslanem zahtevku
		        Log.i(TAG, "PHOTOS4: " + sb.toString());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
			     urlConnection.disconnect();
			   }

			   
		} */
}
