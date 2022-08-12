package com.blog.repository;

import com.blog.dto.MainReadDto;
import com.blog.dto.QMainReadDto;
import com.blog.dto.ReadSearchDto;
import com.blog.entity.QBlogImg;
import com.blog.entity.QRead;
import com.blog.entity.Read;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class ReadRepositoryCustomImpl implements ReadRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public ReadRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }
    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("title", searchBy)){
            return QRead.read.title.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("local", searchBy)){
            return QRead.read.local.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("type", searchBy)){
            return QRead.read.type.like("%"+searchQuery+"%");
        }
        return null;
    }

    @Override
    public Page<Read> getAdminReadPage(ReadSearchDto readSearchDto, Pageable pageable){
        QueryResults<Read> results = queryFactory.selectFrom(QRead.read).
                where(searchByLike(readSearchDto.getSearchBy(), readSearchDto.getSearchQuery()))
                .orderBy(QRead.read.id.desc())
                .offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();
        List<Read> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content,pageable,total);
    }

    private BooleanExpression titleLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null:QRead.read.title.like("%"+searchQuery+"%");
    }

    @Override
    public Page<MainReadDto> getMainReadPage(ReadSearchDto readSearchDto, Pageable pageable){
        QRead read = QRead.read;
        QBlogImg blogImg = QBlogImg.blogImg;

        QueryResults<MainReadDto> results = queryFactory.select(new QMainReadDto(read.id, read.title,
                        read.subtitle, read.local, read.type, blogImg.imgUrl))
                .from(blogImg).join(blogImg.read, read).where(blogImg.repImgYn.eq("Y"))
                .where(titleLike(readSearchDto.getSearchQuery()))
                .orderBy(read.id.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();

        List<MainReadDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content,pageable,total);
    }
}
