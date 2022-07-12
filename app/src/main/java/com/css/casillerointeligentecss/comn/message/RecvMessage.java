package com.css.casillerointeligentecss.comn.message;

import com.css.casillerointeligentecss.util.TimeUtil;

/**
 * Registros recibidos
 */

public class RecvMessage implements IMessage {

    private String command;
    private String message;

    public RecvMessage(String command) {
        this.command = command;
        this.message = TimeUtil.currentTime() + "    recibido：" + command;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public boolean isToSend() {
        return false;
    }
}
