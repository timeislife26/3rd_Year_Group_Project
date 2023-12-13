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
    private static final int REQUEST_CODE_PAYMENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,
                new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                        .clientId(PAYPAL_CLIENT_ID));
        startService(intent);
        PayPalPayment payment = new PayPalPayment(new BigDecimal("5.00"), "USD", "Premium Subscription",
                PayPalPayment.PAYMENT_INTENT_SALE);
        Intent paymentIntent = new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);
        paymentIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,
                new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                        .clientId(PAYPAL_CLIENT_ID));
        paymentIntent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(paymentIntent, REQUEST_CODE_PAYMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();
                // TODO: Update user's subscription status or perform other actions
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Payment canceled", Toast.LENGTH_SHORT).show();
            } else if (resultCode == com.paypal.android.sdk.payments.PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(this, "Invalid payment", Toast.LENGTH_SHORT).show();
            }
        }
        stopService(new Intent(this, PayPalService.class));
        finish();
    }
}
