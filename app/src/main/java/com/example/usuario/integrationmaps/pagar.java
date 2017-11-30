package com.example.usuario.integrationmaps;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.PaymentMethodNonce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/** @brief Clase para gestionar los pagos de la app */
public class pagar extends AppCompatActivity {

    final int REQUEST_CODE = 1;
    final String get_token = "http://192.168.91.1/BraintreePayments/main.php";
    final String send_payment_details = "http://192.168.91.1/BraintreePayments/checkout.php";
    String token, amount;
    HashMap<String, String> paramHash;

    Button btnPay;
    EditText etAmount;
    LinearLayout llHolder;

    boolean pressedPayButton = false;
    int transactionState = 0;

    Logger log = LoggerFactory.getLogger(pagar.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagos_layout);

        Bundle bundle = getIntent().getExtras();


        llHolder = (LinearLayout) findViewById(R.id.llHolder);
        etAmount = (EditText) findViewById(R.id.etPrice);
        btnPay = (Button) findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //comprobar_mesas();
                pressedPayButton = true;
                onBraintreeSubmit();

            }
        });
        new HttpRequest().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String stringNonce = nonce.getNonce();
                //Log.d("mylog", "Resultaaada: " + stringNonce);
                log.debug("Clase pagar, contenido del nonce: " + stringNonce);

                /** Send payment price with the nonce */
                /** use the result to update your UI and send the payment method nonce to your server */
                if (!etAmount.getText().toString().isEmpty()) {
                    amount = etAmount.getText().toString();
                    paramHash = new HashMap<>();
                    paramHash.put("amount", amount);
                    paramHash.put("nonce", stringNonce);
                    sendPaymentDetails();
                } else
                    Toast.makeText(pagar.this, "Please enter a valid amount.", Toast.LENGTH_SHORT).show();

            } else if (resultCode == Activity.RESULT_CANCELED) {
                /** the user canceled */
                //Log.d("mylog", "user canceled");
                log.error("La actividad de pagar ha sido cancelada, usuario cancelado ");
            } else {
                /** handle errors here, an exception may be available in */
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                //Log.d("mylog", "Error : " + error.toString());
                log.debug("Error : " + error.toString());
            }
        }
    }

    /** Método para lanzar la request a Braintree */
    public void onBraintreeSubmit() {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(token);
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }

    /** Método para comprobar si la transacción ha sido exitosa o no */
    public void sendPaymentDetails() {
        RequestQueue queue = Volley.newRequestQueue(pagar.this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, send_payment_details,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("Successful"))
                        {
                            Toast.makeText(pagar.this, "Transaction successful", Toast.LENGTH_LONG).show();
                            transactionState = 1;
                        }
                        else{
                            Toast.makeText(pagar.this, "Transaction failed", Toast.LENGTH_LONG).show();
                            transactionState = -1;
                        }
                        //Log.d("mylog", "Final Response: " + response.toString());
                        log.debug("El resultado ha sido Final Response: " + response.toString());

                        //System.out.println("AXI " + response.toString());

                        //Menu de inicio.


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mylog", "Volley error : " + error.toString());
                log.debug("Error de volley: " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                if (paramHash == null)
                    return null;
                Map<String, String> params = new HashMap<>();
                for (String key : paramHash.keySet()) {
                    params.put(key, paramHash.get(key));
                    //Log.d("mylog", "Key : " + key + " Value : " + paramHash.get(key));
                    log.debug("Clave : " + key + " Valor : " + paramHash.get(key));
                }

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public boolean getPressedPayButton(){

        return pressedPayButton;
    }

    public int getTransactionState(){

        return transactionState;
    }


    private class HttpRequest extends AsyncTask {
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(pagar.this, android.R.style.Theme_DeviceDefault_Dialog);
            progress.setCancelable(false);
            progress.setMessage("We are contacting our servers for token, Please wait");
            progress.setTitle("Getting token");
            progress.show();
        }

        /** Obtener token para pago */
        @Override
        protected Object doInBackground(Object[] objects) {
            HttpClient client = new HttpClient();
            client.get(get_token, new HttpResponseCallback() {
                @Override
                public void success(String responseBody) {
                    //Log.d("mylog", responseBody);
                    log.debug("Exitoso: " + responseBody);

                    token = responseBody;
                    //System.out.println("TOKEEEEN: " + token);
                    log.debug("Este es el token: " + token);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(pagar.this, "Successfully got token", Toast.LENGTH_SHORT).show();
                            llHolder.setVisibility(View.VISIBLE);
                        }
                    });

                }

                @Override
                public void failure(Exception exception) {
                    final Exception ex = exception;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(pagar.this, "Failed to get token: " + ex.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progress.dismiss();
        }
    }


}