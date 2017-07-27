package com.zenvia.sms.sdk.base.requests;

import com.zenvia.sms.sdk.base.rest.ZenviaSmsModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SendSmsMultiRequest extends ZenviaSmsModel{
    /**
     *
     */
    private List<SendSmsRequestList> sendSmsRequestList;

    /**
     *
     */
    private Integer aggregateId;
}
