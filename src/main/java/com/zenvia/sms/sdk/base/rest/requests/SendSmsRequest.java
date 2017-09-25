package com.zenvia.sms.sdk.base.rest.requests;

import com.zenvia.sms.sdk.base.models.CallbackOption;
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
public class SendSmsRequest extends ZenviaSmsModel{
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

    /**
     *
     */
    private Integer aggregateId;
}
