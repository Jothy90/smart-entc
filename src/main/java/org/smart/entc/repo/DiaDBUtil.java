package org.smart.entc.repo;

import com.mysql.jdbc.Connection;
import org.smart.entc.repo.object.Node;
import org.smart.entc.repo.object.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project YIT DIA
 * Created by jaykrish on 5/25/14.
 */
public class DiaDBUtil {
    private static final Logger LOGGER = Logger.getLogger(DiaDBUtil.class.getName());

    /**
     * Create Device instance by device resultSet on current index.
     *
     * @param sql SQL String
     * @return device with positive id if successful, or id 0.
     */
    public static Node getNode(String sql) {

        Node node = null;
        try {
            Connection con = DiaDBConnector.getConnection();
            ResultSet resultSet = sqlQuery(con, sql);
            if (resultSet.next()) {
                node = new Node();
                node.setId(resultSet.getInt("id"));
                node.setName(resultSet.getString("name"));
                node.setTemperature(resultSet.getInt("temperature"));
                node.setHumidity(resultSet.getInt("humidity"));
                node.setNoise(resultSet.getInt("noise"));
                node.setLight(resultSet.getInt("light"));
                node.setPeopleCount(resultSet.getInt("people_count"));
                node.setType(resultSet.getInt("type"));
                node.setActivity(resultSet.getInt("activity"));
            }
            resultSet.close();
            con.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException: " + e);
        }
        return node;
    }

    /**
     * Create List of Device instance by device resultSet.
     *
     * @param sql SQL String
     * @return List of Device with positive id if successful, or id 0.
     */
    public static List<Node> getNodeList(String sql) {

        List<Node> nodeList = new ArrayList<Node>();
        Node node;
        try {
            Connection con = DiaDBConnector.getConnection();
            ResultSet resultSet = sqlQuery(con, sql);
            while(resultSet.next()) {
                node = new Node();
                node.setId(resultSet.getInt("id"));
                node.setName(resultSet.getString("name"));
                node.setTemperature(resultSet.getInt("temperature"));
                node.setHumidity(resultSet.getInt("humidity"));
                node.setNoise(resultSet.getInt("noise"));
                node.setLight(resultSet.getInt("light"));
                node.setPeopleCount(resultSet.getInt("people_count"));
                node.setType(resultSet.getInt("type"));
                node.setActivity(resultSet.getInt("activity"));
                nodeList.add(node);
            }
            resultSet.close();
            con.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException: " + e);
        }
        return nodeList;
    }

    /**
     * Create User instance by device resultSet on current index.
     *
     *
     * @param sql SQL String
     * @return User with positive id if successful, or id 0.
     */
    public static User getUser(String sql) {

        User user =null;
        try {
            Connection con = DiaDBConnector.getConnection();
            ResultSet resultSet = sqlQuery(con, sql);
            if (resultSet.next()) {
                user= new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }
            resultSet.close();
            con.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException: " + e);
        }
        return user;
    }


    /**
     * @param sql executeQuery the sql in Dia DB connection
     * @return resultSet or null
     */
    public static ResultSet sqlQuery(Connection con, String sql) throws SQLException {
        ResultSet resultSet = null;
        Statement stmt = con.createStatement();
        resultSet = stmt.executeQuery(sql);
        return resultSet;
    }

    /**
     * @param sql executeUpdate the sql in Dia DB connection
     * @return positive on success, or 0
     */
    public static int sqlUpdate(Connection con, String sql)  throws SQLException {
        int resultInt = 0;
            Statement stmt = con.createStatement();
            resultInt = stmt.executeUpdate(sql);
        return resultInt;
    }
}
