package com.notificationservice.exception;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

@NoArgsConstructor
public class BadRequestException extends Exception {

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Bad request due to invalid parameters").toString();
    }
}
