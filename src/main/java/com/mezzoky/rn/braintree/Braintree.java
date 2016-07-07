package com.mezzoky.rn.braintree;

import android.content.Intent;
import android.app.Activity;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactMethod;

import com.braintreepayments.api.PaymentRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.braintreepayments.api.BraintreePaymentActivity;

import com.facebook.react.bridge.Promise;


public class Braintree extends ReactContextBaseJavaModule implements ActivityEventListener {
  private static final int REQUEST_CODE = 8888;
  private Promise mPromise;

  public Braintree(ReactApplicationContext reactContext) {
    super(reactContext);
    reactContext.addActivityEventListener(this);
  }

  @Override
  public String getName() {
    return "Braintree";
  }

  @ReactMethod
  public void paymentRequest(final String ctoken, final Promise promise) {
    mPromise = promise;

    PaymentRequest paymentRequest = new PaymentRequest()
      .clientToken(ctoken);

    Activity currentActivity = getCurrentActivity();
    currentActivity.startActivityForResult(
      paymentRequest.getIntent(currentActivity),
      REQUEST_CODE
    );
  }

  @Override
  public void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
    if (requestCode != REQUEST_CODE) {
      return;
    }

    switch (resultCode) {
      case Activity.RESULT_OK:
        PaymentMethodNonce paymentMethodNonce = intent.getParcelableExtra(
          BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE
        );
        String nonce = paymentMethodNonce.getNonce();
        mPromise.resolve(nonce);
        break;
      case BraintreePaymentActivity.BRAINTREE_RESULT_DEVELOPER_ERROR:
      case BraintreePaymentActivity.BRAINTREE_RESULT_SERVER_ERROR:
      case BraintreePaymentActivity.BRAINTREE_RESULT_SERVER_UNAVAILABLE:
        String err = intent.getStringExtra(
          BraintreePaymentActivity.EXTRA_ERROR_MESSAGE
        );
        mPromise.reject(err);
        break;
      default:
        break;
    }
  }

}
