package com.blog.service;

import com.blog.entity.BlogImg;
import com.blog.repository.BlogImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class BlogImgService {
    @Value("${blogImgLocation}")
    private String blogImgLocation;
    private final BlogImgRepository blogImgRepository;
    private final FileService fileService;

    public void saveBlogImg(BlogImg blogImg, MultipartFile blogImgFile) throws Exception{
        String oriImgName = blogImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";
        System.out.println(oriImgName);

        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(blogImgLocation, oriImgName, blogImgFile.getBytes());
            System.out.println(imgName);
            imgUrl="/images/picture/"+imgName;
        }
        blogImg.updateBlogImg(oriImgName, imgName, imgUrl);
        blogImgRepository.save(blogImg);
    }

    public void updateBlogImg(Long blogImgId, MultipartFile blogImgFile) throws Exception {
        if (!blogImgFile.isEmpty()) { // 상품의 이미지를 수정한 경우 상품 이미지 업데이트
            BlogImg saveBlogImg = blogImgRepository.findById(blogImgId).
                    orElseThrow(EntityNotFoundException::new); // 기존 엔티티 조회
            // 기존에 등록된 상품 이미지 파일이 있는경우 파일 삭제
            if (!StringUtils.isEmpty(saveBlogImg.getImgName())) {
                fileService.deleteFile(blogImgLocation + "/" + saveBlogImg.getImgName());
            }
            String oriImgName = blogImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(blogImgLocation, oriImgName,
                    blogImgFile.getBytes()); // 파일 업로드
            String imgUrl = "/images/picture/" + imgName;
            saveBlogImg.updateBlogImg(oriImgName, imgName, imgUrl);
        }
    }
}
