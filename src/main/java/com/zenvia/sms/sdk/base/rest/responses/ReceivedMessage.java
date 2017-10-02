package com.zenvia.sms.sdk.base.rest.responses;

import com.zenvia.sms.sdk.base.models.ZenviaSmsModel;

import java.util.Date;

public class ReceivedMessage extends ZenviaSmsModel{
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private Date dateReceived;

    /**
     *
     */
    private String mobile;

    /**
     *
     */
    private String body;

    /**
     *
     */
    private String  shortcode;

    /**
     *
     */
    private String mobileOperatorName;

    /**
     *
     */
    private String mtId;


    private ReceivedMessage(ReceivedMessageBuilder builder){
        this.id = builder.id;
        this.dateReceived = builder.dateReceived;
        this.mobile = builder.mobile;
        this.body = builder.body;
        this.shortcode = builder.shortcode;
        this.mobileOperatorName = builder.mobileOperatorName;
        this.mtId = builder.mtId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public String getMtId() {
        return mtId;
    }

    public void setMtId(String mtId) {
        this.mtId = mtId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ReceivedMessage that = (ReceivedMessage) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (dateReceived != null ? !dateReceived.equals(that.dateReceived) : that.dateReceived != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (body != null ? !body.equals(that.body) : that.body != null) return false;
        if (shortcode != null ? !shortcode.equals(that.shortcode) : that.shortcode != null) return false;
        if (mobileOperatorName != null ? !mobileOperatorName.equals(that.mobileOperatorName) : that.mobileOperatorName != null)
            return false;
        return mtId != null ? mtId.equals(that.mtId) : that.mtId == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (dateReceived != null ? dateReceived.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (shortcode != null ? shortcode.hashCode() : 0);
        result = 31 * result + (mobileOperatorName != null ? mobileOperatorName.hashCode() : 0);
        result = 31 * result + (mtId != null ? mtId.hashCode() : 0);
        return result;
    }

    private static final class ReceivedMessageBuilder{
        private Long id;

        private Date dateReceived;

        private String mobile;

        private String body;

        private String  shortcode;

        private String mobileOperatorName;

        private String mtId;

        public ReceivedMessageBuilder(){

        }

        public ReceivedMessageBuilder id(Long id){
            this.id = id;
            return this;
        }

        public ReceivedMessageBuilder dateReceived(Date dateReceived){
            this.dateReceived = dateReceived;
            return this;
        }

        public ReceivedMessageBuilder mobile(String mobile){
            this.mobile = mobile;
            return this;
        }

        public ReceivedMessageBuilder body(String body){
            this.body = body;
            return this;
        }

        public ReceivedMessageBuilder shortCode(String shortCode){
            this.shortcode = shortCode;
            return this;
        }

        public ReceivedMessageBuilder mobileOperatorName(String mobileOperatorName){
            this.mobileOperatorName = mobileOperatorName;
            return this;
        }

        public ReceivedMessageBuilder mtId(String mtId){
            this.mtId = mtId;
            return this;
        }

        public ReceivedMessage build(){
            return new ReceivedMessage(this);
        }
    }
}
