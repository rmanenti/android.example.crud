package com.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.db.DatabaseHandler;
import com.example.db.R;
import com.example.db.User;

/**
 * Created by Administrador on 14/05/2016.
 */
public class DetailUsers extends Activity implements View.OnClickListener {

    private static String position = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.details_users );

        Intent intent = getIntent();

        position = intent.getStringExtra( "position" );

        Log.d( "DETAIL_USERS", "Position: " + position );

        DatabaseHandler db = new DatabaseHandler( this );

        User user = db.getUser( position );

        TextView textViewDetailName    = ( TextView ) findViewById( R.id.textViewDetailsName ),
                 textViewDetailAddress = ( TextView ) findViewById( R.id.textViewDetailsAddress ),
                 textViewDetailPhone   = ( TextView ) findViewById( R.id.textViewDetailsPhone );

        textViewDetailName.setText( user.getName() );
        textViewDetailAddress.setText( user.getAddress() );
        textViewDetailPhone.setText( user.getPhone() );

        Button backButton = ( Button ) findViewById( R.id.buttonDetailsBack ),
               editButton = ( Button ) findViewById( R.id.buttonEditDetails );

        backButton.setOnClickListener( this );
        editButton.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {

        Intent intent = null;

        switch ( v.getId() ) {

            case R.id.buttonDetailsBack :

                intent = new Intent( getApplicationContext(), ListUsers.class );
                startActivity( intent );
                break;

            case R.id.buttonEditDetails :

                intent = new Intent( getApplicationContext(), EditUsers.class );
                intent.putExtra( "position", position );
                startActivity( intent );
        }
    }
}
