package com.danmou.beginner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.danmou.beginner.common.Coach;
import com.danmou.beginner.common.SwimCoach;

@Configuration
public class SportConfig {

    @Bean("aquatic")
    Coach swimCoach() {
    return new SwimCoach();
  }
}
