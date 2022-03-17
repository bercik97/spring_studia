package com.jobbed.api.user.domain.command;

import com.jobbed.api.user.domain.dto.UpdatePasswordDto;

public record UpdatePasswordCommand(String email, String currentPassword, String oldPassword, String newPassword, String reNewPassword) {

    public static UpdatePasswordCommand of(String email, String currentPassword, UpdatePasswordDto dto) {
        return new UpdatePasswordCommand(email, currentPassword, dto.oldPassword(), dto.newPassword(), dto.reNewPassword());
    }
}
