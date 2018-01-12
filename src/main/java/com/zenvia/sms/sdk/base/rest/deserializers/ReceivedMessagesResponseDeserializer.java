package com.zenvia.sms.sdk.base.rest.deserializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.zenvia.sms.sdk.base.models.SmsStatusCode;
import com.zenvia.sms.sdk.base.rest.JsonHelper;
import com.zenvia.sms.sdk.base.rest.responses.ReceivedMessage;
import com.zenvia.sms.sdk.base.rest.responses.ReceivedMessagesListResponse;
import com.zenvia.sms.sdk.base.rest.responses.SmsResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReceivedMessagesResponseDeserializer implements JsonDeserializer<ReceivedMessagesListResponse> {
    @Override
    public ReceivedMessagesListResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        SmsResponse smsResponse = extractSmsResponse(jsonObject);

        List<ReceivedMessage> receivedMessages = extractReceivedMessages(jsonObject);

        return new ReceivedMessagesListResponse.ReceivedMessagesListResponseBuilder()
                .receivedMessages(receivedMessages)
                .smsResponse(smsResponse)
                .build();
    }

    private List<ReceivedMessage> extractReceivedMessages(JsonObject jsonObject) {
        List<ReceivedMessage> receivedMessages = new ArrayList<>();

        if(jsonObject.has("receivedMessages") && !jsonObject.get("receivedMessages").isJsonNull()) {
            JsonArray jsonReceivedMessages = jsonObject.getAsJsonArray("receivedMessages");
            Type listType = new TypeToken<List<ReceivedMessage>>(){}.getType();
            receivedMessages = JsonHelper.getGson().fromJson(jsonReceivedMessages,listType);
        }
        return receivedMessages;
    }

    private SmsResponse extractSmsResponse(JsonObject jsonObject) {
        Integer statusCode = jsonObject.get("statusCode").getAsInt();
        String statusDescription = jsonObject.get("statusDescription").getAsString();
        Integer detailCode = jsonObject.get("detailCode").getAsInt();
        String detailDescription = jsonObject.get("detailDescription").getAsString();


        return new SmsResponse.SmsResponseBuilder()
                                    .statusCode(SmsStatusCode.fromValue(statusCode))
                                    .statusDescription(statusDescription)
                                    .detailCode(detailCode)
                                    .detailDescription(detailDescription)
                                    .build();
    }
}
