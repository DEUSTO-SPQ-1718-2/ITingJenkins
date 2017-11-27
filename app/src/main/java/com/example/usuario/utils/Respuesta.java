package com.example.usuario.utils;

import android.support.v7.widget.GridLayoutManager;

import com.android.volley.Response;
import com.example.usuario.integrationmaps.ActivityMain;
import com.example.usuario.integrationmaps.mesa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created by axi_e on 27/11/2017.
 */

public class Respuesta implements Response.Listener<String> {

    private ArrayList<mesa> mesas;
    private Logger log = LoggerFactory.getLogger(Respuesta.class);
    private int state = 0;

    public Respuesta(){
        super();
        mesas = new ArrayList<mesa>();
    }

    @Override
    public void onResponse(String response) {

        try {
            JSONArray jsonArray = new JSONArray(response);
            System.out.println(response);
            log.debug("La respuesta del metodo obtener mesas: " +  response);
            for(int i =0;i<jsonArray.length();i++) {
                //System.out.println(jsonArray.length());
                log.debug("Tamaño del array: " + jsonArray.length());
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //System.out.println(jsonObject.getString("ocupado")+"jaaaaaa");
                log.debug("Valor de vble ocupado: " + jsonObject.getString("ocupado"));
                mesa mes =  new mesa(
                        jsonObject.getString("id"),
                        jsonObject.getString("nombre"),
                        jsonObject.getString("comensales"),
                        jsonObject.getString("ocupado"),
                        "",
                        jsonObject.getString("id_restaurante")

                );
                mesas.add(mes);

            }

            System.out.println("Paso para cambiar el estado a 1");
            state = 1;

        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Paso para cambiar el estado a -1");
            state = -1;
        }
    }

    public ArrayList<mesa> getMesas() {
        return mesas;
    }

    public void setMesas(ArrayList<mesa> mesas) {
        this.mesas = mesas;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
