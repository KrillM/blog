package com.blog.repository;

import com.blog.entity.QRead;
import com.blog.entity.Read;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class ReadRepositoryTest {
    @Autowired
    ReadRepository readRepository;

    @Test
    @DisplayName("Write Content")
    public void createReadTest(){
        Read read = new Read();
        read.setTitle("Hello");
        read.setSubtitle("nice");
        read.setLocal("seoul");
        read.setType("sushi");
        read.setContent("Let's git it");
        Read saveRead = readRepository.save(read);
        System.out.println(saveRead.toString());
    }

    public void createReadList(){
        for(int i=1;i<=10;i++){
            Read read = new Read();
            read.setTitle("Hello"+i);
            read.setSubtitle("nice"+i);
            read.setLocal("seoul"+i);
            read.setType("sushi"+i);
            read.setContent("Radio gaga"+i);
            Read saveRead = readRepository.save(read);
            System.out.println(saveRead.toString());
        }
    }

    @Test
    @DisplayName("Search Title Test")
    public void findByTitleTest(){
        this.createReadList();
        List<Read> readList = readRepository.findByTitle("Hello");
        for(Read read : readList){
            System.out.println(read.toString());
        }
    }

    @Test
    @DisplayName("Search SubTitle Test")
    public void findBySubtitleTest(){
        this.createReadList();
        List<Read> readList = readRepository.findBySubtitle("nice");
        for(Read read : readList){
            System.out.println(read.toString());
        }
    }

    @Test
    @DisplayName("Search local Test")
    public void findByLocalTest(){
        this.createReadList();
        List<Read> readList = readRepository.findByLocal("seoul");
        for(Read read : readList){
            System.out.println(read.toString());
        }
    }

    @Test
    @DisplayName("Search type Test")
    public void findBySubTypeTest(){
        this.createReadList();
        List<Read> readList = readRepository.findByType("sushi");
        for(Read read : readList){
            System.out.println(read.toString());
        }
    }

    @Test
    @DisplayName("Search Content Test")
    public void findByContentTest(){
        this.createReadList();
        List<Read> readList = readRepository.findByContent("radio gaga");
        for(Read read : readList){
            System.out.println(read.toString());
        }
    }

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("Check test Querydsl 1")
    public void queryDslTest(){
        this.createReadList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QRead qRead = QRead.read;
        JPAQuery<Read> query = queryFactory.selectFrom(qRead)
                .where(qRead.title.like("%"+"Test explain"+"%"))
                .where(qRead.local.like("%"+"Test explain"+"%"))
                .where(qRead.type.like("%"+"Test explain"+"%"))
                .where(qRead.subtitle.like("%"+"Test explain"+"%"))
                .where(qRead.content.like("%"+"Test explain"+"%"));
        List<Read> readList = query.fetch();

        for(Read read : readList){
            System.out.println(read.toString());
        }
    }

    public void createReadList2(){
        for(int i=1;i<=5;i++){
            Read read = new Read();
            read.setTitle("Hello"+i);
            read.setSubtitle("nice"+i);
            read.setLocal("seoul"+i);
            read.setType("sushi"+i);
            read.setContent("hahaha"+i);
            readRepository.save(read);
        }
        for(int i=6;i<=10;i++){
            Read read = new Read();
            read.setTitle("Hello"+i);
            read.setSubtitle("nice"+i);
            read.setLocal("seoul"+i);
            read.setType("sushi"+i);
            read.setContent("hahaha"+i);
            readRepository.save(read);
        }
    }

    @Test
    @DisplayName("Check test Querydsl 2")
    public void queryDslTest2(){
        this.createReadList2();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QRead read = QRead.read;

        String subtitle = "lol";
        String local = "seoul";
        String type = "sushi";
        String content = "Explain test item blah";

        booleanBuilder.and(read.local.like("%"+local+"%"));
        booleanBuilder.and(read.type.like("%"+ type+"%"));
        booleanBuilder.and(read.subtitle.like("%"+ subtitle+"%"));
        booleanBuilder.and(read.content.like("%"+content+"%"));

        Pageable pageable = PageRequest.of(0,5);
        Page<Read> readPagingResult = readRepository.findAll(booleanBuilder, pageable);
        System.out.println("total elements :"+readPagingResult.getTotalElements());

        List<Read> resultReadList = readPagingResult.getContent();
        for(Read resultRead : resultReadList){
            System.out.println(resultRead.toString());
        }
    }
}