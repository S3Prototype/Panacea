package com.s3prototype.panacea;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GameTile {
	/*Tiles will have a position, width, height and state.
	 *But individual tiles all have the same width and height,
	 *so those values should be static to the class*/
	private static double width;
	private static double height;
	
	private double x;
	private double y;
	
	private static final Paint testPaint = new Paint();
	
	
	public static final int	//tile status values
		VACANT = 0, PLAYER = 1, NPC = 2;
	
	private int status = VACANT;
	
	public static final int	//tile type values
		GROUND = 0, WALL = 1;
	
	private int type = GROUND;
	
	private static final int NUM_TILE_TYPES = 2;
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
			if(NUM_TILES_W * width < sWidth) NUM_TILES_W++;
			if(NUM_TILES_H * height < sHeight) NUM_TILES_H++;
			alreadyInitialized = true;
		}
	}//InitializeTiles
	
	public static void DrawTiles(Canvas canvas, GameTile tile[][], double scaledX, double scaledY){
		for(int i = 0; i < NUM_TILES_W; i++){
			for(int j = 0; j < NUM_TILES_H; j++){
				int tileType = tile[i][j].getType();
				int tileState = tile[i][j].getStatus();
				double currX = tile[i][j].getX();
				double currY = tile[i][j].getY();
				
				if(j % 2 != 0){
					tileType = WALL;
				}
				
				int color = Color.GRAY;
				
				if(tileType == WALL){
						color = Color.BLACK;
				}//if()
				
				if(tileState == PLAYER){
					color = Color.GREEN;
				} else if(tileState == NPC){
					color = Color.RED;
				}
				
				testPaint.setColor(color);
				
				canvas.drawRect((float)(tile[i][j].x - width/2), (float)(tile[i][j].y - height/2),
								(float)(tile[i][j].x + width/2), (float)(tile[i][j].y + height/2), testPaint);
			}
		}
	}

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
