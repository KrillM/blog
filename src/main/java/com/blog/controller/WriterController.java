package com.blog.controller;

import com.blog.dto.WriterFormDto;
import com.blog.entity.Writer;
import com.blog.service.WriterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/writer")
@Controller
@RequiredArgsConstructor
public class WriterController {
    private final WriterService writerService;

    @GetMapping(value = "/sign")
    public String writerForm(Model model){
        model.addAttribute("writerFormDto", new WriterFormDto());
        return "writer/writerForm";
    }

    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/sign")
    public String writerForm(@Valid WriterFormDto writerFormDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "writer/writerForm";
        }
        try{
            Writer writer = Writer.createWriter(writerFormDto, passwordEncoder);
            writerService.saveWriter(writer);
        }
        catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
                return "writer/writerForm";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String loginWriter(){return "writer/login";}

    @GetMapping(value = "login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMessage", "아이디와 비밀번호를 올바르게 입력하세요");
        return "writer/login";
    }
}
