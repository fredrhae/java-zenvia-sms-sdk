package com.zenvia.sms.sdk.base.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SmsStatusCode {
    OK,
    SCHEDULED,
    DELIVERED,
    NOT_RECEIVED,
    BLOCKED_NO_COVERAGE,
    BLOCKED_BLACK_LISTED,
    BLOCKED_INVALID_NUMBER,
    BLOCKED_CONTENT_NOT_ALLOWED,
    BLOCKED_MESSAGE_EXPIRED,
    BLOCKED,
    ERROR;

    @JsonValue
    public int toValue() {
        return ordinal();
    }

    public static SmsStatusCode fromValue(Integer value) {
        SmsStatusCode[] values = SmsStatusCode.values();
        for(SmsStatusCode currentStatus : values){
            if(currentStatus.toValue() == value){
                return currentStatus;
            }
        }

        return null;
    }
}
