package com.dualism.proj1.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duelist on 26.03.2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dictionaries.db";
    private static final String TABLE_CONTACTS = "dictionary1";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        onCreate(db);
    }

    @Override
    public void addWordTransl(WordTransl wordTransl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, wordTransl.getWord());
        values.put(KEY_PH_NO, wordTransl.getTranslation_value());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    @Override
    public WordTransl getWordTransl(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        WordTransl wordTransl = new WordTransl(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));

        return wordTransl;
    }

    @Override
    public List<WordTransl> getAllWordTransls() {
        List<WordTransl> contactList = new ArrayList<WordTransl>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                WordTransl contact = new WordTransl();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setWord(cursor.getString(1));
                contact.setTranslation_value(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        return contactList;
    }

    @Override
    public int getWordTranslsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    @Override
    public int updateWordTransl(WordTransl contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getWord());
        values.put(KEY_PH_NO, contact.getTranslation_value());

        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    @Override
    public void deleteWordTransl(WordTransl contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[] { String.valueOf(contact.getID()) });
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, null, null);
        db.close();
    }

    // ...
    public String[] getAppCategoryDetail() {

        //final String TABLE_NAME = "name of table";

        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);
        String[] data      = null;

        String string = "";
        if (cursor.moveToFirst()) {
            do {
                // get the data into array, or class variable
                //System.out.println("Reading all contacts..");

                List<WordTransl> wordTranls = getAllWordTransls();
                for (WordTransl wr : wordTranls) {
                    String log = "Id: " + wr.getID() + " ,Word: " + wr.getWord() + " ,Translation: " + wr.getTranslation_value();
                    System.out.print("Name: ");
                    System.out.println(log);
                    string += log + "\n";
                }
            } while (cursor.moveToNext());
        }
        data[0] = string;
        cursor.close();
        return data;
    }
}
