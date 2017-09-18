package com.zenvia.sms.sdk.base.rest.responses;

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
public class ReceivedMessage extends ZenviaSmsModel{
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private DateTime dateReceived;

    /**
     *
     */
    private String mobile;

    /**
     *
     */
    private String body;

    /**
     *
     */
    private String  shortcode;

    /**
     *
     */
    private String mobileOperatorName;

    /**
     *
     */
    private String mtId;
}
