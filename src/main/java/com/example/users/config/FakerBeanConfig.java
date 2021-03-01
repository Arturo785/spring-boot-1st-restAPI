package com.example.users.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FakerBeanConfig {

    @Bean // sets a BEAN available to be used in spring context and inject it
    public Faker getFaker(){
        return new Faker();
    }
}
