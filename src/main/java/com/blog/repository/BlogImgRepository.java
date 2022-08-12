package com.blog.repository;

import com.blog.entity.BlogImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogImgRepository extends JpaRepository<BlogImg, Long> {
    List<BlogImg> findByReadIdOrderByIdAsc(Long readId);
}
