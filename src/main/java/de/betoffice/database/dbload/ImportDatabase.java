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

package de.betoffice.database.dbload;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import de.betoffice.database.data.DeleteDatabase;
import de.dbload.Dbload;
import de.dbload.jdbc.connector.JdbcConnector;

/**
 * Imports a dbload file.
 * 
 * @author Andre Winkler
 */
public class ImportDatabase {

    public static void main(String[] args) {
        CommandLineParser clp = new CommandLineParser();
        CommandLineArguments edp = clp.parse(args, System.out);
        if (edp != null) {
            Connection connection = JdbcConnector.createConnection(
                    edp.getUsername(), edp.getPassword(), edp.getJdbcUrl());
            
            DeleteDatabase.deleteDatabase(connection);

            Dbload.read(connection, new File(edp.getFile()));
            try {
                connection.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}