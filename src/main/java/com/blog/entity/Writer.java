package com.blog.entity;

import com.blog.constant.Role;
import com.blog.dto.WriterFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "writer")
@Getter
@Setter
@ToString
public class Writer extends BaseEntity{
    @Id
    @Column(name="writer_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Writer createWriter(WriterFormDto writerFormDto, PasswordEncoder passwordEncoder){
        Writer writer = new Writer();

        writer.setName(writerFormDto.getName());
        writer.setEmail(writerFormDto.getEmail());

        String password = passwordEncoder.encode(writerFormDto.getPassword());
        writer.setPassword(password);
        writer.setRole(Role.ADMIN);
        return writer;
    }
}
