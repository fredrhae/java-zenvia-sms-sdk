package com.zenvia.sms.sdk.exceptions;

import com.google.gson.JsonObject;

public class ZenviaSmsUnexpectedAPIResponseException extends ZenviaSmsException {
    private JsonObject responseBody;

    public ZenviaSmsUnexpectedAPIResponseException(JsonObject responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public String getMessage() {
        return String.format("Unexpected API response: %s", this.responseBody);
    }
}
