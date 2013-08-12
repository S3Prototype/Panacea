package com.s3prototype.panacea;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class GameTile {
	/*Tiles will have a position, width, height and state.
	 *But individual tiles all have the same width and height,
	 *so those values should be static to the class*/
	private static int width;
	private static int height;
	
	private int x;
	private int y;
	
	
	public static final int	//tile status values
		VACANT = 0, TAKEN = 1;
	
	private int status = VACANT;
	
	public static final int	//tile type values
		GROUND = 0, WALL = 1;
	
	private int type = WALL;
	
	private static final int NUM_TILE_TYPES = 2;
	public static int NUM_TILES_W;
	public static int NUM_TILES_H;
	
	private static Bitmap bitmap[];
	
	private static boolean alreadyInitialized;
	
	public GameTile(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public static void InitializeTiles(int sWidth, int sHeight){
		if(!alreadyInitialized){
			bitmap = new Bitmap[NUM_TILE_TYPES];
			width = 33;
			height = 33;
			NUM_TILES_W = sWidth/width;
			NUM_TILES_H = sHeight/height;
			alreadyInitialized = true;
		}
	}
	
	public static void DrawTiles(Canvas canvas, GameTile tile[][]){
		for(int i = 0; i < NUM_TILES_W; i++){
			for(int j = 0; j < NUM_TILES_H; j++){
				int tileType = tile[i][j].getType();
				int currX = tile[i][j].getX();
				int currY = tile[i][j].getY();
				canvas.drawBitmap(bitmap[tileType], currX - width/2, currY - height/2, null);
			}
		}
	}

	public static int getWidth() {
		return width;
	}

	public static void setWidth(int width) {
		GameTile.width = width;
	}

	public static int getHeight() {
		return height;
	}

	public static void setHeight(int height) {
		GameTile.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public static Bitmap GetBitmap(int tileType) {
		return bitmap[tileType];
	}

	public static void SetBitmap(int tileType, Bitmap bmp) {
		bitmap[tileType] = bmp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public static int getNumTiles() {
		return NUM_TILES_W * NUM_TILES_H;
	}
	
}//GameTile class 
