package com.qburst.contactlistupdater;

import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("ViewConstructor")
public class Preview extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = "Preview";
	SurfaceHolder mHolder;
	public Camera mCamera;

	@SuppressWarnings("deprecation")
	Preview(Context context, Camera camera) {
		super(context);
		mCamera = camera;
		mHolder = getHolder();
		mHolder.addCallback(this);
		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

		if (mHolder.getSurface() == null) { // preview surface does not exist
			return;

		}
		// stop preview before making changes
		try {
			mCamera.stopPreview();
		} catch (Exception e) { // ignore: tried to stop a non-existent preview

		}

		// set preview size and make any resize, rotate or
		// reformatting changes here
		Camera.Parameters parameters = mCamera.getParameters();
		List<Size> sizes = parameters.getSupportedPictureSizes();

		// Choose any one you want among sizes
		Size size = sizes.get(0);
		parameters.setPictureSize(size.width, size.height);

		mCamera.setParameters(parameters);
		// start preview with new settings
		try {
			mCamera.setPreviewDisplay(mHolder);
			mCamera.setDisplayOrientation(90);
			mCamera.startPreview();
		} catch (Exception e1) {
			Log.d(TAG, "Error starting camera preview: " + e1.getMessage());
		}

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// The Surface has been created, acquire the camera and tell it where
		// to draw.
		try {
			mCamera = Camera.open();
		} catch (Exception e) {

		}

		try {
			mCamera.setPreviewDisplay(holder);

			mCamera.setPreviewCallback(new PreviewCallback() {

				@Override
				public void onPreviewFrame(byte[] data, Camera arg1) {
					//
					Preview.this.invalidate();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when we return, so stop the preview.
		// Because the CameraDevice object is not a shared resource, it's very
		// important to release it when the activity is paused.

		if (mCamera != null) {
			Log.d("Entered Surface Destroyed", "Entered Surface Destroyed");
			mCamera.stopPreview();
			mCamera.setPreviewCallback(null);
			mCamera.release();
			mCamera = null;
		}

	}
}
