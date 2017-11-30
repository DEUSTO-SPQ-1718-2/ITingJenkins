package com.example.usuario.integrationmaps;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @brief Clase representativa de la ventana de elección entre tener código o hacer la reserva */
public class Comienzo extends AppCompatActivity {

    Button concodigo;
    Button nuevareserva;
    Logger log;
    int requestCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eleccion);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CONTACTS},
                requestCode);


        concodigo = (Button)findViewById(R.id.concodigo);
        concodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { /** Salta a la ventana de introducir código */
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), introducir_codigo.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                log.info("Opcion codigo elegida por usuario");
                log.info( Environment.getExternalStorageDirectory().toString());
            }
        });
        nuevareserva = (Button)findViewById(R.id.nuevareserva);
        nuevareserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { /** Salta a la ventana de elección de mesa */
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ActivityMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                log.info("Opcion reserva mesa elegida por usuario");

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        log = LoggerFactory.getLogger(Comienzo.class);
        System.out.println("Pasa por la inicialización logger");
    }
}
