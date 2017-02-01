package com.example.javi.repasoimdb;


import android.os.Parcel;
import android.os.Parcelable;

public class Pelicula implements Parcelable {
    private String year;
    private String titulo;

    // constructores
    public Pelicula(){}
    // lee (constructor para descomponer el parcel)
    public Pelicula(Parcel p){
        titulo = p.readString();
        year = p.readString();
    }
    public Pelicula(String year, String titulo) {
        this.year = year;
        this.titulo = titulo;
    }

    // en creator no se cambia nada
    public static final Creator<Pelicula> CREATOR = new Creator<Pelicula>() {
        @Override
        public Pelicula createFromParcel(Parcel in) {
            return new Pelicula(in);
        }

        @Override
        public Pelicula[] newArray(int size) {
            return new Pelicula[size];
        }
    };

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // escribe - tienen que coincidir con la lectura
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titulo);
        dest.writeString(year);
    }
}
