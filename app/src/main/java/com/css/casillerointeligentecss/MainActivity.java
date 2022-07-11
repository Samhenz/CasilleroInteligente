package com.css.casillerointeligentecss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //declaración de objetos
    Button btnCargarInventario;
    Button btnRecogerPaquete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCargarInventario=findViewById(R.id.btn_cargar_inventario);
        btnRecogerPaquete=findViewById(R.id.btn_recoger_paquete);

        /**
         * Click sobre el boton Cargar Inventario
         */
        btnCargarInventario.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,LoginCargarInvent.class);
            startActivity(intent);
        });


    }
}