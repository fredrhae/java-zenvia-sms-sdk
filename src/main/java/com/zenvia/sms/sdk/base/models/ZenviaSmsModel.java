package com.zenvia.sms.sdk.base.models;

import com.google.gson.*;
import com.zenvia.sms.sdk.base.rest.deserializers.GetStatusResponseDeserializer;
import com.zenvia.sms.sdk.base.rest.deserializers.ReceivedMessagesResponseDeserializer;
import com.zenvia.sms.sdk.base.rest.deserializers.SmsResponseDeserializer;
import com.zenvia.sms.sdk.base.rest.responses.GetSmsStatusResponse;
import com.zenvia.sms.sdk.base.rest.responses.ReceivedMessagesListResponse;
import com.zenvia.sms.sdk.base.rest.responses.SmsResponse;
import com.zenvia.sms.sdk.exceptions.ZenviaSmsInvalidEntityException;
import lombok.EqualsAndHashCode;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@EqualsAndHashCode
public class ZenviaSmsModel {

    protected static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .setPrettyPrinting()
            .registerTypeAdapter(Date.class, (JsonSerializer<Date>)
                    (json, typeOfSrc, context) -> {
                        TimeZone tz = TimeZone.getTimeZone("UTC");
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        df.setTimeZone(tz);
                        return new JsonPrimitive(df.format(json));
                    }
            )
            .registerTypeAdapter(Date.class, (JsonDeserializer<Date>)
                    (jsonElement, typeOfSrc, context) -> {
                        String date = jsonElement.getAsString();

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        format.setTimeZone(TimeZone.getTimeZone("UTC"));

                        try {
                            return format.parse(date);
                        } catch (ParseException exp) {
                            return null;
                        }
                    }
            )
            .registerTypeAdapter(SmsResponse.class, new SmsResponseDeserializer())
            .registerTypeAdapter(GetSmsStatusResponse.class, new GetStatusResponseDeserializer())
            .registerTypeAdapter(ReceivedMessagesListResponse.class, new ReceivedMessagesResponseDeserializer())
            .create();

    protected transient List<String> errors = new ArrayList<>();

    /* Serialization methods */

    /**
     * Serializes a model instance to JSON.
     * @return a {@link com.google.gson.JsonObject}
     * @throws ZenviaSmsInvalidEntityException
     */
    public JsonObject toJSON() throws ZenviaSmsInvalidEntityException{
//        if(!this.isValid()) { throw new ZenviaSmsInvalidEntityException(this); }
        return (JsonObject) gson.toJsonTree(this);
    }

    /**
     * Converts a {@link com.google.gson.JsonObject} to a model instance.
     * @param json the serialized instance
     * @param klass the instance class
     * @return an instance of ZenviaSmsModel
     */
    public static ZenviaSmsModel fromJSON(JsonObject json, Class<?> klass){
        return (ZenviaSmsModel) gson.fromJson(json, klass);
    }

    /* Error printing methods */

    /**
     * @return {@link ZenviaSmsModel#errors errors} pretty printed.
     */
    public String getErrors(){
        StringBuilder errors = new StringBuilder();
        for(String error : this.errors) {
            errors.append("\n");
            errors.append(error);
        }
        return this.getClass().getSimpleName() + errors.toString();
    }

    public static Gson getGson(){
        return gson;
    }
}