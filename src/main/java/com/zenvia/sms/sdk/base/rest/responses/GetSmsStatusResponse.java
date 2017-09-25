package com.zenvia.sms.sdk.base.rest.responses;

import com.zenvia.sms.sdk.base.models.ZenviaSmsModel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.util.Date;

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
}
