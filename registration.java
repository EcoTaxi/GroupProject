package com.example.joeco.ecotaxiphoneapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class registration extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    private static final String PREFS_NAME = "prefName";

    EditText editTextName,editTextPass,editTextPhoneNumber,editTextEmail,editTextAddress,editTextDOB;

    //Firebase stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;


    private String userId;
    Button continueButton, socialButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        editTextPass = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextDOB = (EditText) findViewById(R.id.editTextDOB);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();

        continueButton = (Button) findViewById(R.id.continueButton);


        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG,"continue pressed ");


                String name = editTextName.getText().toString();
                String pnum = editTextPhoneNumber.getText().toString();
                String email = editTextEmail.getText().toString();
                String address = editTextAddress.getText().toString();
                String password = editTextPass.getText().toString();
                String dob = editTextDOB.getText().toString();



                if (!editTextDOB.getText().toString().equals("")&&!editTextPass.getText().toString().equals("")&&!editTextAddress.getText().toString().equals("")&&!editTextEmail.getText().toString().equals("")&&!editTextPhoneNumber.getText().toString().equals("")&&!editTextName.getText().toString().equals("")){

                    editTextName.setText("");
                    editTextPass.setText("");
                    editTextPhoneNumber.setText("");
                    editTextEmail.setText("");
                    editTextAddress.setText("");
                    editTextDOB.setText("");
                    sendMessage(name,pnum,email,address,password,dob);

                }else {
                    toastMessage("fill out all the fields");
                }
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG,"on Data change added to the database: \n" + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "failed to read value", databaseError.toException());
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d(TAG,"onAuthStateChanged: signed in as:" + user.getUid());
                    toastMessage("Successfully signed in with: "  + user.getUid());
                }else {
                    Log.d(TAG,"onAuthStateChanged: signed out");
                    toastMessage("Successfully signed out");
                }
            }
        };
    }

    //This is for the continue Button
    public void sendMessage(String name,String pnum, String email,String address, String password,String dob) {
        //creates an intent and bundles it
        Intent intent = new Intent(this, Registration_CardDetails.class);
        Bundle bundle = new Bundle();
        intent.putExtra("Name",name);
        intent.putExtra("pnum",pnum);
        intent.putExtra("email",email);
        intent.putExtra("address",address);
        intent.putExtra("password",password);
        intent.putExtra("dob",dob);
        bundle.putString("Name",name);
        bundle.putString("pnum",pnum);
        bundle.putString("email",email);
        bundle.putString("address",address);
        bundle.putString("password",password);
        bundle.putString("dob",dob);
        startActivity(intent);
    }


    @Override
    public void onStart() {
         super.onStart();
         mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);

        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
