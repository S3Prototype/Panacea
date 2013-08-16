package com.s3prototype.panacea;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GameDBHelper extends SQLiteOpenHelper{

	public static final String TABLE_GAMESTATE = "gamestate";
	
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_TYPE = "type";
		public static final String COLUMN_NUM = "num";
		public static final String COLUMN_X = "x";
		public static final String COLUMN_Y = "y";
		public static final String COLUMN_DSTX = "dstX";
		public static final String COLUMN_DSTY = "dstY";
		public static final String COLUMN_DSTREACHED = "dstReached";
		
	private static final String DATABASE_NAME = "gameSaves.db";
	private static final int DATABASE_VERSION = 1;
	
	public class Indices{
		public final static int
			id = 0, type = 1, num = 2, x = 3, y = 4, dstX = 5, dstY = 6,
			dstReached = 7;
	}
	
	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_GAMESTATE + "(" 
			+ COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_TYPE + " integer, "
			+ COLUMN_NUM + " integer, "
			+ COLUMN_X + " float, "
			+ COLUMN_Y + " float, "
			+ COLUMN_DSTX + " float, "
			+ COLUMN_DSTY + " float, "
			+ COLUMN_DSTREACHED + " integer"
			+ ");";
	
	public GameDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	public static void Cleanup(Cursor cursor, GameDBHelper helper, SQLiteDatabase db){
		if(cursor != null)
			cursor.close();
		if(helper != null)
			helper.close();
		if(db != null)
			db.close();
	}


	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(GameDBHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	  //  db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMESTATE);
	    onCreate(db);
	  }
}//GameDBHelper
