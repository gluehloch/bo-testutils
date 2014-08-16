/*
 * ============================================================================
 * Project betoffice-storage Copyright (c) 2000-2014 by Andre Winkler. All
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
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import de.dbload.Dbload;
import de.dbload.csv.writer.ResourceWriter;
import de.dbload.jdbc.connector.JdbcConnector;

/**
 * Export database with Dbload.
 * 
 * @author Andre Winkler
 */
public class ExportDatabase {

    private static final String TABLES = "t";

    private static final String FILE = "f";

    private static final String JDBCURL = "d";

    private static final String PASSWORD = "p";

    private static final String USERNAME = "u";

    private static final String HELP = "h";

    public static void main(String[] args) {
        ExportDatabase ed = new ExportDatabase();
        Options options = ed.parseCommandLine(args);

        CommandLineParser parser = new GnuParser();
        CommandLine commandLine = null;
        try {
            commandLine = parser.parse(options, args);
        } catch (MissingOptionException ex) {
            System.out.println(String.format("%s", ex.getMessage()));
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("betoffice database csv export tool", options);
            formatter.printUsage(new PrintWriter(System.out), 30, "use me....");
            System.exit(0);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }

        if (commandLine.hasOption(ExportDatabase.HELP)) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("betoffice database csv export tool", options);
        } else {
            ExportDatabaseProperties edp = new ExportDatabaseProperties();
            edp.setUsername(commandLine.getOptionValue(USERNAME));
            edp.setPassword(commandLine.getOptionValue(PASSWORD));
            edp.setJdbcUrl(commandLine.getOptionValue(JDBCURL));
            edp.setFile(commandLine.getOptionValue(FILE));
            edp.setTables(commandLine.getOptionValues(TABLES));

            File file = new File(edp.getFile());
            if (file.exists()) {
                file.delete();
            }
            ResourceWriter resourceWriter = new ResourceWriter(file);

            Connection connection = JdbcConnector.createConnection(
                    edp.getUsername(), edp.getPassword(), edp.getJdbcUrl());

            for (String table : edp.getTables()) {
                try {
                    Dbload.write(connection, new File(edp.getFile()), edp.getTables());
                    resourceWriter.start(connection, table, true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @SuppressWarnings("static-access")
    private Options parseCommandLine(String[] args) {
        Option username = OptionBuilder.withArgName(ExportDatabase.USERNAME)
                .withLongOpt("username").hasArg()
                .withDescription("User for login.").isRequired()
                .create(ExportDatabase.USERNAME);
        Option password = OptionBuilder.withArgName(ExportDatabase.PASSWORD)
                .withLongOpt("password").hasArg()
                .withDescription("Password for login").isRequired()
                .create(ExportDatabase.PASSWORD);
        Option jdbcUrl = OptionBuilder.withArgName(ExportDatabase.JDBCURL)
                .withLongOpt("database").hasArg()
                .withDescription("jdbc database url").isRequired()
                .create(ExportDatabase.JDBCURL);
        Option file = OptionBuilder.withArgName(ExportDatabase.FILE)
                .withLongOpt("file").hasArg()
                .withDescription("the file to wrtite to").isRequired()
                .create(ExportDatabase.FILE);

        Option tables = OptionBuilder.withArgName(ExportDatabase.TABLES)
                .withLongOpt("tables").hasArgs().withValueSeparator(',')
                .withDescription("the tables to export")
                .create(ExportDatabase.TABLES);

        Option help = OptionBuilder.withArgName(ExportDatabase.HELP)
                .withLongOpt("help").hasArg(false)
                .withDescription("print this help").create(ExportDatabase.HELP);

        Options options = new Options();
        options.addOption(username);
        options.addOption(password);
        options.addOption(jdbcUrl);
        options.addOption(file);
        options.addOption(tables);
        options.addOption(help);

        return options;
    }

}
