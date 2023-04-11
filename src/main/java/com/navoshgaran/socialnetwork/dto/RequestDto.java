package com.navoshgaran.socialnetwork.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    @NotBlank(message = "Client Username Can Not Be Blank")
    private String clientUsername;

    @NotBlank(message = "target Username Can Not Be Blank")
    private String targetUsername;
}
