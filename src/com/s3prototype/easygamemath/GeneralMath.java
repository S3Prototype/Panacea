package com.s3prototype.easygamemath;

public class GeneralMath{
	
	public static double Distance(double sourceX, double sourceY, double targetX, double targetY){
		double diffX = targetX - sourceX;
		double diffY = targetY - sourceY;
		
		double distance = Math.sqrt( Math.pow(diffX, 2) + Math.pow(diffY, 2) );
		return distance;
	}
}
