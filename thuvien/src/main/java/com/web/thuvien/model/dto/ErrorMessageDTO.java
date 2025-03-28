package com.web.thuvien.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageDTO {
    private String message;
    private Long code;

}
