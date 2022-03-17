package com.jobbed.api.user.domain.dto;

public record UpdatePasswordDto(String oldPassword, String newPassword, String reNewPassword) {
}
