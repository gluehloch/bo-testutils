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

import org.junit.Ignore;
import org.junit.Test;

import de.betoffice.database.commandline.CommandLineArguments;
import de.betoffice.database.commandline.CommandLineParser;

/**
 * Test for class {@link ExportDatabase}.
 *
 * @author Andre Winkler
 */
public class ExportDatabaseTest {

    //@Ignore
    @Test
    public void testExportDatabase() {
        CommandLineParser clp = new CommandLineParser();
        CommandLineArguments arguments = clp.parse(
                new String[] {
                        "-export",
                        "-u",
                        "betoffice",
                        "-p",
                        "betoffice",
                        "-d",
                        "jdbc:mysql://localhost/betoffice",
                        "-f",
                        "E:/tmp/export.dat",
                        "-t",
                        //"bo_team", "bo_teamalias"},
                        "bo_team,bo_grouptype,bo_user,bo_season,"
                        + "bo_location,bo_group,bo_player,"
                        + "bo_team_group,bo_teamalias,bo_user_season,"
                        + "bo_gamelist,bo_game,bo_gametipp,"
                        + "bo_goal"},
                System.out);
        ExportDatabase.start(arguments);
    }

}
