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

package de.betoffice.database.commandline;

/**
 * Schema properties.
 *
 * @author Andre Winkler
 */
public class CreateSchemaProperties {

    private String user;
    private String userPassword;
    private String su;
    private String suPassword;
    private String schema;

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the userPassword
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * @param userPassword
     *            the userPassword to set
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * @return the su
     */
    public String getSu() {
        return su;
    }

    /**
     * @param su
     *            the su to set
     */
    public void setSu(String su) {
        this.su = su;
    }

    /**
     * @return the suPassword
     */
    public String getSuPassword() {
        return suPassword;
    }

    /**
     * @param suPassword
     *            the suPassword to set
     */
    public void setSuPassword(String suPassword) {
        this.suPassword = suPassword;
    }

    /**
     * @return the schema
     */
    public String getSchema() {
        return schema;
    }

    /**
     * @param schema
     *            the schema to set
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CreateSchemaProperties [user=" + user + ", userPassword="
                + userPassword + ", su=" + su + ", suPassword=" + suPassword
                + ", schema=" + schema + "]";
    }

}
