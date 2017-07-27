package com.zenvia.sms.sdk;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zenvia.sms.sdk.exceptions.ZenviaHTTPSmsException;
import com.zenvia.sms.sdk.exceptions.ZenviaHTTPExceptionFactory;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Properties;

/**
 *
 * ZenviaSms is an HTTP Client for connecting to Zenvia's API.
 *
 */
public final class ZenviaSms {
        private static final Properties PROPERTIES = new Properties();
        static {
            try {
                InputStream propertiesFile = ZenviaSms.class.getResourceAsStream("/konduto.properties");
                PROPERTIES.load(propertiesFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("File zenvia.properties file does not exist.");
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Could not read properties from zenvia.properties file.");
            }
        }

        public static final String VERSION = PROPERTIES.getProperty("version");

        private String base64AuthorizationKey;
        private JsonObject requestBody;
        private JsonObject responseBody;

        private URI endpoint = URI.create("https://api-rest.zenvia360.com.br/services");

        private static final HttpClient HTTP_CLIENT = new HttpClient(new MultiThreadedHttpConnectionManager());


        public ZenviaSms(String base64AuthorizationKey) {
            this.base64AuthorizationKey = base64AuthorizationKey;
        }

        /**
         * Requests to Zenvia API will go through a proxy if the proxy host is set
         *
         * Usage:
         * <code>
         *     String proxyHostname = "proxy.hostname";
         *     int proxyPort = 1234;
         *     ZenviaSms zenviaSms = new Zenvia(API_USERNAME, API_PASSWORD);
         *     zenviaSms.setProxyHost(proxyHostname, proxyPort);
         *
         *     * use zenviaSms API as usual *
         *     zenviaSms.sendSms(SMS_ID);
         *
         * </code>
         *
         * @param proxyHost the proxy host
         * @param proxyPort the proxy port
         * @see org.apache.commons.httpclient.HostConfiguration#setProxy
         */
        public void setProxyHost(String proxyHost, int proxyPort) {
            HTTP_CLIENT.getHostConfiguration().setProxy(proxyHost, proxyPort);
        }

        /**
         * Sets the proxy credentials if required.
         *
         * Usage:
         * <code>
         *     String proxyHostname = "proxy.hostname";
         *     int proxyPort = 1234;
         *     ZenviaSms zenviaSms = new Zenvia(API_USERNAME, API_PASSWORD);
         *     zenviaSms.setProxyHost(proxyHostname, proxyPort);
         *     zenviaSms.setProxyCredentials(new UsernamePasswordCredentials("username", "password"));
         *
         *     * use zenviaSms API as usual *
         *     zenviaSms.sendSms(SMS_ID);
         *
         * </code>
         *
         * @param credentials the proxy credentials
         * @see org.apache.commons.httpclient.Credentials
         */
        public void setProxyCredentials(Credentials credentials) {
            HTTP_CLIENT.getState().setProxyCredentials(AuthScope.ANY, credentials);
        }

        /**
         *
         * @param endpoint Zenvia's API endpoint (default is https://api-rest.zenvia360.com.br/services)
         */
        public void setEndpoint(URI endpoint) {
            this.endpoint = endpoint;
        }

        /**
         *
         * @param basicAuthorizationKey sets the username API sent by email, which is required for Zenvia's API authentication.
         */
        public void setBasicAuthorizationKey(String basicAuthorizationKey) {
            if (basicAuthorizationKey == null || basicAuthorizationKey.length() != 16) {
                throw new IllegalArgumentException("Illegal basic authorization key API:" + basicAuthorizationKey);
            }
            this.base64AuthorizationKey = basicAuthorizationKey;
        }


        /**
         * Helper method to debug requests made to Zenvia's API.
         * @return a String containing API Key, Zenvia's API endpoint, request and response bodies.
         */
        public String debug() {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("Authorization API Key used: %s\n", this.base64AuthorizationKey));
            sb.append(String.format("Endpoint: %s\n", this.endpoint.toString()));
            if(this.requestBody != null) {
                sb.append(String.format("Request body: %s\n", this.requestBody));
            }
            if(this.responseBody != null) {
                sb.append(String.format("Response body: %s\n", this.responseBody));
            }
            return sb.toString();
        }

        /**
         * @return [POST] send sms URI (ENDPOINT/send-sms)
         */
        protected URI zenviaSendSmsUrl(){
            return URI.create(endpoint.toString().concat("/send-sms"));
        }

        /**
         * @return [POST] send sms multiple URI (ENDPOINT/send-sms-multiple)
         */
        protected URI zenviaSendSmsMultipleUrl(){
            return URI.create(endpoint.toString().concat("/send-sms-multiple"));
        }

        /**
         * @param smsId the of sms to get status
         * @return [POST] order URI (ENDPOINT/get-sms-status/smsId)
         */
        protected URI zenviaGetSmsStatusUrl(String smsId){
            return URI.create(endpoint.toString().concat("/get-sms-status/" + smsId));
        }

        /**
         * @return [POST] order URI (ENDPOINT/received/list)
         */
        protected URI zenviaListReceivedSmsUrl(){
            return URI.create(endpoint.toString().concat("/received/list"));
        }

        /**
         * @param startDate the start date from the search
         * @param endDate the end date from the search
         * @return [GET] order URI (ENDPOINT/get-sms-status/smsId)
         */
        protected URI zenviaListReceivedSmsByPeriodUrl(String startDate, String endDate) {
            return URI.create(endpoint.toString().concat("/received/list/" + startDate + "/" + endDate));
        }

        /**
         * @param smsId the of sms to be canceled
         * @return [POST] order URI (ENDPOINT/cancel-sms/smsId)
         */
        protected URI zenviaCancelSmsUrl(String smsId){
            return URI.create(endpoint.toString().concat("/cancel-sms/" + smsId));
        }

        /**
         * @param method the HTTP method
         * @return the response body as a {@link com.google.gson.JsonObject JsonObject}
         * @throws IOException
         */
        private static JsonObject extractResponse(HttpMethod method) throws IOException {
            byte[] responseBodyAsByteArray = method.getResponseBody();
            String responseBodyAsString = new String(responseBodyAsByteArray, "UTF-8");
            JsonParser parser = new JsonParser();
            return (JsonObject) parser.parse(responseBodyAsString);
        }

        /**
         * @param requestBody the HTTP request body
         * @return a request entity
         * @throws UnsupportedEncodingException
         */
        private static StringRequestEntity getRequestEntity(JsonObject requestBody) throws UnsupportedEncodingException {
            return new StringRequestEntity(
                    requestBody.toString(),
                    "application/json",
                    "UTF-8"
            );
        }

        /**
         *
         * @param method the HTTP method
         * @param requestBody the HTTP request body
         * @return the response coming from ZenviaSms's API
         * @throws ZenviaHTTPSmsException when something goes wrong, i.e a non-200 OK response is answered
         */
        private JsonObject sendRequest(HttpMethod method, JsonObject requestBody) throws ZenviaHTTPSmsException {

            if(this.base64AuthorizationKey == null) { throw new NullPointerException("Authorization key cannot be null"); }

            JsonObject responseBody;

            method.addRequestHeader("Authorization", "Basic " + this.base64AuthorizationKey);

            method.addRequestHeader("Content-type", "application/json ");

            method.addRequestHeader("Accept", "application/json ");

            try {
                if(method instanceof PostMethod) {
                    this.requestBody = requestBody; // set ZenviaSms's request body for debugging purposes
                    ((PostMethod) method).setRequestEntity(getRequestEntity(requestBody));
                }

                int statusCode = HTTP_CLIENT.executeMethod(method);

                responseBody = extractResponse(method);

                this.responseBody = responseBody; // set ZenviaSms's response body for debugging purposes

                if(statusCode != 200) { throw ZenviaHTTPExceptionFactory.buildException(statusCode, responseBody); }

                return responseBody;

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                method.releaseConnection();
            }

            return null;
        }

        //TODO: Add the methods to send sms and get status and sms
}
