package com.zenvia.sms.sdk.base.rest.requests;

import com.zenvia.sms.sdk.base.models.CallbackOption;
import com.zenvia.sms.sdk.base.models.ZenviaSmsModel;

import java.util.Date;

public class SendSmsRequestList extends ZenviaSmsModel{
    /**
     *
     */
    private String from;

    /**
     *
     */
    private String to;

    /**
     *
     */
    private Date schedule;

    /**
     *
     */
    private String msg;

    /**
     *
     */
    private CallbackOption callbackOption;

    /**
     *
     */
    private String id;

    private SendSmsRequestList(SendSmsRequestListBuilder builder) {
        this.from = builder.from;
        this.to = builder.to;
        this.schedule = builder.schedule;
        this.msg = builder.msg;
        this.callbackOption = builder.callbackOption;
        this.id = builder.id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getSchedule() {
        return schedule;
    }

    public void setSchedule(Date schedule) {
        this.schedule = schedule;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CallbackOption getCallbackOption() {
        return callbackOption;
    }

    public void setCallbackOption(CallbackOption callbackOption) {
        this.callbackOption = callbackOption;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SendSmsRequestList that = (SendSmsRequestList) o;

        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        if (!to.equals(that.to)) return false;
        if (schedule != null ? !schedule.equals(that.schedule) : that.schedule != null) return false;
        if (!msg.equals(that.msg)) return false;
        if (callbackOption != that.callbackOption) return false;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + to.hashCode();
        result = 31 * result + (schedule != null ? schedule.hashCode() : 0);
        result = 31 * result + msg.hashCode();
        result = 31 * result + (callbackOption != null ? callbackOption.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    public static final class SendSmsRequestListBuilder {

        private String from;

        private String to;

        private Date schedule;

        private String msg;

        private CallbackOption callbackOption;

        private String id;

        public SendSmsRequestListBuilder(String to, String msg) {
            this.to = to;
            this.msg = msg;
        }

        public SendSmsRequestListBuilder from(String from) {
            this.from = from;
            return this;
        }

        public SendSmsRequestListBuilder to(String to){
            this.to = to;
            return this;
        }

        public SendSmsRequestListBuilder schedule(Date schedule) {
            this.schedule = schedule;
            return this;
        }

        public SendSmsRequestListBuilder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public SendSmsRequestListBuilder callbackOption(CallbackOption callbackOption) {
            this.callbackOption = callbackOption;
            return this;
        }

        public SendSmsRequestListBuilder id(String id) {
            this.id = id;
            return this;
        }

        public SendSmsRequestList build(){
            return new SendSmsRequestList(this);
        }
    }
}
