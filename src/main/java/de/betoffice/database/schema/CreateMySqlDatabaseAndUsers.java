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

import static org.apache.commons.lang.StringUtils.replace;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import de.betoffice.database.commandline.CommandLineArguments;
import de.dbload.jdbc.connector.JdbcConnector;

/**
 * Creates the betoffice database schema.
 *
 * @author Andre Winkler
 */
public class CreateMySqlDatabaseAndUsers {

    /** Parameter for the database name. */
    private static final String DATABASE = "{{database}}";

    /**
     * Parameter for the application user. Has less permissions then the super
     * user. SELECT, INSERT, UPDATE and DELETE are typical permissions for this
     * user.
     */
    private static final String USER = "{{user}}";

    /**
     * Parameter for the application´s user password.
     */
    private static final String USER_PASSWORD = "{{userPassword}}";

    /**
     * Parameter for the application super user. Has more permissions the the
     * normal application user. Is able to create or drop tables.
     */
    private static final String SU = "{{superUser}}";

    /**
     * Parameter for the application´s super user password.
     */
    private static final String SU_PASSWORD = "{{superUserPassword}}";

    //
    // SQL Statements for the MySQL root user.
    //

    private static final String CREATE_DATABASE = "CREATE DATABASE `{{database}}` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci";

    private static final String CREATE_USER_LOCALHOST = "CREATE USER '{{user}}'@'localhost' IDENTIFIED BY '{{userPassword}}'";
    private static final String CREATE_USER_REMOTE = "CREATE USER '{{user}}'@'%' IDENTIFIED BY '{{userPassword}}'";

    private static final String REVOKE_ALL_FROM_USER_LOCAL = "REVOKE ALL PRIVILEGES ON * . * FROM '{{user}}'@'localhost'";
    private static final String REVOKE_ALL_FROM_USER_REMOTE = "REVOKE ALL PRIVILEGES ON * . * FROM '{{user}}'@'%'";

    private static final String GRANT_USER_LOCAL = "GRANT SELECT, INSERT, UPDATE, DELETE"
            + " ON {{database}}. *"
            + " TO '{{user}}'@'localhost'"
            + " WITH GRANT OPTION"
            + " MAX_QUERIES_PER_HOUR 0"
            + " MAX_CONNECTIONS_PER_HOUR 0"
            + " MAX_UPDATES_PER_HOUR 0"
            + " MAX_USER_CONNECTIONS 0";

    private static final String GRANT_USER_REMOTE = "GRANT SELECT, INSERT, UPDATE, DELETE"
            + " ON {{database}}. *"
            + " TO '{{user}}'@'%'"
            + " WITH GRANT OPTION "
            + " MAX_QUERIES_PER_HOUR 0"
            + " MAX_CONNECTIONS_PER_HOUR 0"
            + " MAX_UPDATES_PER_HOUR 0 "
            + " MAX_USER_CONNECTIONS 0";

    private static final String GRANT_SU_LOCAL = "GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, INDEX, DROP,"
            + " CREATE TEMPORARY TABLES, SHOW VIEW, CREATE ROUTINE, ALTER ROUTINE,"
            + " EXECUTE, CREATE VIEW, EVENT, TRIGGER, LOCK TABLES"
            + " ON {{database}}. *"
            + " TO '{{superUser}}'@'localhost' WITH GRANT OPTION MAX_QUERIES_PER_HOUR 0"
            + " MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0"
            + " MAX_USER_CONNECTIONS 0";

    private static final String GRANT_SU_REMOTE = "GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, INDEX, DROP,"
            + " CREATE TEMPORARY TABLES, SHOW VIEW, CREATE ROUTINE, ALTER ROUTINE,"
            + " EXECUTE, CREATE VIEW, EVENT, TRIGGER, LOCK TABLES"
            + " ON {{database}}. *"
            + " TO '{{superUser}}'@'%' WITH GRANT OPTION MAX_QUERIES_PER_HOUR 0"
            + " MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0"
            + " MAX_USER_CONNECTIONS 0";

    /**
     * Create the database schema.
     *
     * @param connection
     *            The connection must be authorized by a MySQL root user.
     * @throws SQLException
     *             Ups
     */
    public static void createSchema(Connection connection, String database,
            String user, String userPassword, String superUser,
            String superUserPassword) throws SQLException {

        List<String> commands = new ArrayList<>();

        String sqlCreateDatabase = createDatabase(database);
        commands.add(sqlCreateDatabase);

        String sqlCreateUser = createLocalUser(user, userPassword);
        String sqlCreateUserRemote = createRemoteUser(user, userPassword);
        commands.add(sqlCreateUser);
        commands.add(sqlCreateUserRemote);

        String sqlCreateSuLocalhost = createLocalUser(superUser,
                superUserPassword);
        String sqlCreateSuRemote = createRemoteUser(superUser,
                superUserPassword);
        commands.add(sqlCreateSuLocalhost);
        commands.add(sqlCreateSuRemote);

        String sqlRevokeAllFromUserLocal = createRevokeAllFromLocalUser(user);
        String sqlRevokeAllFromUserRemote = createRevokeAllFromRemoteUser(user);
        String sqlRevokeAllFromSuLocal = createRevokeAllFromLocalUser(superUser);
        String sqlRevokeAllFromSuRemote = createRevokeAllFromRemoteUser(superUser);
        commands.add(sqlRevokeAllFromUserLocal);
        commands.add(sqlRevokeAllFromUserRemote);
        commands.add(sqlRevokeAllFromSuLocal);
        commands.add(sqlRevokeAllFromSuRemote);

        String sqlGrantUserLocal = createGrantsForUserLocal(database, user);
        String sqlGrantUserRemote = createGrantsForUserRemote(database, user);
        String sqlGrantSuLocal = createGrantsForSuLocal(database, superUser);
        String sqlGrantSuRemote = createGrantsForSuRemote(database, superUser);
        commands.add(sqlGrantUserLocal);
        commands.add(sqlGrantUserRemote);
        commands.add(sqlGrantSuLocal);
        commands.add(sqlGrantSuRemote);

        for (String command : commands) {
            System.out.println(command);
            Statement stmt = connection.createStatement();
            stmt.execute(command);
        }
    }

    public static String createDatabase(String database) {
        return replace(CREATE_DATABASE, DATABASE, database);
    }

    public static String createLocalUser(String user, String password) {
        return replace(replace(CREATE_USER_LOCALHOST, USER, user),
                USER_PASSWORD, password);
    }

    public static String createRemoteUser(String user, String password) {
        return replace(replace(CREATE_USER_REMOTE, USER, user), USER_PASSWORD,
                password);
    }

    public static String createRevokeAllFromLocalUser(String user) {
        return replace(REVOKE_ALL_FROM_USER_LOCAL, USER, user);
    }

    public static String createRevokeAllFromRemoteUser(String user) {
        return replace(REVOKE_ALL_FROM_USER_REMOTE, USER, user);
    }

    public static String createGrantsForUserLocal(String database, String user) {
        return replace(replace(GRANT_USER_LOCAL, DATABASE, database), USER,
                user);
    }

    public static String createGrantsForUserRemote(String database, String user) {
        return replace(replace(GRANT_USER_REMOTE, DATABASE, database), USER,
                user);
    }

    public static String createGrantsForSuLocal(String database, String user) {
        return replace(replace(GRANT_SU_LOCAL, DATABASE, database), SU, user);
    }

    public static String createGrantsForSuRemote(String database, String user) {
        return replace(replace(GRANT_SU_REMOTE, DATABASE, database), SU, user);
    }

    public static void start(CommandLineArguments edp) throws SQLException {
        Connection connection = JdbcConnector.createConnection(
                edp.getUsername(), edp.getPassword(), edp.getJdbcUrl());

        if (StringUtils.isEmpty(edp.getCreateSchemaProperties().getUser())) {
            throw new IllegalArgumentException("The user to create is missing!");
        }
        if (StringUtils.isEmpty(edp.getCreateSchemaProperties().getSu())) {
            throw new IllegalArgumentException("The su to create is missing!");
        }
        if (StringUtils.isEmpty(edp.getCreateSchemaProperties().getSchema())) {
            throw new IllegalArgumentException(
                    "The schema name to create is missing!");
        }

        createSchema(connection, edp.getCreateSchemaProperties().getSchema(),
                edp.getCreateSchemaProperties().getUser(), edp
                        .getCreateSchemaProperties().getUserPassword(), edp
                        .getCreateSchemaProperties().getSu(), edp
                        .getCreateSchemaProperties().getSuPassword());
    }

}
