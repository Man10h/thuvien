package com.web.thuvien.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {
    @JsonProperty("name")
    @NotBlank(message = "required")
    private String name;

    @JsonProperty("username")
    @NotBlank(message = "required")
    private String username;

    @JsonProperty("password")
    @NotBlank(message = "required")
    private String password;

    @JsonProperty("retype")
    @NotBlank(message = "required")
    private String retype;

    @JsonProperty("email")
    @NotBlank(message = "required")
    private String email;
}
