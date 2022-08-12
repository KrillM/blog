package com.blog.controller;

import com.blog.dto.ReadDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ThymeleafController {

    @GetMapping(value = "/ex02")
    public String thymeleafEx02(Model model){
        ReadDto readDto = new ReadDto();
        readDto.setTitle("월화식당");
        readDto.setSubtitle("마시써요");
        readDto.setLocal("서울");
        readDto.setType("고기");
        readDto.setContent("돼지고기 가즈아");

        model.addAttribute("readDto", readDto);
        return "thymeleafEx/thymeleafEx02";
    }

    @GetMapping(value = "/ex07")
    public String thymeleafEx07(Model model){
        return "thymeleafEx/thymeleafEx07";
    }
}