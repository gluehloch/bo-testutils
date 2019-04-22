/*
 * $Id: PropertyReaderTest.java 3782 2013-07-27 08:44:32Z andrewinkler $
 * ============================================================================
 * Project betoffice-storage
 * Copyright (c) 2000-2012 by Andre Winkler. All rights reserved.
 * ============================================================================
 *          GNU GENERAL PUBLIC LICENSE
 *  TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package de.betoffice.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.junit.Test;

/**
 * Testet das Auslesen der Property-Dateien.
 *
 * @author by Andre Winkler
 */
public class PropertyReaderTest {

    private static final String PROPERTY_FILE = "/de/betoffice/database/test/botest.properties";

    /**
     * Der Test mit {@link Properties} funktioniert.
     *
     * @throws Exception
     *             Da ging was schief.
     */
    @Test
    public void testReadPropertyFiles() throws Exception {
        InputStream is = this.getClass().getResourceAsStream(PROPERTY_FILE);
        assertNotNull(is);
        Properties props = new Properties();
        props.load(is);

        assertEquals("test",
                props.getProperty("betoffice.persistence.username"));
        assertEquals("test",
                props.getProperty("betoffice.persistence.password"));
        assertEquals("jdbc:mysql://localhost/botest",
                props.getProperty("betoffice.persistence.url"));
        assertEquals("com.mysql.jdbc.Driver",
                props.getProperty("betoffice.persistence.classname"));
        assertEquals("org.hibernate.dialect.MySQLDialect",
                props.getProperty("betoffice.persistence.dialect"));
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
     * Genau der gleiche Test funktioniert im Projekt gluehloch-util. Verlaesst
     * der Code das Projekt und wird z.B. hier ausgefuehrt, liefert der Test
     * einen Fehler zurueck.
     *
     * @throws Exception
     *             Da ging was schief.
     */
    @Test
    public void testReadPropertyFileByPropertyHolder() throws Exception {
        URL resource = this.getClass().getResource(PROPERTY_FILE);
        Properties ph = load(resource);

        assertEquals("test", ph.getProperty("betoffice.persistence.username"));
        assertEquals("test", ph.getProperty("betoffice.persistence.password"));
    }

    /**
     * Mit Commons-Configuration geht es auch noch.
     *
     * @throws Exception
     *             Da ging was schief.
     */
    @Test
    public void testReadPropertyFileByCommonsConfiguration() throws Exception {
        URL resource = this.getClass().getResource(PROPERTY_FILE);
        Properties pc = load(resource);

        assertEquals("test", pc.getProperty("betoffice.persistence.username"));
        assertEquals("test", pc.getProperty("betoffice.persistence.password"));
    }

    /**
     * Mit Commons-Configuration und interpoliert geht es auch noch?
     *
     * @throws Exception
     *             Da ging was schief.
     */
    @Test
    public void testReadPropertyFileByCommonsConfigurationInterpolated()
            throws Exception {

        URL resource = this.getClass().getResource(PROPERTY_FILE);
        Properties gc = load(resource);

        assertEquals("test", gc.getProperty("betoffice.persistence.username"));
        assertEquals("test", gc.getProperty("betoffice.persistence.password"));
    }

}
