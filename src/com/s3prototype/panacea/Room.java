package com.s3prototype.panacea;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;

public class Room {

	List<List<GameTile>> tile = new ArrayList<List<GameTile>>();
	List<AI> cpuList = new ArrayList<AI>();
	
	public Room(){
		cpuList.add(new AI(200, 200));
		InitTiles();
	}
	
	public void InitTiles(){
		final double tWidth = GameTile.getWidth();
		final double tHeight = GameTile.getHeight();
		
		for(int i = 0; i < GameTile.NUM_TILES_W; i++){
			tile.add(new ArrayList<GameTile>());
			for(int k = 0; k < GameTile.NUM_TILES_H; k++){
				final double x = (tWidth * i) + tWidth/2;
				final double y = (tHeight * k) + tHeight/2;
				tile.get(i).add(new GameTile(x, y));
			}
		}//for()
	}//InitTiles()
	
	public void drawTiles(Canvas canvas){
		
		final int NUM_TILES_W = GameTile.NUM_TILES_W;
		final int NUM_TILES_H = GameTile.NUM_TILES_H;
		final double width = GameTile.getWidth();
		final double height = GameTile.getHeight();
		
		for(int i = 0; i < NUM_TILES_W; i++){
			for(int j = 0; j < NUM_TILES_H; j++){
				int tileType = tile.get(i).get(j).getType();
				int tileState = tile.get(i).get(j).getStatus();
				
				if(j % 2 != 0){
					tileType = GameTile.WALL;
				}
				
				int color = Color.GRAY;
				
				if(tileType == GameTile.WALL){
						color = Color.BLACK;
				}//if()
				
				if(tileState == GameTile.PLAYER){
					color = Color.GREEN;
				} else if(tileState == GameTile.NPC){
					color = Color.RED;
				}
				
				GameTile.testPaint.setColor(color);
				
				canvas.drawRect((float)(tile.get(i).get(j).getX() - width/2), (float)(tile.get(i).get(j).getY() - height/2),
								(float)(tile.get(i).get(j).getX() + width/2), (float)(tile.get(i).get(j).getY() + height/2), GameTile.testPaint);
			}
		}
	}//DrawTiles()

	public void update(Canvas canvas){
		drawTiles(canvas);
	}
}//Room class
