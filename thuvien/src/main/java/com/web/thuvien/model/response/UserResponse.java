package com.web.thuvien.model.response;

import com.web.thuvien.model.entity.RefreshTokenEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String username;
    private List<ImageResponse> images;
    private List<RefreshTokenResponse> refreshTokens;
}
