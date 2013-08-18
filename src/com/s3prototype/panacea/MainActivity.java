package com.s3prototype.panacea;

import java.util.concurrent.locks.ReentrantLock;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
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
	
	public void loadGame(){
		if(fileWasSaved && DrawThread.gameInitialized){
			dbHelper = new GameDBHelper(getApplicationContext());
			db = dbHelper.getReadableDatabase();
			Cursor gameData = db.query(GameDBHelper.TABLE_GAMESTATE, null, null, null, null, null, null);
			Player savedPlayer;
			int count = gameData.getColumnCount() - 1;
			
			AI[] cpu = (count > 0) ? new AI[count] : null;
			if(gameData.moveToFirst()){
				int xCol = GameDBHelper.Indices.x;
				int yCol = GameDBHelper.Indices.y;
				int dstXCol = GameDBHelper.Indices.dstX;
				int dstYCol = GameDBHelper.Indices.dstY;
				int dstReachedCol = GameDBHelper.Indices.dstReached;
				
				while(!gameData.isAfterLast()){
					if(gameData.getInt(GameDBHelper.Indices.type) == GameData.playerType){
						savedPlayer = new Player(0, 0);
						savedPlayer.x = gameData.getFloat(xCol);
						savedPlayer.y = gameData.getFloat(yCol);
						savedPlayer.dstX = gameData.getFloat(dstXCol);
						savedPlayer.dstY = gameData.getFloat(dstYCol);
						savedPlayer.dstReached = (gameData.getInt(dstReachedCol) == 1) ? true : false;
						GameData.characters.put(GameData.playerKey, savedPlayer);
					} else {
						
					}//else
					gameData.moveToNext();
				}//while()
				shouldLoadFile = true;
				fileWasSaved = false;
				GameDBHelper.Cleanup(gameData, dbHelper, db);
			}
		}//if(fileWasSaved)
	}
	
	public void saveGame(){
		if(!fileWasSaved && drawThread.gameInitialized){
			dbHelper = new GameDBHelper(getApplicationContext());
			db = dbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			
			drawThread = gameView.drawThread;
			//First put in the player info
			values.put(GameDBHelper.COLUMN_TYPE, GameData.playerType);
			values.put(GameDBHelper.COLUMN_NUM, 0);
			values.put(GameDBHelper.COLUMN_X, drawThread.player.x);
			values.put(GameDBHelper.COLUMN_Y, drawThread.player.y);
			values.put(GameDBHelper.COLUMN_DSTX, drawThread.player.dstX);
			values.put(GameDBHelper.COLUMN_DSTY, drawThread.player.dstY);
			int reached = (drawThread.player.dstReached == true) ? 1 : 0;
			values.put(GameDBHelper.COLUMN_DSTREACHED, reached);
			db.insert(GameDBHelper.TABLE_GAMESTATE, null, values);
			fileWasSaved = true;
			GameDBHelper.Cleanup(null, dbHelper, db);
		}//if(!fileWasSaved)
		
	}

	@Override
	protected void onResume() {
		//loadGame();
		super.onResume();
	}//onResume()
	
	@Override
	protected void onPause() {
	//	saveGame();
  		super.onPause();
	}//onPause()

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}//MainActivity
