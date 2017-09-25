package com.zenvia.sms.sdk.base.rest.deserializers;

import com.google.gson.*;
import com.zenvia.sms.sdk.base.models.SmsStatusCode;
import com.zenvia.sms.sdk.base.rest.responses.GetSmsStatusResponse;
import com.zenvia.sms.sdk.base.rest.responses.SmsResponse;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        Date dateToSet = new Date();

        if (!dateReceived.isEmpty()) {
            try {
                dateToSet = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateReceived);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        SmsResponse smsResponse = SmsResponse.builder()
                                        .statusCode(SmsStatusCode.fromValue(statusCode))
                                        .statusDescription(statusDescription)
                                        .detailCode(detailCode)
                                        .detailDescription(detailDescription)
                                        .build();

        return GetSmsStatusResponse.builder()
                .id(id)
                .received(dateToSet)
                .shortcode(shortCode)
                .mobileOperatorName(mobileOperatorName)
                .smsResponse(smsResponse)
                .build();
    }

    private String returnEmptyIfNull(JsonElement input) {
        if(input.isJsonNull()) {
            return "";
        }
        return input.getAsString();
    }
}
