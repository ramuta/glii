package me.ramuta.daycare.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import me.ramuta.daycare.activity.AddNewsActivity;
import me.ramuta.daycare.activity.AddPhotoActivity;

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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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
		
		boolean withPhoto = intent.getBooleanExtra(AddNewsActivity.WITH_PHOTO, false); // true: with photo, false: without photo
		String comment = intent.getStringExtra(AddNewsActivity.COMMENT); // comment
		
		String photoPath = null;
		
		// if there is a photo, get it's encoded string from intent
		if (withPhoto) {
			photoPath = intent.getStringExtra(AddPhotoActivity.PHOTO_PATH);
		}
		
		Log.i(TAG, "comment: "+comment+", with photo? "+withPhoto+", photoPath: "+photoPath);
		
		addNews(withPhoto, comment, photoPath);
	}

	// get home stream
	private void addNews(boolean withPhoto, String comment, String photoPath) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		//nameValuePairs.add(new BasicNameValuePair("UserID", "1")); // user id
		nameValuePairs.add(new BasicNameValuePair("Note", comment)); // comment		
		
		if (withPhoto) {
			Log.i(TAG, "also sending photo");
			String encodedPhoto = encode64(photoPath);
			nameValuePairs.add(new BasicNameValuePair("Photo", encodedPhoto)); // photo string
		}
	   	
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
	
	private String encode64(String photoPath) {
		Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
		
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
		byte[] byteArray = bao.toByteArray();
		String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
		return encodedImage;
	}
}
