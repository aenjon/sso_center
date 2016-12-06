package com.hsjc.ssoCenter.core.util;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @author : zga
 * @date : 2016-02-16
 *
 * 数据库JDBC连接工具类
 *
 */
public class DBUtil {

    public static String driverClass = null;

    public static String url = null;

    public static String userName = null;

    public static String password = null;

    public static Connection connection;


    /**
     * @author : zga
     * @date : 2016-3-18
     *
     * 获取Connection
     *
     * @return
     */
    public static Connection getConn(){
        try {
            Properties properties = new Properties();
//            properties.load(DBUtil.class.getResourceAsStream("/application.online.properties"));
            //properties.load(DBUtil.class.getResourceAsStream("/application.production.properties"));
            properties.load(DBUtil.class.getResourceAsStream("/application.development.properties"));

            driverClass = properties.get("db.driver").toString();
            url = properties.get("db.url").toString();
            userName = properties.get("db.username").toString();
            password = properties.get("db.password").toString();

            Class.forName(driverClass);
            connection = DriverManager.getConnection(url,userName,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * @author : zga
     * @date : 2016-3-18
     *
     * 释放数据库连接资源
     *
     * @param connection
     * @param resultSet
     * @param statement
     */
    public static void freeConn(Connection connection, ResultSet resultSet, Statement statement){
        try {
            if(resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement != null){
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(connection != null){
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
