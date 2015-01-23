package com.vmordo.flashlite;

import android.view.SurfaceHolder;

@SuppressWarnings("deprecation")
class HolderCallback implements SurfaceHolder.Callback {

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.e("HolderCallback", "surfaceCreated");
		if (FlashLiteActivity.cam != null)
			try {
				FlashLiteActivity.cam.stopPreview();
				FlashLiteActivity.cam.setPreviewDisplay(holder);
				FlashLiteActivity.cam.startPreview();
				Log.e("HolderCallback", "surfaceCreated startPreview");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.e("HolderCallback", "surfaceChanged");
		if (FlashLiteActivity.cam != null)
			try {
				FlashLiteActivity.cam.stopPreview();
				FlashLiteActivity.cam.setPreviewDisplay(holder);
				FlashLiteActivity.cam.startPreview();
				Log.e("HolderCallback", "surfaceChanged startPreview");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.e("HolderCallback", "surfaceDestroyed");
	}

}
