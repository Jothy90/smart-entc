package org.smart.entc.repo;

import com.mysql.jdbc.Connection;
import org.slf4j.LoggerFactory;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DBConnector.class);
    private static Connection connection = null;

    /**
     * Get a JDBC connection.
     *
     * @return connection or null
     */
    public static synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                LOGGER.debug("JDBC connection is null and connection now");
                Class.forName("com.mysql.jdbc.Driver");
                connection = (Connection) DriverManager
                        .getConnection("jdbc:mysql://localhost:3306/fyp","root","root");

                /*connection = (Connection) DriverManager
                        .getConnection(Property.getValue("mysql.url"), Property.getValue("mysql.username"), Property.getValue("mysql.password"));
*/                connection.setAutoReconnect(true);
            } else {
                LOGGER.debug("Returning existing JDBC connection ");
                return connection;
            }
        } catch (ClassNotFoundException e) {
            LOGGER.error("Where is your MySQL JDBC Driver?" + e);
            e.printStackTrace();
            connection = null;
        } catch (SQLException e) {
            LOGGER.error("Connection Failed!" + e);
            e.printStackTrace();
            connection = null;
        } catch (Exception e) {
            LOGGER.error("Connection Failed!" + e);
            e.printStackTrace();
            connection = null;
        }
        return connection;
    }

    /**
     * @param con
     */
    public static void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            LOGGER.error("SQL Exception in closing JDBC connection!" + e);
        }
    }
}
