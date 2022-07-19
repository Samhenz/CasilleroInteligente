package com.css.casillerointeligentecss;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.serialport.SerialPort;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.Scheduler;
import org.greenrobot.eventbus.EventBus;


public class MainActivity extends AppCompatActivity {
    //declaración de objetos
    Button btnCargarInventario;
    Button btnRecogerPaquete;
    Button btnSettings;

    @Override
    protected void onDestroy() {
        finish();

        super.onDestroy();
        //System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCargarInventario=findViewById(R.id.btn_cargar_inventario);
        btnRecogerPaquete=findViewById(R.id.btn_recoger_paquete);
        btnSettings=findViewById(R.id.btn_Settings);

        /**
         * Click sobre el boton Cargar Inventario
         */
        btnCargarInventario.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,LoginCargarInvent.class);
            startActivity(intent);
        });
        /**
         * Click sobre el boton Recoger paquete
         */
        btnRecogerPaquete.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,LoginRecogerProducto.class);
            startActivity(intent);
        });

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,SerialTool.class);
            startActivity(intent);
        });


    }


}