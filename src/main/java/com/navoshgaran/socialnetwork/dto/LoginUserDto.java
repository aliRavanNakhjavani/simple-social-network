package com.navoshgaran.socialnetwork.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {

    @NotBlank(message = "Username Is Null")
    private String username;

    @NotBlank(message = "Password Is Empty")
    private String password;
}
