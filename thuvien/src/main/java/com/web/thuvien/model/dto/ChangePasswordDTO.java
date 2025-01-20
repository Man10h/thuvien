package com.web.thuvien.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO {
    private Long userId;

    @JsonProperty("old_password")
    @NotBlank(message = "required")
    private String oldPassword;

    @JsonProperty("new_password")
    @NotBlank(message = "required")
    private String newPassword;

    @JsonProperty("confirm_password")
    @NotBlank(message = "required")
    private String confirmPassword;
}
