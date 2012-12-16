package me.ramuta.daycare.data;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class CameraHelper {
	private static final String TAG = "CameraHelper";
	public static final int MEDIA_TYPE_IMAGE = 1;
	private static String photoPath;
	private static final String DIRECTORY = "Glii";
	private static final String PREFIX = "GLII_";
	
	// best size
	private static int bestHeight;
	private static int bestWidth;
	
	// variables for finding the best size
	private static ArrayList<String> sizesArray = new ArrayList<String>();
	private static String bestArray;
	private static String bestIndex;
	
	/** Create a file Uri for saving an image or video */
    public static Uri getOutputMediaFileUri(int type){
          return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    public static File getOutputMediaFile(int type){
    	Log.i(TAG, "A je SD priklopljen? "+Environment.getExternalStorageState());

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), DIRECTORY);
			
		// This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.e(DIRECTORY, "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //String timeStamp = "slika346";
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            		PREFIX+ timeStamp + ".jpg");
            System.out.println(mediaStorageDir.getPath() + File.separator + PREFIX+ timeStamp + ".jpg");
            String fajlPath = mediaStorageDir.getPath() + File.separator + PREFIX+ timeStamp + ".jpg";
            Log.i(TAG, "fajlPath: "+fajlPath);
            photoPath = fajlPath;
            
        } else {
            	return null;
        }
        // TODO: daj photoPath v LefemmeUser statièno metodo
        setPhotoPath("/mnt/sdcard/Pictures/"+DIRECTORY+"/"+PREFIX+ timeStamp + ".jpg");        
        return mediaFile;
    }

	/**
	 * @return the photoPath
	 */
	public static String getPhotoPath() {
		return photoPath;
	}

	/**
	 * @param photoPath the photoPath to set
	 */
	public static void setPhotoPath(String photoPath) {
		CameraHelper.photoPath = photoPath;
	}

	/**
	 * @return the bestHeight
	 */
	public static int getBestHeight() {
		return bestHeight;
	}

	/**
	 * @param bestHeight the bestHeight to set
	 */
	public static void setBestHeight(int bestHeight) {
		CameraHelper.bestHeight = bestHeight;
	}

	/**
	 * @return the bestWidth
	 */
	public static int getBestWidth() {
		return bestWidth;
	}

	/**
	 * @param bestWidth the bestWidth to set
	 */
	public static void setBestWidth(int bestWidth) {
		CameraHelper.bestWidth = bestWidth;
	}
	
	/**
	 * @return the sizesArray
	 */
	public static ArrayList<String> getSizesArray() {
		return sizesArray;
	}

	/**
	 * @param sizesArray the sizesArray to set
	 */
	public static void setSizesArray(ArrayList<String> sizesArray) {
		CameraHelper.sizesArray = sizesArray;
	}
	
	public static int closest(int of, List<Integer> in) {
	    int min = Integer.MAX_VALUE;
	    int closest = of;
	    int index = 0;

	    for (int v : in) {
	        final int diff = Math.abs(v - of);

	        if (diff < min) {
	            min = diff;
	            closest = v;
	        }
	        index++;
	    }

	    return closest;
	}
	
	public static int closestIndex(int of, List<Integer> in) {
	    int index = in.indexOf(of);
	    return index;
	}

	/**
	 * @return the bestArray
	 */
	public static String getBestArray() {
		return bestArray;
	}

	/**
	 * @param bestArray the bestArray to set
	 */
	public static void setBestArray(String bestArray) {
		CameraHelper.bestArray = bestArray;
	}

	/**
	 * @return the bestIndex
	 */
	public static String getBestIndex() {
		return bestIndex;
	}

	/**
	 * @param bestIndex the bestIndex to set
	 */
	public static void setBestIndex(String bestIndex) {
		CameraHelper.bestIndex = bestIndex;
	}
}
