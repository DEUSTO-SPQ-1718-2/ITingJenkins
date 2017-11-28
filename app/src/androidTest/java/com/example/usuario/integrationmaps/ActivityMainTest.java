package com.example.usuario.integrationmaps;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by axi_e on 10/11/2017.
 */
public class ActivityMainTest {

    @Rule
    public ActivityTestRule<ActivityMain> activityRule = new ActivityTestRule<ActivityMain>(
            ActivityMain.class,
            true,
            false);

    @Test
    public void launchActivity(){

        Intent intent = new Intent();

        activityRule.launchActivity(intent);

        while(activityRule.getActivity().getState() == 0){

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Tamaño mesas: " + activityRule.getActivity().mesas.size());

        assertEquals(1, activityRule.getActivity().getState());
    }

}