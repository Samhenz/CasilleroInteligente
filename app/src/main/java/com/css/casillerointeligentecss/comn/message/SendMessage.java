package com.css.casillerointeligentecss.comn.message;

import com.css.casillerointeligentecss.util.TimeUtil;
/**
 * Registros enviados
 */

public class SendMessage implements IMessage {

    private String command;
    private String message;

    public SendMessage(String command) {
        this.command = command;
        this.message = TimeUtil.currentTime() + "    enviado：" + command;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public boolean isToSend() {
        return true;
    }
}
