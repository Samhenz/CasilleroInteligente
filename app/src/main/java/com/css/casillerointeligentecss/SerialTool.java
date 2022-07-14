package com.css.casillerointeligentecss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.serialport.SerialPortFinder;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.OnClick;
//import androidx.appcompat.app.ActionBar;
//import android.os.Bundle;

import com.css.casillerointeligentecss.R;
import com.css.casillerointeligentecss.base.BaseActivity;
import com.css.casillerointeligentecss.comn.Device;
import com.css.casillerointeligentecss.comn.SerialPortManager;
import com.css.casillerointeligentecss.util.AllCapTransformationMethod;
import com.css.casillerointeligentecss.util.PrefHelper;
import com.css.casillerointeligentecss.util.ToastUtil;
import com.css.casillerointeligentecss.util.constant.PreferenceKeys;

import static com.css.casillerointeligentecss.R.array.baudrates;


public class SerialTool extends BaseActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.spinner_devices)
    Spinner mSpinnerDevices;
    @BindView(R.id.spinner_baudrate)
    Spinner mSpinnerBaudrate;
    @BindView(R.id.btn_open_device)
    Button mBtnOpenDevice;
    @BindView(R.id.btn_send_data)
    Button mBtnSendData;
    @BindView(R.id.btn_load_list)
    Button mBtnLoadList;
    @BindView(R.id.et_data)
    EditText mEtData;
    @BindView(R.id.button)
    Button mBtn1;
    @BindView(R.id.button2)
    Button mBtn2;
    @BindView(R.id.button3)
    Button mBtn3;
    @BindView(R.id.button4)
    Button mBtn4;
    @BindView(R.id.button5)
    Button mBtn5;
    @BindView(R.id.button6)
    Button mBtn6;
    @BindView(R.id.button7)
    Button mBtn7;
    @BindView(R.id.button8)
    Button mBtn8;
    @BindView(R.id.button9)
    Button mBtn9;
    @BindView(R.id.button10)
    Button mBtn10;

    private Device mDevice;

    private int mDeviceIndex;
    private int mBaudrateIndex;

    private String[] mDevices;
    private String[] mBaudrates;

    private boolean mOpened = false;
    private String cmdHEX;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEtData.setTransformationMethod(new AllCapTransformationMethod(true));
        //setContentView(R.layout.activity_serial_tool);
        //getSupportActionBar().hide();
        initDevice();
        initSpinners();
        updateViewState(mOpened);

    }

    /**
     * No se hará nada, con el propósito de mantener la config de Puerto Serial Abierto
    @Override
    protected void onDestroy() {
        //SerialPortManager.instance().close();
        super.onDestroy();
    }
     */

    @Override
    protected boolean hasActionBar() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_serial_tool;
    }
    /**
     * Inicializar lista de dispositivos
     */
    private void initDevice() {

        SerialPortFinder serialPortFinder = new SerialPortFinder();

        // Device
        mDevices = serialPortFinder.getAllDevicesPath();
        if (mDevices.length == 0) {
            mDevices = new String[] {
                    getString(R.string.no_serial_device)
            };
        }
        // Baudrates
        mBaudrates = getResources().getStringArray(baudrates);
        mDeviceIndex = PrefHelper.getDefault().getInt(PreferenceKeys.SERIAL_PORT_DEVICES, 0);
        mDeviceIndex = mDeviceIndex >= mDevices.length ? mDevices.length - 1 : mDeviceIndex;
        mBaudrateIndex = PrefHelper.getDefault().getInt(PreferenceKeys.BAUD_RATE, 0);

        mDevice = new Device(mDevices[mDeviceIndex], mBaudrates[mBaudrateIndex]);

    }

    /**
     * Inicializa opciones despegables
     */
    private void initSpinners() {

        ArrayAdapter<String> deviceAdapter =
                new ArrayAdapter<String>(this, R.layout.spinner_default_item, mDevices);
        deviceAdapter.setDropDownViewResource(R.layout.spinner_item);
        mSpinnerDevices.setAdapter(deviceAdapter);
        mSpinnerDevices.setOnItemSelectedListener(this);

        ArrayAdapter<String> baudrateAdapter =
                new ArrayAdapter<String>(this, R.layout.spinner_default_item, mBaudrates);
        baudrateAdapter.setDropDownViewResource(R.layout.spinner_item);
        mSpinnerBaudrate.setAdapter(baudrateAdapter);
        mSpinnerBaudrate.setOnItemSelectedListener(this);

        mSpinnerDevices.setSelection(mDeviceIndex);
        mSpinnerBaudrate.setSelection(mBaudrateIndex);
    }

    //copiar @OnClick desde Android SerialPort tool
    @OnClick({ R.id.btn_open_device, R.id.btn_send_data, R.id.btn_load_list,
            R.id.button,R.id.button2,R.id.button3,R.id.button4,R.id.button5,
            R.id.button6,R.id.button7,R.id.button8,R.id.button9,R.id.button10})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                sendData(1);
                break;
            case R.id.button2:
                sendData(2);
                break;
            case R.id.button3:
                sendData(3);
                break;
            case R.id.button4:
                sendData(4);
                break;
            case R.id.button5:
                sendData(5);
                break;
            case R.id.button6:
                sendData(6);
                break;
            case R.id.button7:
                sendData(7);
                break;
            case R.id.button8:
                sendData(8);
                break;
            case R.id.button9:
                sendData(9);
                break;
            case R.id.button10:
                sendData(10);
                break;
            case R.id.btn_open_device:
                switchSerialPort();
                break;
            case R.id.btn_send_data:
                sendEspData();
                break;
            //case R.id.btn_load_list:
            //    startActivity(new Intent(this, LoadCmdListActivity.class));
            //    break;
        }
    }
    /**
     *
     */

    private void sendEspData() {

        String text = mEtData.getText().toString().toUpperCase().trim();
        if (TextUtils.isEmpty(text) || text.length() % 2 != 0) {
            ToastUtil.showOne(this, "invalid data");
            return;
        }
        SerialPortManager.instance().sendCommand(text);
    }
    /**
     *
     */

    private void sendData(int btnNum) {
        switch (btnNum){
            case 1:
                cmdHEX = "AAEB22020000B955";
                SerialPortManager.instance().sendCommand(cmdHEX);
                break;
            case 2:
                cmdHEX = "AAEB22020001BA55";
                SerialPortManager.instance().sendCommand(cmdHEX);
            case 3:
                cmdHEX = "AAEB22020002BB55";
                SerialPortManager.instance().sendCommand(cmdHEX);

            case 4:
                cmdHEX = "AAEB22020003BC55";
                SerialPortManager.instance().sendCommand(cmdHEX);
                break;
            case 5:
                cmdHEX = "AAEB22020004BD55";
                SerialPortManager.instance().sendCommand(cmdHEX);
            case 6:
                cmdHEX = "AAEB22020005BE55";
                SerialPortManager.instance().sendCommand(cmdHEX);

            case 7:
                cmdHEX = "AAEB22020006BF55";
                SerialPortManager.instance().sendCommand(cmdHEX);
                break;
            case 8:
                cmdHEX = "AAEB22020007C055";
                SerialPortManager.instance().sendCommand(cmdHEX);
            case 9:
                cmdHEX = "AAEB22020008C155";
                SerialPortManager.instance().sendCommand(cmdHEX);
            case 10:
                cmdHEX = "AAEB22020009C255";
                SerialPortManager.instance().sendCommand(cmdHEX);

        }

    }
    /**
     * Abrir o cerrar el serial port
     */
    private void switchSerialPort() {
        if (mOpened) {
            SerialPortManager.instance().close();
            mOpened = false;
        } else {

            // 保存配置
            PrefHelper.getDefault().saveInt(PreferenceKeys.SERIAL_PORT_DEVICES, mDeviceIndex);
            PrefHelper.getDefault().saveInt(PreferenceKeys.BAUD_RATE, mBaudrateIndex);

            mOpened = SerialPortManager.instance().open(mDevice) != null;
            if (mOpened) {
                ToastUtil.showOne(this, "Puerto serial abierto");
            } else {
                ToastUtil.showOne(this, "Puerto serial No abierto");
            }
        }
        updateViewState(mOpened);
    }

    /**
     *
     *
     * @param isSerialPortOpened
     */
    private void updateViewState(boolean isSerialPortOpened) {

        int stringRes = isSerialPortOpened ? R.string.close_serial_port : R.string.open_serial_port;

        mBtnOpenDevice.setText(stringRes);

        mSpinnerDevices.setEnabled(!isSerialPortOpened);
        mSpinnerBaudrate.setEnabled(!isSerialPortOpened);
        mBtnSendData.setEnabled(isSerialPortOpened);
        mBtnLoadList.setEnabled(isSerialPortOpened);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.spinner_devices:
                mDeviceIndex = position;
                mDevice.setPath(mDevices[mDeviceIndex]);
                break;
            case R.id.spinner_baudrate:
                mBaudrateIndex = position;
                mDevice.setBaudrate(mBaudrates[mBaudrateIndex]);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}