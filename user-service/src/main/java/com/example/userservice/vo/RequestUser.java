package com.example.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class RequestUser {
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "2 chars")
    @Email
    private String email;

    @NotNull(message = "name cannot be null")
    @Size(min = 2, message = "2 chars")
    private String name;


    @NotNull(message = "password cannot be null")
    @Size(min = 8, message = "8 chars")
    private String pwd;
}
