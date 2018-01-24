package com.dev.chris.cryptonite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chris on 11-1-2018.
 */

public class FavoriteCoinDatabase extends SQLiteOpenHelper {

    public static String NAME = "favoriteCoinTable";
    public static String COL1 = "id";
    public static String COL2 = "name";
    public static String COL3 = "setHighPrice";
    public static String COL4 = "setLowPrice";

    private static FavoriteCoinDatabase instance;


    private FavoriteCoinDatabase(Context context, String name)
    {super(context, name, null, 1);}

    public static FavoriteCoinDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new FavoriteCoinDatabase(context, NAME);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // create table
        db.execSQL("CREATE TABLE " + NAME + "(" + COL1 + " TEXT, " +
                COL2 + " TEXT, " + COL3 + " INTEGER, " + COL4 + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // upgrade table
        db.execSQL("DROP TABLE IF EXISTS " + NAME);
        onCreate(db);
    }

//    public void addItem(int coinId, String coinName, float coinPrice, float coinPriceChange) {
//
//        // add coin to the database
//        SQLiteDatabase db = this.getReadableDatabase();
//        ContentValues coinContentValues = new ContentValues();
//        coinContentValues.put(COL1, coinId);
//        coinContentValues.put(COL2, coinName);
//        db.insert(NAME, null, coinContentValues);
//    }

    public void addCoinNameIdItem(String coinId, String coinName) {

        // add coin to the database
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor selectIdFromDb = db.rawQuery("SELECT " + COL1 + " FROM " + NAME, null);

        while (selectIdFromDb.moveToNext()) {
            String selectString = selectIdFromDb.getString(selectIdFromDb.getColumnIndex(FavoriteCoinDatabase.COL1));
            if (selectString.equals(coinId)) {
                return;
            }
        }

        ContentValues coinContentValues = new ContentValues();
        coinContentValues.put(COL1, coinId);
        coinContentValues.put(COL2, coinName);
        db.insert(NAME, null, coinContentValues);
    }

    public void clearAll() {

        // clear database
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + NAME);
    }

    public Cursor selectId() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor selectIdFromDb = db.rawQuery("SELECT " + COL1 + " FROM " + NAME, null);

//        Log.d("check", selectForFragmentDBCursor.getString(0));
        return selectIdFromDb;
    }

    public void removeCoin(String coinName) {
        SQLiteDatabase db = this.getReadableDatabase();

//        Cursor selectIdFromDb = db.rawQuery("SELECT " + COL2 + " FROM " + NAME, null);
//        selectIdFromDb.moveToFirst();
//
//        while (selectIdFromDb.moveToNext()) {
//            String selectString = selectIdFromDb.getString(selectIdFromDb.getColumnIndex(FavoriteCoinDatabase.COL2));
//            if (selectString.equals(coinName)) {
//                Log.d("coinname Found", "coinName Found!");
//            }
//        }
        db.execSQL("DELETE FROM " + NAME + " WHERE " + COL2 + "=\"" + coinName + "\"");



    }

}
