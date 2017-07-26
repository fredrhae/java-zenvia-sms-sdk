package com.zenvia.sms.sdk.exceptions;

import com.google.gson.JsonObject;
import org.apache.commons.httpclient.HttpStatus;

public class ZenviaHTTPExceptionFactory {

    private static JsonObject responseBody;

    /**
     *
     * @param statusCode the HTTP status code answered by Zenvia's API.
     * @param responseBody the response body.
     * @return an exception corresponding to the HTTP status code.
     */
    public static ZenviaHTTPException buildException(int statusCode, JsonObject responseBody) {
        ZenviaHTTPExceptionFactory.responseBody = responseBody;
        switch(statusCode) {
            case HttpStatus.SC_BAD_REQUEST:
                return new ZenviaHTTPBadRequestException();
            case HttpStatus.SC_UNAUTHORIZED:
                return new ZenviaHTTPUnauthorizedException();
            case HttpStatus.SC_FORBIDDEN:
                return new ZenviaHTTPForbiddenException();
            case HttpStatus.SC_NOT_FOUND:
                return new ZenviaHTTPNotFoundException();
            case HttpStatus.SC_METHOD_NOT_ALLOWED:
                return new ZenviaHTTPMethodNotAllowedException();
            case HttpStatus.SC_UNPROCESSABLE_ENTITY:
                return new ZenviaHTTPUnprocessableEntityException();
            case HttpStatus.SC_INTERNAL_SERVER_ERROR:
                return new ZenviaHTTPInternalErrorException();
        }
        return null;
    }

    /**
     * HTTP 400 is answered when the client sent a bad request to Zenvia's API.
     */
    protected static class ZenviaHTTPBadRequestException extends ZenviaHTTPException {

        private static final long serialVersionUID = 7455960684176084815L;

        public ZenviaHTTPBadRequestException() {
            super("Your request is incorrect. Please review the parameters sent.", responseBody);
        }
    }

    /**
     * HTTP 401 is answered when Zenvia's API fails to authenticate the merchant.
     */
    protected static class ZenviaHTTPUnauthorizedException extends ZenviaHTTPException {

        private static final long serialVersionUID = 5742990822189252699L;

        public ZenviaHTTPUnauthorizedException() {
            super("Invalid API Key", responseBody);
        }
    }

    /**
     * HTTP 403 is answered when the merchant is not authorized to use Zenvia's API.
     */
    protected static class ZenviaHTTPForbiddenException extends ZenviaHTTPException {

        private static final long serialVersionUID = 5528474291801124461L;

        public ZenviaHTTPForbiddenException() {
            super("There are problems with your account. Please contact our support team.", responseBody);
        }
    }

    /**
     * HTTP 404 is answered when the resource is not found by Zenvia's API.
     */
    protected static class ZenviaHTTPNotFoundException extends ZenviaHTTPException{

        private static final long serialVersionUID = -4517266961113978929L;

        public ZenviaHTTPNotFoundException() {
            super("The requested resource could not be found.", responseBody);
        }
    }

    /**
     * HTTP 405 is answered when the HTTP method is not allowed by Zenvia's API.
     */
    protected static class ZenviaHTTPMethodNotAllowedException extends ZenviaHTTPException {

        private static final long serialVersionUID = -5251448135799279299L;

        public ZenviaHTTPMethodNotAllowedException() {
            super("Sorry, we don't accept this HTTP method.", responseBody);
        }
    }

    /**
     * HTTP 422 is RFU
     */
    protected static class ZenviaHTTPUnprocessableEntityException extends ZenviaHTTPException {

        private static final long serialVersionUID = -5191769265138551575L;

        public ZenviaHTTPUnprocessableEntityException() {
            super("Unprocessable entity", responseBody);
        }
    }

    /**
     * HTTP 500 is answered when an internal error happens at Zenvia's API.
     */
    protected static class ZenviaHTTPInternalErrorException extends ZenviaHTTPException {

        private static final long serialVersionUID = -2378137230739295387L;

        public ZenviaHTTPInternalErrorException() {
            super("Oh oh...something wrong happened at our servers. Please contact our support team.", responseBody);
        }
    }
}
