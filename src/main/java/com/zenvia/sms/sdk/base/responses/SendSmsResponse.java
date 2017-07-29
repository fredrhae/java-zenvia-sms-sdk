package com.zenvia.sms.sdk.base.responses;

import com.zenvia.sms.sdk.base.models.SmsStatusCode;
import com.zenvia.sms.sdk.base.rest.ZenviaSmsModel;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SendSmsResponse extends ZenviaSmsModel{

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
