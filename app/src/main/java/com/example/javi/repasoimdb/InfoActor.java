package com.example.javi.repasoimdb;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InfoActor extends AppCompatActivity {

    private static final String TAG = InfoActor.class.getSimpleName();
    public static final String LISTA_PELS = "listaPeliculas";

    String raiz = "http://imdb.wemakesites.net/api/";
    String apiKey = "api_key=b1aec71f-d2d3-4486-bd58-ba1a96f1e427";
    String codigo = "";
    String request = "";

    TextView nombreActor, bioActor;
    Button btnFilmografia;

    // usado para la comunicacion entre la app y la api
    OkHttpClient cliente;

    Actor actor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_actor);

        nombreActor = (TextView)findViewById(R.id.tvNombreActor);
        bioActor = (TextView)findViewById(R.id.tvBioActor);
        btnFilmografia = (Button)findViewById(R.id.btnVerFilmog);

        codigo = getIntent().getStringExtra(MainActivity.CODIGO_ACTOR);
        //Toast.makeText(this, codigo, Toast.LENGTH_LONG).show();

        request = raiz + codigo + "?" + apiKey;
        //Toast.makeText(this, request, Toast.LENGTH_SHORT).show();

        // empieza la actividad con el boton invisible
        btnFilmografia.setVisibility(View.INVISIBLE);
        buscarInfo();
    }

    private void buscarInfo() {
        if(hayInternetDisponible()){
            cliente = new OkHttpClient();

            // crea la request y configura la comunicacion
            Request req = new Request.Builder().url(request).build();

            Call call = cliente.newCall(req);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bioActor.setText("No funciona");
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    // ejecuta la llamada y guarda el resultado
                    try{
                        final String jsonData = response.body().string();
                        //Log.d(TAG, jsonData);
                        if(response.isSuccessful()){
                            // si lo que recibe tiene status success
                            JSONObject jRaiz = new JSONObject(jsonData);
                            if(jRaiz.getString("status").equals("success")){
                                actor = parseActorData(jsonData);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // modificacion de UI por eso va dentro de un runThreadUI
                                        btnFilmografia.setVisibility(View.VISIBLE);
                                        mostrarDatos();
                                    }
                                });
                             // si lo que recibe no tiene estatus success
                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        nombreActor.setText(getString(R.string.tv_actor_noInfo));
                                    }
                                });
                            }
                        }
                    }catch (IOException e){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InfoActor.this, "Algo ha ido mal...", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }catch (JSONException j){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btnFilmografia.setVisibility(View.INVISIBLE);
                                Toast.makeText(InfoActor.this, "Algo ha ido mal cargando los datos del actor...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }else{
            Toast.makeText(this, "No tienes conexion a Internet!", Toast.LENGTH_SHORT).show();
        }
    }

    private Actor parseActorData(String data) throws JSONException{
        Actor actor = new Actor();

        // raiz
        JSONObject jRaiz = new JSONObject(data);
        JSONObject jData = jRaiz.getJSONObject("data");

        JSONArray filmografia = jData.getJSONArray("filmography");

        JSONObject pelisJson;
        ArrayList<Pelicula> pels = new ArrayList<>();
        Pelicula pel;
        for(int i = 0; i < filmografia.length(); i++){
            pelisJson = filmografia.getJSONObject(i);
            pel = new Pelicula();
            pel.setTitulo(pelisJson.getString("title"));
            pel.setYear(pelisJson.getString("year"));

            pels.add(pel);
        }

        String nombreActor = jData.getString("title");
        String bioActor = jData.getString("description");

        actor.setNombre(nombreActor);
        actor.setBio(bioActor);
        actor.setPeliculas(pels);

        return actor;
    }

    private void mostrarDatos() {
        nombreActor.setText(actor.getNombre());
        bioActor.setText(actor.getBio());

    }

    private boolean hayInternetDisponible() {
        boolean disponible = false;
        ConnectivityManager conManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conManager.getActiveNetworkInfo();

        // si es diferente de null y esta conectado --> hay internet
        if(netInfo != null && netInfo.isConnected()){
            disponible = true;
        }

        return disponible;
    }

    public void verFilmografia(View v){
        Intent i = new Intent(InfoActor.this, Filmografia.class);
        i.putExtra(LISTA_PELS, actor.getPeliculas());
        startActivity(i);
    }


}
