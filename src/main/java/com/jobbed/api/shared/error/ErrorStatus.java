package com.jobbed.api.shared.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus {

    USER_NAME_NULL(1),
    USER_SURNAME_NULL(2),
    USER_EMAIL_NULL(3),
    USER_EMAIL_WRONG_FORMAT(4),
    USER_EMAIL_TAKEN(5),
    USER_PASSWORD_NULL(6),
    USER_RE_PASSWORD_NULL(7),
    USER_PASSWORD_NOT_SAFE(8),
    USER_RE_PASSWORD_DO_NOT_MATCH_PASSWORD(9),
    USER_ROLE_NULL(10),
    USER_ROLE_CANNOT_BE_ADMIN(11),
    TOKEN_NOT_FOUND(12),
    TOKEN_NOT_MATCH_WITH_COMPANY(13),
    COMPANY_NAME_TAKEN(14),
    COMPANY_NOT_FOUND(15),
    COMPANY_NAME_NULL(16),
    USER_OLD_PASSWORD_DO_NOT_MATCH_WITH_CURRENT_PASSWORD(17),
    USER_PHONE_NUMBER_NULL(18),
    USER_NOT_FOUND(19),
    USER_NOT_ENABLED(20),
    USER_BAD_CREDENTIALS(21),
    USER_PHONE_NUMBER_CODE_NULL(22),
    WORKPLACE_NOT_FOUND(23),
    WORKPLACE_NAME_NULL(24),
    WORKPLACE_NAME_TAKEN(25),
    WORKPLACE_ADDRESS_NULL(26),
    WORKPLACE_DESCRIPTION_NULL(27),
    WORKPLACE_LOCATION_NULL(28),
    WORKPLACE_LATITUDE_CANNOT_BE_ZERO(29),
    WORKPLACE_LONGITUDE_CANNOT_BE_ZERO(30),
    COMMENT_MESSAGE_NULL(31);

    private final int code;
}
