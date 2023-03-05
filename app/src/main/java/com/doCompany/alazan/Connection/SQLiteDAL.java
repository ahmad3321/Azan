package com.doCompany.alazan.Connection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLiteDAL extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDB.db";
    public static final String TABLE_Salah_Time = "SalahTime";


    public SQLiteDAL(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String salahCreate = "CREATE TABLE '" + TABLE_Salah_Time + "' (" +
                "'date'	TEXT," +
                "'fajr'	TEXT," +
                "'duha' TEXT," +
                "'dhuhor' TEXT," +
                "'asr' TEXT," +
                "'moghrib' TEXT," +
                "'eshaa' TEXT" +
                ");";

        try {
            db.execSQL(salahCreate);
        } catch (Exception ex) {

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("Drop Table If Exists " + TABLE_Salah_Time);
            onCreate(db);
        } catch (Exception ex) {
        }
    }

    public void addDay(String dayDate, SalatRecord salatRecord) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("date", dayDate);
            contentValues.put("fajr", salatRecord.getFajr());
            contentValues.put("duha", salatRecord.getDuha());
            contentValues.put("dhuhor", salatRecord.getDhuhor());
            contentValues.put("asr", salatRecord.getAsr());
            contentValues.put("moghrib", salatRecord.getMoghrib());
            contentValues.put("eshaa", salatRecord.getEshaa());

            db.insert(TABLE_Salah_Time, null, contentValues);

        } catch (Exception ex) {
        }
    }


    public void addListOfDays(ArrayList<SalatRecord> salatRecords) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            for (int i = 0; i < salatRecords.size(); i++) {
                contentValues.put("date", salatRecords.get(i).getDate());
                contentValues.put("fajr", salatRecords.get(i).getFajr());
                contentValues.put("duha", salatRecords.get(i).getDuha());
                contentValues.put("dhuhor", salatRecords.get(i).getDhuhor());
                contentValues.put("asr", salatRecords.get(i).getAsr());
                contentValues.put("moghrib", salatRecords.get(i).getMoghrib());
                contentValues.put("eshaa", salatRecords.get(i).getEshaa());
            }
            db.insert(TABLE_Salah_Time, null, contentValues);

        } catch (Exception ex) {
        }
    }

    public void deleteDay(String dayDate) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("delete from " + TABLE_Salah_Time + " where date = '" + dayDate + "';");
        } catch (Exception ex) {
        }
    }

    public String getFirstValue(String query) {
        SQLiteDatabase db = getReadableDatabase();

        String value = "";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToNext()) {
            cursor.moveToFirst();

            value = cursor.getString(0);
            cursor.close();
        }
        return value;
    }

    public SalatRecord getSalatRecord(String dayDate) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            SalatRecord salatRecord = null;
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_Salah_Time + " WHERE date = '" + dayDate + "'; ", null);
            if (cursor != null && cursor.moveToNext()) {
                cursor.moveToFirst();
                salatRecord = new SalatRecord(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6));
            }
            if (cursor != null) {
                cursor.close();
            }
            return salatRecord;
        } catch (Exception ex) {
            return null;
        }
    }

    public void ClearTable(String tableName) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("Delete from " + tableName);
        } catch (Exception ex) {
        }
    }

    public ArrayList<String> getColumn(String tableName, String columnName) {
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<String> lstResult = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT " + columnName + " FROM " + tableName + " ;", null);

        if (cursor != null && cursor.moveToNext()) {
            cursor.moveToFirst();
            do {
                lstResult.add(cursor.getString(0));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return lstResult;
    }

    public ArrayList<String> getColumn(String query) {
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<String> lstResult = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToNext()) {
            cursor.moveToFirst();
            do {
                lstResult.add(cursor.getString(0));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return lstResult;
    }
}
