package com.web.thuvien.model.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerificationDTO {
    private String email;
    private String code;
}
