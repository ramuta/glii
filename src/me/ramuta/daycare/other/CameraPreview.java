package me.ramuta.daycare.other;

import java.io.IOException;

import me.ramuta.daycare.data.CameraHelper;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = "Preview";

	SurfaceHolder mHolder;
	public Camera camera;

	public CameraPreview(Context context) {
		super(context);

		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); // needed for android before 3
	}

	public void surfaceCreated(SurfaceHolder holder) {
		camera = Camera.open();

		try {
			camera.setPreviewDisplay(holder);

			Camera.Parameters params = camera.getParameters();
			params.setPictureSize(CameraHelper.getBestWidth(), CameraHelper.getBestHeight()); // setting the best resolution, set in Camera Activity (480x640)
			camera.setParameters(params);
			
		} catch (IOException e) {
			Log.e(TAG, "Camera preview error: ", e);
		} 
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		camera.stopPreview();
		camera.release();
		camera = null;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		camera.startPreview();
	}
}