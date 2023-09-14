/*
 * ============================================================================
 * Project betoffice-testutils Copyright (c) 2000-2023 by Andre Winkler. All
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

package de.betoffice.database.hibernate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.hibernate.cfg.Configuration;

/**
 * Handles Hibernate configuration and Hibernate properties.
 * 
 * @author by Andre Winkler
 */
public class HibernateConnectionFactory {

    /** Die Hibernate Configuration. */
    private Configuration config;

    /** Hibernate Eigenschaften zur Erstellung der Configuration. */
    private HibernateProperties hibernateProperties;

    /**
     * Konstruktor.
     * 
     * @param classes Hibernate entity class list
     */
    public HibernateConnectionFactory(List<Class<?>> classes) {
        URL resource = getClass().getResource(
                "test-mysql-piratestest.properties");
        try {
            hibernateProperties = new HibernateProperties(load(resource));
            if (!hibernateProperties.validate()) {
                throw new IllegalStateException(
                        "Hibernate properties are not set!");
            }
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }

        config = hibernateProperties.createConfiguration(classes);
    }

    private Properties load(URL resource) throws IOException {
        Properties properties = new Properties();
        try (InputStream is = resource.openStream()) {
            properties.clear();
            properties.load(is);
        }
        return properties;
    }

    /**
     * Returns the Hibernate configuration.
     * 
     * @return A Hibernate configuration
     */
    public Configuration getConfiguration() {
        return config;
    }

    /**
     * Returns the Hibernate properties.
     * 
     * @return The Hibernate properties
     */
    public HibernateProperties getHibernateProperties() {
        return hibernateProperties;
    }

    /**
     * Creates and returns a connection.
     * 
     * @return              A database connection
     * @throws SQLException A SQL exception
     */
    public Connection createConnection() throws SQLException {
        Connection conn = hibernateProperties.createConnection();
        conn.setAutoCommit(false);
        return conn;
    }

}
