/*
 * ============================================================================
 * Project betoffice-storage Copyright (c) 2000-2014 by Andre Winkler. All
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.Configuration;

/**
 * Holds the Hibernate connection properties.
 * 
 * @author Andre Winkler
 */
public class HibernateProperties {

    /** hibernate.dialect */
    public static final String DIALECT = "betoffice.persistence.dialect";

    /** hibernate.connection.url */
    public static final String URL = "betoffice.persistence.url";

    /** hibernate.connection.driver_class */
    public static final String DRIVER = "betoffice.persistence.classname";

    /** hibernate.connection.username */
    public static final String USERNAME = "betoffice.persistence.username";

    /** hibernate.connection.password */
    public static final String PASSWORD = "betoffice.persistence.password";

    /** Alle Hibernate Properties in einem Array. */
    private static final String[] KEYS = { DIALECT, URL, DRIVER, USERNAME, PASSWORD };

    /** Der Logger der Klasse. */
    private final Log log = LogFactory.getLog(HibernateProperties.class);

    // ------------------------------------------------------------------------

    /** Hibernate Connection Properties. */
    private final Properties properties;

    /**
     * Konstruktor.
     * 
     * @param _properties
     *            Hibernate Connection Properties.
     */
    public HibernateProperties(final Properties _properties) {
        properties = _properties;
    }

    /**
     * Prüft, ob alle notwendigen Hibernate Properties vorhanden sind.
     * 
     * @return Liefert <code>true</code> wenn alle Eigenschaften vorhanden sind.
     */
    public boolean validate() {
        boolean ok = true;
        for (String key : KEYS) {
            if (StringUtils.isBlank(properties.getProperty(key))) {
                log.info("Hibernate property '" + key + "' not set!");
                ok = false;
            }
        }
        return ok;
    }

    /**
     * Erstellt eine Connection anhand der Daten einer hibernate.properties
     * Datei bzw. eine Properties Objekts.
     * 
     * @return Eine Connection zur Datenbank.
     */
    public Connection createConnection() {
        Connection jdbcConnection = null;
        try {
            Class.forName(
                    properties.get(HibernateProperties.DRIVER).toString());
            jdbcConnection = DriverManager.getConnection(
                    properties.getProperty(HibernateProperties.URL),
                    properties.getProperty(HibernateProperties.USERNAME),
                    properties.getProperty(HibernateProperties.PASSWORD));
        } catch (SQLException ex) {
            log.error("connection not created", ex);
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            log.error("connection not created", ex);
            throw new RuntimeException(ex);
        }
        return jdbcConnection;
    }

    /**
     * Erstellt ein Hibernate <code>Configuration</code> Objekt anhand eines
     * Property Objekts.
     * 
     * @return Eine Hibernate Configuration.
     */
    public Configuration createConfiguration(List<Class<?>> classes) {
        Configuration config = new Configuration();
        config.addProperties(properties);
        for (Class<?> clazz : classes) {
            config.addClass(clazz);
        }
        return config;
    }

    /**
     * Liefert die Connection.
     * 
     * @param properties
     *            Hibernate Connection Properties.
     * @return Eine SQL Connection.
     */
    public static Connection createConnection(final Properties properties) {
        HibernateProperties hibernateProperties = new HibernateProperties(
                properties);
        return hibernateProperties.createConnection();
    }

    /**
     * Liefert die Hibernate Configuration.
     * 
     * @param properties
     *            Hibernate Connection Properties.
     * @param classes
     *            The entity Hibernate classes.
     * @return Eine Hibernate Configuration.
     */
    public static Configuration createConfiguration(
            final Properties properties, final List<Class<?>> classes) {
        HibernateProperties hibernateProperties = new HibernateProperties(
                properties);
        return hibernateProperties.createConfiguration(classes);
    }

}
