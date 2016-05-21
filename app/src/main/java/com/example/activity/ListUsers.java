package com.example.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.db.DatabaseHandler;
import com.example.db.R;

/**
 * Created by Administrador on 14/05/2016.
 */
public class ListUsers extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        DatabaseHandler db = new DatabaseHandler( this );

        String[] users = db.getAllUsers();

        if ( users != null ) {

            for( String user : users ) {
                Log.d( "LIST_USERS", user );
            }

            db.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, R.layout.list_users, users );
        setListAdapter( adapter );
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Intent intent = new Intent();
        intent.setClass( getApplicationContext(), DetailUsers.class );
        intent.putExtra( "position", Integer.toString( position + 1 ) );
        startActivity( intent );
    }
}
