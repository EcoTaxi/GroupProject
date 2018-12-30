package com.example.joeco.ecotaxiphoneapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Formatter;


public class registration extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    private static final String PREFS_NAME = "prefName";


    EditText editTextName,editTextPass,editTextPhoneNumber,editTextEmail,editTextAddress,editTextDOB;
    FirebaseAuth mAuth;
    Button continueButton, socialButton;
    private DatabaseReference mDatabase;
    private int mYear;
    private int mMonth;
    private int mDay;


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
        editTextDOB.setShowSoftInputOnFocus(false);

        editTextDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(registration.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                editTextDOB.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });



        continueButton = (Button) findViewById(R.id.continueButton);
        mAuth = FirebaseAuth.getInstance();
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextName.getText().toString().equals("") || !editTextPass.getText().toString().equals("") || !editTextDOB.getText().toString().equals("") || !editTextEmail.getText().toString().equals("")  || !editTextAddress.getText().toString().equals("") || !editTextPhoneNumber.getText().toString().equals(""))
                {

                    //Validate ID onCreate
                    String s = "User";
                    mDatabase = FirebaseDatabase.getInstance().getReference(s);
                    Customer cust = new Customer();
                    cust.setName(editTextName.getText().toString());
                    cust.setPhoneNum(editTextPhoneNumber.getText().toString());
                    cust.setEmail(editTextEmail.getText().toString());
                    cust.setAddress(editTextAddress.getText().toString());
                    cust.setPassword(editTextPass.getText().toString());
                    cust.setDob(editTextDOB.getText().toString());


                    String userId = mDatabase.push().getKey();
                    mDatabase.child(userId).setValue(cust);


                    createUser(editTextEmail.getText().toString(), editTextEmail.getText().toString());
                }else{
                    toastMessage("Please Complete the Sign Up");
                }
            }
        });
    }


    public void createUser(String email, String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(registration.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            toastMessage("User added");
                            sendMessage();
                        }else{
                            Log.d("FirebaseAuth","onComplete" + task.getException().getMessage());
                            toastMessage("Somethings gone wrong...");
                        }
                    }
                });
    }

    //This is for the continue Button
    public void sendMessage() {
        //creates an intent and bundles it
        Intent intent = new Intent(registration.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}