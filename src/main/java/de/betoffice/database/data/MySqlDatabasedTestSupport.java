/*
 * ============================================================================
 * Project betoffice-testutilss Copyright (c) 2000-2014 by Andre Winkler. All
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

package de.betoffice.database.data;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import de.betoffice.database.dbunit.ImportDatabase;
import de.betoffice.database.hibernate.HibernateConnectionFactory;

/**
 * Bereitet eine MySQL Datenbank für einen oder mehrere Testfälle vor. Geladen
 * wird ein relativ komplexer Datenbestand. Auf diesem Datenbestand sollten so
 * viele Testfälle wie möglich abgehandelt werden.
 * 
 * @author Andre Winkler
 */
public final class MySqlDatabasedTestSupport {

    public enum DataLoader {

        /** Eine leere Datenbank. */
        EMPTY(""),

        /** Die kompletten Produktionsdaten einladen. */
        FULL("/de/winkler/betoffice/test/database/database.xml"),

        /** Nur die Stammdaten einladen. */
        MASTER_DATA("/de/winkler/betoffice/test/database/masterdata.xml");

        /** Die zu ladende XML Resource. */
        private final String xmlResource;

        private DataLoader(final String resource) {
            xmlResource = resource;
        }

        /**
         * Liefert die XML Resource.
         * 
         * @return Die Resource.
         */
        String getResource() {
            return xmlResource;
        }
    };

    /**
     * Erstellt wenn nötig das Schema und spielt die Testdaten ein. Die
     * Testdaten enthalten die Produktionsdaten der Spielzeiten 2000 - 2007.
     * 
     * @param _conn
     *            A database connection object
     * @param _dataLoader
     *            Welche Daten sollen geladen werden?
     * @throws SQLException
     *             Datenbank konnte nicht geleert werden.
     */
    public void setUp(final Connection _conn, final DataLoader _dataLoader)
            throws SQLException {

        DeleteDatabase.deleteDatabase(_conn);
        if (!_dataLoader.equals(DataLoader.EMPTY)) {
            ImportDatabase importDatabase = new ImportDatabase();
            InputStream xmlStream = this.getClass().getResourceAsStream(
                    _dataLoader.getResource());
            InputStream dtdStream = this.getClass().getResourceAsStream(
                    "/de/winkler/betoffice/test/database/database.dtd");
            importDatabase.load(_conn, xmlStream, dtdStream);
            _conn.commit();
        }
    }

    /**
     * Lädt die MySQL Datenbank mit Produktionsdaten für den Integrationstest.
     * 
     * @param classes
     *            Hibernate entity classes
     * @throws SQLException
     *             Some errors here
     */
    public static void start(List<Class<?>> classes) throws SQLException {
        HibernateConnectionFactory factory = new HibernateConnectionFactory(
                classes);
        Connection conn = factory.getHibernateProperties().createConnection();
        conn.setAutoCommit(false);

        try {
            MySqlDatabasedTestSupport mySqlDatabasedTestSupport = new MySqlDatabasedTestSupport();
            mySqlDatabasedTestSupport.setUp(conn, DataLoader.FULL);
            conn.commit();
        } catch (Exception ex) {
            conn.rollback();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

}
