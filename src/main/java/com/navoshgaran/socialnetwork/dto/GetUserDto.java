package com.navoshgaran.socialnetwork.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserDto {
    @NotBlank(message = "First Name is Null")
    private String firstName;

    @NotBlank(message = "Last Name is Null")
    private String lastName;

    @NotBlank(message = "Username Is Null")
    private String username;
}
