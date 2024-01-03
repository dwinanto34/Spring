package com.app.beanvalidation.model;

import com.app.beanvalidation.constraint.CheckPassword;
import com.app.beanvalidation.constraint.CheckPasswordParameter;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@CheckPassword(message = "[Custom] Password and retype password does not match")
public class Account {
    private String username;
    private String password;
    private String retypePassword;

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