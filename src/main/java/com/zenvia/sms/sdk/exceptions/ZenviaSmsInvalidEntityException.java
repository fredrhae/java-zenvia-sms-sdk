package com.zenvia.sms.sdk.exceptions;

import com.zenvia.sms.sdk.base.rest.ZenviaSmsModel;

public class ZenviaSmsInvalidEntityException extends ZenviaSmsException {

    private ZenviaSmsModel entity;

    public ZenviaSmsInvalidEntityException(ZenviaSmsModel entity) {
        this.entity = entity;
    }

    /**
     *
     * @return A message informing the invalid entity and reason.
     */
    @Override
    public String getMessage() {
        return String.format("%s is invalid: %s", this.entity, this.entity.getErrors());
    }
}
