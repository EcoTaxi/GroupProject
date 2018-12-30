package com.example.joeco.ecotaxiphoneapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.joeco.ecotaxiphoneapp.config.Config;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class Registration_CardDetails extends AppCompatActivity {
    private static final String TAG = "MyActivity";

    private String email;
    private String price;

    public static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        email = getIntent().getExtras().getString("email");
        price = getIntent().getExtras().getString("price");

        //Start Paypal..
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

        showPaypal();
    }

    private void showBooking() {
        Intent intent = new Intent (Registration_CardDetails.this, booking_Interface.class);
        intent.putExtra("email",email);
        //intent.putExtra("cardForm",cardForm);

        startActivity(intent);
    }

    private void showPaypal() {
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(price)), "EUR",
                "Payment for trip", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE){
            if(requestCode == RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation != null){
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }else if(requestCode == Activity.RESULT_CANCELED){
                toastMessage("Cancel");
            }
        }else if(requestCode == PaymentActivity.RESULT_EXTRAS_INVALID){
            toastMessage("Invalid Request.");
        }
    }
        @Override
            protected void onStart() {
                super.onStart();
                Toast.makeText(getApplicationContext(), "onStart called", Toast.LENGTH_LONG).show();
            }


            @Override
            protected void onResume() {
                super.onResume();
                Toast.makeText(getApplicationContext(), "onResumed called", Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onPause() {

                super.onPause();
                //Toast.makeText(getApplicationContext(), "onPause called", Toast.LENGTH_LONG).show();
            }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

        }