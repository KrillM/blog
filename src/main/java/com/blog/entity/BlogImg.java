package com.blog.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="blog_img")
@Getter
@Setter
public class BlogImg extends BaseEntity {
    @Id
    @Column(name = "blog_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String repImgYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="read_id")
    private Read read;

    public void updateBlogImg(String oriImgName, String imgUrl, String imgName){
        this.oriImgName = oriImgName;
        this.imgName= imgName;
        this.imgUrl=imgUrl;
    }
}
