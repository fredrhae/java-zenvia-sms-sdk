package com.zenvia.sms.sdk.base.rest;

import com.google.gson.JsonObject;
import com.zenvia.sms.sdk.exceptions.ZenviaHTTPExceptionFactory;
import com.zenvia.sms.sdk.exceptions.ZenviaHTTPSmsException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class RestHelper {

    /**
     * @param getRequest the HTTP Get request
     * @return the response coming from Zenvia's API
     * @throws ZenviaHTTPSmsException when something goes wrong, i.e a non-200 OK response is answered
     * @throws IOException            when something goes wrong in the http connection and could not execute the request correctly
     */
    public static JsonObject sendGetRequest(HttpGet getRequest) throws ZenviaHTTPSmsException, IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        JsonObject responseBody;

        HttpResponse response;

        try {

            response = httpClient.execute(getRequest);

            int statusCode = response.getStatusLine().getStatusCode();

            HttpEntity responseEntity = response.getEntity();

            responseBody = JsonHelper.extractResponse(responseEntity.getContent());

            if (statusCode != 200) {
                throw ZenviaHTTPExceptionFactory.buildException(statusCode, responseBody);
            }

            httpClient.close();

            return responseBody;

        } finally {
            httpClient.close();
        }
    }

    /**
     * @param postRequest the HTTP Post request
     * @param requestBody the HTTP request body
     * @return the response coming from Zenvia's API
     * @throws ZenviaHTTPSmsException when something goes wrong, i.e a non-200 OK response is answered
     * @throws IOException            when something goes wrong in the http connection and could not execute the request correctly
     */
    public static JsonObject sendPostRequest(HttpPost postRequest, JsonObject requestBody) throws ZenviaHTTPSmsException, IOException {


        CloseableHttpClient httpClient = HttpClients.createDefault();

        JsonObject responseBody;

        HttpResponse response;

        try {

            if(requestBody != null) {
                StringEntity requestEntity = new StringEntity(requestBody.toString(), ContentType.APPLICATION_JSON);

                postRequest.setEntity(requestEntity);
            }

            response = httpClient.execute(postRequest);

            int statusCode = response.getStatusLine().getStatusCode();

            HttpEntity responseEntity = response.getEntity();

            responseBody = JsonHelper.extractResponse(responseEntity.getContent());

            if (statusCode != 200) {
                throw ZenviaHTTPExceptionFactory.buildException(statusCode, responseBody);
            }

            httpClient.close();

            return responseBody;

        } finally {
            httpClient.close();
        }
    }

}
