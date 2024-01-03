package com.example.LyfeRisk;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.braintreepayments.api.DropInClient;
import com.braintreepayments.api.DropInListener;
import com.braintreepayments.api.DropInRequest;
import com.braintreepayments.api.DropInResult;
import com.braintreepayments.api.GooglePayRequest;
import com.braintreepayments.api.PayPalCheckoutRequest;
import com.braintreepayments.api.PayPalPaymentIntent;
import com.braintreepayments.api.PayPalVaultRequest;
import com.braintreepayments.api.ThreeDSecureRequest;
import com.braintreepayments.api.UserCanceledException;
import com.braintreepayments.api.VenmoPaymentMethodUsage;
import com.braintreepayments.api.VenmoRequest;
import com.google.android.gms.wallet.TransactionInfo;
import com.google.android.gms.wallet.WalletConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PayActivity extends AppCompatActivity implements DropInListener {

    private DropInClient dropInClient;
    private boolean dropInLaunched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        dropInClient = new DropInClient(this, "sandbox_9qtnsks5_vbw2373f6jwdzs4k");

        dropInClient.setListener(this);

        final ViewTreeObserver viewTreeObserver = findViewById(android.R.id.content).getViewTreeObserver();
        final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (viewTreeObserver.isAlive()) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this);
                }

                if (!dropInLaunched) {
                    launchDropIn();
                    dropInLaunched = true;
                }
            }
        };

        viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    private void launchDropIn() {
        DropInRequest dropInRequest = new DropInRequest();

//        PayPalVaultRequest payPalRequest = new PayPalVaultRequest();
//        payPalRequest.setDisplayName("LyfeRisk");
//        dropInRequest.setPayPalRequest(payPalRequest);

        PayPalCheckoutRequest payPalCheckoutRequest = new PayPalCheckoutRequest("2.00");
        payPalCheckoutRequest.setCurrencyCode("EUR");
        payPalCheckoutRequest.setBillingAgreementDescription("This is a monthly payment to LyfeRisk");
        payPalCheckoutRequest.setIntent(PayPalPaymentIntent.SALE);
        payPalCheckoutRequest.setDisplayName("LyfeRisk");
        payPalCheckoutRequest.setShouldRequestBillingAgreement(true);


        dropInRequest.setPayPalDisabled(true);
        GooglePayRequest googlePayRequest = new GooglePayRequest();
        googlePayRequest.setTransactionInfo(TransactionInfo.newBuilder()
                .setTotalPrice("2.00")
                .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                .setCurrencyCode("EUR")
                .build());

        googlePayRequest.setBillingAddressRequired(true);

        dropInRequest.setGooglePayRequest(googlePayRequest);

        dropInClient.launchDropIn(dropInRequest);
    }

    @Override
    public void onDropInSuccess(@NonNull DropInResult dropInResult) {
        Log.d("DropInSuccess", "Payment successful. Proceeding to the next activity.");

        updateIsPaidStatus();

        Intent menuIntent = new Intent(PayActivity.this, MenuActivity.class);
        try {
            startActivity(menuIntent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateIsPaidStatus() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("PatientUsers").child(userId);

        userRef.child("isPaid").setValue(true);
    }

    @Override
    public void onDropInFailure(@NonNull Exception error) {
        if (error instanceof UserCanceledException) {
            Toast.makeText(PayActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
            Intent menuIntent = new Intent(PayActivity.this, MenuActivity.class);
            try {
                startActivity(menuIntent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
            Log.e("DropInFailure", "User canceled the operation");
        } else {
            Log.e("DropInFailure", "Error: " + error.getMessage());
        }
    }
}
