package com.zenvia.sms.sdk.base.rest.requests;

import com.zenvia.sms.sdk.base.models.ZenviaSmsModel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SendSmsMultiRequest extends ZenviaSmsModel{
    /**
     *
     */
    private Integer aggregateId;

    /**
     *
     */
    private List<SendSmsRequestList> sendSmsRequestList;
}
