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

import de.betoffice.database.test.Database;
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
            Dbload.read(_conn, _dataLoader.getResource());
            _conn.commit();
        }
    }

}
