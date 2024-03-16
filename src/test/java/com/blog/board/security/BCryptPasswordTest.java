package com.blog.board.security;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BCryptPasswordTest {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Test
    public void passwordTest() {
        String password = "1234";
        String encode = passwordEncoder().encode(password);
        System.out.println(encode);
    }

    @Test
    public void passwordTest2() {
        String password = "1234";
        String encode = passwordEncoder().encode(password);
        boolean matches = passwordEncoder().matches(password, encode);


    }


    @Test
    public void passwordTest3() {
        String password = "1234";
        String encode = passwordEncoder().encode(password);

        boolean b = passwordEncoder().upgradeEncoding(encode);
        System.out.println(b);

    }


}
