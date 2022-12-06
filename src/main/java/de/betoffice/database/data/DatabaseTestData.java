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

package de.betoffice.database.data;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.betoffice.database.test.ClasspathResource;
import de.betoffice.database.test.Complete;
import de.betoffice.database.test.CompleteExTipp;
import de.betoffice.database.test.Core;
import de.dbload.Dbload;

/**
 * Bereitet eine MySQL Datenbank für einen oder mehrere Testfälle vor. Geladen wird ein relativ komplexer Datenbestand.
 * Auf diesem Datenbestand sollten so viele Testfälle wie möglich abgehandelt werden.
 *
 * @author Andre Winkler
 */
public final class DatabaseTestData {

    private static final Logger log = LoggerFactory.getLogger(DatabaseTestData.class);

    public enum DataLoader {

        EMPTY(null),

        COMPLETE(Complete.instance()),

        COMPLETE_EX_TIPP(CompleteExTipp.instance()),

        CORE(Core.instance());

        private final ClasspathResource classpathResource;

        private DataLoader(final ClasspathResource resource) {
            classpathResource = resource;
        }

        ClasspathResource getResource() {
            return classpathResource;
        }
    };

    private final StopWatch stopWatch = new StopWatch();

    /**
     * Setup the test database with data.
     *
     * @param  conn         A database connection object
     * @param  dataLoader   Welche Daten sollen geladen werden?
     * @throws SQLException Datenbank konnte nicht geleert werden.
     */
    public void setUp(final Connection conn, final DataLoader dataLoader) throws SQLException {
        stopWatch.start();
        DeleteDatabase.deleteDatabase(conn);
        stopWatch.stop();

        if (log.isInfoEnabled()) {
            log.info("Deleting the database: {} ms", stopWatch.getTime());
        }

        if (!DataLoader.EMPTY.equals(dataLoader)) {
            stopWatch.reset();

            stopWatch.start();
            Dbload.read(conn, dataLoader.getResource().getClass(), dataLoader.getResource().name());
            conn.commit();
            stopWatch.stop();

            if (log.isInfoEnabled()) {
                log.info("Setup the database: {} ms", stopWatch.getTime());
            }
        }
    }

}
