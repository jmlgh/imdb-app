package com.example.javi.repasoimdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String CODIGO_ACTOR = "cod_actor";
    private EditText etCodigo;
    private Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCodigo = (EditText)findViewById(R.id.etCodigo);
        btnBuscar = (Button)findViewById(R.id.btnBuscar);


    }

    public void buscar(View v){
        String codigo = etCodigo.getText().toString();
        if(codigo.equals("")){
            Toast.makeText(this, "Introduce el codigo del actor para empezar", Toast.LENGTH_SHORT).show();
        }else{
            //Toast.makeText(this, codigo, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, InfoActor.class);
            i.putExtra(CODIGO_ACTOR, codigo);
            startActivity(i);
        }

    }
}
