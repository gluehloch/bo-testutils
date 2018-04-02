/*
 * ============================================================================
 * Project betoffice-testutils Copyright (c) 2000-2014 by Andre Winkler. All
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

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.betoffice.database.test.Database;
import de.betoffice.database.test.DatabaseMinusTipp;
import de.betoffice.database.test.Masterdata;
import de.dbload.Dbload;

/**
 * Bereitet eine MySQL Datenbank für einen oder mehrere Testfälle vor. Geladen
 * wird ein relativ komplexer Datenbestand. Auf diesem Datenbestand sollten so
 * viele Testfälle wie möglich abgehandelt werden.
 *
 * @author Andre Winkler
 */
public final class MySqlDatabasedTestSupport {

    public enum DataLoader {

        EMPTY(null),

        FULL(Database.class),

        FULL_WITHOUT_TIPP(DatabaseMinusTipp.class),

        MASTER_DATA(Masterdata.class);

        private final Class<?> datResource;

        private DataLoader(final Class<?> resource) {
            datResource = resource;
        }

        /**
         * Returns the resource classpath.
         *
         * @return Die Resource.
         */
        Class<?> getResource() {
            return datResource;
        }
    };

    private static final Logger log = LoggerFactory
            .getLogger(MySqlDatabasedTestSupport.class);

    private StopWatch stopWatch = new StopWatch();

    /**
     * Setup the test database with data.
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

        stopWatch.start();
        DeleteDatabase.deleteDatabase(_conn);
        stopWatch.stop();

        if (log.isInfoEnabled()) {
            log.info("Deleting the database: {} ms", stopWatch.getTime());
        }

        if (!_dataLoader.equals(DataLoader.EMPTY)) {
            stopWatch.reset();

            stopWatch.start();
            Dbload.read(_conn, _dataLoader.getResource());
            _conn.commit();
            stopWatch.stop();

            if (log.isInfoEnabled()) {
                log.info("Setup the database: {} ms", stopWatch.getTime());
            }
        }
    }

}
