package com.css.casillerointeligentecss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginRecogerProducto extends AppCompatActivity {

    EditText etNombreRecolector, etClave;
    Button btnValidar;
    String nombreRecolector, clave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_recoger_producto);

        /**
         * Campos a registrar de quien recoge el paquete
         */


        /**
         * Boton para validar Clave de acceso
         */
        btnValidar = findViewById(R.id.btn_validarRecolector);
        btnValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Luego de enviar la notificacion por email se redirige a la interfaz de los botones
                 */
                Intent intent=new Intent(LoginRecogerProducto.this, BotonesRecogerProducto.class);
                startActivity(intent);
            }
        });

    }
}