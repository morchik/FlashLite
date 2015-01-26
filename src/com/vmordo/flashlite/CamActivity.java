package com.vmordo.flashlite;
import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.Window;

public class CamActivity extends Activity {
	final String TAG = "CamActivity";

	private static SurfaceView sv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Cnt.set(getApplicationContext());
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cam);
		sv = (SurfaceView) findViewById(R.id.surfaceView2);
	}

	@Override
	public void onStart() {
		super.onStart();
		TakePhoto.getOne(sv);
		finishActivity(RESULT_OK);
	}

}
