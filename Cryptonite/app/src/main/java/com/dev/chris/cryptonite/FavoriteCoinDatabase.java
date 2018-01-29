package com.dev.chris.cryptonite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.loopj.android.http.AsyncHttpClient.log;

/**
 * Created by chris on 11-1-2018.
 */

public class FavoriteCoinDatabase extends SQLiteOpenHelper {

    public static String NAME = "favoriteCoinTable";
    public static String COL1 = "rank";
    public static String COL2 = "symbol";
    public static String COL3 = "name";


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
        db.execSQL("CREATE TABLE " + NAME + "(" + COL1 + " INTEGER, " +
                COL2 + " TEXT, " + COL3 + " TEXT)");
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

    public void addCoinRankSymbolName(int coinRank, String coinSymbol, String coinName) {

        // add coin to the database
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor checkCursor = db.rawQuery("SELECT * FROM " + NAME, null);

        Log.d("wtfCursor1", checkCursor.getColumnName(0));
        Log.d("wtfCursor2", checkCursor.getColumnName(1));
        Log.d("wtfCursor3", checkCursor.getColumnName(2));

        while (checkCursor.moveToNext()) {
                String selectString = checkCursor.getString(checkCursor.getColumnIndex(FavoriteCoinDatabase.COL2));
            if (selectString.equals(coinSymbol)) {
                return;
            }
        }
        checkCursor.close();

        ContentValues cointentValues = new ContentValues();
        cointentValues.put(COL1, coinRank);
        cointentValues.put(COL2, coinSymbol);
        cointentValues.put(COL3, coinName);
        db.insert(NAME, null, cointentValues);
    }

    public void clearAll() {

        // clear database
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + NAME);
    }

    public Cursor selectRankSymbolName() {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery("SELECT * FROM " + NAME, null);
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
        db.execSQL("DELETE FROM " + NAME + " WHERE " + COL3 + "=\"" + coinName + "\"");



    }

}
