package com.sushil.backend.payload;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDto {

    private String name;
    private String username;

    private String email;

    private String password;
}
