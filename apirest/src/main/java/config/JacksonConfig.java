package config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/*@Configuration
public class ObjectMapperConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper(){
        ObjectMapper mapper = new ObjectMapper();

        SimpleFilterProvider filters = new SimpleFilterProvider()
                .addFilter("PersonFilter",
                        SimpleBeanPropertyFilter.serializeAllExcept("sensitiveData"));
        filters.setFailOnUnknownId(false);
        mapper.setFilterProvider(filters);
        return mapper;
    }
}*/

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> {

            SimpleFilterProvider filters = new SimpleFilterProvider()
                .addFilter(
                    "PersonFilter",
                    SimpleBeanPropertyFilter.serializeAllExcept("sensitiveData")
                )
                .setFailOnUnknownId(false);

            builder.filters(filters);
        };
    }
}


