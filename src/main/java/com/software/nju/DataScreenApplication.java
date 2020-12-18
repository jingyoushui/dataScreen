package com.software.nju;


import com.software.nju.WebSocket.WebsocketServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class DataScreenApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataScreenApplication.class,args);
    }


}
