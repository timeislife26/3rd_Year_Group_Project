package com.example.LyfeRisk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;

import java.math.BigDecimal;

public class PaymentActivity extends Activity {
    private static final String PAYPAL_CLIENT_ID = "ASz2PyFUD-2oIrvM_7HdLP7JLT1KLPDFoaBgeuj_2tZVJxrMRY0BgWZPycX0U4hogIBVRal8tGf3xjDY";
    private static final int REQUEST_CODE_PAYMENT = 1;
    public static final String EXTRA_PAYMENT_AMOUNT = "EXTRA_PAYMENT_AMOUNT";
    public static final String EXTRA_PAYMENT_CURRENCY = "EXTRA_PAYMENT_CURRENCY";
    public static final String EXTRA_PAYMENT_DESCRIPTION = "EXTRA_PAYMENT_DESCRIPTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,
                new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                        .clientId(PAYPAL_CLIENT_ID));
        startService(intent);

        String paymentAmountString = getIntent().getStringExtra(EXTRA_PAYMENT_AMOUNT);

        if (paymentAmountString != null) {
            double paymentAmountDouble = Double.parseDouble(paymentAmountString);
            BigDecimal paymentAmount = BigDecimal.valueOf(paymentAmountDouble);

            Log.d("PaymentActivity", "Payment amount: " + paymentAmount.toString());

            String paymentCurrency = getIntent().getStringExtra(EXTRA_PAYMENT_CURRENCY);
            String paymentDescription = getIntent().getStringExtra(EXTRA_PAYMENT_DESCRIPTION);

            PayPalPayment payment = new PayPalPayment(paymentAmount, paymentCurrency, paymentDescription,
                    PayPalPayment.PAYMENT_INTENT_SALE);

            Intent paymentIntent = new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);
            paymentIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,
                    new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                            .clientId(PAYPAL_CLIENT_ID));
            paymentIntent.putExtra(EXTRA_PAYMENT_AMOUNT, paymentAmount.toString());
            paymentIntent.putExtra(EXTRA_PAYMENT_CURRENCY, paymentCurrency);
            paymentIntent.putExtra(EXTRA_PAYMENT_DESCRIPTION, paymentDescription);

            startActivityForResult(paymentIntent, REQUEST_CODE_PAYMENT);
        } else {
            Toast.makeText(this, "Payment amount is null", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("PayPal", "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                // Update user's subscription status in the database
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("PatientUsers").child(userId);

                userRef.child("isPaid").setValue(true)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(PaymentActivity.this, "Payment and subscription updated successfully", Toast.LENGTH_SHORT).show();
                                Log.d("PayPal", "Subscription updated successfully");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(PaymentActivity.this, "Failed to update subscription status. Please try again.", Toast.LENGTH_SHORT).show();
                                Log.e("PayPal", "Failed to update subscription status", e);
                            }
                        });
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Payment canceled", Toast.LENGTH_SHORT).show();
                Log.d("PayPal", "Payment canceled by user");
            } else if (resultCode == com.paypal.android.sdk.payments.PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(this, "Invalid payment", Toast.LENGTH_SHORT).show();
                Log.e("PayPal", "Invalid payment");
            }
        }

        stopService(new Intent(this, PayPalService.class));
        finish();
    }
}
