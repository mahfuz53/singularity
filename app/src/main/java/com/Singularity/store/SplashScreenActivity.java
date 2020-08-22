package com.Singularity.store;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;

public class SplashScreenActivity extends Activity {
	private Thread splashThresd;
	Context con;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.screen);
		con =this;
		final ImageView splashImageView = (ImageView) findViewById(R.id.SplashImageView);
		splashImageView.setBackgroundResource(R.drawable.flag);
		final AnimationDrawable frameAnimation = (AnimationDrawable)splashImageView.getBackground();
		splashImageView.post(new Runnable(){
			public void run() {
				frameAnimation.start();
			}
		});

		final SplashScreenActivity sPlashScreen = this;
		splashThresd = new Thread(){
			@Override
			public void run(){
				try {

					synchronized(this){
						wait(1000);
					}
				}
				catch(InterruptedException ex){
				}

				String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
				Permissions.check(SplashScreenActivity.this, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
					@Override
					public void onGranted() {
						Intent intent = new Intent();
						intent.setClass(sPlashScreen, StoreActivity.class);
						startActivity(intent);
						finish();

					}


					@Override
					public void onDenied(Context context, ArrayList<String> deniedPermissions) {
						super.onDenied(context, deniedPermissions);
						Toast.makeText(con,getResources().getString(R.string.reload),Toast.LENGTH_LONG).show();
					}
				});

			}
		};
		splashThresd.start();
	}
	@Override
	public boolean onTouchEvent(MotionEvent evt) {
		return true;
	}

}
