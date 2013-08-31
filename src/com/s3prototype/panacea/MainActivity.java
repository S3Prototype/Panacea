package com.s3prototype.panacea;

import java.util.concurrent.locks.ReentrantLock;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;

public class MainActivity extends Activity {
	GameView gameView;
	DrawThread drawThread;
	final ReentrantLock threadLock = new ReentrantLock();
	boolean shouldLoadFile = false;
	static boolean fileWasSaved = false;
	boolean gameWasStarted = false;
	SQLiteDatabase db;
	GameDBHelper dbHelper;
	@Override
	protected void onCreate(Bundle savedData) {
		super.onCreate(savedData);
		//drawThread = new DrawThread(getApplicationContext(), null, null, null);
		gameView = new GameView(MainActivity.this, threadLock);
		drawThread = gameView.drawThread;//We'll save data from this later
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(gameView);
		Log.d("ON CREATE:", "CALLED");
	}
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}//onResume()
	
	@Override
	protected void onPause() {
  		super.onPause();
	}//onPause()

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}//MainActivity
