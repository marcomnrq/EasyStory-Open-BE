package com.dystopiastudios.easystory;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EasystoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasystoryApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper() { return new ModelMapper(); }

}
