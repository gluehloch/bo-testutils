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

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 * Utility to delete the betoffice database.
 * 
 * @author Andre Winkker
 */
public class DeleteDatabase {

    /**
     * Deletes all data from all betoffice tables.
     * 
     * @param connection a database connection
     */
    public static void deleteDatabase(final Connection connection) {
        try {
            SingleConnectionDataSource scds = new SingleConnectionDataSource(
                    connection, true);
            scds.setAutoCommit(false);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(scds);
            jdbcTemplate.execute("DELETE FROM bo_gametipp");
            jdbcTemplate.execute("DELETE FROM bo_game");
            jdbcTemplate.execute("DELETE FROM bo_gamelist");
            jdbcTemplate.execute("DELETE FROM bo_team_group");
            jdbcTemplate.execute("DELETE FROM bo_group");
            jdbcTemplate.execute("DELETE FROM bo_user_season");
            jdbcTemplate.execute("DELETE FROM bo_season");
            jdbcTemplate.execute("DELETE FROM bo_teamalias");
            jdbcTemplate.execute("DELETE FROM bo_team");
            jdbcTemplate.execute("DELETE FROM bo_user");
            jdbcTemplate.execute("DELETE FROM bo_grouptype");
            connection.commit();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
