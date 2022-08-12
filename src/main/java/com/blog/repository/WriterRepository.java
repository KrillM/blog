package com.blog.repository;

import com.blog.entity.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriterRepository extends JpaRepository<Writer, Long> {
    Writer findByEmail(String email);
}
