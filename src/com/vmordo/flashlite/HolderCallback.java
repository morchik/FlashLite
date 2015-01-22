package com.vmordo.flashlite;

import java.io.IOException;

import android.view.SurfaceHolder;

class HolderCallback implements SurfaceHolder.Callback {

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		//try {
			//camera.setPreviewDisplay(holder);
			//camera.startPreview();
		//} catch (IOException e) {
			//e.printStackTrace();
		//}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		//camera.stopPreview();
		//setCameraDisplayOrientation(CAMERA_ID);
		try {
			//camera.setPreviewDisplay(holder);
			//camera.startPreview();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

}

