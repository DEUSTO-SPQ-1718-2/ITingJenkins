package com.example.usuario.integrationmaps;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Usuario on 01/12/2017.
 */

public class MockingInstrumentedTest {
    ArrayList<mesa> mesas;

    @Rule
    public ActivityTestRule<perfilTestActivity> activityRule = new ActivityTestRule<perfilTestActivity>(
            perfilTestActivity.class,
            true,
            false);

    @Before
    public void init(){
        mesa uno = new mesa("1", "2", "3", "0", "","3");
        mesas = new ArrayList<>();
        mesas.add(uno);

    }

    @Test
    public void pruebas(){
//        Obtener_Mesas mesa = mock(Obtener_Mesas.class);
//        when(mesa.obtener_mesas()).thenReturn(mesas);
//        assertThat(mesas.get(0).getNombre(), is("2"));
//
//        int respuesta = mesa.validarReserva(mesas);
//
//        if(respuesta==0) {
//            assertThat(respuesta, is(equalTo(0)));
//
//            //ArrayList<String> a= mesa.obtener_datos();
//            Intent intent = new Intent();
//
//            Bundle bundle = new Bundle();
//            bundle.putString("nombre", "Asier");
//            bundle.putString("edad", "25");
//            bundle.putString("ciudad", "Urnieta");
//            intent.putExtras(bundle);
//
//            activityRule.launchActivity(intent);
//
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//
//        }else{
//            assertThat(respuesta, is(equalTo(0)));
//            System.out.println("ha dado un error");
//        }






    }
}
