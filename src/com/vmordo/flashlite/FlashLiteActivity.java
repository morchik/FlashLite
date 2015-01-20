package com.vmordo.flashlite;

import android.support.v7.app.ActionBarActivity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class FlashLiteActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_lite);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.flash_lite, menu);
        return true;
    }
    
    @SuppressWarnings("deprecation")
	public void onClick(View v){
    	if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
    		Toast.makeText(this, "has", Toast.LENGTH_SHORT).show();
    		Camera cam = Camera.open();     
    		Parameters p = cam.getParameters();

    		p.setFlashMode(Parameters.FLASH_MODE_TORCH);
    		cam.setParameters(p);
    		cam.startPreview();
    	}else
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
