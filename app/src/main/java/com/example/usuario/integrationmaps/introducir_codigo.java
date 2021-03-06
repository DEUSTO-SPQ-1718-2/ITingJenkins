package com.example.usuario.integrationmaps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @brief Clase representativa de la ventana de introducción de código */
public class introducir_codigo extends AppCompatActivity {

    Button check;
    EditText primero, segundo, tercero, cuarto, qinto, sexto;
    Logger log = LoggerFactory.getLogger(introducir_codigo.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codigo);
        check = (Button)findViewById(R.id.checkear);

        primero = (EditText)findViewById(R.id.primero);
        segundo = (EditText)findViewById(R.id.segundo);
        tercero = (EditText)findViewById(R.id.tercero);
        cuarto = (EditText)findViewById(R.id.cuarto);
        qinto = (EditText)findViewById(R.id.quinto);
        sexto = (EditText)findViewById(R.id.sexto);





        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { /** Listener para recoger el código y pasarlo a la parte de cifrado */
                System.out.println(primero.getText());
                String nombre = primero.getText().toString()+segundo.getText().toString()+tercero.getText().toString()+cuarto.getText().toString()+qinto.getText().toString()+sexto.getText().toString();

                log.debug("Los numeros recogidos en el codigo son "+nombre);

                /*Intent intent = new Intent();
                Bundle a = new Bundle();

                intent.setClass(getApplicationContext(), verificar_codigo.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);*/


                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), com.example.usuario.codigo_reserva.Gestion_dos_nivel.class);
                Bundle b = new Bundle();
                b.putInt("pass", Integer.parseInt(nombre));
                b.putInt("encriptar", 0);
                intent.putExtras(b);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(intent);


            }
        });
    }
}
