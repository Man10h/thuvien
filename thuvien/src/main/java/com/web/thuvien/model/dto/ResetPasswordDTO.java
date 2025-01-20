package com.web.thuvien.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDTO {
    private Long userId;

    @JsonProperty("password")
    @NotBlank(message = "required")
    private String password;

    @JsonProperty("new_password")
    @NotBlank(message = "required")
    private String newPassword;

    @JsonProperty("confirm_password")
    @NotBlank(message = "required")
    private String confirmPassword;
}
