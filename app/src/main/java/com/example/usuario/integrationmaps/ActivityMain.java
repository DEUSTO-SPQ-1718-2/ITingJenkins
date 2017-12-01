package com.example.usuario.integrationmaps;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    int state = 0;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    /** Constructor de la ventana de elección de mesas*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        confirmar = (Button)findViewById(R.id.confirmar);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  /** Listener que lleva a la pestaña de nº eCamareros */
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
        obtener_mesas();

        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                System.out.println("idddddddddddd" + item.getItemId());
                Intent i;
                switch (id) {
                    case R.id.account:
                        Toast.makeText(ActivityMain.this, "My Account", Toast.LENGTH_SHORT).show();
                        i = new Intent(ActivityMain.this, perfil.class);
                        startActivity(i);
                        return true;
                    case R.id.settings:
                        finish();
                        return true;
                    case R.id.mycart:
                        Toast.makeText(ActivityMain.this, "My Cart", Toast.LENGTH_SHORT).show();
                        System.out.println("ue");
                        finishAffinity();
                        System.exit(0);
                        return true;
                    default:
                        return true;
                }
            }

        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


    /** Método para pintar las mesas en pantalla */
    public void obtener_mesas(){

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

                          ActivityMain.MyAdapter adapter = new ActivityMain.MyAdapter(mesas, logos);
                          rvMain.setLayoutManager(new GridLayoutManager(ActivityMain.this, 3));
                          rvMain.setAdapter(adapter);

                          System.out.println("Paso para cambiar el estado a 1");
                          state = 1;

                      } catch (JSONException e) {
                          e.printStackTrace();
                          System.out.println("Paso para cambiar el estado a -1");
                          state = -1;
                      }
                  }

              } ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Algo ha ido mal", Toast.LENGTH_LONG).show();
                System.out.println("Pasa por el onError Response");
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("restaurante_id", "26" );
                System.out.println("Pasa por el getParams");
                return params;
            }
        };

        Mysingleton.getnInstance(this).addToRequestQue(stringRequest);

        System.out.println("Pasa con estado " + state);
    }

    public int getState(){

        return state;
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
                        /**metodo de comprobaciones de vinculados */
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
