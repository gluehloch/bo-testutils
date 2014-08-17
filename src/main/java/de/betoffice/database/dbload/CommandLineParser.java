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

    @SuppressWarnings("static-access")
    private Options parseCommandLine(String[] args) {
        Option username = OptionBuilder.withArgName(CommandLineParser.USERNAME)
                .withLongOpt("username").hasArg()
                .withDescription("User for login.").isRequired()
                .create(CommandLineParser.USERNAME);
        Option password = OptionBuilder.withArgName(CommandLineParser.PASSWORD)
                .withLongOpt("password").hasArg()
                .withDescription("Password for login").isRequired()
                .create(CommandLineParser.PASSWORD);
        Option jdbcUrl = OptionBuilder.withArgName(CommandLineParser.JDBCURL)
                .withLongOpt("database").hasArg()
                .withDescription("jdbc database url").isRequired()
                .create(CommandLineParser.JDBCURL);
        Option file = OptionBuilder.withArgName(CommandLineParser.FILE)
                .withLongOpt("file").hasArg()
                .withDescription("the file to wrtite to").isRequired()
                .create(CommandLineParser.FILE);

        Option tables = OptionBuilder.withArgName(CommandLineParser.TABLES)
                .withLongOpt("tables").hasArgs().withValueSeparator(',')
                .withDescription("the tables to export")
                .create(CommandLineParser.TABLES);

        Option help = OptionBuilder.withArgName(CommandLineParser.HELP)
                .withLongOpt("help").hasArg(false)
                .withDescription("print this help")
                .create(CommandLineParser.HELP);

        Options options = new Options();
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
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("betoffice database csv import/export tool",
                    options);
        } else {
            edp = new CommandLineArguments();
            edp.setUsername(commandLine.getOptionValue(USERNAME));
            edp.setPassword(commandLine.getOptionValue(PASSWORD));
            edp.setJdbcUrl(commandLine.getOptionValue(JDBCURL));
            edp.setFile(commandLine.getOptionValue(FILE));
            edp.setTables(commandLine.getOptionValues(TABLES));
        }

        return edp;
    }

}
