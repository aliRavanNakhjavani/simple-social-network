package com.navoshgaran.socialnetwork.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserDto {
    @NotBlank(message = "First Name is Null")
    private String firstName;

    @NotBlank(message = "Last Name is Null")
    private String lastName;

    @NotBlank(message = "Email Is Null")
    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "Username Is Null")
    private String username;

    @NotBlank(message = "Password Is Empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!#*@$%&]).{8,20}$",
            message = "Wrong Pattern For Password")
    private String password;
}
