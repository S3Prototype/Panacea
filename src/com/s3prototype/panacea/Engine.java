package com.s3prototype.panacea;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import android.graphics.Canvas;
import android.view.MotionEvent;


public class Engine {

	static HashMap <String, Room> rooms = new HashMap<String, Room>();
	static Room currRoom; 
	static Player player = new Player(0, 0);
	static double scaledX, scaledY;
	private static boolean initialized = false;
	static PriorityQueue <GameCharacter> drawList;
	
	public static boolean getInitialized() {
		return initialized;
	}

	public static void setInitialized(boolean initialized) {
		Engine.initialized = initialized;
	}

	public static void CheckCharacterTiles(List <GameCharacter> charList){
		final double tWidth = GameTile.getWidth();
		final double tHeight = GameTile.getHeight();
		
		for(GameCharacter currChar : charList){
			if (currChar.tile == null
				|| currChar.x > (currChar.tile.getX() + tWidth / 2)
				|| currChar.x < (currChar.tile.getX() - tWidth / 2)
				|| currChar.y > (currChar.tile.getY() + tHeight / 2)
				|| currChar.y < (currChar.tile.getY() - tHeight / 2)) {
					//Then the character is not in its tile, So find its tile
					for(int i = 0; i < GameTile.NUM_TILES_W; i++){
						for(int j = 0; j < GameTile.NUM_TILES_H; j++){
							final GameTile currTile = currRoom.tile.get(i).get(j);
							final double tX = currTile.getX();
							final double tY = currTile.getY();
							if(currChar.x < tX + tWidth/2 &&
							   currChar.x > tX - tWidth/2 &&
							   currChar.y < tY + tHeight/2 &&
							   currChar.y > tY - tHeight/2){
							//If the character is in the tile, that's its tile.
								if(currChar.tile != null){
									currChar.tile.revertType();
								}
								currChar.tile = currTile;
								currChar.tile.setType(GameTile.PLAYER);
							}//if()
						} 
					}//outer for()
			}//if()
		}//for()
	}//CheckCharacterTiles()
	
	public static void Update(Canvas canvas){
			//Put the currRoom's characters, and the player, into a List of characters and pass them to:
				/*CheckCharTiles
				 */
			//Then draw the tiles with DrawTiles
			//Really, they should be organized by y-position so they're drawn overlapping properly
		int numChars = currRoom.cpuList.size() + 1;
		drawList = new PriorityQueue<GameCharacter>(numChars, new CharacterComparator());
		
		player.update(canvas);
		drawList.add(player);
		for(AI currChar : currRoom.cpuList){
			currChar.update(canvas);
			drawList.add(currChar);
		}

		Draw(canvas);
	}//Update
	
	private static void Draw(Canvas canvas){
		currRoom.drawTiles(canvas);
		//Now draw all the characters in order of precedence
		while(!drawList.isEmpty()){
			drawList.remove().draw(canvas);
		}
	}//Draw()
	
	public static class CharacterComparator implements Comparator<GameCharacter>{

		@Override
		public int compare(GameCharacter lhs, GameCharacter rhs) {
			if(lhs.y > rhs.y){
				return -1;
			} else if(lhs.y < rhs.y){
				return 1;
			} else {
				return 0;
			}
		}
	}//CharacterComparator
	
	public static void InitializeGame(GameView drawSurface){
		if(!initialized){
			//First have to set num tile types
			GameTile.NUM_TILE_TYPES = 2;
			//Initialize Tiles based on XML.
				/**TODO: Add XML
				 */
			final int sWidth = drawSurface.getWidth();
			final int sHeight = drawSurface.getHeight();
			scaledX = sWidth / drawSurface.baseWidth;
			scaledY = sHeight / drawSurface.baseHeight;
			
			GameTile.InitializeTiles(sWidth, sHeight, scaledX, scaledY);
			GameTile.InitializeBitmaps(drawSurface.sActivity);
			
			//Must use XML to initialize the rooms, and player info. This code'll work for now
			player = new Player((float)(sWidth/2 * scaledX), (float)(sHeight/2 * scaledY));
			RoomManager.gameRooms.add(new Room());
			currRoom = RoomManager.gameRooms.get(0);
			currRoom.InitTiles();
			
			initialized = true;
		}//if(!initialized)
	}
	
	public static void onTouch(final MotionEvent event){
		final int action = event.getAction();
		if(action == MotionEvent.ACTION_DOWN){
			player.goToDestination(event.getX(), event.getY());
		}
	}//onTouch()

	
		public static class RoomManager{
			public static List<Room> gameRooms = new ArrayList<Room>();
		}
	
}//Engine class
