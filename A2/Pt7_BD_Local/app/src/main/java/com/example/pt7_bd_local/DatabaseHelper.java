package com.example.pt7_bd_local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "parking.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "vehicles";
    public static final String COL_NOM = "nom";
    public static final String COL_COGNOMS = "cognoms";
    public static final String COL_TELEFON = "telefon";
    public static final String COL_MARCA = "marca";
    public static final String COL_MODEL = "model";
    public static final String COL_MATRICULA = "matricula";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_MATRICULA + " TEXT PRIMARY KEY, " +
                COL_NOM + " TEXT, " +
                COL_COGNOMS + " TEXT, " +
                COL_TELEFON + " TEXT, " +
                COL_MARCA + " TEXT, " +
                COL_MODEL + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String nom, String cognoms, String telefon, String marca, String model, String matricula) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOM, nom);
        contentValues.put(COL_COGNOMS, cognoms);
        contentValues.put(COL_TELEFON, telefon);
        contentValues.put(COL_MARCA, marca);
        contentValues.put(COL_MODEL, model);
        contentValues.put(COL_MATRICULA, matricula);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean updateData(String nom, String cognoms, String telefon, String marca, String model, String matricula) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOM, nom);
        contentValues.put(COL_COGNOMS, cognoms);
        contentValues.put(COL_TELEFON, telefon);
        contentValues.put(COL_MARCA, marca);
        contentValues.put(COL_MODEL, model);
        int result = db.update(TABLE_NAME, contentValues, COL_MATRICULA + " = ?", new String[]{matricula});
        return result > 0;
    }

    public Integer deleteData(String matricula) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL_MATRICULA + " = ?", new String[]{matricula});
    }

    public Cursor getData(String matricula) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_MATRICULA + " = ?", new String[]{matricula});
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
