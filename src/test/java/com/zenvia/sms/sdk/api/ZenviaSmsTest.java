package com.zenvia.sms.sdk.api;

import com.zenvia.sms.sdk.base.CallbackOption;
import com.zenvia.sms.sdk.base.models.SmsStatusCode;
import com.zenvia.sms.sdk.base.requests.SendSmsRequest;
import com.zenvia.sms.sdk.base.responses.SendSmsResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class ZenviaSmsTest {
    private final String AUTH_KEY = "base64encodingkey";
    private final String PHONE_NUMBER = "555199999999";

    private ZenviaSms zenviaSms;
    @Before
    public void setUp() throws Exception {
        zenviaSms = new ZenviaSms(AUTH_KEY);
    }

    @Test
    public void setEndpoint() throws Exception {
    }

    @Test
    public void setBasicAuthorizationKey() throws Exception {
    }

    @Test
    public void zenviaSendSmsUrl() throws Exception {
    }

    @Test
    public void zenviaSendSmsMultipleUrl() throws Exception {
    }

    @Test
    public void zenviaGetSmsStatusUrl() throws Exception {
    }

    @Test
    public void zenviaListReceivedSmsUrl() throws Exception {
    }

    @Test
    public void zenviaListReceivedSmsByPeriodUrl() throws Exception {
    }

    @Test
    public void zenviaCancelSmsUrl() throws Exception {
    }

    @Test
    public void sendSingleSms() throws Exception {
        SendSmsRequest smsRequest = SendSmsRequest.builder()
                                        .from("Sdk test")
                                        .to(PHONE_NUMBER)
                                        .msg("Teste de envio!")
                                        .id("test-" + new Random().nextInt(100))
                                        .callbackOption(CallbackOption.NONE)
                                        .build();

        SendSmsResponse response = zenviaSms.sendSingleSms(smsRequest);
        assertEquals(response.getStatusCode(), SmsStatusCode.OK);
    }

}