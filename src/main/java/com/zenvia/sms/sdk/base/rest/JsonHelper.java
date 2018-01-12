package com.zenvia.sms.sdk.base.rest;

import com.google.gson.*;
import com.zenvia.sms.sdk.base.models.ZenviaSmsModel;
import com.zenvia.sms.sdk.base.rest.deserializers.GetStatusResponseDeserializer;
import com.zenvia.sms.sdk.base.rest.deserializers.ReceivedMessagesResponseDeserializer;
import com.zenvia.sms.sdk.base.rest.deserializers.SmsResponseDeserializer;
import com.zenvia.sms.sdk.base.rest.responses.GetSmsStatusResponse;
import com.zenvia.sms.sdk.base.rest.responses.ReceivedMessagesListResponse;
import com.zenvia.sms.sdk.base.rest.responses.SmsResponse;
import com.zenvia.sms.sdk.exceptions.ZenviaSmsInvalidEntityException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class JsonHelper {
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

    public static Gson getGson(){
        return gson;
    }

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

    /**
     * @param inputStream the stream with HTTP response
     * @return the response body as a {@link com.google.gson.JsonObject JsonObject}
     * @throws IOException
     */
    public static JsonObject extractResponse(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        JsonParser parser = new JsonParser();
        return (JsonObject) parser.parse(stringBuilder.toString());
    }
}
