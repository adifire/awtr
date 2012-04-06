package app.awtr;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class Awtr_Splash extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		final Handler handler = new Handler();
		Timer timer = new Timer();
		
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				handler.post(new Runnable() {
					
					public void run() {
						Intent sendToMain = new Intent();
						sendToMain.setClass(Awtr_Splash.this, Awtr_Main.class);
						startActivity(sendToMain);
						finish();
					}
				});
			}
		};
		timer.schedule(task, 3000);
	}

}
