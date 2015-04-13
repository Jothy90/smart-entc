package org.smart.entc.repo;
/**
 * Created by john on 4/13/15.
 */

import com.mysql.jdbc.Connection;
import org.smart.entc.repo.object.Node;
import org.smart.entc.repo.object.User;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataLayer {
    private static final Logger LOGGER = Logger.getLogger(DataLayer.class.getName());

    public static Node getNodeByName(String name) {
        String sql = String.format("SELECT * FROM node WHERE name=\"%s\"", name);
        return DiaDBUtil.getNode(sql);
    }



    public static User getUserByName(String name) {
        String sql = String.format("SELECT * FROM user WHERE name=\"%s\"", name);
        return DiaDBUtil.getUser(sql);
    }

    public static int update(Node node) {
        int result = 0;
        Connection con = DiaDBConnector.getConnection();
        String sql = String.format("UPDATE node "
                + "SET temperature = \"%d\", humidity = \"%d\", noise = \"%d\", light = \"%d\", peopleCount = \"%d\""
                + "WHERE name = \"%s\""
                ,node.getTemperature(),node.getHumidity(),node.getNoise(),node.getLight(),node.getPeopleCount(),node.getName());
        try {
            result = DiaDBUtil.sqlUpdate(con, sql);
            con.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException: " + e);
        }
        return result;
    }
    public static int updateNodeTemperature(Node node) {
        int result = 0;
        Connection con = DiaDBConnector.getConnection();
        String sql = String.format("UPDATE node "
                + "SET temperature = \"%d\""
                + "WHERE name = \"%s\""
                ,node.getTemperature(),node.getName());
        try {
            result = DiaDBUtil.sqlUpdate(con, sql);
            con.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException: " + e);
        }
        return result;
    }
    public static int updateNodeHumidity(Node node) {
        int result = 0;
        Connection con = DiaDBConnector.getConnection();
        String sql = String.format("UPDATE node "
                + "SET humidity = \"%d\""
                + "WHERE name = \"%s\""
                ,node.getHumidity(),node.getName());
        try {
            result = DiaDBUtil.sqlUpdate(con, sql);
            con.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException: " + e);
        }
        return result;
    }
    public static int updateNodeLight(Node node) {
        int result = 0;
        Connection con = DiaDBConnector.getConnection();
        String sql = String.format("UPDATE node "
                + "SET light = \"%d\""
                + "WHERE name = \"%s\""
                ,node.getLight(),node.getName());
        try {
            result = DiaDBUtil.sqlUpdate(con, sql);
            con.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException: " + e);
        }
        return result;
    }
    public static int updateNodeNoise(Node node) {
        int result = 0;
        Connection con = DiaDBConnector.getConnection();
        String sql = String.format("UPDATE node "
                + "SET noise = \"%d\""
                + "WHERE name = \"%s\""
                ,node.getNoise(),node.getName());
        try {
            result = DiaDBUtil.sqlUpdate(con, sql);
            con.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException: " + e);
        }
        return result;
    }
    public static int updatePeopleCount(Node node) {
        int result = 0;
        Connection con = DiaDBConnector.getConnection();
        String sql = String.format("UPDATE node "
                + "SET peopleCount = \"%d\""
                + "WHERE name = \"%s\""
                ,node.getPeopleCount(),node.getName());
        try {
            result = DiaDBUtil.sqlUpdate(con, sql);
            con.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException: " + e);
        }
        return result;
    }
    public static int updateActivity(Node node) {
        int result = 0;
        Connection con = DiaDBConnector.getConnection();
        String sql = String.format("UPDATE node "
                + "SET activity = \"%d\""
                + "WHERE name = \"%s\""
                ,node.getActivity(),node.getName());
        try {
            result = DiaDBUtil.sqlUpdate(con, sql);
            con.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException: " + e);
        }
        return result;
    }

    /*public static int add(Node node) {
        Connection con = DiaDBConnector.getConnection();
        int result = 0;
        String sql = String.format("INSERT INTO node (name,temperature,humidity,noise,light)" +
                " VALUES (\"%s\",\"%d\",\"%d\",\"%d\",\"%d\")"
                , node.getName(),node.getTemperature(),node.getHumidity(),node.getNoise(),node.getLight());
        try {
            result = DiaDBUtil.sqlUpdate(con, sql);
            con.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException: " + e);
        }
        return result;
    }*/
    public static int add(Node node) {
        Connection con = DiaDBConnector.getConnection();
        int result = 0;
        String sql = String.format("INSERT INTO node (name,type)" +
                " VALUES (\"%s\",\"%d\")"
                , node.getName(),node.getType());
        try {
            result = DiaDBUtil.sqlUpdate(con, sql);
            con.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException: " + e);
        }
        return result;
    }

    public static int add(User user) {
        Connection con = DiaDBConnector.getConnection();
        int result = 0;
        String sql = String.format("INSERT INTO user (name,password)" +
                " VALUES (\"%s\",\"%s\")"
                , user.getName(),user.getPassword());
        try {
            result = DiaDBUtil.sqlUpdate(con, sql);
            con.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException: " + e);
        }
        return result;
    }
    public static int update(User user) {
        int result = 0;
        Connection con = DiaDBConnector.getConnection();
        String sql = String.format("UPDATE user "
                + "SET password = \"%s\""
                + "WHERE name = \"%s\""
                ,user.getName(),user.getPassword());
        try {
            result = DiaDBUtil.sqlUpdate(con, sql);
            con.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException: " + e);
        }
        return result;
    }

}
