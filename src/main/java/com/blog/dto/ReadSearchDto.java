package com.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadSearchDto {
    private String searchBy;
    // searchBy: 조회할 방법 선택 || 여기서는 title, type, local로 결정!
    private String searchQuery ="";
}
