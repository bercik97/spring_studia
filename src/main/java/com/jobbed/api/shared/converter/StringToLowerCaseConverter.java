package com.jobbed.api.shared.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

// TODO should be replaced by annotation which converts String to lowerCase

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringToLowerCaseConverter {

    public static String convert(String value) {
        if (value == null) {
            return null;
        }
        return value.toLowerCase();
    }
}
