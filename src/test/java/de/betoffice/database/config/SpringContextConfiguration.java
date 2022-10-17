package de.betoffice.database.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:/botest.properties")
public class SpringContextConfiguration {

    @Value("${betoffice.persistence.username}")
    String username;

    @Value("${betoffice.persistence.password}")
    String password;

    @Value("${betoffice.persistence.url}")
    String url;

    @Value("${betoffice.persistence.classname}")
    String classname;

    @Value("${betoffice.persistence.dialect}")
    String dialect;

    @Bean
    public DatabaseConnectionFactory createConfiguration() {
        return new DatabaseConnectionFactory(username, password, url, classname, dialect);
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
}
