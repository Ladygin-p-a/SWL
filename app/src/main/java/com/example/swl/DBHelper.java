package com.example.swl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "settings.db";
    private static final int SCHEMA = 1;
    static final String TABLE_PARAM = "param";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "NameBTN";
    public static final String COLUMN_IPADDR = "IPAddress";


    public DBHelper(@Nullable Context context) {

        super(context, DB_NAME, null, SCHEMA);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_PARAM+" (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_IPADDR  + " TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
