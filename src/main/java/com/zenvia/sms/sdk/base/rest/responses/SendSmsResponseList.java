package com.zenvia.sms.sdk.base.rest.responses;

import com.zenvia.sms.sdk.base.models.ZenviaSmsModel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;


@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SendSmsResponseList extends ZenviaSmsModel{

    /**
     *
     */
    private List<SmsResponse> sendSmsResponseList;

    private SendSmsResponseList(SendSmsResponseListBuilder builder){
        this.sendSmsResponseList = builder.sendSmsResponseList;
    }

    public List<SmsResponse> getSendSmsResponseList() {
        return sendSmsResponseList;
    }

    public void setSendSmsResponseList(List<SmsResponse> sendSmsResponseList) {
        this.sendSmsResponseList = sendSmsResponseList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SendSmsResponseList that = (SendSmsResponseList) o;

        return sendSmsResponseList.equals(that.sendSmsResponseList);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + sendSmsResponseList.hashCode();
        return result;
    }

    private final static class SendSmsResponseListBuilder{
        private List<SmsResponse> sendSmsResponseList;

        public SendSmsResponseListBuilder(){

        }

        public SendSmsResponseListBuilder sendSmsResponseList(List<SmsResponse> smsResponseList){
            this.sendSmsResponseList = smsResponseList;
            return this;
        }

        public SendSmsResponseList build() {
            return new SendSmsResponseList(this);
        }
    }

}
