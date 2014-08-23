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

import java.sql.Connection;

/**
 * Creates the betoffice database schema.
 * 
 * @author Andre Winkler
 */
public class CreateSchema {

    public static final String DATABASE_NAME = "{{botest}}";
    public static final String USER = "{{test}}";
    public static final String USER_SU = "{{testsu}}";
    
    public void createSchema(Connection connection) {
        String createDatabase = "CREATE DATABASE `{{botest}}` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci";

        String user1 = "CREATE USER '{{test}}'@'localhost' IDENTIFIED BY '{{test}}'";
        String user2 = "CREATE USER '{{test}}'@'%' IDENTIFIED BY '{{test}}'";

        String userSu1 = "CREATE USER '{{testsu}'@'localhost' IDENTIFIED BY '{{test}}'";
        String userSu2 = "CREATE USER '{{testsu}'@'%' IDENTIFIED BY '{{test}}'";

        String revokePrivilegeTest1 = "REVOKE ALL PRIVILEGES ON * . * FROM '{{test}}'@'localhost'";
        String revokePrivilegeTest2 = "REVOKE ALL PRIVILEGES ON * . * FROM '{{test}}'@'%'";

        String revokePrivilegeSu1 = "REVOKE ALL PRIVILEGES ON * . * FROM '{{testsu}'@'localhost'";
        String revokePrivilegeSu2 = "REVOKE ALL PRIVILEGES ON * . * FROM '{{testsu}'@'%'";

        String grantTest1 = "GRANT SELECT, INSERT, UPDATE, DELETE"
                + " ON {{botest}}. *" + " TO '{{test}}'@'localhost'"
                + " WITH GRANT OPTION" + " MAX_QUERIES_PER_HOUR 0"
                + " MAX_CONNECTIONS_PER_HOUR 0" + " MAX_UPDATES_PER_HOUR 0"
                + " MAX_USER_CONNECTIONS 0";

        String grantTest2 = "GRANT SELECT, INSERT, UPDATE, DELETE"
                + " ON {{botest}}. *" + " TO '{{test}}'@'%'"
                + " WITH GRANT OPTION " + " MAX_QUERIES_PER_HOUR 0"
                + " MAX_CONNECTIONS_PER_HOUR 0" + " MAX_UPDATES_PER_HOUR 0 "
                + " MAX_USER_CONNECTIONS 0";

        String grantTestSu1 = "GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, INDEX, DROP,"
                + " CREATE TEMPORARY TABLES, SHOW VIEW, CREATE ROUTINE, ALTER ROUTINE,"
                + " EXECUTE, CREATE VIEW, EVENT, TRIGGER, LOCK TABLES"
                + " ON {{botest}}. *"
                + " TO '{{testsu}'@'localhost' WITH GRANT OPTION MAX_QUERIES_PER_HOUR 0"
                + " MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0"
                + " MAX_USER_CONNECTIONS 0";

        String grantTestSu2 = "GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, INDEX, DROP,"
                + " CREATE TEMPORARY TABLES, SHOW VIEW, CREATE ROUTINE, ALTER ROUTINE,"
                + " EXECUTE, CREATE VIEW, EVENT, TRIGGER, LOCK TABLES"
                + " ON {{botest}}. *"
                + " TO '{{testsu}'@'%' WITH GRANT OPTION MAX_QUERIES_PER_HOUR 0"
                + " MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0"
                + " MAX_USER_CONNECTIONS 0";
    }

}
