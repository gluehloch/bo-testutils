/*
 * ============================================================================
 * Project betoffice-testutils Copyright (c) 2000-2022 by Andre Winkler. All
 * rights reserved.
 * ============================================================================
 * GNU GENERAL PUBLIC LICENSE TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND
 * MODIFICATION
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */

package de.betoffice.database.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Holds betoffice database connection properties.
 * 
 * @author Andre Winkler
 */
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
