package com.inflearn.springsecuritycoreproject.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
    private String username;
    private String password;
    private String email;
    private String age;
    private String role;
}
