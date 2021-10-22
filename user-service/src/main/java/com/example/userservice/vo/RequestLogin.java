package com.example.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestLogin {
    @NotNull(message = "Email cannot be Null")
    @Size(min = 2, message = "Email not be less than 2 Characters")
    @Email
    private String email;

    @NotNull(message = "Pwd cannot be Null")
    @Size(min = 8, message = "pwd must be equals or greater than 2 Characters")
    private String password;
}
