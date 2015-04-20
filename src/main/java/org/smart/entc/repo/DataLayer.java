package org.smart.entc.repo;
/**
 * Created by john on 4/13/15.
 */

import com.mysql.jdbc.Connection;
import org.slf4j.LoggerFactory;
import org.smart.entc.repo.object.Activity;
import org.smart.entc.repo.object.Node;
import org.smart.entc.repo.object.User;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class DataLayer {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DBConnector.class);

    public static Node getNodeByName(String name) {
        String sql = String.format("SELECT * FROM node WHERE name=\"%s\"", name);
        return DBUtil.getNode(sql);
    }


    public static User getUserByName(String name) {
        String sql = String.format("SELECT * FROM user WHERE name=\"%s\"", name);
        return DBUtil.getUser(sql);
    }

    public static int update(Node node) {
        int result = 0;
        Connection con = DBConnector.getConnection();
        String sql = String.format("UPDATE node "
                + "SET temperature = \"%d\", humidity = \"%d\", noise = \"%d\", light = \"%d\", people_count = \"%d\""
                + "WHERE name = \"%s\""
                , node.getTemperature(), node.getHumidity(), node.getNoise(), node.getLight(), node.getPeopleCount(), node.getName());
        try {
            result = DBUtil.sqlUpdate(con, sql);
            ////con.close();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
        }
        return result;
    }

    public static int updateNodeTemperature(Node node) {
        int result = 0;
        Connection con = DBConnector.getConnection();
        String sql = String.format("UPDATE node "
                + "SET temperature = \"%d\""
                + "WHERE name = \"%s\""
                , node.getTemperature(), node.getName());
        try {
            result = DBUtil.sqlUpdate(con, sql);
            ////con.close();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
        }
        return result;
    }

    public static int updateNodeHumidity(Node node) {
        int result = 0;
        Connection con = DBConnector.getConnection();
        String sql = String.format("UPDATE node "
                + "SET humidity = \"%d\""
                + "WHERE name = \"%s\""
                , node.getHumidity(), node.getName());
        try {
            result = DBUtil.sqlUpdate(con, sql);
            //con.close();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
        }
        return result;
    }

    public static int updateNodeLight(Node node) {
        int result = 0;
        Connection con = DBConnector.getConnection();
        String sql = String.format("UPDATE node "
                + "SET light = \"%d\""
                + "WHERE name = \"%s\""
                , node.getLight(), node.getName());
        try {
            result = DBUtil.sqlUpdate(con, sql);
            //con.close();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
        }
        return result;
    }

    public static int updateNodeNoise(Node node) {
        int result = 0;
        Connection con = DBConnector.getConnection();
        String sql = String.format("UPDATE node "
                + "SET noise = \"%d\""
                + "WHERE name = \"%s\""
                , node.getNoise(), node.getName());
        try {
            result = DBUtil.sqlUpdate(con, sql);
            //con.close();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
        }
        return result;
    }

    public static int updatePeopleCount(Node node) {
        int result = 0;
        Connection con = DBConnector.getConnection();
        String sql = String.format("UPDATE node "
                + "SET people_count = \"%d\""
                + "WHERE name = \"%s\""
                , node.getPeopleCount(), node.getName());
        try {
            result = DBUtil.sqlUpdate(con, sql);
            //con.close();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
        }
        return result;
    }

    public static int updateActivity(Node node) {
        int result = 0;
        Connection con = DBConnector.getConnection();
        String sql = String.format("UPDATE node "
                + "SET activity = \"%d\""
                + "WHERE name = \"%s\""
                , node.getActivity(), node.getName());
        try {
            result = DBUtil.sqlUpdate(con, sql);
            //con.close();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
        }

        Date date = new Date();
        String timeStamp = new Timestamp(date.getTime()).toString();
        int resultAct = 0;
        sql = String.format("INSERT INTO activity_list (name,time_stamp,activity)" +
                " VALUES (\"%s\",\"%s\",\"%d\")"
                , node.getName(), timeStamp, node.getActivity());
        try {
            resultAct = DBUtil.sqlUpdate(con, sql);
            //con.close();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
        }

        return result*resultAct;
    }

    /*public static int add(Node node) {
        Connection con = DBConnector.getConnection();
        int result = 0;
        String sql = String.format("INSERT INTO node (name,temperature,humidity,noise,light)" +
                " VALUES (\"%s\",\"%d\",\"%d\",\"%d\",\"%d\")"
                , node.getName(),node.getTemperature(),node.getHumidity(),node.getNoise(),node.getLight());
        try {
            result = DBUtil.sqlUpdate(con, sql);
            //con.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException: " + e);
        }
        return result;
    }*/
    public static int add(Node node) {
        Connection con = DBConnector.getConnection();
        int result = 0;
        String sql = String.format("INSERT INTO node (name,type)" +
                " VALUES (\"%s\",\"%d\")"
                , node.getName(), node.getType());
        try {
            result = DBUtil.sqlUpdate(con, sql);
            //con.close();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
        }
        return result;
    }

    public static int add(User user) {
        Connection con = DBConnector.getConnection();
        int result = 0;
        String sql = String.format("INSERT INTO user (name,password)" +
                " VALUES (\"%s\",\"%s\")"
                , user.getName(), user.getPassword());
        try {
            result = DBUtil.sqlUpdate(con, sql);
            //con.close();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
        }
        return result;
    }

    public static int update(User user) {
        int result = 0;
        Connection con = DBConnector.getConnection();
        String sql = String.format("UPDATE user "
                + "SET password = \"%s\""
                + "WHERE name = \"%s\""
                , user.getName(), user.getPassword());
        try {
            result = DBUtil.sqlUpdate(con, sql);
            //con.close();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
        }
        return result;
    }

    public static List<Node> getNodeList() {
        String sql = String.format("SELECT * FROM node");
        return DBUtil.getNodeList(sql);
    }

    public static List<Activity> getActivityList() {
        String sql = String.format("SELECT * FROM activity_list");
        return DBUtil.getActivityList(sql);
    }
}
