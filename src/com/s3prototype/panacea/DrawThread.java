package com.s3prototype.panacea;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DrawThread extends Thread{
	
	GameView drawSurface;
	SurfaceHolder surfaceHolder;
	Context surfaceContext;
	Paint paint;
	Activity surfaceActivity;
	
	int scaledW, scaledH;
	
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
		scaledW = GameTile.getWidth() / drawSurface.getWidth() ;
		scaledH = GameTile.getHeight() / drawSurface.getHeight();
		//Load ground bitmap:
		temp = BitmapFactory.decodeResource(surfaceActivity.getResources(), 
											R.drawable.google);
		temp = Bitmap.createScaledBitmap(temp, GameTile.getHeight(),
										 GameTile.getHeight(), false);
		
		GameTile.SetBitmap(GameTile.GROUND, temp);
		
		//Now load wall bitmap
		temp = BitmapFactory.decodeResource(surfaceActivity.getResources(), 
				R.drawable.google_buzz_icon);
		temp = Bitmap.createScaledBitmap(temp, GameTile.getWidth(), 
										 GameTile.getHeight(), false);
		
		GameTile.SetBitmap(GameTile.WALL, temp);
	}//setTileBitmaps()
	
	public void InitGame(){
		GameTile.InitializeTiles(drawSurface.getWidth(), drawSurface.getHeight());
		setTileBitmaps();
		tiles = new GameTile[GameTile.NUM_TILES_W][GameTile.NUM_TILES_H];
		int tileWidth = GameTile.getWidth();
		int tileHeight = GameTile.getHeight();
		
		for(int i = 0; i < GameTile.NUM_TILES_W; i++){
			for(int k = 0; k < GameTile.NUM_TILES_H; k++){
				int xVal = (i * tileWidth) - tileWidth/2;
				int yVal = (k * tileHeight) - tileHeight/2;
				tiles[i][k] = new GameTile(xVal, yVal);
			}
		}
	}
	
	public void run(){
		InitGame();//Call all necessary init methods
		while(okToRun){
			Canvas c = null;
			if(!surfaceHolder.getSurface().isValid()){
				continue;
			}
			c = surfaceHolder.lockCanvas();
			synchronized(surfaceHolder){
				try{
					c.drawColor(Color.RED);
					GameTile.DrawTiles(c, tiles);
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			
			if(c != null){
				surfaceHolder.unlockCanvasAndPost(c);
			}
		}//while()
	}
	
	public void setOkToRun(boolean status){
		okToRun = status;
	}
}
