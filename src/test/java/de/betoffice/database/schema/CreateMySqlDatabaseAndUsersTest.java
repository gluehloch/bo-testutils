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

package de.betoffice.database.schema;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Creates the database schema.
 * 
 * @author Andre Winkler
 */
public class CreateMySqlDatabaseAndUsersTest {

    @Test
    public void testCreateSchemaCreateDatabase() {
        String sqlCreateDatabase = CreateMySqlDatabaseAndUsers.createDatabase("mydatabase");
        assertThat(
                sqlCreateDatabase,
                equalTo("CREATE DATABASE `mydatabase` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci"));
    }

    @Test
    public void testCreateSchemaCreateLocalUser() {
        String sqlCreateUser = CreateMySqlDatabaseAndUsers.createLocalUser("winkler",
                "winklerandre");
        assertThat(
                sqlCreateUser,
                equalTo("CREATE USER 'winkler'@'localhost' IDENTIFIED BY 'winklerandre'"));
    }

    @Test
    public void testCreateSchemaCreateRemoteUser() {
        String sqlCreateUser = CreateMySqlDatabaseAndUsers.createRemoteUser("winkler",
                "winklerandre");
        assertThat(
                sqlCreateUser,
                equalTo("CREATE USER 'winkler'@'%' IDENTIFIED BY 'winklerandre'"));
    }

}