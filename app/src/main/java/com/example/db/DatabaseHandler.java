package com.example.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 14/05/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 3;

    //Database Name
    private static final String DATABASE_NAME = "userDB",
    // Employee table name
                                TABLE_USERS   = "users",
                                USER_ID       = "user_id",
                                USER_NAME     = "name",
                                USER_PHONE    = "phone",
                                USER_ADDRESS  = "address";



    public DatabaseHandler( Context context ) {

        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createUserTable = "CREATE TABLE %s ( \n " +
                                 "                  %s INTEGER PRIMARY KEY, \n" +
                                 "                  %s TEXT, \n " +
                                 "                  %s TEXT, \n " +
                                 "                  %s TEXT \n " +
                                 " ) ";

        db.execSQL(
                String.format( createUserTable, TABLE_USERS, USER_ID, USER_NAME, USER_PHONE, USER_ADDRESS ) );

        Log.d( "CREATE", String.format( createUserTable, TABLE_USERS, USER_ID, USER_NAME, USER_PHONE, USER_ADDRESS ));

        // db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL(
                String.format( "DROP TABLE IF EXISTS %s", TABLE_USERS ) );

        // Creates the table again
        onCreate( db );
    }

    /**
     * Adding a new user
     * @param user
     * @return
     */
    public int addUser( User user ) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( USER_NAME, user.getName() );
        values.put( USER_ADDRESS, user.getAddress() );
        values.put( USER_PHONE, user.getPhone() );

       // Inserting Row
        int id = ( int ) db.insert( TABLE_USERS, null, values );

        db.close();

        return id;
    }

    // Updating an existing user
    public int editUser( User user, String position ) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( USER_NAME, user.getName() );
        values.put( USER_PHONE, user.getPhone() );
        values.put( USER_ADDRESS, user.getAddress() );

        // Updating row
        return db.update( TABLE_USERS,
                            values,
                            String.format( "%s = ?", USER_ID ),
                            new String[] { String.valueOf( position ) } );
    }

    public User getUser( String id ) {

        User user = null;

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(  TABLE_USERS,
                                   new String[] {
                                                    USER_ID,
                                                    USER_NAME,
                                                    USER_PHONE,
                                                    USER_ADDRESS },
                                   String.format( "%s = ?", USER_ID ),
                                   new String[] { id }, null, null, null, null );

        if ( cursor != null ) {

            cursor.moveToFirst();
            user = getUserFromCursor( cursor );
        }

        db.close();

        return user;
    }

    public String[] getAllUsers() {

        List<String> users = new ArrayList<>();

        String query = String.format( "SELECT * FROM %s", TABLE_USERS );

        SQLiteDatabase db = null;

        try {

            db = getReadableDatabase();

            Cursor cursor = db.rawQuery(TABLE_USERS, null);

            if (cursor != null && cursor.moveToFirst()) {

                do {
                    users.add(getUserFromCursor(cursor).toString());
                } while (cursor.moveToNext());
            }
        }
        catch ( Exception e ) {
            Log.d( "DATABASE", "Error: " + e.getLocalizedMessage() );
        }
        finally {

            if ( db != null ) {
                db.close();
            }
        }

        return users.toArray( new String[] {} );
    }

    private User getUserFromCursor( Cursor cursor ) {

        User user = null;

        if ( cursor != null ) {

            user = new User();
            user.setUser_id( cursor.getInt( cursor.getColumnIndex( USER_ID ) ) );
            user.setName( cursor.getString( cursor.getColumnIndex( USER_NAME ) ) );
            user.setPhone( cursor.getString( cursor.getColumnIndex( USER_PHONE ) ) );
            user.setAddress( cursor.getString( cursor.getColumnIndex( USER_ADDRESS ) ) );
        }

        return user;
    }
}
