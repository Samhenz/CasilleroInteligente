package com.css.casillerointeligentecss;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.serialport.SerialPortFinder;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
//import android.widget.Button;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.css.casillerointeligentecss.R;
import com.css.casillerointeligentecss.comn.Device;
import com.css.casillerointeligentecss.comn.SerialPortManager;
import com.css.casillerointeligentecss.util.ToastUtil;
import com.css.casillerointeligentecss.util.PrefHelper;
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

    private Device mDevice;

    private String espDevicePath = "/dev/ttyS3"; //En este caso, fisicamente tengo conectado un disp al COM3
    private String espBaudrate = "19200";   //opera a 19200 baudios

    private boolean mOpened = false;

    private int btnSelected = 0;
    private String cmdHEX;

    Button[] btn;
    Button btnFinalizar;


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
        /*
         * Boton de Finalizar
         */
        btnFinalizar = findViewById(R.id.btnOK_cargarInventario);
        btnFinalizar.setOnClickListener(this);

        initDevice();         // Inicializamos Disp serial
        switchSerialPort(); // Aperturamos Puerto Serial COM3
        //updateViewState(true); *Solo Debug. Habilitar botones sin Serial COM.
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
        //SerialPort manager nos demanda un objeto Device
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
                    btn[0].setBackgroundResource(R.color.red_700);
                } else {
                    btn[0].setBackgroundResource(R.color.red_700);
                    Toast.makeText(this, getString(cmd_1),Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                //cmdHEX = "AAEB22020001BA55";
                if (mOpened){
                    SerialPortManager.instance().sendHEXData(getString(cmd_2));
                    btn[1].setBackgroundResource(R.color.red_700);
                } else {
                    btn[1].setBackgroundResource(R.color.red_700);
                    Toast.makeText(this, getString(cmd_3),Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                //cmdHEX = "AAEB22020002BB55";
                if (mOpened){
                    SerialPortManager.instance().sendHEXData(getString(cmd_3));
                    btn[2].setBackgroundResource(R.color.red_700);
                } else {
                    btn[2].setBackgroundResource(R.color.red_700);
                    Toast.makeText(this, getString(cmd_3),Toast.LENGTH_SHORT).show();
                }
                break;
            case 4:
                //cmdHEX = "AAEB22020003BC55";
                if (mOpened){
                    SerialPortManager.instance().sendHEXData(getString(cmd_4));
                    btn[3].setBackgroundResource(R.color.red_700);
                } else {
                    btn[3].setBackgroundResource(R.color.red_700);
                    Toast.makeText(this, getString(cmd_5),Toast.LENGTH_SHORT).show();
                }
                break;
            case 5:
                //cmdHEX = "AAEB22020004BD55";
                if (mOpened){
                    SerialPortManager.instance().sendHEXData(getString(cmd_5));
                    btn[4].setBackgroundResource(R.color.red_700);
                } else {
                    btn[4].setBackgroundResource(R.color.red_700);
                    Toast.makeText(this, getString(cmd_5),Toast.LENGTH_SHORT).show();
                }
                break;
            case 6:
                //cmdHEX = "AAEB22020005BE55";
                if (mOpened){
                    SerialPortManager.instance().sendHEXData(getString(cmd_6));
                    btn[5].setBackgroundResource(R.color.red_700);
                } else {
                    btn[5].setBackgroundResource(R.color.red_700);
                    Toast.makeText(this, getString(cmd_6),Toast.LENGTH_SHORT).show();
                }
                break;
            case 7:
                //cmdHEX = "AAEB22020006BF55";
                if (mOpened){
                    SerialPortManager.instance().sendHEXData(getString(cmd_7));
                    btn[6].setBackgroundResource(R.color.red_700);
                } else {
                    btn[6].setBackgroundResource(R.color.red_700);
                    Toast.makeText(this, getString(cmd_7),Toast.LENGTH_SHORT).show();
                }
                break;
            case 8:
                //cmdHEX = "AAEB22020007C055";
                if (mOpened){
                    SerialPortManager.instance().sendHEXData(getString(cmd_8));
                    btn[7].setBackgroundResource(R.color.red_700);
                } else {
                    btn[7].setBackgroundResource(R.color.red_700);
                    Toast.makeText(this, getString(cmd_8),Toast.LENGTH_SHORT).show();
                }
                break;
            case 9:
                //cmdHEX = "AAEB22020008C155";
                if (mOpened){
                    SerialPortManager.instance().sendHEXData(getString(cmd_9));
                    btn[8].setBackgroundResource(R.color.red_700);
                } else {
                    btn[8].setBackgroundResource(R.color.red_700);
                    Toast.makeText(this, getString(cmd_9),Toast.LENGTH_SHORT).show();
                }
                break;
            case 10:
                //cmdHEX = "AAEB22020009C255";
                if (mOpened){
                    SerialPortManager.instance().sendHEXData(getString(cmd_10));
                    btn[9].setBackgroundResource(R.color.red_700);
                } else {
                    btn[9].setBackgroundResource(R.color.red_700);
                    Toast.makeText(this, getString(cmd_10),Toast.LENGTH_SHORT).show();
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
        updateViewState(mOpened);
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
     * Evento de click en botones
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                try {
                    sendData(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, "Caja 1 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                try {
                    sendData(2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, "Caja 2 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button3:
                try {
                    sendData(3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, "Caja 3 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button4:
                try {
                    sendData(4);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, "Caja 4 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button5:
                try {
                    sendData(5);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, "Caja 5 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button6:
                try {
                    sendData(6);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, "Caja 6 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button7:
                try {
                    sendData(7);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, "Caja 7 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button8:
                try {
                    sendData(8);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, "Caja 8 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button9:
                try {
                    sendData(9);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, "Caja 9 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button10:
                try {
                    sendData(10);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, "Caja 10 abierta",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnOK_cargarInventario:
                //Toast.makeText(this, "Boton FINALIZAR presionadp",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BotonesCargarInventario.this, MainActivity.class));
                break;
        }
    }
}