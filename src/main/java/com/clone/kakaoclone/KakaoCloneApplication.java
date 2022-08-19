package com.clone.kakaoclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KakaoCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(KakaoCloneApplication.class, args);
    }

}
