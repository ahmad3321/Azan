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

import com.doCompany.alazan.Models.SalatRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLiteDAL extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDB.db";
    public static final String TABLE_Salah_Time = "SalahTime";

    String[] SalahColumns = new String[]{"date", "imsak", "fajr", "duha", "dhuhor", "asr", "moghrib", "eshaa"};

    public SQLiteDAL(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String salahCreate = "CREATE TABLE '" + TABLE_Salah_Time + "' (" +
                "'date' TEXT," +
                "'imsak' TEXT," +
                "'fajr' TEXT," +
                "'duha' TEXT," +
                "'dhuhor' TEXT," +
                "'asr' TEXT," +
                "'moghrib' TEXT," +
                "'eshaa' TEXT);";
        try {
            db.execSQL(salahCreate);
        } catch (Exception ex) {
            Log.d("SQLITE", ex.toString());
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

    public void addDay(SalatRecord salatRecord) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(SalahColumns[0], salatRecord.getDate());
            contentValues.put(SalahColumns[1], salatRecord.getImsak());
            contentValues.put(SalahColumns[2], salatRecord.getFajr());
            contentValues.put(SalahColumns[3], salatRecord.getDuha());
            contentValues.put(SalahColumns[4], salatRecord.getDhuhor());
            contentValues.put(SalahColumns[5], salatRecord.getAsr());
            contentValues.put(SalahColumns[6], salatRecord.getMoghrib());
            contentValues.put(SalahColumns[7], salatRecord.getEshaa());

            db.insert(TABLE_Salah_Time, null, contentValues);

        } catch (Exception ex) {
        }
    }

    public void addListOfDays(ArrayList<SalatRecord> salatRecords) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            for (int i = 0; i < salatRecords.size(); i++) {
                contentValues.put(SalahColumns[0], salatRecords.get(i).getDate());
                contentValues.put(SalahColumns[1], salatRecords.get(i).getImsak());
                contentValues.put(SalahColumns[2], salatRecords.get(i).getFajr());
                contentValues.put(SalahColumns[3], salatRecords.get(i).getDuha());
                contentValues.put(SalahColumns[4], salatRecords.get(i).getDhuhor());
                contentValues.put(SalahColumns[5], salatRecords.get(i).getAsr());
                contentValues.put(SalahColumns[6], salatRecords.get(i).getMoghrib());
                contentValues.put(SalahColumns[7], salatRecords.get(i).getEshaa());
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
                        cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
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
