package com.example.usuario.codigo_reserva;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by axi_e on 28/11/2017.
 */

public class Gestion_dos_nivelInstrumentedTest {

    @Rule
    public ActivityTestRule<Gestion_dos_nivel> activityRule = new ActivityTestRule<>(
            Gestion_dos_nivel.class,
            true,
            false);

    @Test
    public void launchActivity(){

        Intent intent = new Intent();

        Bundle b = new Bundle();
        b.putInt("pass", 342122);
        b.putInt("encriptar", 0);

        intent.putExtras(b);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activityRule.launchActivity(intent);

        while(activityRule.getActivity().getState() == 0){

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        assertEquals(-1, activityRule.getActivity().getState());

    }
}
