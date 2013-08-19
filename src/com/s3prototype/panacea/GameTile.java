package com.s3prototype.panacea;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;

public class GameTile {
	/*Tiles will have a position, width, height and state.
	 *But individual tiles all have the same width and height,
	 *so those values should be static to the class*/
	private static double width;
	private static double height;
	
	private double x;
	private double y;
	
	public static final Paint testPaint = new Paint();
	
	
	public static final int	//tile status values
		VACANT = 0, PLAYER = 1, NPC = 2;
	
	private int status = VACANT;
	
	public static final int	//tile type values
		GROUND = 0, WALL = 1;
	
	private int type = GROUND;
	private int formerType = type;
	
	public static int NUM_TILE_TYPES = 2;
	public static int NUM_TILES_W;
	public static int NUM_TILES_H;
	
	private static Bitmap bitmap[];
	
	private static boolean alreadyInitialized;
	
	public GameTile(double x, double y){
		this.x = x;
		this.y = y;
	}//GameTile
	
	public static void InitializeTiles(int sWidth, int sHeight, double scaledX, double scaledY){
		if(!alreadyInitialized){
			bitmap = new Bitmap[NUM_TILE_TYPES];
			width = (int) (40 * scaledX);
			height = (int) (40 * scaledY);
			NUM_TILES_W = (int) (sWidth/width);
			NUM_TILES_H = (int) (sHeight/height);
				//If there aren't enough tiles, we have to add more until there are
			while(NUM_TILES_W * width < sWidth) NUM_TILES_W++;
			while(NUM_TILES_H * height < sHeight) NUM_TILES_H++;
			alreadyInitialized = true;
		}
	}//InitializeTiles

	public static double getWidth() {
		return width;
	}

	public static void setWidth(int width) {
		GameTile.width = width;
	}

	public static double getHeight() {
		return height;
	}

	public static void setHeight(int height) {
		GameTile.height = height;
	}

	public double getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public static void InitializeBitmaps(MainActivity surfaceActivity){
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
		formerType = this.type;
		this.type = type;
	}
	
	public void revertType(){
		type = formerType;
	}

	public static int getNumTiles() {
		return NUM_TILES_W * NUM_TILES_H;
	}
	
}//GameTile class 
