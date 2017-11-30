package com.example.usuario.codigo_reserva;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.usuario.integrationmaps.pagar;
import com.example.usuario.mailsender.MailSender;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

/** @brief Clase que gestiona las llamadas a los métodos de encriptación */
public class Gestion_dos_nivel extends Activity{

    private static final String TAG = Gestion_dos_nivel.class.getSimpleName();
    private static String MESSAGE="vamos";
    private String password = "213414";//Por eso he hecho un numero super aleatorio. Ya que con dos mismos passwords se puede llegar a entrar.
    //Por eso la aleatoriedad, ya que podría estar esperando al final del dia y el 0001 siempre siempre siempre caerá!
    ArrayList<Reserva> reservas;
    Reserva reserva;
    int contador=0;
    int i =0;
    Logger log = LoggerFactory.getLogger(Gestion_dos_nivel.class);

    abstract class Encryptor {
        SecretKey key;

        abstract SecretKey deriveKey(String passpword, byte[] salt);

        abstract String encrypt(String plaintext, String password);

        abstract String decrypt(String ciphertext, String password);


        String getRawKey() {
            if (key == null) {
                return null;
            }

            return Crypto_dos_nivel.toHex(key.getEncoded());


        }
    }

    public void setReservas(ArrayList<Reserva> a){
        reservas= a;
    }

    public ArrayList<Reserva> getReservas(){
     return reservas;
    }


    private final Encryptor PBKDF2_ENCRYPTOR = new Encryptor() {

        @Override
        public SecretKey deriveKey(String password, byte[] salt) {
            return Crypto_dos_nivel.deriveKeyPbkdf2(salt, password);
        }

        @Override
        public String encrypt(String plaintext, String password) {
            byte[] salt = Crypto_dos_nivel.generateSalt();
            key = deriveKey(password, salt);
            //Log.d(TAG, "Generated key: " + getRawKey());
            log.debug("Se ha generado la clave: " + getRawKey());

            return Crypto_dos_nivel.encrypt(plaintext, key, salt);
        }

        @Override
        public String decrypt(String ciphertext, String password) {
            System.out.println("vamos");
            return Crypto_dos_nivel.decryptPbkdf2(ciphertext, password);
        }
    };


    private String ciphertext1;
    String para_envio;
    ArrayList<String>mails;

    private Encryptor encryptor;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        int intermedio;
        intermedio = bundle.getInt("pass");
        para_envio = bundle.getString("mails_formateado");
        mails=bundle.getStringArrayList("mails");
        int booleano= bundle.getInt("encriptar");
        System.out.println("vamos");
        MESSAGE = String.valueOf(intermedio);
        this.password = String.valueOf(intermedio);
        encryptor = PBKDF2_ENCRYPTOR;
        reservas = new ArrayList<>();
        if(booleano==0){
            recuperar_reservas();
        }else{
            encriptar();
        }
    }


    abstract class CryptoTask extends AsyncTask<Void, Void, String> {

        Exception error;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                return doCrypto();
            } catch (Exception e) {
                error = e;
                //log.e(TAG, "Error: " + e.getMessage(), e);
                log.error("Error encontrado: " + e.getMessage());

                return null;
            }
        }

        protected abstract String doCrypto();

        @Override
        protected void onPostExecute(String result) {
        String fallo="no";
            if (error != null) {
                Toast.makeText(Gestion_dos_nivel.this,
                        "Error: " + error.getMessage(), Toast.LENGTH_LONG)
                        .show();
                fallo ="si";
                log.error("El codigo introducido no existe o ya no es valido: " + error.getMessage());
            }

            if(fallo.equals("no")){
                fallo= result;

            }
            updateUi(fallo);
        }

        protected abstract void updateUi(String result);
    }

    public void encriptar(){

        final String plaintext = MESSAGE;

        new CryptoTask() {

            @Override
            protected String doCrypto() {
                return encryptor.encrypt(plaintext, password);
            }

            @Override
            protected void updateUi(String ciphertext) {
                ciphertext1= ciphertext.toString().trim();
                update_DB();
            }
        }.execute();

        System.out.println(ciphertext1);
    }

    /** Actualiza la base de datos */
    public void update_DB(){

        final String mesa_id="1";
        String URL_DATA = "http://www.iting.es/ultimophp/insertar_reserva_newDB.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DATA,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i =0;i<mails.size();i++){
                                new MailSender("****", mails.get(i), "Lasarte", password).execute();
                            }

                            Intent a = new Intent(getApplicationContext(), pagar.class);
                            startActivity(a);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Algo ha ido mal", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cifrado_texto", ciphertext1 );
                params.put("mesa_id", mesa_id );
                return params;
            }
        };

        Mysingleton.getnInstance(this).addToRequestQue(stringRequest);

    }

    public void desencriptar(int parmetro){

        i = parmetro;
        ciphertext1 = reservas.get(i).getCodigo();
        System.out.println("DESENCRIPTAR-> PASSWORD "+password);
        System.out.println("CIFRADO->"+ ciphertext1);

        new CryptoTask() {

            @Override
            protected String doCrypto() {
                return encryptor.decrypt(ciphertext1, password);
            }



            protected void updateUi(String plaintext1) {

                if(plaintext1.equals("si")){
                    contador=contador+1;
                }
                else {
                    System.out.println("resultado"+plaintext1);
                    return;
                }

                i= i+1;
                if(i==reservas.size()){
                    System.out.println("TERMINADO!!");
                }else{
                    System.out.println("Siguiente bucle!!");
                    desencriptar(i);
                }
            }

        }.execute();
        System.out.println(contador);

        }


    /** Recupera la reserva de un código de reserva existente */
    public void  recuperar_reservas(){
        String URL_DATA = "http://www.iting.es/ultimophp/recuperar_reservas_newDB.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DATA,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        try {
                            log.debug("Esta entrando a recuperar reserva desde codigo existente.");
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i =0;i<jsonArray.length();i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                reserva =  new Reserva(
                                        jsonObject.getString("id"),
                                        jsonObject.getString("codigo"),
                                        jsonObject.getString("id_mesa")
                                );
                                reservas.add(reserva);
                            }

                            desencriptar(0);
                            log.debug("Ha llegado al metodo desencriptar!");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Algo ha ido mal", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        Mysingleton.getnInstance(this).addToRequestQue(stringRequest);
    }
}
