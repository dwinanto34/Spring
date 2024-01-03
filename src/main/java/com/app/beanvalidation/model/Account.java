package com.app.beanvalidation.model;

import com.app.beanvalidation.constraint.CheckPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@CheckPassword(message = "[Custom] Password and retype password does not match")
public class Account {
    private String username;
    private String password;
    private String retypePassword;
}