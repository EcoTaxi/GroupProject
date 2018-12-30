package com.example.joeco.ecotaxiphoneapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    private DatabaseReference mDatabase;

    FirebaseAuth mAuth;
    Button SignUpButton, loginButton;
    EditText editTextEmail, editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        //Initialise btns
        SignUpButton = (Button) findViewById(R.id.SignUpButton);
        loginButton = (Button) findViewById(R.id.loginButton);

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterPage(v);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startBookingInterface("Test");

                if (!editTextEmail.getText().toString().equals("") || !editTextPassword.getText().toString().equals("")) {
                    userLogin();
                }else {
                    toastMessage("Please enter your Email and Password.");
                }

            }
        });

        }

    private void userLogin() {
        final String emailInput = editTextEmail.getText().toString().trim();
        final String passwordInput = editTextPassword.getText().toString().trim();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth.signInWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startBookingInterface(emailInput);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            toastMessage("Somethings gone wrong...");

                        }

                        // ...
                    }
                });

    }



    private void startBookingInterface(String email) {
        Intent intent = new Intent (MainActivity.this, booking_Interface.class);
        intent.putExtra("email",email);
        startActivity(intent);
    }

    private void showRegisterPage(View v)
    {
        Intent intent = new Intent (MainActivity.this, registration.class);
        startActivity(intent);
    }

    @Override
    protected void onStart(){

        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
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
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
