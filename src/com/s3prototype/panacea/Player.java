package com.s3prototype.panacea;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Player extends GameCharacter{
	
	public Player(float x, float y){
		super(x, y);
		color = Color.BLUE;
		paint.setColor(color);
	}

}//Player
