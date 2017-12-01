package com.example.usuario.integrationmaps;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class perfilTestActivity extends AppCompatActivity {

    EditText a, b, c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        String nombre = bundle.getString("nombre");
        String edad = bundle.getString("edad");
        String ciudad = bundle.getString("ciudad");

        a = (EditText) findViewById(R.id.editText);
        b  = (EditText) findViewById(R.id.editText3);
        c = (EditText) findViewById(R.id.editText4);

        a.setText(nombre);
        b.setText(edad);
        c.setText(ciudad);


    }

}
