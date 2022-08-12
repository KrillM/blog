package com.blog.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainReadDto {
    private Long id;
    private String title;
    private String subtitle;
    private String local;
    private String type;
    private String imgUrl;

    @QueryProjection
    public MainReadDto(Long id, String title, String subtitle, String local, String type, String imgUrl){
        this.id=id;
        this.title=title;
        this.subtitle=subtitle;
        this.local=local;
        this.type=type;
        this.imgUrl=imgUrl;
    }
}
