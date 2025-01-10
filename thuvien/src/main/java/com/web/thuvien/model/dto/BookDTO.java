package com.web.thuvien.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    private String name;
    private String author;
    private List<String> types;
    private Long likeCount;
    private String description;
    private List<MultipartFile> images;
    private List<MultipartFile> files;
}
