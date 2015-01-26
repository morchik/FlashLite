package com.vmordo.flashlite;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class FlashLiteActivity extends ActionBarActivity {

	File directory;
	public static Camera cam;
	private static SurfaceView sv;
	private Button btn;
	final int TYPE_PHOTO = 1;
	final int TYPE_VIDEO = 2;

	final int REQUEST_CODE_PHOTO = 1;
	final int REQUEST_CODE_VIDEO = 2;

	final String TAG = "myLogs";

	ImageView ivPhoto;

	private void createDirectory() {
		directory = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"MyFolder");
		if (!directory.exists())
			directory.mkdirs();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Cnt.set(getApplicationContext());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_lite);
		createDirectory();
		ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
		btn = (Button) findViewById(R.id.button1);
		sv = (SurfaceView) findViewById(R.id.surfaceView);
	}

	public void onClickHide(View v) {
		TakePhoto.getOne(sv);
		startService(new Intent(this, TrackingService.class));  // запуск службы
		TakePhoto.getOne(sv);
	}

	public void onClick(View v) {
		if (getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA_FLASH)) {
			if (cam == null) {
				Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
				cam = Camera.open();
				Parameters p = cam.getParameters();
				p.setFlashMode(Parameters.FLASH_MODE_TORCH);
				cam.setParameters(p);
				cam.startPreview();
				btn.setText(R.string.flash_off);
			} else {
				Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show();
				cam.stopPreview();
				cam.release();
				cam = null;
				btn.setText(R.string.flash_on);
			}

		} else
			Toast.makeText(this, "no flash", Toast.LENGTH_SHORT).show();
	}

	public void onClickPhoto(View view) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(TYPE_PHOTO));
		startActivityForResult(intent, REQUEST_CODE_PHOTO);
	}

	public void onClickVideo(View view) {
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(TYPE_VIDEO));
		startActivityForResult(intent, REQUEST_CODE_VIDEO);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == REQUEST_CODE_PHOTO) {
			if (resultCode == RESULT_OK) {
				if (intent == null) {
					Log.d(TAG, "Intent is null");
				} else {
					Log.d(TAG, "Photo uri: " + intent.getData());
					Bundle bndl = intent.getExtras();
					if (bndl != null) {
						Object obj = intent.getExtras().get("data");
						if (obj instanceof Bitmap) {
							Bitmap bitmap = (Bitmap) obj;
							Log.d(TAG, "bitmap " + bitmap.getWidth() + " x "
									+ bitmap.getHeight());
							ivPhoto.setImageBitmap(bitmap);
						}
					}
				}
			} else if (resultCode == RESULT_CANCELED) {
				Log.d(TAG, "Canceled");
			}
		}

		if (requestCode == REQUEST_CODE_VIDEO) {
			if (resultCode == RESULT_OK) {
				if (intent == null) {
					Log.d(TAG, "Intent is null");
				} else {
					Log.d(TAG, "Video uri: " + intent.getData());
				}
			} else if (resultCode == RESULT_CANCELED) {
				Log.d(TAG, "Canceled");
			}
		}
	}

	private Uri generateFileUri(int type) {
		File file = null;
		switch (type) {
		case TYPE_PHOTO:
			file = new File(directory.getPath() + "/" + "photo_"
					+ System.currentTimeMillis() + ".jpg");
			break;
		case TYPE_VIDEO:
			file = new File(directory.getPath() + "/" + "video_"
					+ System.currentTimeMillis() + ".mp4");
			break;
		}
		Log.d(TAG, "fileName = " + file);
		return Uri.fromFile(file);
	}

	public static void setPicMax() {
		Camera.Parameters param;
		param = cam.getParameters();

		Camera.Size bestSize = null;
		List<Camera.Size> sizeList = cam.getParameters()
				.getSupportedPictureSizes();
		bestSize = sizeList.get(0);
		for (int i = 1; i < sizeList.size(); i++) {
			if ((sizeList.get(i).width * sizeList.get(i).height) > (bestSize.width * bestSize.height)) {
				bestSize = sizeList.get(i);
			}
		}
		param.setPictureSize(bestSize.width, bestSize.height);
		cam.setParameters(param);
	}

	void someTask() { 
		Timer myTimer = new Timer(); // Создаем таймер
		Log.e("someTask", " someTask ");
		myTimer.schedule(new TimerTask() { // Определяем задачу
					@Override
					public void run() {
						TakePhoto.getOne(sv);
					}
				}, 5L * 1000L, 3L * 1000L); // интервал
	}
}
