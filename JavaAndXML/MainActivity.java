package com.example.joeco.ecotaxiphoneapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button SignUpButton, loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialise btns
        SignUpButton = (Button) findViewById(R.id.SignUpButton);
        loginButton = (Button) findViewById(R.id.loginButton);

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterPage(v);
            }
        });

        }

    private void showRegisterPage(View v)
    {
        Intent intent = new Intent (MainActivity.this, booking_Interface.class);
        startActivity(intent);
    }

    @Override
    protected void onStart(){

        super.onStart();
    }

    @Override
    protected void onResume(){

        super.onResume();
    }

    @Override
    protected void onPause(){

        super.onPause();
    }

    @Override
    protected void onDestroy(){

        super.onDestroy();
    }

}
