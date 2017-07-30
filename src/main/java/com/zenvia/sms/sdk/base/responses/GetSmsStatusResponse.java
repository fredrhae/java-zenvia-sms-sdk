package com.zenvia.sms.sdk.base.responses;

import com.zenvia.sms.sdk.base.models.SmsStatusCode;
import com.zenvia.sms.sdk.base.models.ZenviaSmsModel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class GetSmsStatusResponse extends ZenviaSmsModel{
    /**
     *
     */
    private String id;

    /**
     *
     */
    private DateTime received;

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
}
