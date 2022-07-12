package com.css.casillerointeligentecss.comn.message;

/**
 * Interfaz de datos de mensajes de registro
 */

public interface IMessage {
    /**
     * Texto del mensaje
     *
     * @return
     */
    String getMessage();

    /**
     * Mensajes enviados o no
     *
     * @return
     */
    boolean isToSend();
}
