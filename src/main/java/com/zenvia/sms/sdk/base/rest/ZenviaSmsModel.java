package com.zenvia.sms.sdk.base.rest;

import com.google.gson.*;
import com.zenvia.sms.sdk.base.responses.SendSmsResponse;
import com.zenvia.sms.sdk.exceptions.ZenviaSmsInvalidEntityException;
import lombok.EqualsAndHashCode;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class ZenviaSmsModel {

    protected static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .setPrettyPrinting()
            .registerTypeAdapter(DateTime.class, new JsonSerializer<DateTime>(){
                public JsonElement serialize(DateTime json, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(ISODateTimeFormat.dateTime().print(json));
                }
            })
            .registerTypeAdapter(SendSmsResponse.class, new SmsResponseDeserializer())
            .create();

    protected transient List<String> errors = new ArrayList<String>();

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