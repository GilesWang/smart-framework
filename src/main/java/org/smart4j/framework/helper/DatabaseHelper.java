package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseHelper {
    private static ThreadLocal<Connection> CONNECTION_HOLDER=new ThreadLocal<Connection>();
    private static final Logger LOGGER=LoggerFactory.getLogger(DatabaseHelper.class);
    public static void beginTransaction(){
        Connection conn =getConnection();
        if(conn!=null){
            try {
                conn.setAutoCommit(false);
            }catch (SQLException ex){
                LOGGER.error("begin transaction failure",ex);
                throw new RuntimeException(ex);
            }
        }
    }
    public static void commitTransaction(){
        Connection connection=getConnection();
        if(connection!=null){
            try {
                connection.commit();
                closeConnection();
            }catch (SQLException ex){
                LOGGER.error("commit transaction error",ex);
            }
        }
    }
    public static void rollbackTransaction(){
        Connection connection=getConnection();
        if(connection!=null){
            try {
                connection.rollback();
                closeConnection();
            }catch (SQLException ex){
                LOGGER.error("commit transaction error",ex);
            }
        }
    }
    public static Connection getConnection(){
        Connection connection=CONNECTION_HOLDER.get();
        if(connection==null){
            try {
                Class.forName(ConfigHelper.getJdbcDriver());
                connection=DriverManager.getConnection(ConfigHelper.getJdbcUrl(),ConfigHelper.getJdbcUserName(),ConfigHelper.getJdbcPassword());
            }catch (ClassNotFoundException ex){
                LOGGER.error("load driver"+ConfigHelper.getJdbcDriver()+"error",ex);
                throw new RuntimeException(ex);
            }
            catch (SQLException ex){
                LOGGER.error("get connection error",ex);
                throw new RuntimeException(ex);
            }
            finally {
                CONNECTION_HOLDER.set(connection);
            }
        }
        return connection;
    }
    public static void closeConnection(){
        Connection connection=CONNECTION_HOLDER.get();
        try {
            if(connection!=null){
                connection.close();
            }
        }catch (SQLException ex){
            LOGGER.error("close connection error",ex);
        }finally {
            CONNECTION_HOLDER.remove();
        }
    }
}
