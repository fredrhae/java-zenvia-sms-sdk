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
}
