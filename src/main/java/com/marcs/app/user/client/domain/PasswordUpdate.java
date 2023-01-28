/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.app.user.client.domain;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Object used to update a users password. This will hold a current password and
 * new password fields.
 * 
 * @author Sam Butler
 * @since October 29, 2021
 */
@Schema(description = "Password update object.")
public class PasswordUpdate {

    @Schema(description = "Current password.")
    private String currentPassword;

    @Schema(description = "The new password.")
    private String newPassword;

    public PasswordUpdate() {
    }

    public PasswordUpdate(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
