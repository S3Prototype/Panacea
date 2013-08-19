package com.s3prototype.panacea;

import java.util.concurrent.locks.ReentrantLock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class DrawThread extends Thread{
	
	GameView drawSurface;
	SurfaceHolder surfaceHolder;
	Context surfaceContext;
	Paint paint;
	MainActivity surfaceActivity;
	public final String PLAYER_FILE = "playerInfo";
	public final String CPU_FILE = "cpuInfo";
	public final String PLAYER_X = "PLAYER_X";
	public final String PLAYER_Y = "Player_Y";
	public final String PLAYER_DX = "Player_DX";
	public final String PLAYER_DY = "Player_DY";
	public final String PLAYER_DREACHED = "Player_DREACHED";
	static boolean gameInitialized = false;
	boolean threadInitialized = false;
	double scaledX, scaledY;
	
	boolean okToRun;
	
	Player player;
	AI ai;
	
	public ReentrantLock threadLock;
	
	GameTile tiles[][];
	
	public DrawThread(Context vContext, GameView sView, SurfaceHolder vHolder, MainActivity sActivity){
		surfaceContext = vContext;
		surfaceActivity = sActivity;
		drawSurface = sView;
		surfaceActivity = sActivity;
		surfaceHolder = vHolder;
		surfaceActivity.gameWasStarted = true;
	}
	
	public void InitGame(){
		if(!threadInitialized){
			Engine.InitializeGame(drawSurface);
			gameInitialized = true;
			threadInitialized = true;
		}//if()
	}//InitGame()
	
	public void run() {
		Engine.InitializeGame(drawSurface);
		while (okToRun) {
			Canvas c = null;
			if (!surfaceHolder.getSurface().isValid()) {
				continue;
			}
			c = surfaceHolder.lockCanvas();
			if (c != null) {
				synchronized (surfaceHolder) {
					try {
						c.drawColor(Color.WHITE);
						Engine.Update(c);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				surfaceHolder.unlockCanvasAndPost(c);
			}//if c != null
		}// while()
	}

	public void onTouch(MotionEvent event){
	}//onTouch()
	
	public void setOkToRun(boolean status){
		okToRun = status;
/*		if(!okToRun){
			SharedPreferences prefs = surfaceActivity.getSharedPreferences(PLAYER_FILE, 0);
			SharedPreferences.Editor editor = prefs.edit();
			
			editor.putFloat(PLAYER_X, player.x);
			editor.putFloat(PLAYER_Y, player.y);
			editor.putFloat(PLAYER_DX, player.dstX);
			editor.putFloat(PLAYER_DY, player.dstY);
			editor.putBoolean(PLAYER_DREACHED, player.dstReached);
			editor.commit();
		}*/
	}//setOkToRun()
}//DrawThread
