package com.zenvia.sms.sdk.api;

import com.zenvia.sms.sdk.base.CallbackOption;
import com.zenvia.sms.sdk.base.models.SmsStatusCode;
import com.zenvia.sms.sdk.base.requests.SendSmsRequest;
import com.zenvia.sms.sdk.base.responses.SendSmsResponse;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class ZenviaSmsTest {
    private final String AUTH_KEY = "authkey";
    private final String PHONE_NUMBER = "55519999999";
    private final String ENDPOINT = "https://api-rest.zenvia360.com.br/services";

    private ZenviaSms zenviaSms;
    @Before
    public void setUp() throws Exception {
        zenviaSms = new ZenviaSms(AUTH_KEY);
    }

    @Test
    public void setEndpoint() throws Exception {
        zenviaSms.setEndpoint(new URI("https://api-rest.zenvia360.com.br/services/test"));
        assertEquals(this.ENDPOINT + "/test", zenviaSms.getEndpoint().toString());
        zenviaSms.setEndpoint(new URI(ENDPOINT));
    }

    @Test
    public void setBasicAuthorizationKey() throws Exception {
        zenviaSms.setBasicAuthorizationKey(this.AUTH_KEY);
        assertEquals(this.AUTH_KEY, zenviaSms.getBasicAuthorizationKey());
    }

    @Test
    public void zenviaSendSmsUrl() throws Exception {
        assertEquals("https://api-rest.zenvia360.com.br/services/send-sms",
                zenviaSms.zenviaSendSmsUrl().toString());
    }

    @Test
    public void zenviaSendSmsMultipleUrl() throws Exception {
        assertEquals("https://api-rest.zenvia360.com.br/services/send-sms-multiple",
                zenviaSms.zenviaSendSmsMultipleUrl().toString());
    }

    @Test
    public void zenviaGetSmsStatusUrl() throws Exception {
        assertEquals("https://api-rest.zenvia360.com.br/services/get-sms-status/test-id",
                zenviaSms.zenviaGetSmsStatusUrl("test-id").toString());
    }

    @Test
    public void zenviaListReceivedSmsUrl() throws Exception {
        assertEquals("https://api-rest.zenvia360.com.br/services/received/list",
                zenviaSms.zenviaListReceivedSmsUrl().toString());
    }

    @Test
    public void zenviaListReceivedSmsByPeriodUrl() throws Exception {
        assertEquals("https://api-rest.zenvia360.com.br/services/received/search/test-date/test-date",
                zenviaSms.zenviaListReceivedSmsByPeriodUrl("test-date","test-date").toString());
    }

    @Test
    public void zenviaCancelSmsUrl() throws Exception {
        assertEquals("https://api-rest.zenvia360.com.br/services/cancel-sms/test-id",
                zenviaSms.zenviaCancelSmsUrl("test-id").toString());
    }

    @Test
    public void sendSingleSms() throws Exception {
        SendSmsRequest smsRequest = SendSmsRequest.builder()
                                        .from("Frederico Leal")
                                        .to(PHONE_NUMBER)
                                        .msg("Teste de envio!")
                                        .id("test-" + new Random().nextInt(100))
                                        .callbackOption(CallbackOption.NONE)
                                        .build();

        SendSmsResponse response = zenviaSms.sendSingleSms(smsRequest);
        assertEquals(SmsStatusCode.OK, response.getStatusCode());
    }

}