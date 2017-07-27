package com.zenvia.sms.sdk.exceptions;

public class ZenviaHTTPSmsException extends ZenviaSmsException {

    private static final long serialVersionUID = 6043245262354589627L;
    private String message;

    /**
     *
     * @param message instance's message
     * @param responseBody Zenvia's API response
     */
    protected ZenviaHTTPSmsException(String message, String responseBody){
        this.message = String.format("%s Response body: %s", message, responseBody);
    }

    @Override
    public String getMessage() { return this.message; }
}
