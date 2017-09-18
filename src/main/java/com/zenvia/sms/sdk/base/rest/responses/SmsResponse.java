package com.zenvia.sms.sdk.base.rest.responses;

import com.zenvia.sms.sdk.base.models.SmsStatusCode;
import com.zenvia.sms.sdk.base.models.ZenviaSmsModel;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
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

}
