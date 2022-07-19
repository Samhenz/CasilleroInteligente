package com.css.casillerointeligentecss;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.os.StrictMode;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import android.provider.Settings.Secure;

public class LoginCargarInvent extends AppCompatActivity {

    EditText etNoTelefono, etNoOrden, etNombreCourier, etCorreoReceptor;
    Button btnIniSesion;
    Session session;

    String numTelefono,numOrden,nCourier, emailMsg, mailReceptor, serieID, imeiDev;

    /**
     * Credenciales del Emisor
     */
    //String username="";  //Correo personal de Samuel Hernandez
    //String password="mjfddbypfmutpibl";         //Contraseña generada por google 2-step verification
    String username = "casillerointeligentegt@gmail.com";  //Correo de pruebas
    String password = "nqnyblldvpjhhjih";                  //Contraseña generada por google 2-step verification

    /**
     * Cuenta Receptor del email
     */
    // El receptor del email
    //Receptor principal
    String mailReceptorCC = "hernandezgsamuel@outlook.com";   //Carbon copy opcional


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_cargar_invent);

        String androidSN = Build.SERIAL; // No de Serie del dispositivo

        /**
         * Campos a registrar del Courier
         */
        etNoTelefono = findViewById(R.id.editText_NoTelefono);
        etNoOrden = findViewById(R.id.editText_Orden);
        etNombreCourier = findViewById(R.id.editText_Courier);
        etCorreoReceptor = findViewById(R.id.editText_Correo); //Receptor principal

        /**
         * Boton para confirmar inicio de Sesión
         */
        btnIniSesion = findViewById(R.id.btn_iniciarSesion);

        btnIniSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Datos a enviar via email como notificacion Inicio de Sesion
                 */
                numTelefono = "NO. DE TELÉFONO: "+etNoTelefono.getText().toString();
                numOrden =  " || NO. DE ORDEN: "+etNoOrden.getText().toString();
                nCourier = " || COURIER: "+etNombreCourier.getText().toString();
                mailReceptor = etCorreoReceptor.getText().toString(); // Receptor principal
                serieID = "|| NO. DE SERIE: "+androidSN;
                // ++++ Subject para el email ++++
                String emailSubject = "PRUEBA - carga de inventario smartlocker #"+androidSN;
                emailMsg = numTelefono+System.lineSeparator()+numOrden+System.lineSeparator()+nCourier;

                //========================================================================================
                // SEND EMAIL
                //========================================================================================
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Properties props=new Properties();
                props.put("mail.smtp.auth","true");
                props.put("mail.smtp.host","smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port","465");
                props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.port","465");

                // Deshabilitar el envío de e-mail para propósitos de Debug

                try {
                    session=Session.getDefaultInstance(props, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username,password);
                        }
                    });

                    if (session!=null){
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(username));
                        message.setSubject(emailSubject);
                        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(mailReceptor));
                        message.addRecipients(Message.RecipientType.CC,InternetAddress.parse(mailReceptorCC));
                        message.setContent(emailMsg,"text/html; charset=utf-8");
                        Transport.send(message); // ENVIAR DATOS POR EMAIL
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                /**
                 * Luego de enviar la notificacion por email se redirige a la interfaz de los botones
                 */
                Intent intent=new Intent(LoginCargarInvent.this, BotonesCargarInventario.class);
                startActivity(intent);

            }
        });



    }
}