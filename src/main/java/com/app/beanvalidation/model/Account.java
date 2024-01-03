package com.app.beanvalidation.model;

import com.app.beanvalidation.constraint.CheckPassword;
import com.app.beanvalidation.constraint.CheckPasswordParameter;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@CheckPassword(message = "[Custom] Password and retype password does not match")
public class Account {
    private String username;
    private String password;
    private String retypePassword;

    // For container data
    // The annotation should be put on the generic data type level, and not the field level
    private List<@NotBlank(message = "Password histories list cannot accept an empty string") String> passwordHistories;

    @CheckPasswordParameter(
            passwordParam = 1,
            retypePasswordParam = 2
    )
    public Account(String username, String password, String retypePassword) {
        this.username = username;
        this.password = password;
        this.retypePassword = retypePassword;
    }
}