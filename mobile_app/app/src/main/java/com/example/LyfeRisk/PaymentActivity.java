package com.example.LyfeRisk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;

import java.math.BigDecimal;

public class PaymentActivity extends Activity {

    private static final String PAYPAL_CLIENT_ID = "ASz2PyFUD-2oIrvM_7HdLP7JLT1KLPDFoaBgeuj_2tZVJxrMRY0BgWZPycX0U4hogIBVRal8tGf3xjDY";


    public static final String EXTRA_PAYMENT = "EXTRA_PAYMENT";
    public static final String EXTRA_RESULT_CONFIRMATION = "EXTRA_RESULT_CONFIRMATION";
    public static final int RESULT_EXTRAS_INVALID = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,
                new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX));
        startService(intent);


        PayPalPayment payment = new PayPalPayment(new BigDecimal("5.00"), "USD", "Premium Subscription",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent paymentIntent = new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);
        paymentIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,
                new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX));
        paymentIntent.putExtra(EXTRA_PAYMENT, payment);

        startActivityForResult(paymentIntent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();

            // TODO: Update user's subscription status
        } else if (resultCode == Activity.RESULT_CANCELED) {

            Toast.makeText(this, "Payment canceled", Toast.LENGTH_SHORT).show();
        } else if (resultCode == RESULT_EXTRAS_INVALID) {

            Toast.makeText(this, "Invalid payment", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
