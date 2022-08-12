package com.blog.service;

import com.blog.dto.BlogImgDto;
import com.blog.dto.MainReadDto;
import com.blog.dto.ReadFormDto;
import com.blog.dto.ReadSearchDto;
import com.blog.entity.BlogImg;
import com.blog.entity.Read;
import com.blog.repository.BlogImgRepository;
import com.blog.repository.ReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReadService {
    private final ReadRepository readRepository;
    private final BlogImgService blogImgService;

    public Long saveRead(ReadFormDto readFormDto, List<MultipartFile> blogImgFileList) throws Exception{
        Read read = readFormDto.createRead();
        readRepository.save(read);

        for(int i=0;i<blogImgFileList.size();i++){
            BlogImg blogImg = new BlogImg();
            blogImg.setRead(read);

            if(i==0)
                blogImg.setRepImgYn("Y");
            else
                blogImg.setRepImgYn("N");
            blogImgService.saveBlogImg(blogImg, blogImgFileList.get(i));
        }
        return read.getId();
    }

    private final BlogImgRepository blogImgRepository;

    @Transactional(readOnly = true)// 읽기전용 -> 더티체킹(변경감지) -> 성능향상
    public ReadFormDto getReadDetail(Long readId){
        List<BlogImg> blogImgList = blogImgRepository.findByReadIdOrderByIdAsc(readId);
        List<BlogImgDto> blogImgDtoList = new ArrayList<>();

        for(BlogImg blogImg : blogImgList){
            BlogImgDto blogImgDto = BlogImgDto.of(blogImg);
            blogImgDtoList.add(blogImgDto);
        }

        Read read = readRepository.findById(readId).orElseThrow(EntityNotFoundException::new);
        ReadFormDto readFormDto = ReadFormDto.of(read);
        readFormDto.setBlogImgDtoList(blogImgDtoList);
        return readFormDto;
    }

    public Long updateRead(ReadFormDto readFormDto, List<MultipartFile> blogImgFileList) throws Exception{
        Read read = readRepository.findById(readFormDto.getId()).
                orElseThrow(EntityNotFoundException::new);
        read.updateRead(readFormDto);

        for(int i=0;i<readFormDto.getBlogImgIds().size();i++){
            System.out.println(readFormDto.getBlogImgIds().get(i));
        }
        List<Long> blogImgIds = readFormDto.getBlogImgIds();
        System.out.println(blogImgFileList.size());
        System.out.println(blogImgIds.get(0));
        for(int i=0;i<blogImgFileList.size();i++){
            blogImgService.updateBlogImg(blogImgIds.get(i), blogImgFileList.get(i));
        }
        return read.getId();
    }
    @Transactional(readOnly = true)
    public Page<Read> getAdminReadPage(ReadSearchDto readSearchDto, Pageable pageable){
        return readRepository.getAdminReadPage(readSearchDto, pageable);
    }
    @Transactional(readOnly = true)
    public Page<MainReadDto> getMainReadPage(ReadSearchDto readSearchDto, Pageable pageable){
        return readRepository.getMainReadPage(readSearchDto, pageable);
    }
}
