package com.dev.chris.cryptonite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Christiaan Wewer
 * 11943858
 * SQL Database to save favorites
 */

public class FavoriteCoinDatabase extends SQLiteOpenHelper {

    private static String NAME = "favoriteCoinTable";
    static String COL1 = "rank";
    static String COL2 = "symbol";
    static String COL3 = "name";


    private static FavoriteCoinDatabase instance;

    private FavoriteCoinDatabase(Context context, String name)
    {super(context, name, null, 1);}

    static FavoriteCoinDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new FavoriteCoinDatabase(context, NAME);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + NAME + "(" + COL1 + " INTEGER, " +
                COL2 + " TEXT, " + COL3 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + NAME);
        onCreate(db);
    }

    void addCoinRankSymbolName(int coinRank, String coinSymbol, String coinName) {

        // add coin to the database if exists
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor checkCursor = db.rawQuery("SELECT * FROM " + NAME, null);

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

    Cursor selectRankSymbolName() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + NAME, null);
    }

    void removeCoinBySymbol(String coinSymbol) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + NAME + " WHERE " + COL2 + "=\"" + coinSymbol + "\"");
    }

}
