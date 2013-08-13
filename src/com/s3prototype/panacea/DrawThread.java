package com.s3prototype.panacea;

import android.app.Activity;
import android.content.Context;
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
	Activity surfaceActivity;
	
	double scaledX, scaledY;
	
	boolean gameInitialized;
	
	boolean okToRun;
	
	GameTile tiles[][];
	
	public DrawThread(Context vContext, GameView sView, SurfaceHolder vHolder, Activity sActivity){
		surfaceContext = vContext;
		surfaceActivity = sActivity;
		drawSurface = sView;
		surfaceActivity = sActivity;
		surfaceHolder = vHolder;
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
		if(!gameInitialized){
			scaledX = drawSurface.getWidth() / drawSurface.baseWidth;
			scaledY = drawSurface.getHeight() / drawSurface.baseHeight;
			
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
		}//if()
	}//InitGame()
	
	public void run(){
		while(okToRun){
			InitGame();//Call all necessary init methods
			Canvas c = null;
			if(!surfaceHolder.getSurface().isValid()){
				continue;
			}
			c = surfaceHolder.lockCanvas();
			synchronized(surfaceHolder){
				try{ 
					c.drawColor(Color.WHITE);
					GameTile.DrawTiles(c, tiles, scaledX, scaledY);
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			
			if(c != null){
				surfaceHolder.unlockCanvasAndPost(c);
			}
		}//while()
	}

	public void onTouch(MotionEvent event){
		int action = event.getAction();
	}//onTouch()
	
	public void setOkToRun(boolean status){
		okToRun = status;
	}
}//DrawThread
