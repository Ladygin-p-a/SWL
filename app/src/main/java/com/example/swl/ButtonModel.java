package com.example.swl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;

public class ButtonModel {

    private static final String DB_NAME = "settings.db";
    private static final int SCHEMA = 1;
    static final String TABLE_PARAM = "param";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "NameBTN";
    public static final String COLUMN_IPADDR = "IPAddress";
    private String IPAddress;
    private String MAC_Addr;
    private String Name;
    private Boolean onLineState;

    String btnName;
    long btnId = 0;

    DBHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    SQLiteOpenHelper sqLiteOpenHelper;

    public void setOnLineState(Boolean onLineState) {
        this.onLineState = onLineState;
    }

    public Boolean getOnLineState() {
        return onLineState;
    }

    public ButtonModel(@Nullable Context context, String btnName) {

        this.btnName = btnName;
        this.onLineState = false;

        dbHelper = new DBHelper(context);

        db = dbHelper.getReadableDatabase();

        cursor = db.rawQuery("select * from "+ DBHelper.TABLE_PARAM + " where " + DBHelper.COLUMN_NAME + " = ?", new String[]{btnName});

        if (cursor.moveToFirst()){
            btnId     = cursor.getLong(0);
            this.IPAddress = cursor.getString(2);
        } else {
            this.IPAddress = "";
        }

        cursor.close();
        db.close();

        //getStateRelay(context);

    }

    public String GetURL_ON(){
        return "http://"+getIP_Addr()+"/update?output=0&state="+Constants.ON.toString();
    }

    public String GetURL_OFF(){
        return "http://"+getIP_Addr()+"/update?output=0&state="+Constants.OFF.toString();
    }

    public String GetURL_getState(){
        return "http://"+getIP_Addr()+"/getState";
    }

    public String getIP_Addr() {
        return IPAddress;
    }

    public void setIP_Addr(String IPAddress) {

        this.IPAddress = IPAddress;

        db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_NAME, btnName);
        cv.put(DBHelper.COLUMN_IPADDR, IPAddress);

        if (btnId > 0) {
            db.update(dbHelper.TABLE_PARAM, cv, dbHelper.COLUMN_ID + "="+btnId, null);
        } else {
            db.insert(dbHelper.TABLE_PARAM,null,cv);
        }

        db.close();

    }

    public String getMAC_Addr() {
        return MAC_Addr;
    }

    public void setMAC_Addr(String MAC_Addr) {
        this.MAC_Addr = MAC_Addr;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
