package com.zenvia.sms.sdk.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zenvia.sms.sdk.base.models.ZenviaSmsModel;
import com.zenvia.sms.sdk.base.rest.requests.SendSmsMultiRequest;
import com.zenvia.sms.sdk.base.rest.requests.SendSmsRequest;
import com.zenvia.sms.sdk.base.rest.responses.GetSmsStatusResponse;
import com.zenvia.sms.sdk.base.rest.responses.ReceivedMessagesListResponse;
import com.zenvia.sms.sdk.base.rest.responses.SendSmsResponseList;
import com.zenvia.sms.sdk.base.rest.responses.SmsResponse;
import com.zenvia.sms.sdk.exceptions.ZenviaHTTPExceptionFactory;
import com.zenvia.sms.sdk.exceptions.ZenviaHTTPSmsException;
import com.zenvia.sms.sdk.exceptions.ZenviaSmsInvalidEntityException;
import com.zenvia.sms.sdk.exceptions.ZenviaSmsUnexpectedAPIResponseException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 * ZenviaSms is an HTTP Client for connecting to Zenvia's API.
 */
public final class ZenviaSms {
    public static final String VERSION = "0.0.1";

    private String base64AuthorizationKey;

    private JsonObject requestBody;
    private JsonObject responseBody;

    private URI endpoint = URI.create("https://api-rest.zenvia360.com.br/services");

    public ZenviaSms(String base64AuthorizationKey) {
        this.base64AuthorizationKey = base64AuthorizationKey;
    }


    /**
     * @param endpoint Zenvia's API endpoint (default is https://api-rest.zenvia360.com.br/services)
     */
    public void setEndpoint(URI endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * @return Endpoint for Zenvia API
     */
    public URI getEndpoint() {
        return this.endpoint;
    }


    /**
     * @param base64AuthorizationKey sets the auth key generated by username and password sent by email, which is required for Zenvia's API authentication.
     * @throws IllegalArgumentException
     */
    public void setBasicAuthorizationKey(String base64AuthorizationKey) throws UnsupportedEncodingException {
        if (base64AuthorizationKey == null) {
            throw new IllegalArgumentException("Illegal auth Key:" + base64AuthorizationKey);
        } else {
            this.base64AuthorizationKey = base64AuthorizationKey;
        }

    }

    public String getBasicAuthorizationKey() {
        return this.base64AuthorizationKey;
    }

    /**
     * @return [POST] send sms URI (ENDPOINT/send-sms)
     */
    public URI zenviaSendSmsUrl() {
        return URI.create(endpoint.toString().concat("/send-sms"));
    }

    /**
     * @return [POST] send sms multiple URI (ENDPOINT/send-sms-multiple)
     */
    public URI zenviaSendSmsMultipleUrl() {
        return URI.create(endpoint.toString().concat("/send-sms-multiple"));
    }

    /**
     * @param smsId the of sms to get status
     * @return [POST] order URI (ENDPOINT/get-sms-status/smsId)
     */
    public URI zenviaGetSmsStatusUrl(String smsId) {
        return URI.create(endpoint.toString().concat("/get-sms-status/" + smsId));
    }

    /**
     * @return [POST] order URI (ENDPOINT/received/list)
     */
    public URI zenviaListReceivedSmsUrl() {
        return URI.create(endpoint.toString().concat("/received/list"));
    }

    /**
     * @param startDate the start date from the search
     * @param endDate   the end date from the search
     * @return [GET] order URI (ENDPOINT/get-sms-status/smsId)
     */
    public URI zenviaListReceivedSmsByPeriodUrl(String startDate, String endDate) {
        return URI.create(endpoint.toString().concat("/received/search/" + startDate + "/" + endDate));
    }

    /**
     * @param smsId the of sms to be canceled
     * @return [POST] order URI (ENDPOINT/cancel-sms/smsId)
     */
    public URI zenviaCancelSmsUrl(String smsId) {
        return URI.create(endpoint.toString().concat("/cancel-sms/" + smsId));
    }

    /**
     * Helper method to debug requests made to Zenvia's API.
     *
     * @return a String containing API Key, Zenvia's API endpoint, request and response bodies.
     */
    public String debug() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("API Key: %s\n", this.base64AuthorizationKey));
        sb.append(String.format("Endpoint: %s\n", this.endpoint.toString()));
        if (this.requestBody != null) {
            sb.append(String.format("Request body: %s\n", this.requestBody));
        }
        if (this.responseBody != null) {
            sb.append(String.format("Response body: %s\n", this.responseBody));
        }
        return sb.toString();
    }

    /**
     * @param inputStream the stream with HTTP response
     * @return the response body as a {@link com.google.gson.JsonObject JsonObject}
     * @throws IOException
     */
    private static JsonObject extractResponse(InputStream inputStream) throws IOException {
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

    /**
     * @param getRequest the HTTP Get request
     * @return the response coming from Zenvia's API
     * @throws ZenviaHTTPSmsException when something goes wrong, i.e a non-200 OK response is answered
     * @throws IOException            when something goes wrong in the http connection and could not execute the request correctly
     */
    private JsonObject sendGetRequest(HttpGet getRequest) throws ZenviaHTTPSmsException, IOException {


        CloseableHttpClient httpClient = HttpClients.createDefault();

        JsonObject responseBody;

        HttpResponse response;

        checkAuthorizationData();

        addAuthorizationAndAccept(getRequest);

        try {

            response = httpClient.execute(getRequest);

            int statusCode = response.getStatusLine().getStatusCode();

            HttpEntity responseEntity = response.getEntity();

            responseBody = extractResponse(responseEntity.getContent());

            this.responseBody = responseBody; // set Zenvia's response body for debugging purposes

            System.out.println(debug());

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
    private JsonObject sendPostRequest(HttpPost postRequest, JsonObject requestBody) throws ZenviaHTTPSmsException, IOException {


        CloseableHttpClient httpClient = HttpClients.createDefault();

        JsonObject responseBody;

        HttpResponse response;

        checkAuthorizationData();

        addCompleteHeaders(postRequest);

        try {

            if(requestBody != null) {
                this.requestBody = requestBody; // set Zenvia's request body for debugging purposes

                StringEntity requestEntity = new StringEntity(requestBody.toString(), ContentType.APPLICATION_JSON);

                postRequest.setEntity(requestEntity);
            }

            response = httpClient.execute(postRequest);

            int statusCode = response.getStatusLine().getStatusCode();

            HttpEntity responseEntity = response.getEntity();

            responseBody = extractResponse(responseEntity.getContent());

            this.responseBody = responseBody; // set Zenvia's response body for debugging purposes

            System.out.println(debug());

            if (statusCode != 200) {
                throw ZenviaHTTPExceptionFactory.buildException(statusCode, responseBody);
            }

            httpClient.close();

            return responseBody;

        } finally {
            httpClient.close();
        }
    }


    private void checkAuthorizationData() {
        if (this.base64AuthorizationKey == null) {
            throw new NullPointerException("API authorization key cannot be generated, since there is not username and password configured");
        }
    }

    private JsonObject addPropertyAndConvertToJson(ZenviaSmsModel smsModel, String property) {
        JsonElement elements = ZenviaSmsModel.getGson().toJsonTree(smsModel);
        JsonObject jsonObject = new JsonObject();
        jsonObject.add(property, elements);
        return jsonObject;
    }

    private void addCompleteHeaders(HttpRequestBase requestBase) {
        addAuthorizationAndAccept(requestBase);

        requestBase.addHeader("Content-Type", "application/json");
    }

    private void addAuthorizationAndAccept(HttpRequestBase requestBase) {
        requestBase.addHeader("Authorization", "Basic " + this.base64AuthorizationKey);
        requestBase.addHeader("Accept", "application/json");
    }

    /**
     * Sends a single sms using Zenvia SMS API
     *
     * @param sendSingleSmsRequest a {@link SendSmsRequest} instance
     * @throws ZenviaHTTPSmsException
     * @throws ZenviaSmsUnexpectedAPIResponseException
     * @throws ZenviaSmsInvalidEntityException
     * @see <a href="http://docs.zenviasms.apiary.io/">Zenvia Sms API Spec</a>
     */

    public SmsResponse sendSingleSms(SendSmsRequest sendSingleSmsRequest)
            throws ZenviaHTTPSmsException, ZenviaSmsUnexpectedAPIResponseException, ZenviaSmsInvalidEntityException {

        HttpPost postMethod = new HttpPost(zenviaSendSmsUrl().toString());

        JsonObject responseBody;

        JsonObject sendSmsJson = addPropertyAndConvertToJson(sendSingleSmsRequest, "sendSmsRequest");

        try {
            responseBody = sendPostRequest(postMethod, sendSmsJson);

            if (responseBody == null) {
                throw new ZenviaSmsUnexpectedAPIResponseException(null);
            }

            return (SmsResponse) ZenviaSmsModel.fromJSON(responseBody.getAsJsonObject("sendSmsResponse"), SmsResponse.class);

        } catch (IOException e) {
            throw new ZenviaSmsInvalidEntityException(sendSingleSmsRequest);
        }
    }

    /**
     * Send multiple sms simultaneously using Zenvia SMS API
     *
     * @param sendSmsRequestList a {@link com.zenvia.sms.sdk.base.rest.requests.SendSmsRequestList} instance
     * @throws ZenviaHTTPSmsException
     * @throws ZenviaSmsUnexpectedAPIResponseException
     * @throws ZenviaSmsInvalidEntityException
     * @see <a href="http://docs.zenviasms.apiary.io/">Zenvia Sms API Spec</a>
     */

    public SendSmsResponseList sendMultipleSms(SendSmsMultiRequest sendSmsRequestList)
            throws ZenviaHTTPSmsException, ZenviaSmsUnexpectedAPIResponseException, ZenviaSmsInvalidEntityException {

        HttpPost postMethod = new HttpPost(zenviaSendSmsMultipleUrl().toString());

        JsonObject responseBody;

        JsonObject sendMultipleSmsJson = addPropertyAndConvertToJson(sendSmsRequestList, "sendSmsMultiRequest");

        try {
            responseBody = sendPostRequest(postMethod, sendMultipleSmsJson);

            if (responseBody == null) {
                throw new ZenviaSmsUnexpectedAPIResponseException(null);
            }

            return (SendSmsResponseList) ZenviaSmsModel.fromJSON(responseBody.getAsJsonObject("sendSmsMultiResponse"), SendSmsResponseList.class);

        } catch (IOException e) {
            throw new ZenviaSmsInvalidEntityException(sendSmsRequestList);
        }
    }

    /**
     * Get status from sms simultaneously using Zenvia SMS API
     *
     * @param id from sms which is desired to get status
     * @throws ZenviaHTTPSmsException
     * @throws ZenviaSmsUnexpectedAPIResponseException
     * @see <a href="http://docs.zenviasms.apiary.io/">Zenvia Sms API Spec</a>
     */

    public GetSmsStatusResponse getStatusFromSms(String id)
            throws ZenviaHTTPSmsException, ZenviaSmsUnexpectedAPIResponseException, ZenviaSmsInvalidEntityException {

        HttpGet getMethod = new HttpGet(zenviaGetSmsStatusUrl(id).toString());

        JsonObject responseBody;

        try {
            responseBody = sendGetRequest(getMethod);

            if (responseBody == null) {
                throw new ZenviaSmsUnexpectedAPIResponseException(null);
            }

            return (GetSmsStatusResponse) ZenviaSmsModel.fromJSON(responseBody.getAsJsonObject("getSmsStatusResp"),
                                                                    GetSmsStatusResponse.class);

        } catch (IOException e) {
            throw new ZenviaSmsInvalidEntityException(null);
        }
    }

    /**
     * Request list of received messages, using Zenvia's API
     *
     * @throws ZenviaHTTPSmsException
     * @throws ZenviaSmsUnexpectedAPIResponseException
     * @throws ZenviaSmsInvalidEntityException
     * @see <a href="http://docs.zenviasms.apiary.io/">Zenvia Sms API Spec</a>
     */

    public ReceivedMessagesListResponse listReceivedMessages()
            throws ZenviaHTTPSmsException, ZenviaSmsUnexpectedAPIResponseException, ZenviaSmsInvalidEntityException {

        HttpPost postMethod = new HttpPost(zenviaListReceivedSmsUrl().toString());

        JsonObject responseBody;

        try {
            responseBody = sendPostRequest(postMethod, null);

            if (responseBody == null) {
                throw new ZenviaSmsUnexpectedAPIResponseException(null);
            }

            return (ReceivedMessagesListResponse) ZenviaSmsModel.fromJSON(responseBody.getAsJsonObject("receivedResponse"), ReceivedMessagesListResponse.class);

        } catch (IOException e) {
            throw new ZenviaSmsInvalidEntityException(null);
        }
    }

    /**
     * Request list of received messages between period, using Zenvia's API
     * @param startDate start date from search
     * @param endDate end date from search
     * @throws ZenviaHTTPSmsException
     * @throws ZenviaSmsUnexpectedAPIResponseException
     * @throws ZenviaSmsInvalidEntityException
     * @see <a href="http://docs.zenviasms.apiary.io/">Zenvia Sms API Spec</a>
     */

    public ReceivedMessagesListResponse listReceivedMessagesByPeriod(Date startDate, Date endDate)
            throws ZenviaHTTPSmsException, ZenviaSmsUnexpectedAPIResponseException, ZenviaSmsInvalidEntityException {

        HttpGet getMethod = new HttpGet(zenviaListReceivedSmsByPeriodUrl(dateToIsoFormat(startDate), dateToIsoFormat(endDate)).toString());

        JsonObject responseBody;

        try {
            responseBody = sendGetRequest(getMethod);

            if (responseBody == null) {
                throw new ZenviaSmsUnexpectedAPIResponseException(null);
            }

            return (ReceivedMessagesListResponse) ZenviaSmsModel.fromJSON(responseBody.getAsJsonObject("receivedResponse"), ReceivedMessagesListResponse.class);

        } catch (IOException e) {
            throw new ZenviaSmsInvalidEntityException(null);
        }
    }

    /**
     * Cancel scheduled SMS, using Zenvia's API
     * @param id from sms which is desired to cancel
     * @throws ZenviaHTTPSmsException
     * @throws ZenviaSmsUnexpectedAPIResponseException
     * @throws ZenviaSmsInvalidEntityException
     * @see <a href="http://docs.zenviasms.apiary.io/">Zenvia Sms API Spec</a>
     */

    public SmsResponse cancelScheduledSms(String id)
            throws ZenviaHTTPSmsException, ZenviaSmsUnexpectedAPIResponseException, ZenviaSmsInvalidEntityException {

        HttpPost postMethod = new HttpPost(zenviaCancelSmsUrl(id).toString());

        JsonObject responseBody;

        try {
            responseBody = sendPostRequest(postMethod, null);

            if (responseBody == null) {
                throw new ZenviaSmsUnexpectedAPIResponseException(null);
            }

            return (SmsResponse) ZenviaSmsModel.fromJSON(responseBody.getAsJsonObject("cancelSmsResp"), SmsResponse.class);

        } catch (IOException e) {
            throw new ZenviaSmsInvalidEntityException(null);
        }
    }

    private String dateToIsoFormat(Date inputDate) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df.setTimeZone(tz);
        return df.format(inputDate);
    }
}
