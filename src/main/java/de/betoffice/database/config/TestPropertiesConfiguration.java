package de.betoffice.database.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration for dev environment.
 */
@Profile(value = "dev")
@Configuration
@PropertySource(ignoreResourceNotFound = true, value = {
        "file:${AWTOOLS_CONFDIR}/betoffice/betoffice.properties",
        "file:${user.home}/.botest.properties",
        "classpath:/botest.properties"
})
public class TestPropertiesConfiguration {

}
