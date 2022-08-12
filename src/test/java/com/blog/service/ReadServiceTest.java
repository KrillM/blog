package com.blog.service;

import com.blog.dto.ReadFormDto;
import com.blog.entity.BlogImg;
import com.blog.entity.Read;
import com.blog.repository.BlogImgRepository;
import com.blog.repository.ReadRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class ReadServiceTest {
    @Autowired
    ReadService rs;
    @Autowired
    ReadRepository rr;
    @Autowired
    BlogImgRepository bir;

    List<MultipartFile> createMultipartFiles() throws Exception{
        List<MultipartFile> multipartFileList = new ArrayList<>();
        for(int i=0;i<5;i++){
            String path = "C:/springTest/blog/picture";
            String imgName = "image"+i+".jpg";
            MockMultipartFile mockMultipartFile =
                    new MockMultipartFile(path, imgName, "image/jpg", new byte[]{1,2,3,4});
            multipartFileList.add(mockMultipartFile);
        }
        return multipartFileList;
    }

    @Test
    @DisplayName("Item Enroll Test")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void saveRead()throws Exception{
        ReadFormDto rfd = new ReadFormDto();
        rfd.setTitle("기꾸스시");
        rfd.setSubtitle("가지마셈");
        rfd.setLocal("신촌");
        rfd.setType("초밥");
        rfd.setContent("생각보다 별로인데다 어떤 아저씨가 술 먹고 ㅈㄹ함");
        List<MultipartFile> multipartFileList = createMultipartFiles();
        Long readId = rs.saveRead(rfd, multipartFileList);

        List<BlogImg> blogImgList = bir.findByReadIdOrderByIdAsc(readId);
        Read read = rr.findById(readId).orElseThrow(EntityNotFoundException::new);
        assertEquals(rfd.getTitle(), read.getTitle());
        assertEquals(rfd.getSubtitle(), read.getSubtitle());
        assertEquals(rfd.getLocal(), read.getLocal());
        assertEquals(rfd.getType(), read.getType());
        assertEquals(rfd.getContent(), read.getContent());
        assertEquals(multipartFileList.get(0).getOriginalFilename(), blogImgList.get(0).getOriImgName());
    }
}