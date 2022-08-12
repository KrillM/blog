package com.blog.service;

import com.blog.dto.WriterFormDto;
import com.blog.entity.Writer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class WriterServiceTest {
    @Autowired
    WriterService ws;
    @Autowired
    PasswordEncoder pe;

    public Writer createWriter(){
        WriterFormDto wfd = new WriterFormDto();
        wfd.setName("우영우");
        wfd.setEmail("wyw@naver.com");
        wfd.setPassword("vimilbunho");
        return Writer.createWriter(wfd, pe);
    }

    @Test
    @DisplayName("Enroll Test")
    public void saveWriterTest(){
        Writer writer = new Writer();
        Writer saveWriter = ws.saveWriter(writer);

        assertEquals(writer.getName(), saveWriter.getName());
        assertEquals(writer.getEmail(), saveWriter.getEmail());
        assertEquals(writer.getPassword(), saveWriter.getPassword());
    }

    @Test
    @DisplayName("Check duplicated or not")
    public void SaveDuplicateWriterTest(){
        Writer w1 = createWriter();
        Writer w2 = createWriter();
        ws.saveWriter(w1);
        Throwable e = assertThrows(IllegalStateException.class,() ->{
           ws.saveWriter(w2);});
        assertEquals("이미 가입된 회원입니다", e.getMessage());
    }
}