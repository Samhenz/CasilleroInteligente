package com.css.casillerointeligentecss;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
//import android.widget.Button;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.css.casillerointeligentecss.comn.Device;
import com.css.casillerointeligentecss.comn.SerialPortManager;
import com.css.casillerointeligentecss.util.ToastUtil;
import static com.css.casillerointeligentecss.R.string.cmd_1;
import static com.css.casillerointeligentecss.R.string.cmd_2;
import static com.css.casillerointeligentecss.R.string.cmd_3;
import static com.css.casillerointeligentecss.R.string.cmd_4;
import static com.css.casillerointeligentecss.R.string.cmd_5;
import static com.css.casillerointeligentecss.R.string.cmd_6;
import static com.css.casillerointeligentecss.R.string.cmd_7;
import static com.css.casillerointeligentecss.R.string.cmd_8;
import static com.css.casillerointeligentecss.R.string.cmd_9;
import static com.css.casillerointeligentecss.R.string.cmd_10;

import java.io.IOException;


public class BotonesCargarInventario extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;

    /*
    public static final String casillero1_ocupado = "casillero1ocupado";
    public static final String casillero2_ocupado = "casillero2ocupado";
    public static final String casillero3_ocupado = "casillero3ocupado";
    public static final String casillero4_ocupado = "casillero4ocupado";
    public static final String casillero5_ocupado = "casillero5ocupado";
    public static final String casillero6_ocupado = "casillero6ocupado";
    public static final String casillero7_ocupado = "casillero7ocupado";
    public static final String casillero8_ocupado = "casillero8ocupado";
    public static final String casillero9_ocupado = "casillero9ocupado";
    public static final String casillero10_ocupado = "casillero10ocupado";
     */

    //String mypreference = "mypref";

    Boolean locker1Status= false;
    Boolean locker2Status= false;
    Boolean locker3Status= false;
    Boolean locker4Status= false;
    Boolean locker5Status= false;
    Boolean locker6Status= false;
    Boolean locker7Status= false;
    Boolean locker8Status= false;
    Boolean locker9Status= false;
    Boolean locker10Status= false;

    Boolean boton1presionado = false;
    Boolean boton2presionado = false;
    Boolean boton3presionado = false;
    Boolean boton4presionado = false;
    Boolean boton5presionado = false;
    Boolean boton6presionado = false;
    Boolean boton7presionado = false;
    Boolean boton8presionado = false;
    Boolean boton9presionado = false;
    Boolean boton10presionado = false;

    private Device mDevice;

    private String espDevicePath = "/dev/ttyS3"; //En este caso, fisicamente tengo conectado un disp al COM3
    private String espBaudrate = "19200";   //opera a 19200 baudios

    private boolean mOpened = false;

    private int btnSelected = 0;
    private String cmdHEX;

    Button[] btn; // Botones del casillero
    Button btnFinalizar; // Boton finalizar/enviar clave/validar casilleros cerrados
    TextView tvDebug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botones_cargar_inventario);
        /*
         * Botones para el casillero
         */
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

        //tvDebug = findViewById(R.id.textView3);

        /*
         * Shared preferences para Almacenar información:
         * Botón presionado = casillero correspodiente ocupado
         * Se valida la operación con boton Finalizar/Enviar clave
         */
        sharedPreferences = getSharedPreferences(getString(R.string.mypreference),Context.MODE_PRIVATE);
        // Estado inicial lockers: desocupado
        // ocupado=true, desocupado=false
        // En la sintaxis de abajo, se cambió la lógica a "True" debido a incongruencias
        // en el estado inicial
        locker1Status = sharedPreferences.getBoolean(getString(R.string.casillero1_ocupado),true);
        locker2Status = sharedPreferences.getBoolean(getString(R.string.casillero2_ocupado),true);
        locker3Status = sharedPreferences.getBoolean(getString(R.string.casillero3_ocupado),true);
        locker4Status = sharedPreferences.getBoolean(getString(R.string.casillero4_ocupado),true);
        locker5Status = sharedPreferences.getBoolean(getString(R.string.casillero5_ocupado),true);
        locker6Status = sharedPreferences.getBoolean(getString(R.string.casillero6_ocupado),true);
        locker7Status = sharedPreferences.getBoolean(getString(R.string.casillero7_ocupado),true);
        locker8Status = sharedPreferences.getBoolean(getString(R.string.casillero8_ocupado),true);
        locker9Status = sharedPreferences.getBoolean(getString(R.string.casillero9_ocupado),true);
        locker10Status = sharedPreferences.getBoolean(getString(R.string.casillero10_ocupado),true);
        //tvDebug.setText(String.valueOf(locker2Status));

        /*
         * Boton de Finalizar/Validar/Enviar clave
         */
        btnFinalizar = findViewById(R.id.btnOK_cargarInventario);
        btnFinalizar.setOnClickListener(this);

        initDevice();                         // Inicializamos Disp serial
        switchSerialPort();                 // Aperturamos Puerto Serial COM3. * Comentar para Debug.
        //updateViewState(true); //*Solo Debug. Habilitar botones sin Serial COM.
    }

    @Override
    protected void onResume() {
        super.onResume();

        // *** Casillero 1 *********************************
        if (locker1Status){
            btn[0].setBackgroundResource(R.color.red_700);
            btn[0].setEnabled(!locker1Status);
        } else {
            btn[0].setBackgroundResource(R.color.green_700);
        }
        // *** Casillero 2 *********************************
        if (locker2Status){
            btn[1].setBackgroundResource(R.color.red_700);
            btn[1].setEnabled(!locker2Status);
        } else {
            btn[1].setBackgroundResource(R.color.green_700);
        }
        // *** Casillero 3 *********************************
        if (locker3Status){
            btn[2].setBackgroundResource(R.color.red_700);
            btn[2].setEnabled(!locker3Status);
        } else {
            btn[2].setBackgroundResource(R.color.green_700);
        }
        // *** Casillero 4 *********************************
        if (locker4Status){
            btn[3].setBackgroundResource(R.color.red_700);
            btn[3].setEnabled(!locker4Status);
        } else {
            btn[3].setBackgroundResource(R.color.green_700);
        }
        // *** Casillero 5 *********************************
        if (locker5Status){
            btn[4].setBackgroundResource(R.color.red_700);
            btn[4].setEnabled(!locker5Status);
        } else {
            btn[4].setBackgroundResource(R.color.green_700);
        }
        // *** Casillero 6 *********************************
        if (locker6Status){
            btn[5].setBackgroundResource(R.color.red_700);
            btn[5].setEnabled(!locker6Status);
        } else {
            btn[5].setBackgroundResource(R.color.green_700);
        }
        // *** Casillero 7 *********************************
        if (locker7Status){
            btn[6].setBackgroundResource(R.color.red_700);
            btn[6].setEnabled(!locker7Status);
        } else {
            btn[6].setBackgroundResource(R.color.green_700);
        }
        // *** Casillero 8 *********************************
        if (locker8Status){
            btn[7].setBackgroundResource(R.color.red_700);
            btn[7].setEnabled(!locker8Status);
        } else {
            btn[7].setBackgroundResource(R.color.green_700);
        }
        // *** Casillero 9 *********************************
        if (locker9Status){
            btn[8].setBackgroundResource(R.color.red_700);
            btn[8].setEnabled(!locker9Status);
        } else {
            btn[8].setBackgroundResource(R.color.green_700);
        }
        // *** Casillero 10 *********************************
        if (locker10Status){
            btn[9].setBackgroundResource(R.color.red_700);
            btn[9].setEnabled(!locker10Status);
        } else {
            btn[9].setBackgroundResource(R.color.green_700);
        }

    }

    @Override
    protected void onDestroy() {
        SerialPortManager.instance().close();
        super.onDestroy();
    }

    /**
     * Inicializar dispositivo Serial
     */
    private void initDevice() {
        // SerialPort manager nos demanda un objeto Device
        mDevice = new Device(espDevicePath, espBaudrate);
    }

    /**
     * Envio de comandos HEX despues de hacer Click en botón
     */
    private void sendData(int btnNum) throws IOException {
        switch (btnNum){
            case 1:
                //cmdHEX = "AAEB22020000B955";
                if (mOpened){
                    SerialPortManager.instance().sendHEXData(getString(cmd_1));
                    //btn[0].setBackgroundResource(R.color.red_700);
                } else {
                    //Debug only
                    //btn[0].setBackgroundResource(R.color.red_700);
                    //Toast.makeText(this, getString(cmd_1),Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                //cmdHEX = "AAEB22020001BA55";
                if (mOpened){
                    SerialPortManager.instance().sendHEXData(getString(cmd_2));
                    //btn[1].setBackgroundResource(R.color.red_700);
                } else {
                    //btn[1].setBackgroundResource(R.color.red_700);
                    //Toast.makeText(this, getString(cmd_3),Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                //cmdHEX = "AAEB22020002BB55";
                if (mOpened){
                    SerialPortManager.instance().sendHEXData(getString(cmd_3));
                    //btn[2].setBackgroundResource(R.color.red_700);
                } else {
                    //btn[2].setBackgroundResource(R.color.red_700);
                    //Toast.makeText(this, getString(cmd_3),Toast.LENGTH_SHORT).show();
                }
                break;
            case 4:
                //cmdHEX = "AAEB22020003BC55";
                if (mOpened){
                    SerialPortManager.instance().sendHEXData(getString(cmd_4));
                    //btn[3].setBackgroundResource(R.color.red_700);
                } else {
                    //btn[3].setBackgroundResource(R.color.red_700);
                    //Toast.makeText(this, getString(cmd_5),Toast.LENGTH_SHORT).show();
                }
                break;
            case 5:
                //cmdHEX = "AAEB22020004BD55";
                if (mOpened){
                    SerialPortManager.instance().sendHEXData(getString(cmd_5));
                    //btn[4].setBackgroundResource(R.color.red_700);
                } else {
                    //btn[4].setBackgroundResource(R.color.red_700);
                    //Toast.makeText(this, getString(cmd_5),Toast.LENGTH_SHORT).show();
                }
                break;
            case 6:
                //cmdHEX = "AAEB22020005BE55";
                if (mOpened){
                    SerialPortManager.instance().sendHEXData(getString(cmd_6));
                    //btn[5].setBackgroundResource(R.color.red_700);
                } else {
                    // Debug only
                    //btn[5].setBackgroundResource(R.color.red_700);
                    //Toast.makeText(this, getString(cmd_6),Toast.LENGTH_SHORT).show();
                }
                break;
            case 7:
                //cmdHEX = "AAEB22020006BF55";
                if (mOpened){
                    SerialPortManager.instance().sendHEXData(getString(cmd_7));
                    //btn[6].setBackgroundResource(R.color.red_700);
                } else {
                    //btn[6].setBackgroundResource(R.color.red_700);
                    //Toast.makeText(this, getString(cmd_7),Toast.LENGTH_SHORT).show();
                }
                break;
            case 8:
                //cmdHEX = "AAEB22020007C055";
                if (mOpened){
                    SerialPortManager.instance().sendHEXData(getString(cmd_8));
                    //btn[7].setBackgroundResource(R.color.red_700);
                } else {
                    //btn[7].setBackgroundResource(R.color.red_700);
                    //Toast.makeText(this, getString(cmd_8),Toast.LENGTH_SHORT).show();
                }
                break;
            case 9:
                //cmdHEX = "AAEB22020008C155";
                if (mOpened){
                    SerialPortManager.instance().sendHEXData(getString(cmd_9));
                    //btn[8].setBackgroundResource(R.color.red_700);
                } else {
                    //btn[8].setBackgroundResource(R.color.red_700);
                    //Toast.makeText(this, getString(cmd_9),Toast.LENGTH_SHORT).show();
                }
                break;
            case 10:
                //cmdHEX = "AAEB22020009C255";
                if (mOpened){
                    SerialPortManager.instance().sendHEXData(getString(cmd_10));
                    //btn[9].setBackgroundResource(R.color.red_700);
                } else {
                    //btn[9].setBackgroundResource(R.color.red_700);
                    //Toast.makeText(this, getString(cmd_10),Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * Abrir o cerrar el serial port
     *
     */
    private void switchSerialPort() {
        if (mOpened) {
            SerialPortManager.instance().close();
            mOpened = false;
        } else {
            // Guardar la config
            //PrefHelper.getDefault().saveInt(PreferenceKeys.SERIAL_PORT_DEVICES, mDeviceIndex);
            //PrefHelper.getDefault().saveInt(PreferenceKeys.BAUD_RATE, mBaudrateIndex);
            mOpened = SerialPortManager.instance().open(mDevice) != null;   //Abrir
            if (mOpened) {
                ToastUtil.showOne(this, "Puerto Serial Abierto");   //Notificar apertura de serial port
            } else {
                ToastUtil.showOne(this, "Puerto Serial No Abierto");//Notificar fallo de apertura
            }
        }
        //updateViewState(mOpened);
    }

    /**
     * State view depending of "Is Serial port opened"
     * Habilitamos o deshabilitamos los botones
     *
     * @param isSerialPortOpened
     */
    private void updateViewState(boolean isSerialPortOpened) {
        btn[0].setEnabled(isSerialPortOpened);
        btn[1].setEnabled(isSerialPortOpened);
        btn[2].setEnabled(isSerialPortOpened);
        btn[3].setEnabled(isSerialPortOpened);
        btn[4].setEnabled(isSerialPortOpened);
        btn[5].setEnabled(isSerialPortOpened);
        btn[6].setEnabled(isSerialPortOpened);
        btn[7].setEnabled(isSerialPortOpened);
        btn[8].setEnabled(isSerialPortOpened);
        btn[9].setEnabled(isSerialPortOpened);
    }
    /*
     * Evento de CLICK en botones
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                try {
                    sendData(1);
                    boton1presionado = true; //El boton ha sido presionado
                    btn[0].setBackgroundResource(R.color.red_700); //Marcar en rojo
                    SharedPreferences.Editor editor = sharedPreferences.edit();//Preparamos el Editor
                    editor.putBoolean(getString(R.string.casillero1_ocupado),true);// Cambiamos el valor del casillero
                    editor.apply(); //Aplicamos cambios
                    // sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                    // btn[0].setEnabled(!locker1Status);
                    //tvDebug.setText(String.valueOf(locker2Status));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, "Caja 1 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                try {
                    sendData(2);
                    boton2presionado = true;
                    btn[1].setBackgroundResource(R.color.red_700);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(getString(R.string.casillero2_ocupado),true);
                    editor.apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, "Caja 2 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button3:
                try {
                    sendData(3);
                    boton3presionado = true;
                    btn[2].setBackgroundResource(R.color.red_700);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(getString(R.string.casillero3_ocupado),true);
                    editor.apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, "Caja 3 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button4:
                try {
                    sendData(4);
                    boton4presionado = true;
                    btn[3].setBackgroundResource(R.color.red_700);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(getString(R.string.casillero4_ocupado),true);
                    editor.apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, "Caja 4 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button5:
                try {
                    sendData(5);
                    boton5presionado = true;
                    btn[4].setBackgroundResource(R.color.red_700);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(getString(R.string.casillero5_ocupado),true);
                    editor.apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, "Caja 5 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button6:
                try {
                    sendData(6);
                    boton6presionado = true;
                    btn[5].setBackgroundResource(R.color.red_700);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(getString(R.string.casillero6_ocupado),true);
                    editor.apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, "Caja 6 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button7:
                try {
                    sendData(7);
                    boton7presionado = true;
                    btn[6].setBackgroundResource(R.color.red_700);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(getString(R.string.casillero7_ocupado),true);
                    editor.apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, "Caja 7 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button8:
                try {
                    sendData(8);
                    boton8presionado = true;
                    btn[7].setBackgroundResource(R.color.red_700);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(getString(R.string.casillero8_ocupado),true);
                    editor.apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, "Caja 8 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button9:
                try {
                    sendData(9);
                    boton9presionado = true;
                    btn[8].setBackgroundResource(R.color.red_700);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(getString(R.string.casillero9_ocupado),true);
                    editor.apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, "Caja 9 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button10:
                try {
                    sendData(10);
                    boton10presionado = true;
                    btn[9].setBackgroundResource(R.color.red_700);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(getString(R.string.casillero10_ocupado),true);
                    editor.apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, "Caja 10 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnOK_cargarInventario:
                //Toast.makeText(this, "Boton FINALIZAR presionadp",Toast.LENGTH_SHORT).show();
                // sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                // btn[0].setEnabled(!locker1Status);
                // tvDebug.setText(String.valueOf(locker2Status));

                startActivity(new Intent(BotonesCargarInventario.this, MainActivity.class));
                break;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();


    }
}