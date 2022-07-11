package com.css.casillerointeligentecss;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
//import android.widget.Button;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class BotonesCargarInventario extends AppCompatActivity implements View.OnClickListener {

    Button[] btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botones_cargar_inventario);
        btn = new Button[10];

        btn[0] = findViewById(R.id.button);
        btn[1] = findViewById(R.id.button2);
        btn[2] = findViewById(R.id.button3);
        btn[3] = findViewById(R.id.button4);
        btn[4] = findViewById(R.id.button5);
        btn[5] = findViewById(R.id.button6);
        btn[6] = findViewById(R.id.button7);
        btn[7] = findViewById(R.id.button8);
        btn[8] = findViewById(R.id.button9);
        btn[9] = findViewById(R.id.button10);

        for (int i=0; i < 10; i++){
            btn[i].setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:


                Toast.makeText(this, "Caja 1 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                Toast.makeText(this, "Caja 2 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button3:
                Toast.makeText(this, "Caja 3 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button4:
                Toast.makeText(this, "Caja 4 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button5:
                Toast.makeText(this, "Caja 5 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button6:
                Toast.makeText(this, "Caja 6 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button7:
                Toast.makeText(this, "Caja 7 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button8:
                Toast.makeText(this, "Caja 8 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button9:
                Toast.makeText(this, "Caja 9 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button10:
                Toast.makeText(this, "Caja 10 abierta",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}