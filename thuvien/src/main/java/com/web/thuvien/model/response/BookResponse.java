package com.web.thuvien.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private Long id;
    private String name;
    private String author;
    private List<String> types;
    private String description;
    private Long likeCount;
    private List<String> files;
    private List<String> images;
}
