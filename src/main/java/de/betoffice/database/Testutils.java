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

package de.betoffice.database;

import java.sql.SQLException;

import de.betoffice.database.commandline.CommandLineArguments;
import de.betoffice.database.commandline.CommandLineParser;
import de.betoffice.database.dbload.ExportDatabase;
import de.betoffice.database.schema.CreateMySqlDatabaseAndUsers;

/**
 * Command line processor.
 *
 * @author Andre Winkler
 */
public class Testutils {

    public static void main(String[] args) {
        CommandLineParser clp = new CommandLineParser();
        CommandLineArguments arguments = clp.parse(args, System.out);

        if (arguments != null) {
            switch (arguments.getCommand()) {
            case EXPORT:
                ExportDatabase.start(arguments);
                break;
            case IMPORT:
                ExportDatabase.start(arguments);
                break;
            case CREATE_SCHEMA:
                try {
                    CreateMySqlDatabaseAndUsers.start(arguments);
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
                break;
            default:
                System.out.println("Unknown command line arguments.");
            }
        }
    }

}
