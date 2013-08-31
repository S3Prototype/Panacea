package com.s3prototype.panacea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

public class Room {

	List<List<GameTile>> tile = new ArrayList<List<GameTile>>();
	List<AI> cpuList = new ArrayList<AI>();
	public int NUM_TILES_W;
	public int NUM_TILES_H;
	
	public Room(Resources appResources, DrawThread drawThread){
		cpuList.add(new AI(200, 200));
		try {
			InitTiles(appResources, drawThread);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			Log.d("ROOM ERROR:", e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("ROOM ERROR:", e.getMessage());
		}
	}

	public int getNumTiles() {
		return NUM_TILES_W * NUM_TILES_H;
	}
	
	public void InitTiles(Resources appResources, DrawThread drawThread)
						  throws XmlPullParserException, IOException {
		
		final double tWidth = GameTile.getWidth();
		final double tHeight = GameTile.getHeight();
/*		final double scaledW = drawThread.surfaceActivity.gameView.sWidth;
		final double scaledH = drawThread.surfaceActivity.gameView.sHeight;*/
		
			XmlPullParserFactory factory;
				factory = XmlPullParserFactory
						.newInstance();
				factory.setNamespaceAware(true);
				XmlPullParser xpp = factory.newPullParser();
				xpp.setInput(
						appResources.openRawResource(R.raw.blah),
						null);
				
				int eventType = xpp.getEventType();
				int a = 0, b = 0;
				int background[][] = null;
				boolean bgFound = false;
				while(eventType != XmlPullParser.END_DOCUMENT){
					if (eventType == XmlPullParser.START_TAG) {
						if(xpp.getAttributeValue(null, "Name").equalsIgnoreCase("background")){
							NUM_TILES_W = Integer.parseInt(xpp.getAttributeValue(null, "width"));
							NUM_TILES_H = Integer.parseInt(xpp.getAttributeValue(null, "height"));
							background = new int[NUM_TILES_W][NUM_TILES_H];
							bgFound = true;
						}
						
						if(bgFound && xpp.getAttributeValue(null, "gid") != null){
							background[a][b] = Integer.parseInt(xpp.getAttributeValue(null, "gid"));
							if(a + 1 < NUM_TILES_W) a++;
							else if(b + 1 < NUM_TILES_H) b++;
						}
							eventType = xpp.next();
					}
				}//while()
				
		Log.d("TILE_W AND TILE_H:", NUM_TILES_W + " " + NUM_TILES_H);

		for(int i = 0; i < NUM_TILES_W; i++){
			tile.add(new ArrayList<GameTile>());
			for(int k = 0; k < NUM_TILES_H; k++){
				final double x = (tWidth * i) + tWidth/2;
				final double y = (tHeight * k) + tHeight/2;
				tile.get(i).add(new GameTile(x, y));
				if(background[i][k] == 1050){
					tile.get(i).get(k).setType(GameTile.GROUND);
				} else {
					tile.get(i).get(k).setType(GameTile.WALL);
				}
			}
		}//for()
	}//InitTiles()
	
	public void drawTiles(Canvas canvas){
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
