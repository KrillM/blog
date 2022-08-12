package com.blog.dto;

import com.blog.entity.BlogImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class BlogImgDto {
    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static BlogImgDto of(BlogImg blogImg){
        return modelMapper.map(blogImg, BlogImgDto.class);
    }
}
