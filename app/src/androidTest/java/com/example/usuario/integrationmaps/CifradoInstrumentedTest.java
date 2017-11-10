package com.example.usuario.integrationmaps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.example.usuario.codigo_reserva.Gestion_dos_nivel;
import com.example.usuario.codigo_reserva.Reserva;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * Created by Usuario on 09/11/2017.
 */

@RunWith(AndroidJUnit4.class)
public class CifradoInstrumentedTest {


    @Rule
    public ActivityTestRule<Gestion_dos_nivel> activityRule
            = new ActivityTestRule<>(
            Gestion_dos_nivel.class,
            true,     // initialTouchMode
            false);   // launchActivity. False to customize the intent

    @Test
    public void intent() {
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putInt("pass", Integer.parseInt("213414"));
        b.putInt("encriptar", 0);
        intent.putExtras(b);
        activityRule.launchActivity(intent);


    }



}
