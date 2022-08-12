package com.blog.controller;

import com.blog.dto.MainReadDto;
import com.blog.dto.ReadSearchDto;
import com.blog.service.ReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ReadService readService;

    @GetMapping(value = "/")
    public String main(ReadSearchDto readSearchDto,Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);

        if(readSearchDto.getSearchQuery()==null){
            readSearchDto.setSearchQuery("");
        }
        Page<MainReadDto> reads = readService.getMainReadPage(readSearchDto, pageable);
        model.addAttribute("reads", reads);
        model.addAttribute("readSearchDto",readSearchDto);
        model.addAttribute("maxPage",5);
        return "main";
    }
}
