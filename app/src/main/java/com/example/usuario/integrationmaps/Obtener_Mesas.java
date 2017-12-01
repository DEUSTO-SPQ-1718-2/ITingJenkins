package com.example.usuario.integrationmaps;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Usuario on 01/12/2017.
 */

public class Obtener_Mesas {

    public ArrayList<mesa> obtener_mesas(){

        return null;
    }

    public int validarReserva(ArrayList<mesa> a){

        for(int i = 0; i<a.size();i++){
            if(a.get(i).isOcupado()){
                return 0;
            }else{

            }
        }
        return 1;
    }

    public ArrayList<String> obtener_datos(){
        ArrayList<String> a = new ArrayList<>();
        a.add("Asier");
        a.add("56");
        a.add("Tolosa");

        return a;
    }
}
