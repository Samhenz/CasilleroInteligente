package com.css.casillerointeligentecss.comn;

import android.os.HandlerThread;
import android.serialport.SerialPort;
import com.licheedev.hwutils.ByteUtil;
import com.licheedev.myutils.LogPlus;
import com.css.casillerointeligentecss.comn.message.LogManager;
import com.css.casillerointeligentecss.comn.message.SendMessage;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialPortManager {

    private static final String TAG = "SerialPortManager";

    private SerialReadThread mReadThread;
    private OutputStream mOutputStream;
    private HandlerThread mWriteThread;
    private Scheduler mSendScheduler;
    private InputStream mInputStream;

    private static class InstanceHolder {

        public static SerialPortManager sManager = new SerialPortManager();

    }

    public static SerialPortManager instance() {
        return InstanceHolder.sManager;
    }

    private SerialPort mSerialPort;

    private SerialPortManager() {
    }

    /**
     * Abrir serial port
     *
     * @param device
     * @return
     */
    public SerialPort open(Device device) {
        return open(device.getPath(), device.getBaudrate());
    }

    /**
     * abrir serial port
     *
     * @param devicePath
     * @param baudrateString
     * @return
     */
    public SerialPort open(String devicePath, String baudrateString) {

        if (mSerialPort != null) {  //Si el objeto Serial está "lleno" (previamente abierto)
            close();                //llamar a la función Cerrar();
        }

        try {
            File device = new File(devicePath);
            int baurate = Integer.parseInt(baudrateString);
            mSerialPort = new SerialPort(device, baurate,8,2,1,0);

            mInputStream = mSerialPort.getInputStream();
            //mReadThread = new SerialReadThread(mSerialPort.getInputStream());
            //mReadThread.start();

            mOutputStream = mSerialPort.getOutputStream();

            //mWriteThread = new HandlerThread("write-thread");
            //mWriteThread.start();
            //mSendScheduler = AndroidSchedulers.from(mWriteThread.getLooper());

            return mSerialPort;
        } catch (Throwable tr) {
            LogPlus.e(TAG, "Serial port not opened", tr);
            close();
            return null;
        }
    }

    /**
     * cerrar serial port
     */
    public void close() {
        /*
        if (mReadThread != null) {
            mReadThread.close();
        }
         */

        if (mOutputStream != null) {
            try {
                mOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        /*
        if (mWriteThread != null) {
            mWriteThread.quit();
        }
        */


        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }

    /**
     * Enviar Data desde Command()
     *
     * @param datas
     * @return
     */
    private void sendData(byte[] datas) throws Exception {
        mOutputStream.write(datas);
    }

    /**
     * Enviar HEX Data
     *
     */
    public void sendHEXData(final String command) throws IOException {
        byte[] hex_bytes = ByteUtil.hexStr2bytes(command);
        mOutputStream.write(hex_bytes);
    }

    /**
     * (rx package) send data
     *
     * @param datas
     * @return
     */
    private Observable<Object> rxSendData(final byte[] datas) {

        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                try {
                    sendData(datas);
                    emitter.onNext(new Object());
                } catch (Exception e) {

                    LogPlus.e("enviar：" + ByteUtil.bytes2HexStr(datas) + " fallido", e);

                    if (!emitter.isDisposed()) {
                        emitter.onError(e);
                        return;
                    }
                }
                emitter.onComplete();
            }
        });
    }

    /**
     * Sending command packets
     */
    public void sendCommand(final String command) {

        // TODO: 2018/3/22
        LogPlus.i("enviar：" + command);

        byte[] bytes = ByteUtil.hexStr2bytes(command);
        rxSendData(bytes).subscribeOn(mSendScheduler).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onNext(Object o) {
                LogManager.instance().post(new SendMessage(command));
            }

            @Override
            public void onError(Throwable e) {
                LogPlus.e("falló envío ", e);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
