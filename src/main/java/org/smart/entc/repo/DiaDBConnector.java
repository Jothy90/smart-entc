package org.smart.entc.repo;

import com.mysql.jdbc.Connection;
import org.smart.entc.util.Property;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project YIT DIA
 * Created by jaykrish on 5/25/14.
 */
public class DiaDBConnector {
    private static final Logger LOGGER = Logger.getLogger(DiaDBConnector.class.getName());
    private static Connection connection = null;

    /**
     * Get a JDBC connection.
     *
     * @return connection or null
     */
    public static synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                LOGGER.log(Level.ALL, "JDBC connection is null and connection now");
                Class.forName("com.mysql.jdbc.Driver");
                connection = (Connection) DriverManager
                        .getConnection(Property.getValue("mysql.url"), Property.getValue("mysql.username"), Property.getValue("mysql.password"));
                connection.setAutoReconnect(true);
            } else {
                LOGGER.log(Level.ALL, "Returning existing JDBC connection ");
                return connection;
            }
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Where is your MySQL JDBC Driver?" + e);
            connection = null;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Connection Failed!" + e);
            connection = null;
        }
        return connection;
    }

    /**
     *
     * @param con
     */
    public static void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception in closing JDBC connection!" + e);
        }
    }
}
