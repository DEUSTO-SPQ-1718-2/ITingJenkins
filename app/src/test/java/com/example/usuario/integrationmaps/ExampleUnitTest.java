package com.example.usuario.integrationmaps;

import com.example.usuario.mailsender.GMailSender;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
/** Clase de tests con contiperf */
public class ExampleUnitTest {

    Logger log = LoggerFactory.getLogger(ActivityMain.class);

    @Test
    @PerfTest(invocations = 2000000000, threads = 9)
    @Required(throughput =  50000000, max = 1, totalTime = 3000)
    public void probarConti() throws Exception{

        try {
            GMailSender objeto = new GMailSender("martinbeitia@opendeusto.es", "password");
            for (int i = 0; i < 5; i++) {
                objeto.sendMail("prueba", "probando", "martinbeitia@opendeusto.es", "beitialauaizeta@gmail.com");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @PerfTest(invocations = 2000000000, threads = 25)
    @Required(throughput =  50000000, max = 1)
    public void probarContiDos() throws Exception{

        try {
            MailsPagadores objeto = new MailsPagadores();
            for (int i = 0; i < 5; i++) {
                objeto.aniadir_conbucle(10);
            }
        }catch(Exception e){
            log.error(String.valueOf(e));
        }
    }

    @Test
    @PerfTest(invocations = 2000000000, threads = 25)
    @Required(throughput =  50000000, max = 1, median = 45)
    public void probarContiTres() throws Exception{

        int i;
        ArrayList<Ciudad> array = new ArrayList<>();

        for(i=0; i < 10; i++) {
            array.add(i, new Ciudad("Donosti"+i, "Cuadrado"+i));
            System.out.println(array.toString());
            System.out.println(array.get(i).getMunicipio());
        }
    }
}