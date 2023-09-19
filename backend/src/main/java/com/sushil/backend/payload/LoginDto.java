package com.sushil.backend.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    private String usernameOrEmail;
    private String password;
}
