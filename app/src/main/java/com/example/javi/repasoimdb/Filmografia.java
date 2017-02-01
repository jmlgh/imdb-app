package com.example.javi.repasoimdb;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Filmografia extends AppCompatActivity {

    ListView lView;
    ArrayList<Pelicula> peliculas;
    PeliculaAdapter peliculaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmografia);

        lView = (ListView)findViewById(R.id.listView_Peliculas);

        Intent i = getIntent();
        // esto ya hace la transformacion interna
        peliculas = i.getParcelableArrayListExtra(InfoActor.LISTA_PELS);

        // crea un nuevo adapater pasandole este contexto y la lista de peliculas
        peliculaAdapter = new PeliculaAdapter(this, peliculas);

        // setea el adapter
        lView.setAdapter(peliculaAdapter);
    }
}
