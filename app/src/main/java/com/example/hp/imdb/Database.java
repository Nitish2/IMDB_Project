package com.example.hp.imdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;

public class Database extends SQLiteOpenHelper
{
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "DB_IMDB";

    private static final String TABLE_IMDB = "IMDB";
    private static final String COLOUMN_MOVIE_ID = "ID";
    private static final String COLOUMN_FAVORITE = "FAVORITE";
    private static final String COLOUMN_WATCHLIST = "WATCHLIST";

    private static final String[] COLUMNS_IMDB = {COLOUMN_MOVIE_ID, COLOUMN_FAVORITE, COLOUMN_WATCHLIST};

    public Database(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String str_create_table = "CREATE TABLE " + TABLE_IMDB + " ( "
                + COLOUMN_MOVIE_ID + " INTEGER PRIMARY KEY, "
                + COLOUMN_FAVORITE + " INTEGER, "
                + COLOUMN_WATCHLIST + " INTEGER )";
        db.execSQL(str_create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_IMDB);
        this.onCreate(db);
    }

    public void InsertNewEntry(int id, int favorite, int watchlist)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLOUMN_MOVIE_ID, id);
        contentValues.put(COLOUMN_FAVORITE, favorite);
        contentValues.put(COLOUMN_WATCHLIST, watchlist);

        db.insert(TABLE_IMDB, null, contentValues);

        db.close();
    }

    public int UpdateFavorite(int id, int favorite)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLOUMN_FAVORITE, favorite);

        int i = db.update(TABLE_IMDB, contentValues, COLOUMN_MOVIE_ID + "=?",
                new String[] {String.valueOf(id)});
        db.close();

        return i;
    }

    public int UpdateWatchlist(int id, int watchlist)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLOUMN_WATCHLIST, watchlist);

        int i = db.update(TABLE_IMDB, contentValues, COLOUMN_MOVIE_ID + "=?",
                new String[] {String.valueOf(id)});
        db.close();

        return i;
    }

    public int DeleteEntry(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        int i = db.delete(TABLE_IMDB, COLOUMN_MOVIE_ID + "=?", new String[] {String.valueOf(id)});

        db.close();
        return i;
    }

    public ArrayList<MovieInfo> getFavorite()
    {
        ArrayList<MovieInfo> arrayList_imdb = new ArrayList<>();

        String str_query = "SELECT * FROM " + TABLE_IMDB + " WHERE " + COLOUMN_FAVORITE + " = 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(str_query, null);

        MovieInfo imdb = null;

        if (cursor.moveToFirst())
        {
            do {
                imdb = new MovieInfo();

                imdb.setMovie_id(Integer.parseInt(cursor.getString(0)));
                imdb.setFavorite(Integer.parseInt(cursor.getString(1)));
                imdb.setWatchlist(Integer.parseInt(cursor.getString(2)));

                arrayList_imdb.add(imdb);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList_imdb;
    }

    public ArrayList<MovieInfo> getWatchlist()
    {
        ArrayList<MovieInfo> arrayList_imdb = new ArrayList<>();

        String str_query = "SELECT * FROM " + TABLE_IMDB + " WHERE " + COLOUMN_WATCHLIST + " = 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(str_query, null);

        MovieInfo imdb = null;

        if (cursor.moveToFirst())
        {
            do {
                imdb = new MovieInfo();

                imdb.setMovie_id(Integer.parseInt(cursor.getString(0)));
                imdb.setFavorite(Integer.parseInt(cursor.getString(1)));
                imdb.setWatchlist(Integer.parseInt(cursor.getString(2)));

                arrayList_imdb.add(imdb);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList_imdb;
    }

    public boolean MovieIDExist(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String str_query = "SELECT * FROM " + TABLE_IMDB + " WHERE " + COLOUMN_MOVIE_ID + " = " + id;
        Cursor cursor = db.rawQuery(str_query, null);
        if(cursor.getCount() <= 0)
        {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public MovieInfo getEntry(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_IMDB, COLUMNS_IMDB, COLOUMN_MOVIE_ID + " =?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        MovieInfo imdb = null;

        if ((cursor != null) && (cursor.getCount()>0))
        {
            cursor.moveToFirst();
            imdb = new MovieInfo();
            Log.e("cursor", String.valueOf(cursor.getString(0)));
            imdb.setMovie_id(Integer.parseInt(cursor.getString(0)));
            imdb.setFavorite(Integer.parseInt(cursor.getString(1)));
            imdb.setWatchlist(Integer.parseInt(cursor.getString(2)));
        }

        if (cursor != null) {
            cursor.close();
        }
        return imdb;


    }
}


