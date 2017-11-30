package com.example.usuario.integrationmaps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vishal on 4/3/2017.
 */

/** @brief Clase asociada a la pantalla de elegir las mesas para la reserva */
public class ActivityMain extends AppCompatActivity {

    RecyclerView rvMain;
    ArrayList<mesa> mesas;
    Bitmap[] logos;
    Bitmap [] logo_ocupado;
    Button confirmar;
    Logger log = LoggerFactory.getLogger(ActivityMain.class);

    /** Constructor de la ventana de elección de mesas*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        confirmar = (Button)findViewById(R.id.confirmar);
        confirmar.setOnClickListener(new View.OnClickListener() {
            /** Listener que lleva a la pestaña de nº eCamareros */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), eCamareros.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(intent);
            }
        });
        rvMain = (RecyclerView) findViewById(R.id.rvMain);
        logos = new Bitmap[1];
        logos[0] = BitmapFactory.decodeResource(getResources(), R.drawable.mesalibre);
        logo_ocupado = new Bitmap[3];
        logo_ocupado[0] = BitmapFactory.decodeResource(getResources(), R.drawable.mesaocupada);
        logo_ocupado[1] = BitmapFactory.decodeResource(getResources(), R.drawable.mesaseleccionada);
        mesas = new ArrayList<>();
        this.obtener_mesas();

    }

    /** Método para pintar las mesas en pantalla */
    public boolean obtener_mesas(){



        String URL_DATA = "http://www.iting.es/ultimophp/obtener_mesas_rest_newDB2.php";
        log.debug("Se esta atacando la base de datos: " + URL_DATA);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DATA,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            System.out.println(response);
                            log.debug("La respuesta del metodo obtener mesas: " +  response);
                            for(int i =0;i<jsonArray.length();i++) {
                                log.debug("Tamaño del array: " + jsonArray.length());
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
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
                            MyAdapter adapter = new MyAdapter(mesas, logos);
                            rvMain.setLayoutManager(new GridLayoutManager(ActivityMain.this, 3));
                            rvMain.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();


                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Algo ha ido mal", Toast.LENGTH_LONG).show();
                error.printStackTrace();


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("restaurante_id", "26" );
                return params;
            }
        };



        Mysingleton.getnInstance(this).addToRequestQue(stringRequest);

        return stringRequest.toString().contains("null");



    }


    /** Adaptador que coge los datos de las mesas de la BD y se los pasa a la view*/
    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        ArrayList<mesa> mesas;
        Bitmap[] logoList;

        public MyAdapter(ArrayList<mesa> mesas, Bitmap[] logoList) {
            this.mesas   = mesas;
            this.logoList = logoList;
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.logo.setImageBitmap(logoList[0]);
            if(mesas.get(position).isOcupado()==true) {
                holder.logo.setImageBitmap(logo_ocupado[0]);
                holder.logo.setEnabled(false);


            }else{
            }
            holder.logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(ActivityMain.this, "This is: " + mesas.get(position).getId(), Toast.LENGTH_SHORT).show();
                    System.out.println(mesas.get(position).isOcupado()+"***");
                    System.out.println(mesas.get(position).isSeleccionado()+"^^^^");
                    if(mesas.get(position).isOcupado()==false && mesas.get(position).isSeleccionado()== false) {
                        //metodo de comprobaciones de vinculados
                        boolean sepuede = comprobar(position);
                        if(sepuede) {

                            holder.logo.setImageBitmap(logo_ocupado[1]);
                            Toast.makeText(ActivityMain.this, "Mesa: " + mesas.get(position).getId() + " seleccionada", Toast.LENGTH_SHORT).show();
                            mesas.get(position).setSeleccionado(true);
                        }else{
                            Toast.makeText(ActivityMain.this, "No puede ser seleccionada", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        holder.logo.setImageBitmap(logos[0]);

                        Toast.makeText(ActivityMain.this, "Mesa NO SELECCIONADA", Toast.LENGTH_SHORT).show();
                        mesas.get(position).setSeleccionado(false);
                    }
                }
            });
            holder.name.setText(mesas.get(position).getId());
        }

        public boolean comprobar(int numero){

            boolean pasar = true;

                mesa mahai = mesas.get(numero);
                ArrayList<mesa> seleccionadas = new ArrayList<>();

                for (int i = 0; i < mesas.size(); i++) {
                    if (mesas.get(i).isSeleccionado() == true) {
                        seleccionadas.add(mesas.get(i));
                    }

                }
            if(seleccionadas.size()>0) {
                ArrayList<Integer> vinculadas;
                vinculadas = mahai.getVinculadas();
            System.out.println("si que pasaaa");

                for (int z = 0; z < seleccionadas.size(); z++) {
                    if (vinculadas.contains(Integer.parseInt(seleccionadas.get(z).getId()))) {

                        pasar = true;
                    } else {
                        pasar = false;
                        return pasar;
                    }
                }
            }

                return pasar;



        }

        @Override
        public int getItemCount() {
            return mesas.size();
        }
    }

    /** Vista que recibe las mesas del adaptador*/
    private class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView logo;
        public TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            logo = (ImageView)itemView.findViewById(R.id.ivLogo);
            name = (TextView)itemView.findViewById(R.id.tvCompany);
        }
     }
}
