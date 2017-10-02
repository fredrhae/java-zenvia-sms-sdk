package com.zenvia.sms.sdk.api;

import com.zenvia.sms.sdk.base.models.CallbackOption;
import com.zenvia.sms.sdk.base.models.SmsStatusCode;
import com.zenvia.sms.sdk.base.rest.requests.SendSmsMultiRequest;
import com.zenvia.sms.sdk.base.rest.requests.SendSmsRequest;
import com.zenvia.sms.sdk.base.rest.requests.SendSmsRequestList;
import com.zenvia.sms.sdk.base.rest.responses.GetSmsStatusResponse;
import com.zenvia.sms.sdk.base.rest.responses.ReceivedMessagesListResponse;
import com.zenvia.sms.sdk.base.rest.responses.SendSmsResponseList;
import com.zenvia.sms.sdk.base.rest.responses.SmsResponse;
import com.zenvia.sms.sdk.exceptions.ZenviaHTTPSmsException;
import com.zenvia.sms.sdk.exceptions.ZenviaSmsInvalidEntityException;
import com.zenvia.sms.sdk.exceptions.ZenviaSmsUnexpectedAPIResponseException;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class ZenviaSmsTest {
    private final String AUTH_KEY =  "your_authorization_key";
    private final String PHONE_NUMBER = "5551999999999";
    private final String PHONE_NUMBER_2 = "5551999999999";
    private final String PHONE_NUMBER_3 = "5551999999999";
    private final String[] PHONE_NUMBERS = new String[]{PHONE_NUMBER, PHONE_NUMBER_2, PHONE_NUMBER_3};
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
    public void sendSingleSmsTest() throws Exception {
        SmsResponse response = sendSingleSms("Test from single sms",getRandomId());
        assertEquals(SmsStatusCode.OK, response.getStatusCode());
    }

    @Test
    public void sendSingleSmsTestScheduled() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 30);

        String smsId = getRandomId();

        SmsResponse response = sendSingleScheduledSms("Test of scheduled sms", smsId, calendar.getTime());
        assertEquals(SmsStatusCode.OK, response.getStatusCode());

        GetSmsStatusResponse statusSms = zenviaSms.getStatusFromSms(smsId);
        assertEquals(SmsStatusCode.SCHEDULED, statusSms.getSmsResponse().getStatusCode());
        assertEquals("Scheduled", statusSms.getSmsResponse().getStatusDescription());
        assertEquals(smsId, statusSms.getId());
        assertEquals((Integer)100, statusSms.getSmsResponse().getDetailCode());
        assertEquals("Message Queued", statusSms.getSmsResponse().getDetailDescription());
    }

    @Test
    public void sendMultipleSmsTest() throws Exception {
        List<SendSmsRequestList> multipleSms = new ArrayList<>();

        for(int i = 0; i < 3; i ++){
            SendSmsRequestList currentSmsRequestList = new SendSmsRequestList.
                                    SendSmsRequestListBuilder(PHONE_NUMBERS[i],"Teste of multiple sends!")
                                                        .from("Frederico Leal")
                                                        .id(getRandomId())
                                                        .build();

            multipleSms.add(currentSmsRequestList);
        }

        SendSmsMultiRequest multipleSmsRequest = new SendSmsMultiRequest.SendSmsMultiRequestBuilder()
                                                    .sendSmsRequestList(multipleSms)
                                                    .build();

        SendSmsResponseList responseList = zenviaSms.sendMultipleSms(multipleSmsRequest);

        for(SmsResponse currentSmsResponse : responseList.getSendSmsResponseList()) {
            assertEquals(SmsStatusCode.OK, currentSmsResponse.getStatusCode());
        }
    }

    @Test
    public void getStatusFromSmsTest() throws Exception {
        String smsId = getRandomId();
        SmsResponse responseFromSend = sendSingleSms("Test from get status function", smsId);

        assertEquals(SmsStatusCode.OK, responseFromSend.getStatusCode());

        GetSmsStatusResponse response = zenviaSms.getStatusFromSms(smsId);
        assertEquals(SmsStatusCode.DELIVERED, response.getSmsResponse().getStatusCode());
        assertEquals(smsId, response.getId());
        assertEquals("Sent", response.getSmsResponse().getStatusDescription());
        assertEquals((Integer)110, response.getSmsResponse().getDetailCode());
    }

    @Test
    public void listReceivedMessagesTest() throws Exception{
        ReceivedMessagesListResponse response = zenviaSms.listReceivedMessages();
        assertEquals(SmsStatusCode.OK, response.getSmsResponse().getStatusCode());
    }

    @Test
    public void listReceivedMessagesTestWithNewMessages() throws Exception{

        for(int i = 0; i < 3; i ++){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND,i*10);

            String smsId = getRandomId();

            SmsResponse responseSendSms = sendSingleScheduledSms("Test of received messages list, scheduling message " + i,
                    smsId, calendar.getTime());
            assertEquals(SmsStatusCode.OK, responseSendSms.getStatusCode());
        }

        ReceivedMessagesListResponse response = zenviaSms.listReceivedMessages();
        assertEquals(SmsStatusCode.OK, response.getSmsResponse().getStatusCode());
    }

    @Test
    public void listReceivedMessagesTestWithNewMessagesByPeriod() throws Exception{

        for(int i = 0; i < 5; i ++){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND,i*10);

            String smsId = getRandomId();

            SmsResponse responseSendSms = sendSingleScheduledSms("Test of received messages list, scheduling message " + i,
                    smsId, calendar.getTime());
            assertEquals(SmsStatusCode.OK, responseSendSms.getStatusCode());
        }
        Calendar calendarStartDate = Calendar.getInstance();
        calendarStartDate.add(Calendar.DAY_OF_MONTH,-2);
        Calendar calendarEndDate = Calendar.getInstance();
        calendarEndDate.add(Calendar.DAY_OF_MONTH,1);

        Date startDate = calendarStartDate.getTime();
        Date endDate = calendarEndDate.getTime();

        ReceivedMessagesListResponse response = zenviaSms.listReceivedMessagesByPeriod(startDate,endDate);
        assertEquals(SmsStatusCode.OK, response.getSmsResponse().getStatusCode());
    }

    @Test
    public void cancelScheduledSmsTest() throws Exception{

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,1);

        String smsId = getRandomId();

        SmsResponse responseSendSms = sendSingleScheduledSms("Test of cancel sms, scheduling message", smsId, calendar.getTime());
        assertEquals(SmsStatusCode.OK, responseSendSms.getStatusCode());

        SmsResponse response = zenviaSms.cancelScheduledSms(smsId);
        assertEquals(SmsStatusCode.BLOCKED, response.getStatusCode());
        assertEquals(2, response.getDetailCode().intValue());
    }

    private SmsResponse sendSingleSms(String message, String id) throws ZenviaHTTPSmsException, ZenviaSmsUnexpectedAPIResponseException, ZenviaSmsInvalidEntityException {
        SendSmsRequest smsRequest = new  SendSmsRequest.SendSmsRequestBuilder(PHONE_NUMBER, message)
                .from("Frederico Leal")
                .id(id)
                .callbackOption(CallbackOption.NONE)
                .build();

        return zenviaSms.sendSingleSms(smsRequest);
    }

    private SmsResponse sendSingleScheduledSms(String message, String id, Date scheduled) throws ZenviaHTTPSmsException, ZenviaSmsUnexpectedAPIResponseException, ZenviaSmsInvalidEntityException {
        SendSmsRequest smsRequest = new SendSmsRequest.SendSmsRequestBuilder(PHONE_NUMBER, message)
                .from("Frederico Leal")
                .id(id)
                .schedule(scheduled)
                .callbackOption(CallbackOption.NONE)
                .build();

        return zenviaSms.sendSingleSms(smsRequest);
    }

    private String getRandomId() {
        return "test-" + new Random().nextInt(100);
    }

}