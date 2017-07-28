package com.zenvia.sms.sdk.exceptions;

import com.google.gson.JsonObject;
import org.apache.http.HttpStatus;

public class ZenviaHTTPExceptionFactory {

    private static JsonObject responseBody;

    /**
     *
     * @param statusCode the HTTP status code answered by Zenvia's API.
     * @param responseBody the response body.
     * @return an exception corresponding to the HTTP status code.
     */
    public static ZenviaHTTPSmsException buildException(int statusCode, JsonObject responseBody) {
        ZenviaHTTPExceptionFactory.responseBody = responseBody;
        switch(statusCode) {
            case HttpStatus.SC_BAD_REQUEST:
                return new ZenviaHTTPBadRequestSmsException();
            case HttpStatus.SC_UNAUTHORIZED:
                return new ZenviaHTTPUnauthorizedSmsException();
            case HttpStatus.SC_FORBIDDEN:
                return new ZenviaHTTPForbiddenSmsException();
            case HttpStatus.SC_NOT_FOUND:
                return new ZenviaHTTPNotFoundSmsException();
            case HttpStatus.SC_METHOD_NOT_ALLOWED:
                return new ZenviaHTTPMethodNotAllowedSmsException();
            case HttpStatus.SC_UNPROCESSABLE_ENTITY:
                return new ZenviaHTTPUnprocessableEntitySmsException();
            case HttpStatus.SC_INTERNAL_SERVER_ERROR:
                return new ZenviaHTTPInternalErrorSmsException();
        }
        return null;
    }

    /**
     * HTTP 400 is answered when the client sent a bad request to Zenvia's API.
     */
    protected static class ZenviaHTTPBadRequestSmsException extends ZenviaHTTPSmsException {

        private static final long serialVersionUID = 7455960684176084815L;

        public ZenviaHTTPBadRequestSmsException() {
            super("Your request is incorrect. Please review the parameters sent.", responseBody);
        }
    }

    /**
     * HTTP 401 is answered when Zenvia's API fails to authenticate the merchant.
     */
    protected static class ZenviaHTTPUnauthorizedSmsException extends ZenviaHTTPSmsException {

        private static final long serialVersionUID = 5742990822189252699L;

        public ZenviaHTTPUnauthorizedSmsException() {
            super("Invalid API Key", responseBody);
        }
    }

    /**
     * HTTP 403 is answered when the merchant is not authorized to use Zenvia's API.
     */
    protected static class ZenviaHTTPForbiddenSmsException extends ZenviaHTTPSmsException {

        private static final long serialVersionUID = 5528474291801124461L;

        public ZenviaHTTPForbiddenSmsException() {
            super("There are problems with your account. Please contact our support team.", responseBody);
        }
    }

    /**
     * HTTP 404 is answered when the resource is not found by Zenvia's API.
     */
    protected static class ZenviaHTTPNotFoundSmsException extends ZenviaHTTPSmsException {

        private static final long serialVersionUID = -4517266961113978929L;

        public ZenviaHTTPNotFoundSmsException() {
            super("The requested resource could not be found.", responseBody);
        }
    }

    /**
     * HTTP 405 is answered when the HTTP method is not allowed by Zenvia's API.
     */
    protected static class ZenviaHTTPMethodNotAllowedSmsException extends ZenviaHTTPSmsException {

        private static final long serialVersionUID = -5251448135799279299L;

        public ZenviaHTTPMethodNotAllowedSmsException() {
            super("Sorry, we don't accept this HTTP method.", responseBody);
        }
    }

    /**
     * HTTP 422 is RFU
     */
    protected static class ZenviaHTTPUnprocessableEntitySmsException extends ZenviaHTTPSmsException {

        private static final long serialVersionUID = -5191769265138551575L;

        public ZenviaHTTPUnprocessableEntitySmsException() {
            super("Unprocessable entity", responseBody);
        }
    }

    /**
     * HTTP 500 is answered when an internal error happens at Zenvia's API.
     */
    protected static class ZenviaHTTPInternalErrorSmsException extends ZenviaHTTPSmsException {

        private static final long serialVersionUID = -2378137230739295387L;

        public ZenviaHTTPInternalErrorSmsException() {
            super("Oh oh...something wrong happened at our servers. Please contact our support team.", responseBody);
        }
    }
}
