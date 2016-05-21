package com.example.activity;

import android.app.Activity;
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
public class Registration extends Activity implements View.OnClickListener {

    protected TextView textViewName,
                       textViewPhone,
                       textViewAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView( R.layout.registration );

        textViewName    = ( TextView ) findViewById( R.id.editTextRegName );
        textViewPhone   = ( TextView ) findViewById( R.id.editTextRegPhone );
        textViewAddress = ( TextView ) findViewById( R.id.editTextRegAddress );

        Button registrationBackButton = ( Button ) findViewById( R.id.buttonRegisterBack ),
               registrationButton     = ( Button ) findViewById( R.id.buttonRegister );

        registrationBackButton.setOnClickListener( this );
        registrationButton.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {

        switch ( v.getId() ) {

            case R.id.buttonRegisterBack :
                break;

            case R.id.buttonRegister :

                User user = new User();
                user.setName( textViewName.getText().toString() );
                user.setPhone( textViewPhone.getText().toString() );
                user.setAddress( textViewAddress.getText().toString() );

                DatabaseHandler db = new DatabaseHandler( this );

                int result = db.addUser( user );

                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.layout_registration), "User successfully added.", Snackbar.LENGTH_SHORT );

                if ( result > 0 ) {

                    mySnackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i( "SNACKBAR", "Clicked!" );
                        }
                    });
                }
                else {
                    mySnackbar.setText( "User addition failed. Try again." );
                }

                mySnackbar.show();

                break;
        }
    }
}
