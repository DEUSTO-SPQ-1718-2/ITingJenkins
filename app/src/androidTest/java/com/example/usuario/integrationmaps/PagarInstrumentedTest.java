package com.example.usuario.integrationmaps;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by axi_e on 28/11/2017.
 */

public class PagarInstrumentedTest {

    @Rule
    public ActivityTestRule<pagar> activityRule = new ActivityTestRule<>(
            pagar.class,
            true,
            false);

    @Test
    public void launchActivity(){

        Intent intent = new Intent();

        activityRule.launchActivity(intent);

        while(!activityRule.getActivity().getPressedPayButton()){

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while(activityRule.getActivity().getTransactionState() == 0){

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        assertEquals(1, activityRule.getActivity().getTransactionState());



    }
}
