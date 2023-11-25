package com.adziri.springweb.dto;

import lombok.Data;

@Data
public class RegistrationUserDTO {
    private String email;
    private String username;
    private String password;
    private String confirmPassword;
}
