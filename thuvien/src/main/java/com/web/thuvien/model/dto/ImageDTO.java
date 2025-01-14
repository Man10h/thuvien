package com.web.thuvien.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private List<MultipartFile> images;
    private Long userId;
    private Long bookId;
}
