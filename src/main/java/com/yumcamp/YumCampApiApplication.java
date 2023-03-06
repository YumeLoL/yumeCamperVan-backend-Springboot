package com.yumcamp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@Slf4j
@ServletComponentScan
public class YumCampApiApplication {

    public static void main(String[] args) {

        SpringApplication.run(YumCampApiApplication.class, args);
        log.info("----YumCamp project is running----");
    }

}
