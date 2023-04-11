package com.navoshgaran.socialnetwork.dto;

import jakarta.persistence.Column;
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
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Last Name is Null")
    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email Is Null")
    @Email(message = "Invalid Email")
    private String email;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username Is Null")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Password Is Empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!#*@$%&]).{8,20}$",
            message = "Wrong Pattern For Password")
    private String password;
}
