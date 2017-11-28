package com.example.usuario.integrationmaps;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * Created by Usuario on 27/11/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class prueba_JsonTest {
    Soporte_unit a;
    Context context;
    @Before
    public void inicializar(){

        context = RuntimeEnvironment.application.getApplicationContext();
        a = new Soporte_unit(context);
    }
    @Test
    public void bucle(){
        a.recuperar_rests();
        while(!a.getTerminado()) {

        }
        System.out.println(a.getTerminado());
        System.out.println(a.obtenerLista().size());

    }
}
