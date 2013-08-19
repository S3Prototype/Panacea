package com.s3prototype.panacea;


import android.graphics.Canvas;
import android.graphics.Paint;

public class GameCharacter{
	public float x, y;
	public float dstX, dstY;
	public int radius;
	public int color;
	final Paint paint = new Paint();
	public boolean dstReached = true;
	public final float speed = 1;
	public GameTile tile;  
	
	public GameCharacter(float x, float y){
		radius = 50;
		this.x = x - 15;
		this.y = y - 15;
	}
	
	public void draw(Canvas canvas){
		canvas.drawCircle(x, y, radius, paint);
	}
	
	public void goToDestination(float destX, float destY){
		if( destX - x > -2 && destX - x < 2 &&
			destY - y > -2 && destY - y < 2)
			return;
		
		dstX = destX;
		dstY = destY;
		dstReached = false;
	}
	
	public void update(Canvas canvas){
		if(!dstReached){
			double xDistance = dstX - x;
			double yDistance = dstY - y;
			boolean dstXReached = (xDistance > -2 && xDistance < 2);
			boolean dstYReached = (yDistance > -2 && yDistance < 2);
			float addSpeed;
			if(!dstXReached){
				addSpeed = (xDistance > 0) ? speed : -1 * speed;
				x += addSpeed;
			} else if(!dstYReached){
				addSpeed = (yDistance > 0) ? speed : -1 * speed;
				y += addSpeed;
			}
			
			dstReached = (dstXReached && dstYReached);
		}//if()
	}
}//GameCharacter class
