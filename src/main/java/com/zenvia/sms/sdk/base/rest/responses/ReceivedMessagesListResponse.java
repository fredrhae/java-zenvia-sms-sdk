package com.zenvia.sms.sdk.base.rest.responses;

import com.zenvia.sms.sdk.base.models.ZenviaSmsModel;

import java.util.List;

public class ReceivedMessagesListResponse extends ZenviaSmsModel{

    /**
     *
     */
    private SmsResponse smsResponse;

    /**
     *
     */
    private List<ReceivedMessage> receivedMessages;

    private ReceivedMessagesListResponse(ReceivedMessagesListResponseBuilder builder){
        this.smsResponse = builder.smsResponse;
        this.receivedMessages = builder.receivedMessages;
    }

    public SmsResponse getSmsResponse() {
        return smsResponse;
    }

    public void setSmsResponse(SmsResponse smsResponse) {
        this.smsResponse = smsResponse;
    }

    public List<ReceivedMessage> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<ReceivedMessage> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ReceivedMessagesListResponse that = (ReceivedMessagesListResponse) o;

        if (smsResponse != null ? !smsResponse.equals(that.smsResponse) : that.smsResponse != null) return false;
        return receivedMessages != null ? receivedMessages.equals(that.receivedMessages) : that.receivedMessages == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (smsResponse != null ? smsResponse.hashCode() : 0);
        result = 31 * result + (receivedMessages != null ? receivedMessages.hashCode() : 0);
        return result;
    }

    public static final class ReceivedMessagesListResponseBuilder {
        private SmsResponse smsResponse;

        private List<ReceivedMessage> receivedMessages;

        public ReceivedMessagesListResponseBuilder(){

        }

        public ReceivedMessagesListResponseBuilder smsResponse(SmsResponse smsResponse){
            this.smsResponse = smsResponse;
            return this;
        }

        public ReceivedMessagesListResponseBuilder receivedMessages(List<ReceivedMessage> receivedMessages){
            this.receivedMessages = receivedMessages;
            return this;
        }

        public ReceivedMessagesListResponse build(){
            return new ReceivedMessagesListResponse(this);
        }
    }
}
