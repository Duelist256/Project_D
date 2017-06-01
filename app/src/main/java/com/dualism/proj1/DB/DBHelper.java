package com.dualism.proj1.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duelist on 01.06.2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "myDictionary";
    private static final int DATABASE_VERSION = 1;

    // Table Words
    private static final String TABLE_WORDS = "posts";

    // Words Table Columns
    private static final String KEY_WORDS_ID = "id";
    private static final String KEY_WORDS_WORD = "word";
    private static final String KEY_WORDS_TRANSLATION = "translation";


    private static DBHelper sInstance;

    private final String TAG = "Database";

    public static synchronized DBHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORDS_TABLE = "CREATE TABLE " + TABLE_WORDS +
                "(" +
                KEY_WORDS_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_WORDS_WORD + " TEXT," + // Define a foreign key
                KEY_WORDS_TRANSLATION + " TEXT" +
                ")";


        db.execSQL(CREATE_WORDS_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
            //db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }
    }

    // Insert a post into the database
    public void addWord(Word word) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            // The user might already exist in the database (i.e. the same user created multiple posts).
            //long userId = addOrUpdateUser(word.user);

            ContentValues values = new ContentValues();
            values.put(KEY_WORDS_WORD, word.getWord());
            values.put(KEY_WORDS_TRANSLATION, word.getTranslation());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_WORDS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    // Get all posts in the database
    public List<Word> getAllWords() {
        List<Word> words = new ArrayList<>();

        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s ", TABLE_WORDS);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Word newWord = new Word();
                    newWord.setWord(cursor.getString(cursor.getColumnIndex(KEY_WORDS_WORD)));
                    newWord.setTranslation(cursor.getString(cursor.getColumnIndex(KEY_WORDS_TRANSLATION)));;
                    words.add(newWord);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return words;
    }

    // Update example
    /*public int updateUserProfilePicture(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_PROFILE_PICTURE_URL, user.profilePictureUrl);

        // Updating profile picture url for user with that userName
        return db.update(TABLE_USERS, values, KEY_USER_NAME + " = ?",
                new String[] { String.valueOf(user.userName) });
    }*/

    // Delete all posts and users in the database
    public void deleteAllWords() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(TABLE_WORDS, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all posts and users");
        } finally {
            db.endTransaction();
        }
    }
}
