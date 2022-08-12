package com.blog.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class WriterFormDto {
    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @NotEmpty(message = "이메일 값을 입력해주세요")
    @Email(message = "이메일 형식을 지켜주세요")
    private String email;
    
    @NotEmpty(message = "비밀번호는 필수 입력값입니다")
    @Length(min=4, max=10, message = "비밀번호는 4~10자 사이로 해주세요")
    private String password;
}
