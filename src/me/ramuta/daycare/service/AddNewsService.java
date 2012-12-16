package me.ramuta.daycare.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

public class AddNewsService extends IntentService {
	private static final String TAG = "AddNewsService";
	
	// connection variables
	private InputStream is = null;
	private StringBuilder sb = null;
	
	// data variables
	private String streamResponse;

	public AddNewsService() {
		super("AddNewsService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i(TAG, "Service started");
		addNews();
	}

	// get home stream
	public void addNews() {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("UserID", "1")); // post values
		nameValuePairs.add(new BasicNameValuePair("Note", "èæžšðèžæžð")); // post values
	   	
    	// http post 
    	try {
    	     HttpClient httpclient = new DefaultHttpClient();
    	     HttpPost httppost = new HttpPost("http://api.glii.me/api/Post");
    	     httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
    	     //httppost.setHeader("Accept","application/json");
    	     //httppost.setHeader("Content-type","application/json");
    	     HttpResponse response = httpclient.execute(httppost);
    	     HttpEntity entity = response.getEntity();
    	     is = entity.getContent();
    	} catch(Exception e){
    	     Log.e(TAG, "Error in http connection"+e.toString());
    	}
    	
    	//convert response to string
    	try {
    		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    		sb = new StringBuilder();
    	    sb.append(reader.readLine() + "\n");
	        String line="0";	        
	        while ((line = reader.readLine()) != null) {
	        	sb.append(line + "\n");
	        }        
	        is.close();
	        streamResponse = sb.toString(); // odgovor (rezultat) ki ga dobimo po poslanem zahtevku
	        Log.i(TAG, "response: " + sb.toString());
    	} catch(Exception e){
    		Log.e(TAG, "Error converting result "+e.toString());
    	}
	}
}
