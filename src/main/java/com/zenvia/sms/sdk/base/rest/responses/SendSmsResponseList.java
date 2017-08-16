package com.zenvia.sms.sdk.base.rest.responses;

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
public class SendSmsResponseList extends ZenviaSmsModel{

    /**
     *
     */
    List<SmsResponse> sendSmsResponseList;
}
