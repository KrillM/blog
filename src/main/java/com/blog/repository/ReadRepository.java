package com.blog.repository;

import com.blog.entity.Read;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ReadRepository extends JpaRepository<Read, Long>, QuerydslPredicateExecutor<Read>, ReadRepositoryCustom {
    List<Read> findByTitle(String title);
    List<Read> findBySubtitle(String subtitle);
    List<Read> findByContent(String content);
    List<Read> findByLocal(String local);
    List<Read> findByType(String type);
}
