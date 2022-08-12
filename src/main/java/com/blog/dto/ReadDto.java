package com.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadDto {
    private Long id;
    private String title;
    private String subtitle;
    private String local;
    private String type;
    private String content;
}
