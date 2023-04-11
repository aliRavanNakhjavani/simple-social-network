package com.navoshgaran.socialnetwork.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionDto {
    private String timeStamp;
    private Integer status;
    private String error;
    private String message;
}
