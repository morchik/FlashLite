package com.vmordo.flashlite;

import android.support.v7.app.ActionBarActivity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class FlashLiteActivity extends ActionBarActivity {
	private static Camera cam;
	private Button btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_lite);
		btn = (Button) findViewById(R.id.button1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flash_lite, menu);
		return true;
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
