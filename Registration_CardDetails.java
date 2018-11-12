package com.example.joeco.ecotaxiphoneapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class Registration_CardDetails extends Activity {
    private static final String TAG = "MyActivity";

    EditText editTextCardHolder,editTextCardNumber,editTextExpiry,editTextCSV;
    Button CardRegister;
    private String userId;

    //Firebase stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__card_details);

        editTextCardHolder = (EditText) findViewById(R.id.editTextCardHolder);
        editTextCardNumber = (EditText) findViewById(R.id.editTextCardNumber);
        editTextExpiry = (EditText) findViewById(R.id.editTextExpiry);
        editTextCSV = (EditText) findViewById(R.id.editTextCSV);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();
        Bundle bundle = getIntent().getExtras();
        final String name = bundle.getString("Name");
        final String pnum = bundle.getString("pnum");
        final String email = bundle.getString("email");
        final String address = bundle.getString("address");
        final String password = bundle.getString("password");
        final String dob = bundle.getString("dob");


        CardRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cName = editTextCardHolder.getText().toString();
                String num = editTextCardNumber.getText().toString();
                String Exp = editTextExpiry.getText().toString();
                String CSV = editTextCSV.getText().toString();
                if (!editTextCardHolder.getText().toString().equals("")&&!editTextCardNumber.getText().toString().equals("")&&!editTextExpiry.getText().toString().equals("")&&!editTextCSV.getText().toString().equals("")){
                    if(validateCreditCardNumber(num) == true){
                        //*************THIS IS WHERE YOU STOPPED***********
                        Customer cust = new Customer();
                        cust.setName(name);
                        cust.setPhoneNum(pnum);
                        cust.setEmail(email);
                        cust.setCardName(cName);
                        cust.setAddress(address);
                        cust.setPassword(password);
                        cust.setDob(dob);
                        cust.setExpire(Exp);
                        cust.setCsv(CSV);
                        myRef.child(userId).setValue(cust);
                        toastMessage("Info Saved");

                    }else{
                        toastMessage("Please Enter A Valid Credit card Number");
                    }
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

    //Visa validation.
    private static boolean validateCreditCardNumber(String str) {

        int[] ints = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            ints[i] = Integer.parseInt(str.substring(i, i + 1));
        }
        for (int i = ints.length - 2; i >= 0; i = i - 2) {
            int j = ints[i];
            j = j * 2;
            if (j > 9) {
                j = j % 10 + 1;
            }
            ints[i] = j;
        }
        int sum = 0;
        for (int i = 0; i < ints.length; i++) {
            sum += ints[i];
        }
        if (sum % 10 == 0) {
            System.out.println(str + " is a valid credit card number");
            return true;
        } else {
            System.out.println(str + " is an invalid credit card number");
            return false;
        }
    }
}
