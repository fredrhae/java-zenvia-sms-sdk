package com.zenvia.sms.sdk.exceptions;

/**
 *
 * This exception is the parent of all Konduto exceptions.
 *
 * Use it to catch any instance of its children and handle as you wish
 * (e.g saving an order, reporting to our support team automatically, etc.)
 *
 */
public abstract class ZenviaSmsException extends Exception {

    private static final long serialVersionUID = 7652761688561093159L;

    public abstract String getMessage();

}
