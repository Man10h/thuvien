package com.web.thuvien.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private Long id;
    private String fileName;
    private String fileUrl;
    private String fileId;
}
