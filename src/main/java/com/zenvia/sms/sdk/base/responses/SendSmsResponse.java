package com.zenvia.sms.sdk.base.responses;

import com.zenvia.sms.sdk.base.rest.ZenviaSmsModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SendSmsResponse extends ZenviaSmsModel{

    /**
     *
     */
    private Integer statusCode;

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
