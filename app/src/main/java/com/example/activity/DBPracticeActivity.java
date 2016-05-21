package com.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.db.R;

/**
 * Created by Administrador on 14/05/2016.
 */
public class DBPracticeActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );

        setContentView( R.layout.main );

        Button registerButton = ( Button ) findViewById( R.id.buttonMainRegister ),
               listButton     = ( Button ) findViewById( R.id.ButtonMainList );

        registerButton.setOnClickListener( this );
        listButton.setOnClickListener( this );
    }

    @Override
    public void onClick( View v ) {

        Intent intent;

        switch ( v.getId() ) {

            case R.id.buttonMainRegister :

                intent = new Intent();
                intent.setClass( getApplicationContext(), Registration.class );
                startActivity( intent );
                break;

            case R.id.ButtonMainList :

                intent = new Intent();
                intent.setClass( getApplicationContext(), ListUsers.class );
                startActivity( intent );
                break;
        }
    }
}
