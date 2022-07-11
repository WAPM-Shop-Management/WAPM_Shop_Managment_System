package spring.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Sahan Nimesha on 2022 - Jul
 * In IntelliJ IDEA
 */

@Configuration
public class WebAppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
