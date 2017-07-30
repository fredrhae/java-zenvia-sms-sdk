package com.zenvia.sms.sdk.base.rest;

import com.google.gson.*;
import com.zenvia.sms.sdk.base.models.SmsStatusCode;
import com.zenvia.sms.sdk.base.responses.GetSmsStatusResponse;
import org.joda.time.DateTime;

import java.lang.reflect.Type;

public class GetStatusResponseDeserializer implements JsonDeserializer<GetSmsStatusResponse> {

    public GetSmsStatusResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String id = returnEmptyIfNull(jsonObject.get("id"));
        String dateReceived = returnEmptyIfNull(jsonObject.get("received"));
        String shortCode = returnEmptyIfNull(jsonObject.get("shortcode"));
        String mobileOperatorName = returnEmptyIfNull(jsonObject.get("mobileOperatorName"));
        Integer statusCode = jsonObject.get("statusCode").getAsInt();
        String statusDescription = returnEmptyIfNull(jsonObject.get("statusDescription"));
        Integer detailCode = jsonObject.get("detailCode").getAsInt();
        String detailDescription = returnEmptyIfNull(jsonObject.get("detailDescription"));

        DateTime dateToSet = (dateReceived.isEmpty()) ? null : new DateTime(dateReceived);

        return GetSmsStatusResponse.builder()
                .id(id)
                .received(dateToSet)
                .shortcode(shortCode)
                .mobileOperatorName(mobileOperatorName)
                .statusCode(SmsStatusCode.fromValue(statusCode))
                .statusDescription(statusDescription)
                .detailCode(detailCode)
                .detailDescription(detailDescription)
                .build();
    }

    private String returnEmptyIfNull(JsonElement input) {
        if(input.isJsonNull()) {
            return "";
        }
        return input.getAsString();
    }

}
