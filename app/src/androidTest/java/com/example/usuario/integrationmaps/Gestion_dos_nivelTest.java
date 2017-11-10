package com.example.usuario.integrationmaps;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.usuario.codigo_reserva.Gestion_dos_nivel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by axi_e on 10/11/2017.
 */
@RunWith(AndroidJUnit4.class)
public class Gestion_dos_nivelTest {

    private int random;
    private String para_envio;
    private int encriptar;
    private ArrayList<String> mails;

    @Rule
    public ActivityTestRule<Gestion_dos_nivel> activityRule = new ActivityTestRule<>(
         Gestion_dos_nivel.class,
            true,
            false);

    @Before
    public void init(){

        random = 123;
        para_envio = "";
        encriptar = 1;
        mails = new ArrayList<String>();
        mails.add("asier.elorza@opendeusto.es");
    }

    @Test
    public void launchActivity(){

        try{

            Intent intent = new Intent();
            Bundle b = new Bundle();
            b.putInt("pass", random);
            b.putString("mails_formateado", para_envio);
            b.putStringArrayList("mails", mails);
            b.putInt("encriptar", encriptar);
            intent.putExtras(b);
            activityRule.launchActivity(intent);

            assertTrue(true);

        } catch(Exception e){

            assertTrue(false);

        }
    }

}