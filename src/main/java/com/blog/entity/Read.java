package com.blog.entity;

import com.blog.dto.ReadFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"read\"")
@Getter
@Setter
@ToString
public class Read extends BaseEntity{
    @Id
    @Column(name="read_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(nullable = false, length = 30)
    private String local;

    @Column(nullable = false, length = 30)
    private String type;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToMany
    @JoinTable(
            name = "writer_read",
            joinColumns = @JoinColumn(name = "writer_id"),
            inverseJoinColumns = @JoinColumn(name = "read_id")
    )
    private List<Writer> writerList;

    public void updateRead(ReadFormDto readFormDto) {
        this.title = readFormDto.getTitle();
        this.subtitle = readFormDto.getSubtitle();
        this.local = readFormDto.getLocal();
        this.type = readFormDto.getType();
        this.content = readFormDto.getContent();
    }
}
