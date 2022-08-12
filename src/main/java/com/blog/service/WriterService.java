package com.blog.service;

import com.blog.entity.Writer;
import com.blog.repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WriterService implements UserDetailsService {
    private final WriterRepository writerRepository;

    public Writer saveWriter(Writer writer){
        validateDuplicateWriter(writer);
        return writerRepository.save(writer);
    }

    private void validateDuplicateWriter(Writer writer){
        Writer findWriter = writerRepository.findByEmail(writer.getEmail());
        if(findWriter!=null){
            throw new IllegalStateException("이미 가입된 회원입니다");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Writer writer = writerRepository.findByEmail(email);

        if(writer==null){
            throw new UsernameNotFoundException(email);
        }
        return User.builder().username(writer.getEmail())
                .password(writer.getPassword())
                .roles(writer.getRole().toString())
                .build();
    }
}
