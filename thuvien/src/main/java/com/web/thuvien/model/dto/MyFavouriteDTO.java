package com.web.thuvien.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyFavouriteDTO {
    private Long userId;
    private Long bookId;
}
