/*
 * Copyright (C) <2017>  <AlphaHelixDev>
 *
 *       This program is free software: you can redistribute it under the
 *       terms of the GNU General Public License as published by
 *       the Free Software Foundation, either version 3 of the License.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU General Public License for more details.
 *
 *       You should have received a copy of the GNU General Public License
 *       along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.alphahelix.alphabungeelibary.mysql;

import de.alphahelix.alphabungeelibary.files.SimpleYAMLFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MySQLAPI {

    private static ArrayList<MySQLAPI> mysqlDBs = new ArrayList<>();
    private static HashMap<String, Connection> cons = new HashMap<>();
    private static MySQLFileManager fm = new MySQLFileManager();

    static {
        fm.setupConnection();
    }

    private String username;
    private String password;
    private String database;
    private String host;
    private String port;

    MySQLAPI(String username, String password, String database, String host, String port) {
        this.username = username;
        this.password = password;
        this.database = database;
        this.host = host;
        this.port = port;

        if (getMySQL(database) == null) {
            mysqlDBs.add(this);
        }
    }

    public static MySQLAPI getMySQL(String db) {
        for (MySQLAPI api : mysqlDBs) {
            if (api.getDatabase().equals(db)) return api;
        }
        return null;
    }

    public static ArrayList<MySQLAPI> getMysqlDBs() {
        return mysqlDBs;
    }

    /**
     * Gets the {@link Connection} to the database
     *
     * @return the {@link Connection}
     */
    public Connection getMySQLConnection() {
        return cons.get(database);
    }

    /**
     * Checks if the plugin is connected to the database
     *
     * @return plugin is connected to the database
     */
    public boolean isConnected() {
        return cons.containsKey(database);
    }

    /**
     * Creates a {@link Connection} to the database. Run this async inside your onEnable
     *
     * @throws SQLException
     */
    public void initMySQLAPI() {
        if (!isConnected()) {
            try {
                cons.put(database, DriverManager.getConnection(
                        "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password));
            } catch (SQLException ignore) {
                System.out.println("LamaAPI konnte keine Verbindung zur Datenbank " + database + " aufbauen");
            }
        }
    }

    /**
     * Closes the {@link Connection} to the database. Run this inside your onDisable
     *
     * @throws SQLException
     */
    public void closeMySQLConnection() throws SQLException {
        if (isConnected()) {
            cons.get(database).close();
            cons.remove(database);
        }
    }

    public String getDatabase() {
        return database;
    }

    public enum MySQLDataType {
        VARCHAR, CHAR, TEXT, INT, BIGINT, SMALLINT, TINYINT
    }
}

class MySQLFileManager extends SimpleYAMLFile {

    MySQLFileManager() {
        super("ClazeBungeeAPI", "mysql");
        addValues();
    }

    public void addValues() {
        if (getFile().getKeys().size() > 0) return;

        setDefault("lamamain.username", "root");
        setDefault("lamamain.password", "password");
        setDefault("lamamain.host", "localhost");
        setDefault("lamamain.port", "3306");

        setDefault("lamapvp.username", "root");
        setDefault("lamapvp.password", "password");
        setDefault("lamapvp.host", "localhost");
        setDefault("lamapvp.port", "3306");
    }

    void setupConnection() {
        for (String dbName : getFile().getKeys()) {
            new MySQLAPI(
                    getFile().getString(dbName + ".username"),
                    getFile().getString(dbName + ".password"),
                    dbName,
                    getFile().getString(dbName + ".host"),
                    getFile().getString(dbName + ".port")
            );
        }
    }
}
