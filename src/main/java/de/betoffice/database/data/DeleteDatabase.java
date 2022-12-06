/*
 * ============================================================================
 * Project betoffice-testutils Copyright (c) 2000-2019 by Andre Winkler. All
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
import java.sql.Statement;

/**
 * Utility to delete the betoffice database.
 * 
 * @author Andre Winkker
 */
public class DeleteDatabase {

    /**
     * Deletes all data from all betoffice tables.
     * 
     * @param connection
     *            a database connection
     */
    public static void deleteDatabase(Connection connection) {
        executeStatement(connection, "DELETE FROM bo_session");
        executeStatement(connection, "DELETE FROM bo_community_user");
        executeStatement(connection, "DELETE FROM bo_community");
        executeStatement(connection, "DELETE FROM bo_goal");
        executeStatement(connection, "DELETE FROM bo_gametipp");
        executeStatement(connection, "DELETE FROM bo_game");
        executeStatement(connection, "DELETE FROM bo_gamelist");
        executeStatement(connection, "DELETE FROM bo_team_group");
        executeStatement(connection, "DELETE FROM bo_group");
        executeStatement(connection, "DELETE FROM bo_user_season");
        executeStatement(connection, "DELETE FROM bo_season");
        executeStatement(connection, "DELETE FROM bo_teamalias");
        executeStatement(connection, "DELETE FROM bo_team");
        executeStatement(connection, "DELETE FROM bo_user");
        executeStatement(connection, "DELETE FROM bo_grouptype");
        executeStatement(connection, "DELETE FROM bo_location");
        executeStatement(connection, "DELETE FROM bo_player");

        try (Statement stmt = connection.createStatement()) {
            connection.commit();
        } catch (SQLException ex) {
            throw new RuntimeException("Unable to delete the botest database!", ex);
        }
    }

    private static void executeStatement(Connection connection, String statement) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(statement);
        } catch (SQLException ex) {
            System.out.println(String.format("Unable to execute statement: %s", statement));
        }
    }

}
