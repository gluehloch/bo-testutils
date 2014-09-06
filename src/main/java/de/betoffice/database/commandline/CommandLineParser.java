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

package de.betoffice.database.commandline;

import java.io.PrintStream;
import java.io.PrintWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import de.betoffice.database.commandline.CommandLineArguments.Command;

/**
 * Command line parser for database export/import.
 *
 * @author Andre Winkler
 */
public class CommandLineParser {

    public static final String TABLES = "t";
    public static final String FILE = "f";
    public static final String JDBCURL = "d";
    public static final String PASSWORD = "p";
    public static final String USERNAME = "u";
    public static final String HELP = "h";

    public static final String SCHEMANAME = "schema";
    public static final String USER = "user";
    public static final String USER_PASSWORD = "password";
    public static final String SU = "su";
    public static final String SU_PASSWORD = "supassword";

    public static final String COMMAND_EXPORT = "export";
    public static final String COMMAND_IMPORT = "import";
    public static final String COMMAND_CREATE = "createschema";

    @SuppressWarnings("static-access")
    private Options parseCommandLine(String[] args) {
        Option exportOption = OptionBuilder.withArgName(COMMAND_EXPORT)
                .withDescription("Export some tables of the database.")
                .create(COMMAND_EXPORT);
        Option importOption = OptionBuilder.withArgName(COMMAND_IMPORT)
                .withDescription("Import some data to the database.")
                .create(COMMAND_IMPORT);
        Option createSchemaOption = OptionBuilder
                .withArgName(COMMAND_CREATE)
                .withDescription(
                        "Creates application and administration users with different grants.")
                .create(COMMAND_CREATE);

        Option createSchema = OptionBuilder.withArgName(SCHEMANAME)
                .withDescription("The name of the schema to create.").hasArg()
                .create(SCHEMANAME);
        Option userDatabase = OptionBuilder.withArgName(USER)
                .withDescription("The name of the database user to create.")
                .hasArg().create(USER);
        Option userPasswordDatabase = OptionBuilder
                .withArgName(USER_PASSWORD)
                .withDescription("The password of the database user to create.")
                .hasArg().create(USER_PASSWORD);
        Option suDatabase = OptionBuilder
                .withArgName(SU)
                .withDescription(
                        "The name of the database super user to create.")
                .hasArg().create(SU);
        Option suPasswordDatabase = OptionBuilder
                .withArgName(SU_PASSWORD)
                .withDescription(
                        "The name of the database super user password to create.")
                .hasArg().create(SU_PASSWORD);

        Option username = OptionBuilder.withArgName(CommandLineParser.USERNAME)
                .withLongOpt("username").hasArg()
                .withDescription("User for login.").isRequired()
                .create(CommandLineParser.USERNAME);
        Option password = OptionBuilder.withArgName(CommandLineParser.PASSWORD)
                .withLongOpt("password").hasArg()
                .withDescription("Password for login").isRequired(false)
                .create(CommandLineParser.PASSWORD);
        Option jdbcUrl = OptionBuilder.withArgName(CommandLineParser.JDBCURL)
                .withLongOpt("database").hasArg()
                .withDescription("jdbc database url").isRequired()
                .create(CommandLineParser.JDBCURL);
        Option file = OptionBuilder.withArgName(CommandLineParser.FILE)
                .withLongOpt("file").hasArg()
                .withDescription("the file to wrtite to").isRequired(false)
                .create(CommandLineParser.FILE);

        Option tables = OptionBuilder.withArgName(CommandLineParser.TABLES)
                .withLongOpt("tables").hasArgs().withValueSeparator(',')
                .withDescription("the tables to export").isRequired(false)
                .create(CommandLineParser.TABLES);

        Option help = OptionBuilder.withArgName(CommandLineParser.HELP)
                .withLongOpt("help").hasArg(false)
                .withDescription("print this help")
                .create(CommandLineParser.HELP);

        Options options = new Options();
        options.addOption(createSchema);
        options.addOption(userDatabase);
        options.addOption(userPasswordDatabase);
        options.addOption(suDatabase);
        options.addOption(suPasswordDatabase);

        options.addOption(exportOption);
        options.addOption(importOption);
        options.addOption(createSchemaOption);
        options.addOption(username);
        options.addOption(password);
        options.addOption(jdbcUrl);
        options.addOption(file);
        options.addOption(tables);
        options.addOption(help);

        return options;
    }

    /**
     * Parse the command line.
     *
     * @param args
     *            the command line arguments
     * @param ps
     *            the print stream
     * @return The properties from the command line
     */
    public CommandLineArguments parse(String[] args, PrintStream ps) {
        Options options = parseCommandLine(args);
        org.apache.commons.cli.CommandLineParser parser = new GnuParser();
        CommandLine commandLine = null;

        try {
            commandLine = parser.parse(options, args);
        } catch (MissingOptionException ex) {
            ps.println(String.format("%s", ex.getMessage()));
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("betoffice database csv export tool", options);
            formatter.printUsage(new PrintWriter(ps), 30, "use me....");
            return null;
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }

        CommandLineArguments edp = null;
        if (commandLine.hasOption(CommandLineParser.HELP)) {
            printHelp(options);
        } else {
            edp = new CommandLineArguments();
            setupConnectionProperties(edp, commandLine);
            if (commandLine.hasOption(COMMAND_EXPORT)) {
                edp.setCommand(Command.EXPORT);
            } else if (commandLine.hasOption(COMMAND_IMPORT)) {
                edp.setCommand(Command.IMPORT);
            } else if (commandLine.hasOption(COMMAND_CREATE)) {
                edp.setCommand(Command.CREATE_SCHEMA);
                setupCommandLineArgumentsForDatabaseCreate(edp, commandLine);
            } else {
                System.out.println("Missing command parameter.");
                printHelp(options);
            }
        }

        return edp;
    }

    private void setupConnectionProperties(CommandLineArguments edp,
            CommandLine commandLine) {

        edp.setUsername(commandLine.getOptionValue(USERNAME));
        if (commandLine.hasOption(PASSWORD)) {
            edp.setPassword(commandLine.getOptionValue(PASSWORD));
        }
        edp.setJdbcUrl(commandLine.getOptionValue(JDBCURL));
        edp.setFile(commandLine.getOptionValue(FILE));
        edp.setTables(commandLine.getOptionValues(TABLES));
    }

    private void setupCommandLineArgumentsForDatabaseCreate(
            CommandLineArguments cla, CommandLine commandLine) {

        CreateSchemaProperties csp = new CreateSchemaProperties();
        cla.setCreateSchemaProperties(csp);
        csp.setSchema(commandLine.getOptionValue(SCHEMANAME));
        csp.setUser(commandLine.getOptionValue(USER));
        csp.setUserPassword(commandLine.getOptionValue(USER_PASSWORD));
        csp.setSu(commandLine.getOptionValue(SU));
        csp.setSuPassword(commandLine.getOptionValue(SU_PASSWORD));
    }

    private void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("betoffice database csv import/export tool",
                options);
    }

}
