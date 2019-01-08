package ru.testfield.training.userService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by aNNufriy in Jan, 2019
 */
@Configuration
public class AppConfig {

    @Bean(destroyMethod = "shutdown")
    ExecutorService executorService(){
        return Executors.newWorkStealingPool();
    }

}
