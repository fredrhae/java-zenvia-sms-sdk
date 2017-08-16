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
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class ZenviaSmsModel {

    protected static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .setPrettyPrinting()
            .registerTypeAdapter(DateTime.class, (JsonSerializer<DateTime>)
                                    (json, typeOfSrc, context) -> new JsonPrimitive(ISODateTimeFormat.dateTime().print(json))
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