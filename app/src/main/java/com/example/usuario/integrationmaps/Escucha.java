package com.example.usuario.integrationmaps;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 27/11/2017.
 */

public class Escucha implements Response.Listener<JSONArray> {
    private List<Listitem> listItems;
    boolean terminado;


    public Escucha(){
        super();
        System.out.println("nuni");
        listItems = new ArrayList<>();
        terminado = false;
    }
    @Override
    public void onResponse(JSONArray response) {
        int count=0;
        System.out.println("jodu");
        while(count<response.length()){
            try {
                JSONObject jsonObject = response.getJSONObject(count);

                Listitem item =  new Listitem(
                        jsonObject.getString("id"),
                        jsonObject.getString("nombre"),
                        jsonObject.getString("direccion"),
                        jsonObject.getString("precio_medio"),
                        jsonObject.getString("tipo"),
                        jsonObject.getString("valoracion"),
                        jsonObject.getString("imagen"),
                        jsonObject.getString("longi"),
                        jsonObject.getString("lat")
                );
                System.out.println(item.getNombre());
                listItems.add(item);
                count++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        terminado = true;
        // addMarkers();

    }

    public List<Listitem> getList(){
        return listItems;
    }

    public boolean enviar_terminado(){
        return terminado;
    }
}
