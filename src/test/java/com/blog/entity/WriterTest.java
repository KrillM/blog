package com.blog.entity;

import com.blog.repository.WriterRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class WriterTest {
    @Autowired
    WriterRepository wr;
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Auditing Test")
    @WithMockUser(username = "youngwoo", roles = "USER")
    public void auditingTest(){
        Writer newWriter = new Writer();
        wr.save(newWriter);

        Writer writer = wr.findById(newWriter.getId())
                .orElseThrow(EntityNotFoundException::new);

        System.out.println("register time" + writer.getRegisterTime());
        System.out.println("update time" + writer.getUpdateTime());
        System.out.println("create time" + writer.getCreatedBy());
        System.out.println("modify time" + writer.getModifiedBy());
    }
}