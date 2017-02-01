package com.example.javi.repasoimdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PeliculaAdapter extends BaseAdapter {
    ArrayList<Pelicula> peliculas;
    Context context;

    public PeliculaAdapter(Context contex, ArrayList<Pelicula> peliculas) {
        this.peliculas = peliculas;
        this.context = contex;
    }

    @Override
    public int getCount() {
        return peliculas.size();
    }

    @Override
    public Object getItem(int position) {
        return peliculas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // configura el view holder dependiendo de es null o no (si se inicia por primera vez o no)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder;

        if(convertView == null){
            vHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_filmografia, null);
            vHolder.tvYear = (TextView)convertView.findViewById(R.id.tvYear);
            vHolder.tvTitulo = (TextView)convertView.findViewById(R.id.tvTitulo);
            convertView.setTag(vHolder);

        } else {
            vHolder = (ViewHolder)convertView.getTag();
        }

        // muestra, setea los elementos visuales
        vHolder.tvTitulo.setText(peliculas.get(position).getTitulo());
        vHolder.tvYear.setText(peliculas.get(position).getYear());


        return convertView;
    }

    // elementos visuales
    public class ViewHolder{
        TextView tvYear, tvTitulo;

    }
}
