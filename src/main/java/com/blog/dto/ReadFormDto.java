package com.blog.dto;

import com.blog.entity.Read;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReadFormDto {
    private Long id;

    @NotBlank(message = "제목을 적어주세요")
    private String title;
    private String subtitle;

    @NotEmpty(message = "지역을 골라주세요")
    private String local;
    @NotEmpty(message = "어느 맛집인가요?")
    private String type;
    @NotEmpty(message = "내용을 적으세요")
    private String content;

    private List<BlogImgDto> blogImgDtoList = new ArrayList<>();
    private List<Long> blogImgIds = new ArrayList<>();
    private static ModelMapper modelMapper = new ModelMapper();
    
    // Read와 ReadFormDto 상호 연결
    public Read createRead(){
        return modelMapper.map(this, Read.class);
    }
    public static ReadFormDto of(Read read){
        return modelMapper.map(read, ReadFormDto.class);
    }
}
