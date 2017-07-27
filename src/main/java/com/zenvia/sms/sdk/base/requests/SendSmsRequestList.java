package com.zenvia.sms.sdk.base.requests;

import com.zenvia.sms.sdk.base.CallbackOption;
import com.zenvia.sms.sdk.base.rest.ZenviaSmsModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
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
    private String schedule;

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
}
