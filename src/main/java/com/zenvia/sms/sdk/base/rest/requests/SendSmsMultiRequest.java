package com.zenvia.sms.sdk.base.rest.requests;

import com.zenvia.sms.sdk.base.models.ZenviaSmsModel;

import java.util.List;

public class SendSmsMultiRequest extends ZenviaSmsModel{
    /**
     *
     */
    private Integer aggregateId;

    /**
     *
     */
    private List<SendSmsRequestList> sendSmsRequestList;

    private SendSmsMultiRequest(SendSmsMultiRequestBuilder builder) {
        this.aggregateId = builder.aggregateId;
        this.sendSmsRequestList = builder.sendSmsRequestList;
    }

    public Integer getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(Integer aggregateId) {
        this.aggregateId = aggregateId;
    }

    public List<SendSmsRequestList> getSendSmsRequestList() {
        return sendSmsRequestList;
    }

    public void setSendSmsRequestList(List<SendSmsRequestList> sendSmsRequestList) {
        this.sendSmsRequestList = sendSmsRequestList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SendSmsMultiRequest that = (SendSmsMultiRequest) o;

        if (aggregateId != null ? !aggregateId.equals(that.aggregateId) : that.aggregateId != null) return false;
        return sendSmsRequestList.equals(that.sendSmsRequestList);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (aggregateId != null ? aggregateId.hashCode() : 0);
        result = 31 * result + sendSmsRequestList.hashCode();
        return result;
    }

    public static final class SendSmsMultiRequestBuilder {

        private Integer aggregateId;

        private List<SendSmsRequestList> sendSmsRequestList;

        public SendSmsMultiRequestBuilder(){

        }

        public SendSmsMultiRequestBuilder aggregateId(Integer aggregateId){
            this.aggregateId = aggregateId;
            return this;
        }

        public SendSmsMultiRequestBuilder sendSmsRequestList(List<SendSmsRequestList> sendSmsRequestList){
            this.sendSmsRequestList = sendSmsRequestList;
            return this;
        }

        public SendSmsMultiRequest build(){
            return new SendSmsMultiRequest(this);
        }
    }
}
