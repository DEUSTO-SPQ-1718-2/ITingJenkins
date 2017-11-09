package com.example.usuario.integrationmaps;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Usuario on 10/09/2017.
 */

public class Mysingleton {

    private static Mysingleton nInstance;
    private RequestQueue requestQueue;
    private static Context context;
    Logger log = LoggerFactory.getLogger(Mysingleton.class);

    private Mysingleton(Context cont){

        context = cont;
        requestQueue = getRequestQueue();
    }


    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized Mysingleton getnInstance(Context cont){

        if(nInstance==null){
            nInstance = new Mysingleton(cont);
        }
        return nInstance;
    }

    public <T> void addToRequestQue(Request<T> request){
        System.out.println("QUE COÑO ESTÁ PASANDO");
        log.warn("No esta pasando por aquí");
        requestQueue.add(request);
    }

}
