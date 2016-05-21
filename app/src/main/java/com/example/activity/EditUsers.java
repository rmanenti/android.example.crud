package com.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
public class EditUsers extends Activity implements View.OnClickListener {

    private static String position = null;

    protected TextView textViewName,
                       textViewPhone,
                       textViewAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_user );

        Intent intent = getIntent();

        position = intent.getStringExtra( "position" );

        DatabaseHandler db = new DatabaseHandler( this );

        User user = db.getUser( position );
        Log.d( "EDIT_USER", user.toString() );

        textViewName    = ( TextView ) findViewById( R.id.textViewRegName );
        textViewPhone   = ( TextView ) findViewById( R.id.textViewRegPhone );
        textViewAddress = ( TextView ) findViewById( R.id.textViewRegAddress );

        textViewName.setText( user.getName() );
        textViewPhone.setText( user.getPhone() );
        textViewAddress.setText( user.getAddress() );

        Button backButton = ( Button ) findViewById( R.id.buttonEditBack ),
               editButton = ( Button ) findViewById( R.id.buttonEdit );

        backButton.setOnClickListener( this );
        editButton.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch ( v.getId() ) {

            case R.id.buttonEditBack :

                intent = new Intent( getApplicationContext(), ListUsers.class );
                startActivity( intent );
                break;

            case R.id.buttonEdit :

                User user = new User();
                user.setName( textViewName.getText().toString() );
                user.setPhone( textViewPhone.getText().toString() );
                user.setAddress( textViewAddress.getText().toString() );
                user.setUser_id( Integer.valueOf( position ) );

                DatabaseHandler db = new DatabaseHandler( this );

                int result = db.editUser( user, position );

                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.layout_edit_user), "User successfully updated.", Snackbar.LENGTH_SHORT );

                if ( result > 0 ) {

                    mySnackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i( "SNACKBAR", "Clicked!" );
                        }
                    });
                }
                else {
                    mySnackbar.setText( "User update failed. Try again." );
                }

                mySnackbar.show();

                break;
        }
    }
}
