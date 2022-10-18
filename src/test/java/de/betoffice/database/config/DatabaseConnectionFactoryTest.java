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

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 
 * 
 * @author Andre Winkler
 */
@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = "de.betoffice.database.config")
@PropertySource("classpath:/botest.properties")
@ContextConfiguration(classes = { SpringContextConfiguration.class })
class DatabaseConnectionFactoryTest {

    @Autowired
    private DatabaseConnectionFactory databaseConnectionConfiguration;
    
    @Test
    void createConnection() throws SQLException {
        assertThat(databaseConnectionConfiguration.username()).isEqualTo("test");
        
        try (Connection connection = databaseConnectionConfiguration.createConnection()) {
            assertThat(connection).isNotNull();
        }
    }

}
