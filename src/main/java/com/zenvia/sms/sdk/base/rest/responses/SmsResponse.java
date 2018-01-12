package com.zenvia.sms.sdk.base.rest.responses;

import com.zenvia.sms.sdk.base.models.SmsStatusCode;
import com.zenvia.sms.sdk.base.models.ZenviaSmsModel;

import static com.zenvia.sms.sdk.utils.Constants.HASH_VALUE_BASE;

public class SmsResponse extends ZenviaSmsModel{

    /**
     *
     */
    private SmsStatusCode statusCode;

    /**
     *
     */
    private String statusDescription;

    /**
     *
     */
    private Integer detailCode;

    /**
     *
     */
    private String detailDescription;

    private SmsResponse(SmsResponseBuilder builder){
        this.statusCode = builder.statusCode;
        this.statusDescription = builder.statusDescription;
        this.detailCode = builder.detailCode;
        this.detailDescription = builder.detailDescription;
    }

    public SmsStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(SmsStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Integer getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(Integer detailCode) {
        this.detailCode = detailCode;
    }

    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SmsResponse that = (SmsResponse) o;

        if (statusCode != that.statusCode) return false;
        if (statusDescription != null ? !statusDescription.equals(that.statusDescription) : that.statusDescription != null)
            return false;
        if (detailCode != null ? !detailCode.equals(that.detailCode) : that.detailCode != null) return false;
        return detailDescription != null ? detailDescription.equals(that.detailDescription) : that.detailDescription == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = HASH_VALUE_BASE * result + (statusDescription != null ? statusDescription.hashCode() : 0);
        result = HASH_VALUE_BASE * result + (statusCode != null ? statusCode.hashCode() : 0);
        result = HASH_VALUE_BASE * result + (detailCode != null ? detailCode.hashCode() : 0);
        result = HASH_VALUE_BASE * result + (detailDescription != null ? detailDescription.hashCode() : 0);
        return result;
    }

    public final static class SmsResponseBuilder{
        private SmsStatusCode statusCode;

        private String statusDescription;

        private Integer detailCode;

        private String detailDescription;

        public SmsResponseBuilder(){

        }

        public SmsResponseBuilder statusCode(SmsStatusCode statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public SmsResponseBuilder statusDescription(String statusDescription){
            this.statusDescription = statusDescription;
            return this;
        }

        public SmsResponseBuilder detailCode(Integer detailCode){
            this.detailCode = detailCode;
            return this;
        }

        public SmsResponseBuilder detailDescription(String detailDescription){
            this.detailDescription = detailDescription;
            return this;
        }

        public SmsResponse build(){
            return new SmsResponse(this);
        }
    }
}
