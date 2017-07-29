package com.zenvia.sms.sdk.base.rest;

import com.google.gson.*;
import com.zenvia.sms.sdk.base.models.SmsStatusCode;
import com.zenvia.sms.sdk.base.responses.SendSmsResponse;

import java.lang.reflect.Type;

public class SmsResponseDeserializer implements JsonDeserializer<SendSmsResponse> {

    public SendSmsResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement elementStatusCode = jsonObject.get("statusCode");
        JsonElement elementStatusDescription = jsonObject.get("statusDescription");
        JsonElement elementDetailCode = jsonObject.get("detailCode");
        JsonElement elementDetailDescription = jsonObject.get("detailDescription");

        return SendSmsResponse.builder()
                    .statusCode(SmsStatusCode.fromValue(elementStatusCode.getAsInt()))
                    .statusDescription(elementStatusDescription.getAsString())
                    .detailCode(elementDetailCode.getAsInt())
                    .detailDescription(elementDetailDescription.getAsString())
                    .build();
    }
}
