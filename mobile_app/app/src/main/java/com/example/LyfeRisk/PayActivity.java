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
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

public class PayActivity extends Activity {
    private static final String PAYPAL_CLIENT_ID = "AcLjqJRuS8qElmyPzPO7V0TqXzxOdgCVhjdBOTwgQC_Zn2TxOdfWsr6DE3tiMZO-lNBsDqybkxu2sQyS";
    private static final int REQUEST_CODE_PAYMENT = 1;
    private boolean paypalServiceStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        Log.d("PayPal", "onCreate: Started");

        if (!paypalServiceStarted) {
            startPayPalService();
            paypalServiceStarted = true;
        }

        createSubscription();
    }

    private void startPayPalService() {
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,
                new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                        .clientId(PAYPAL_CLIENT_ID));
        startService(intent);
    }

    private void createSubscription() {
        PayPalPayment payment = new PayPalPayment(
                new BigDecimal("2.00"),
                "EUR",
                "LyfeRisk Subscription",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent paymentIntent = new Intent(this, PaymentActivity.class);
        paymentIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,
                new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                        .clientId(PAYPAL_CLIENT_ID));
        paymentIntent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(paymentIntent, REQUEST_CODE_PAYMENT);

        Log.d("PayPal", "Client ID: " + PAYPAL_CLIENT_ID);
        Log.d("PayPal", "Environment: " + PayPalConfiguration.ENVIRONMENT_SANDBOX);
    }

    @Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == REQUEST_CODE_PAYMENT) {
        if (resultCode == Activity.RESULT_OK) {
            handleSuccessfulPayment(data);
        } else if (resultCode == Activity.RESULT_CANCELED) {
            handlePaymentCanceled();
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            handleInvalidPayment();
        }

        stopService(new Intent(this, PayPalService.class));
        finish();
    }
}

    private void handleSuccessfulPayment(Intent data) {
        PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
        if (confirmation != null) {
            String paymentId = confirmation.getProofOfPayment().getPaymentId();
            updateSubscriptionStatus(paymentId);
        }
    }

    private void updateSubscriptionStatus(String paymentId) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("PatientUsers").child(userId);

        userRef.child("isPaid").setValue(true)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PayActivity.this, "Payment and subscription updated successfully", Toast.LENGTH_SHORT).show();
                        Log.d("PayPal", "Subscription updated successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PayActivity.this, "Failed to update subscription status. Please try again.", Toast.LENGTH_SHORT).show();
                        Log.e("PayPal", "Failed to update subscription status", e);
                    }
                });
    }

    private void handlePaymentCanceled() {
        Toast.makeText(this, "Payment canceled", Toast.LENGTH_SHORT).show();
        Log.d("PayPal", "Payment canceled by user");
    }

    private void handleInvalidPayment() {
        Toast.makeText(this, "Invalid payment. Please try again.", Toast.LENGTH_SHORT).show();
        Log.e("PayPal", "Invalid payment");
    }
}
