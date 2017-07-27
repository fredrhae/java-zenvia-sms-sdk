package com.zenvia.sms.sdk.base.rest;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ZenviaSmsModel {

    /**
     * Returns a Json string corresponding to object state
     *
     * @return Json representation
     */
    public String toJson() {
        return JsonFormatter.toJSON(this);
    }

    @Override
    public String toString() {
        return toJson();
    }
}