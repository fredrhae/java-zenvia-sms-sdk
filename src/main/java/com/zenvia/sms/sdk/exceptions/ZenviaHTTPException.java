package com.zenvia.sms.sdk.exceptions;

import com.google.gson.JsonObject;

public class ZenviaHTTPException extends ZenviaException{

    private static final long serialVersionUID = 6043245262354589627L;
    private String message;

    /**
     *
     * @param message instance's message
     * @param responseBody Zenvia's API response
     */
    protected ZenviaHTTPException(String message, JsonObject responseBody){
        this.message = String.format("%s Response body: %s", message, responseBody.toString());
    }

    @Override
    public String getMessage() { return this.message; }
}
