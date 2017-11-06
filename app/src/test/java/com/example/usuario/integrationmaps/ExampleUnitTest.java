package com.example.usuario.integrationmaps;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

//    @Test
//    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
//    }

    @Test
    @PerfTest(invocations = 2000000000, threads = 9)
    @Required(throughput =  50000000, max = 1)
    public void probarConti() throws Exception{

        try {
            for (int i = 0; i < 2100000000; i++) {
                MailsPagadores objeto = new MailsPagadores();
                objeto.aniadir_conbucle(2);
            }
        }catch(Exception e){

        }
    }
}