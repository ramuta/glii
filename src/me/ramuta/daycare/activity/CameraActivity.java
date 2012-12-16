package me.ramuta.daycare.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.ramuta.daycare.R;
import me.ramuta.daycare.data.CameraHelper;
import me.ramuta.daycare.other.CameraPreview;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class CameraActivity extends Activity {
	  private static final String TAG = "CameraActivity";
	  CameraPreview preview;
	  Button takePhoto;
	  Camera camera;
	  FrameLayout frameLay;
	  int bestH = 640;
	  int bestW = 480;
	  private File imageFile;

	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_camera);	    

	    // find camera resolutions and set 480x640
		findCameraSize();
		
		preview = new CameraPreview(this);
		
	    frameLay = ((FrameLayout) findViewById(R.id.camera_preview));
	    
	    RelativeLayout.LayoutParams layParams = new RelativeLayout.LayoutParams(bestW, bestH);
	    frameLay.setLayoutParams(layParams);
	    frameLay.addView(preview);
	    
	    takePhoto = (Button) findViewById(R.id.camera_shot_button);
	    takePhoto.setOnClickListener(new OnClickListener() {
	      public void onClick(View v) {
	    	  preview.camera.autoFocus(cb); // set autofocus for better photos
	        
//		        Intent intent = new Intent(CameraActivity.this, DataActivity.class);
//		        startActivity(intent);
	      }
	    });


	    Log.d(TAG, "onCreate'd");
	  }
	  
	  private AutoFocusCallback cb = new AutoFocusCallback() {
		
		@Override
		public void onAutoFocus(boolean success, Camera camera) {
			// after photo is focused, take a photo
			preview.camera.takePicture(shutterCallback, rawCallback, jpegCallback);	
		}
	};	  
	
	/** Go to AddPhotoActivity. */
    private void goToAddItemActivity(String picPath) {
    	Intent addPhotoIntent = new Intent(this, AddPhotoActivity.class);
    	addPhotoIntent.putExtra(MainActivity.PIC_PATH, picPath);
    	startActivity(addPhotoIntent);
    }

	/** Find the resolutions that certain phone's camera supports and sets the one closest to 640X480 */
	  private void findCameraSize() {
		  	camera = Camera.open();
		  	List<Camera.Size> sizes = camera.getParameters().getSupportedPictureSizes();
			
			List<Integer> visine = new ArrayList<Integer>();
			List<Integer> sirine = new ArrayList<Integer>();
			
			for (int x = 0; x < sizes.size(); x++) {
				int xH = sizes.get(x).height;
				int xW = sizes.get(x).width;
				String skupna = "višina X širina: "+xH+" X "+xW;
				Log.i(TAG, skupna);
				CameraHelper.getSizesArray().add(skupna);
				visine.add(xH);
				sirine.add(xW);
			}
	
			int closest = CameraHelper.closest(480, visine);
			int closestIndex = CameraHelper.closestIndex(closest, visine);
			Log.i(TAG, "najbližji indeks: "+closestIndex);
			Log.i(TAG, "najbližja: "+closest);
			
			bestH = sizes.get(closestIndex).height;
			bestW = sizes.get(closestIndex).width;
			
			CameraHelper.setBestHeight(bestH);
			CameraHelper.setBestWidth(bestW);
			
			String bestArray = "Najboljše razmerje: "+bestH+" X "+bestW;
			
			Log.i(TAG, bestArray);
			
			CameraHelper.setBestArray(bestArray);
			CameraHelper.setBestIndex(""+closestIndex);
			
			camera.release();
	  }

	  // Called when shutter is opened
	  ShutterCallback shutterCallback = new ShutterCallback() {
	    public void onShutter() {
	      Log.d(TAG, "onShutter'd");
	    }
	  };

	  // Handles data for raw picture
	  PictureCallback rawCallback = new PictureCallback() {
	    public void onPictureTaken(byte[] data, Camera camera) {
	      Log.d(TAG, "onPictureTaken - raw");
	    }
	  };

	  // Handles data for jpeg picture
	  PictureCallback jpegCallback = new PictureCallback() {
	    public void onPictureTaken(byte[] data, Camera camera) {
	      FileOutputStream outStream = null;
	      try {
	        // Write to SD Card
	    	imageFile = CameraHelper.getOutputMediaFile(CameraHelper.MEDIA_TYPE_IMAGE); // create a file to save the image
	        outStream = new FileOutputStream(imageFile);
	        outStream.write(data);
	        outStream.close();
	        Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);
	        Camera.Size size22 = camera.getParameters().getPictureSize();
	        Log.i(TAG, "height: "+size22.height+", width: "+size22.width);
	        
	        // get photo path and go to AddPhotoActivity;
	        String photoPath = CameraHelper.getPhotoPath();
			Log.i(TAG, "CA path: "+photoPath);
			goToAddItemActivity(photoPath);
			CameraActivity.this.finish();
	      } catch (FileNotFoundException e) {
	        e.printStackTrace();
	      } catch (IOException e) {
	        e.printStackTrace();
	      } finally {
	      }
	      Log.d(TAG, "onPictureTaken - jpeg");
	    }
	  };
	}