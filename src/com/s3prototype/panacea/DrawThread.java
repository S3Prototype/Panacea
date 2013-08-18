package com.s3prototype.panacea;

import java.util.concurrent.locks.ReentrantLock;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	
	
	
	public void setTileBitmaps(){
		Bitmap temp;
		//Load ground bitmap:
		temp = BitmapFactory.decodeResource(surfaceActivity.getResources(), 
											R.drawable.google);
		temp = Bitmap.createScaledBitmap(temp, (int)GameTile.getWidth(),
										 (int) GameTile.getHeight(),
										false);
		
		GameTile.SetBitmap(GameTile.GROUND, temp);
		
		//Now load wall bitmap
		temp = BitmapFactory.decodeResource(surfaceActivity.getResources(), 
											R.drawable.google_buzz_icon);
		
		temp = Bitmap.createScaledBitmap(temp, (int) GameTile.getWidth(),
										 (int) GameTile.getHeight(),
										 false);
		
		GameTile.SetBitmap(GameTile.WALL, temp);
	}//setTileBitmaps()
	
	public void InitGame(){
		if(!threadInitialized){
			scaledX = drawSurface.getWidth() / drawSurface.baseWidth;
			scaledY = drawSurface.getHeight() / drawSurface.baseHeight;
			
			SharedPreferences prefs = surfaceActivity.getSharedPreferences(PLAYER_FILE, 0);

			float playerX = prefs.getFloat(PLAYER_X, (float) (drawSurface.getWidth()/2));
			float playerY = prefs.getFloat(PLAYER_Y, (float) (drawSurface.getHeight()/2));
			
			player = new Player(playerX, playerY);
			
			player.dstReached = prefs.getBoolean(PLAYER_DREACHED, true);
			
			if(!player.dstReached){
				player.dstX = prefs.getFloat(PLAYER_DX, 50);
				player.dstY = prefs.getFloat(PLAYER_DY, 50);
			}
				

			float aiX = (float) (playerX + (scaledX * 100));
			float aiY = (float) (playerY - (scaledY * 50));
			if(ai == null)
				ai = new AI(aiX, aiY);
			
			GameTile.InitializeTiles(drawSurface.getWidth(), drawSurface.getHeight(),
									 scaledX, scaledY);
			setTileBitmaps();
			tiles = new GameTile[GameTile.NUM_TILES_W][GameTile.NUM_TILES_H];
			double tileWidth = GameTile.getWidth();
			double tileHeight = GameTile.getHeight();
			
			for(int i = 0; i < GameTile.NUM_TILES_W; i++){
				for(int k = 0; k < GameTile.NUM_TILES_H; k++){
					double xVal = (i * tileWidth) + tileWidth/2;
					double yVal = (k * tileHeight) + tileHeight/2;
					tiles[i][k] = new GameTile(xVal, yVal);
				}
			}
			tiles[0][0].setType(GameTile.GROUND);
			tiles[GameTile.NUM_TILES_W - 1][1].setType(GameTile.GROUND);
			gameInitialized = true;
			threadInitialized = true;
		}//if()
	}//InitGame()
	
	public void run() {
		while (okToRun) {
			InitGame();// Call all necessary init methods 
			Canvas c = null;
			if (!surfaceHolder.getSurface().isValid()) {
				continue;
			}
			c = surfaceHolder.lockCanvas();
			if (c != null) {
				synchronized (surfaceHolder) {
					try {
						c.drawColor(Color.WHITE);
						GameTile.DrawTiles(c, tiles, scaledX, scaledY);
							player.update(c);
							ai.update(c);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
					surfaceHolder.unlockCanvasAndPost(c);
			}//if c != null
		}// while()
	}

	public void onTouch(MotionEvent event){
		int action = event.getAction();
		if(action == MotionEvent.ACTION_DOWN){
			player.goToDestination(event.getX(), event.getY());
		}
	}//onTouch()
	
	public void setOkToRun(boolean status){
		okToRun = status;
		if(!okToRun){
			SharedPreferences prefs = surfaceActivity.getSharedPreferences(PLAYER_FILE, 0);
			SharedPreferences.Editor editor = prefs.edit();
			
			editor.putFloat(PLAYER_X, player.x);
			editor.putFloat(PLAYER_Y, player.y);
			editor.putFloat(PLAYER_DX, player.dstX);
			editor.putFloat(PLAYER_DY, player.dstY);
			editor.putBoolean(PLAYER_DREACHED, player.dstReached);
			editor.commit();
		}
	}
}//DrawThread
