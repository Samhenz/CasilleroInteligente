package com.css.casillerointeligentecss.comn;

import static com.css.casillerointeligentecss.R.string.cmd_1;

import android.content.Context;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Toast;
import com.css.casillerointeligentecss.R;
import com.css.casillerointeligentecss.BotonesCargarInventario;
import com.css.casillerointeligentecss.util.ToastUtil;
import com.licheedev.hwutils.ByteUtil;
import com.licheedev.myutils.LogPlus;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import com.css.casillerointeligentecss.util.ToastUtil;
//import com.css.casillerointeligentecss.comn.message.LogManager;
//import com.css.casillerointeligentecss.comn.message.RecvMessage;

/**
 * Read serial port threads
 */
public class SerialReadThread extends Thread {


    private static final String TAG = "SerialReadThread";
    //public static Toast makeText (Context context, CharSequence text, int duration);
    private BufferedInputStream mInputStream;

    public SerialReadThread(InputStream is) {
        mInputStream = new BufferedInputStream(is);
    }


    @Override
    public void run() {
        byte[] received = new byte[1024];
        int size;

        LogPlus.e("Start reading threads");

        while (true) {

            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            try {

                int available = mInputStream.available();

                if (available > 0) {
                    size = mInputStream.read(received);
                    if (size > 0) {
                        onDataReceive(received, size);
                    }
                } else {
                    // Pause for a little while, so as not to keep looping causing high CPU usage
                    SystemClock.sleep(1);
                }
            } catch (IOException e) {
                LogPlus.e("Failed to read data", e);
            }
            //Thread.yield();
        }

        LogPlus.e("Ending the read process");
    }

    /**
     * Processing the acquired data
     *
     * @param received
     * @param size
     */
    private void onDataReceive(byte[] received, int size) {
        // TODO: 2018/3/22
        String hexStr = ByteUtil.bytes2HexStr(received, 0, size);
        switch (hexStr){
            case "AAEB2203000001BB55":
                //btn[0].setBackgroundResource(R.color.red_700);
                break;
            case "AAEB2203000101BC55":
                //btn[1].setBackgroundResource(R.color.red_700);
                break;
            case "AAEB2203000201BD55":
                //btn[2].setBackgroundResource(R.color.red_700);
                break;
            case "AAEB2203000301BE55":
                //btn[3].setBackgroundResource(R.color.red_700);
                break;
            case "AAEB2203000401BF55":
                break;

            case "AAEB2203000501C055":
                break;

            case "AAEB2203000601C155":
                break;

            case "AAEB2203000701C255":
                break;

            case "AAEB2203000801C355":
                break;

            case "AAEB2203000901C455":
                break;

        }
        // LogManager.instance().post(new RecvMessage(hexStr));
    }

    /**
     * Stop reading threads
     */
    public void close() {

        try {
            mInputStream.close();
        } catch (IOException e) {
            LogPlus.e("Exceptions", e);
        } finally {
            super.interrupt();
        }
    }
}
