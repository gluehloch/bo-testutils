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

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Export database with Dbload.
 * 
 * @author Andre Winkler
 */
public class ExportDatabase {

    public static void main(String[] args) {
        parseCommandLine(args);

        /*
         * File file = Files.newFile("C:/tmp/betoffice/betoffice.dat");
         * ResourceWriter resourceWriter = new ResourceWriter(file);
         * resourceWriter.start(conn, sqlSelect, append);
         */
    }

    /** Die CommandLine. */
    private static CommandLine line;

    private static void parseCommandLine(String[] args) {
        Option username = OptionBuilder.withArgName("u")
                .withLongOpt("username").hasArgs()
                .withDescription("User for login.").create("u");

        Options options = new Options();
        options.addOption(username);

        CommandLineParser parser = new GnuParser();
        try {
            line = parser.parse(options, args);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("betoffice database export tool", options);

        if (line.hasOption("u")) {
            System.out.println(line.getOptionValue("u"));
        } else {
            System.out.println("Empty command line!");
        }
    }

}
