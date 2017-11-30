package com.example.usuario.integrationmaps;

import com.example.usuario.codigo_reserva.Gestion_dos_nivel;
import com.example.usuario.codigo_reserva.Reserva;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Usuario on 09/11/2017.
 */

/** Clase de test para testear el cifrado */
public class CifradoTest {

    Gestion_dos_nivel a;
    public ArrayList<Reserva> reservas;



    @Before
    public void initReservas(){

        reservas = new ArrayList<Reserva>();
        a = new Gestion_dos_nivel();
        reservas.add(new Reserva("1", "sqxtPth9MP8=]Ijv/yyNRMNXKJHlpneXHLA==]BXi1LGHfZ3hCXHv3XBJFSw==","1"));
        reservas.add(new Reserva("2", "69ruLeLFipM=]wByxoItXV3fHiM0X7OXyZA==]pq2EAQLqMnKtPI68BQhUww==","9"));

    }
    @Test
    public void desencriptarTest(){

        a.setReservas(reservas);
        System.out.println(a.getReservas().size());
        for(int i=0;i<reservas.size();i++){
            a.desencriptar(i);

        }
    }
}
