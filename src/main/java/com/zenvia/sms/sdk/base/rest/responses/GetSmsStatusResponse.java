package com.zenvia.sms.sdk.base.rest.responses;

import com.zenvia.sms.sdk.base.models.ZenviaSmsModel;

import java.util.Date;

public class GetSmsStatusResponse extends ZenviaSmsModel{
    /**
     *
     */
    private String id;

    /**
     *
     */
    private Date received;

    /**
     *
     */
    private String shortcode;

    /**
     *
     */
    private String mobileOperatorName;


    /**
     *
     */
    private SmsResponse smsResponse;

    private GetSmsStatusResponse(GetSmsStatusResponseBuilder builder){
        this.id = builder.id;
        this.received = builder.received;
        this.shortcode = builder.shortcode;
        this.mobileOperatorName = builder.mobileOperatorName;
        this.smsResponse = builder.smsResponse;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getReceived() {
        return received;
    }

    public void setReceived(Date received) {
        this.received = received;
    }

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    public String getMobileOperatorName() {
        return mobileOperatorName;
    }

    public void setMobileOperatorName(String mobileOperatorName) {
        this.mobileOperatorName = mobileOperatorName;
    }

    public SmsResponse getSmsResponse() {
        return smsResponse;
    }

    public void setSmsResponse(SmsResponse smsResponse) {
        this.smsResponse = smsResponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        GetSmsStatusResponse that = (GetSmsStatusResponse) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (received != null ? !received.equals(that.received) : that.received != null) return false;
        if (shortcode != null ? !shortcode.equals(that.shortcode) : that.shortcode != null) return false;
        if (mobileOperatorName != null ? !mobileOperatorName.equals(that.mobileOperatorName) : that.mobileOperatorName != null)
            return false;
        return smsResponse != null ? smsResponse.equals(that.smsResponse) : that.smsResponse == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (received != null ? received.hashCode() : 0);
        result = 31 * result + (shortcode != null ? shortcode.hashCode() : 0);
        result = 31 * result + (mobileOperatorName != null ? mobileOperatorName.hashCode() : 0);
        result = 31 * result + (smsResponse != null ? smsResponse.hashCode() : 0);
        return result;
    }

    public static final class GetSmsStatusResponseBuilder{

        private String id;

        private Date received;

        private String shortcode;

        private String mobileOperatorName;

        private SmsResponse smsResponse;

        public GetSmsStatusResponseBuilder(){

        }

        public GetSmsStatusResponseBuilder id(String id){
            this.id = id;
            return this;
        }

        public GetSmsStatusResponseBuilder received(Date received){
            this.received = received;
            return this;
        }

        public GetSmsStatusResponseBuilder shortCode(String shortCode){
            this.shortcode = shortCode;
            return this;
        }

        public GetSmsStatusResponseBuilder mobileOperatorName(String mobileOperatorName){
            this.mobileOperatorName = mobileOperatorName;
            return this;
        }

        public GetSmsStatusResponseBuilder smsResponse(SmsResponse smsResponse){
            this.smsResponse = smsResponse;
            return this;
        }

        public GetSmsStatusResponse build(){
            return new GetSmsStatusResponse(this);
        }
    }
}
