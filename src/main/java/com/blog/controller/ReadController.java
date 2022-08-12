package com.blog.controller;

import com.blog.dto.ReadFormDto;
import com.blog.dto.ReadSearchDto;
import com.blog.entity.Read;
import com.blog.service.ReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ReadController {
    @GetMapping(value = "/admin/read/create")
    public String readForm(Model model) {
        model.addAttribute("readFormDto", new ReadFormDto());
        return "/read/createForm";
    }

    private final ReadService readService;

    @PostMapping(value = "/admin/read/create")
    public String ReadCreate(@Valid ReadFormDto readFormDto, BindingResult bindingResult, Model model,
                             @RequestParam("blogImgFile")List<MultipartFile> blogImgFileList) {
        if (bindingResult.hasErrors()) {
            return "read/createForm";
        }
        if(blogImgFileList.get(0).isEmpty()&&readFormDto.getId()==null){
            model.addAttribute("errorMessage",
                    "대표이미지는 필수입니다");
            return "read/createForm";
        }

        try {
            readService.saveRead(readFormDto, blogImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "글 등록 관련 에러가 발생했습니다");
            return "read/createForm";
        }
        return "redirect:/";
    }
    @GetMapping(value = "/admin/read/{readId}")
    public String readDetail(@PathVariable("readId")Long readId, Model model){
        try{
            ReadFormDto readFormDto = readService.getReadDetail(readId);
            model.addAttribute("readFormDto", readFormDto);
        } catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 게시글입니다");
            model.addAttribute("readFormDto", new ReadFormDto());
            return "read/createForm";
        }
        return "read/createForm";
    }
    @PostMapping(value = "/admin/read/{itemId}")
    public String readUpdate(@Valid ReadFormDto readFormDto, BindingResult bindingResult,
                             @RequestParam("blogImgFile") List<MultipartFile> blogImgFileList,
                             Model model){
        if(bindingResult.hasErrors()){
            return "read/createForm";
        }
        if(blogImgFileList.get(0).isEmpty()&&readFormDto.getId()==null){
            model.addAttribute("errorMessage", "대표 이미지를 넣어주세요");
            return "read/createForm";
        }
        try {
            readService.updateRead(readFormDto, blogImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "글 수정 관련 에러가 발생하였습니다.");
            return "read/createForm";
        }
        return "redirect:/"; // 다시 실행 /
    }
    @GetMapping(value = {"/admin/reads", "/admin/reads/{page}"}) // 페이지 번호가 없는 경우와 있는 경우를 get
    public String readManage(ReadSearchDto readSearchDto, @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        // pageable 객체 생성
        // 첫번째 파라미터: 페이지 번호, 두번째 파라미터: 한번에 가져올 데이터 수
        Page<Read> reads = readService.getAdminReadPage(readSearchDto, pageable);
        model.addAttribute("reads", reads);
        model.addAttribute("readSearchDto",readSearchDto);
        model.addAttribute("maxPage",5);
        return "read/readManage";
    }

    @GetMapping(value = "/read/{readId}")
    public String readDetail(Model model, @PathVariable("readId")Long readId){
        ReadFormDto readFormDto = readService.getReadDetail(readId);
        model.addAttribute("read",readFormDto);
        return "read/detail";
    }
}