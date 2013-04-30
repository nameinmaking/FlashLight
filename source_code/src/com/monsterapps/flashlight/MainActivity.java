package com.monsterapps.flashlight;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	//Flag to lnow LED is ON/OFF
	private boolean isLightOn=false;
	
	private Camera camera;
	private ImageButton iButton;
	
	protected void onStop()
	{
		super.onStop();
		if(camera!=null)
			camera.release();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		iButton = (ImageButton)findViewById(R.id.bulb);
		
		final Context context=this;
		PackageManager pm = context.getPackageManager();
		
		//If device is supported 
		if(!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
			Toast.makeText(this, "Device has no Camera Flash..!!",Toast.LENGTH_SHORT).show();
			Log.e("err","Device has no Camera Flash..!!");
		}
		
		camera = Camera.open();
		final Parameters p = camera.getParameters();
		
		iButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				
				if(isLightOn){
					Log.i("info","LED is OFF");
					//int drawableIDOff = context.getResources().getIdentifier("bulboff", "drawable", getPackageName());
					//iButton.setBackgroundResource(drawableIDOff);
					iButton.setBackgroundResource(R.drawable.bulboff);
					p.setFlashMode(Parameters.FLASH_MODE_OFF);
					camera.setParameters(p);
					camera.stopPreview();
					isLightOn=false;
				}
				
				else{
					Log.i("info","Torch is ON!");
					//int drawableIDOn = context.getResources().getIdentifier("bulbon", "drawable", getPackageName());
					iButton.setBackgroundResource(R.drawable.bulbon);
					p.setFlashMode(Parameters.FLASH_MODE_TORCH);
					camera.setParameters(p);
					camera.startPreview();
					isLightOn=true;
				}
			}
		});
	}

	

}
