package com.web.thuvien.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {
    private Long id;
    private String fileName;
    private String fileUrl;
    private String fileId;
}
