package com.zenvia.sms.sdk.api;

import com.zenvia.sms.sdk.base.requests.SendSmsRequest;
import com.zenvia.sms.sdk.base.responses.SendSmsResponse;
import com.zenvia.sms.sdk.base.rest.JsonFormatter;
import com.zenvia.sms.sdk.exceptions.ZenviaHTTPExceptionFactory;
import com.zenvia.sms.sdk.exceptions.ZenviaHTTPSmsException;
import com.zenvia.sms.sdk.exceptions.ZenviaSmsUnexpectedAPIResponseException;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
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

        private URI endpoint = URI.create("https://api-rest.zenvia360.com.br/services");


        public ZenviaSms(String base64AuthorizationKey) {
            this.base64AuthorizationKey = base64AuthorizationKey;
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
         *
         * @param method the HTTP method
         * @param requestBody the HTTP request body
         * @return the response coming from ZenviaSms's API
         * @throws ZenviaHTTPSmsException when something goes wrong, i.e a non-200 OK response is answered
         */
        private String sendRequest(HttpMethod method, String requestBody, URI endpointZenvia) throws ZenviaHTTPSmsException {
            HttpEntity<String> request = new HttpEntity<String>(requestBody, buildHeaders());

            RestTemplate restTemplate = buildRestTemplate();

            ResponseEntity<String> response = restTemplate.exchange(endpointZenvia,method, request,String.class);

            if(response.getStatusCode() != HttpStatus.OK) { throw ZenviaHTTPExceptionFactory.buildException(response.getStatusCode().value(), response.getBody()); }

            return response.getBody();
        }

        private HttpHeaders buildHeaders() {
            if(this.base64AuthorizationKey == null) { throw new NullPointerException("Authorization key cannot be null"); }

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("Authorization", String.format("Basic %s", this.base64AuthorizationKey));

            return headers;
        }

        private RestTemplate buildRestTemplate() {

            RestTemplate restTemplate = new RestTemplate();
            ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(2000);
            return restTemplate;
        }

        //TODO: Add the methods to send sms and get status and sms
        /**
         * Sends a single sms using Zenvia SMS API
         *
         * @param sendSmsRequest a {@link SendSmsRequest} instance
         * @throws ZenviaHTTPSmsException
         * @throws ZenviaSmsUnexpectedAPIResponseException
         *
         * @see <a href="http://docs.zenviasms.apiary.io/">Zenvia Sms API Spec</a>
         */

        public void sendSingleSms(SendSmsRequest sendSmsRequest)
                throws ZenviaHTTPSmsException, ZenviaSmsUnexpectedAPIResponseException {

            String responseBody = sendRequest(HttpMethod.POST, sendSmsRequest.toJson(), zenviaSendSmsUrl());

            SendSmsResponse response = JsonFormatter.fromJSON(responseBody, SendSmsResponse.class);
        }

}
