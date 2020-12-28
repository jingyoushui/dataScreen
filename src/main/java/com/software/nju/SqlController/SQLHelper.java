package com.software.nju.SqlController;

import com.software.nju.SqlController.connection.ConnectionPool;
import com.software.nju.SqlController.connection.ConnectionPoolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Component
public class SQLHelper {

    //静海数据库
    static ConnectionPool JHconnPool= ConnectionPoolUtils.GetPoolInstance();//单例模式创建连接池对象
    //高院信访集中库
    static ConnectionPool GYconnPool= ConnectionPoolUtils.GetGYPoolInstance();//单例模式创建连接池对象
    /**
     * 不允许实例化该类
     */
    private SQLHelper() {

    }

    /**
     * 获取一个数据库连接
     * 通过设置类的  driver / url / user / password 这四个静态变量来 设置数据库连接属性
     *
     * @return 数据库连接
     */
    public static Connection getJHConnection() {
        try{
            Connection connection = JHconnPool.getConnection();
//            log.info("获取静海连接："+connection);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    //获取高院的数据库连接
    public static Connection getGYConnection() {
        try{
            Connection connection = GYconnPool.getConnection();
//            log.info("获取高院连接："+connection);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * 获取一个 Statement
     * 该 Statement 已经设置数据集 可以滚动,可以更新
     *
     * @param conn 数据库连接
     * @return 如果获取失败将返回 null,调用时记得检查返回值
     */
    public static Statement getStatement(Connection conn) {
        if (conn == null) {
            return null;
        }
        try {
            return (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            // 设置数据集可以滚动,可以更新
        } catch (SQLException ex) {
            log.error(ex.toString());
            return null;
        }
    }


    /**
     * 返回一个 ResultSet
     * @param cmdText SQL 语句
     * @return
     */
    public static ResultSet getGYResultSet( String cmdText) {
        Connection conn = getGYConnection();
        Statement stmt = getStatement(conn);
        ResultSet res = null;
//        logger.info("使用高院连接："+conn.toString());
        if (stmt == null) {
            log.info("高院stmt为空");
            return null;
        }
        try {
            res =  ((java.sql.Statement) stmt).executeQuery(cmdText);
        } catch (SQLException ex) {
            log.error(ex.toString());
        }finally {
            closeGYConnection(conn);
        }
        return res;
    }

    /**
     * 返回一个 ResultSet
     * @param cmdText SQL 语句
     * @return
     */
    public static ResultSet getJHResultSet( String cmdText) {
        Connection conn = getJHConnection();
        Statement stmt = getStatement(conn);
        ResultSet res = null;
//        logger.info("使用静海连接："+conn.toString());
        if (stmt == null) {
            log.info("静海stmt为空");
            return null;
        }
        try {
            res =  ((java.sql.Statement) stmt).executeQuery(cmdText);
        } catch (SQLException ex) {
            log.error(ex.toString());
        }finally {
            closeJHConnection(conn);
        }
        return res;
    }


    private static void closeJHConnection(Object obj) {
        if (obj == null) {
            return;
        }
        try {
            if (obj instanceof Statement) {
//                ((java.sql.Statement) obj).getConnection().close();
                JHconnPool.returnConnection(((java.sql.Statement) obj).getConnection());
            } else if (obj instanceof PreparedStatement) {
//                ((PreparedStatement) obj).getConnection().close();
                JHconnPool.returnConnection(((PreparedStatement) obj).getConnection());
            } else if (obj instanceof ResultSet) {
//                ((ResultSet) obj).getStatement().getConnection().close();
                JHconnPool.returnConnection(((ResultSet) obj).getStatement().getConnection());
            } else if (obj instanceof Connection) {
//                ((Connection) obj).close();
                JHconnPool.returnConnection((Connection) obj);// 连接使用完后释放连接到连接池
            }
//            logger.info("关闭静海连接："+obj);
        } catch (SQLException ex) {
            log.error(ex.toString());
            ex.printStackTrace();
        }
    }

    private static void closeGYConnection(Object obj) {
        if (obj == null) {
            return;
        }
        try {
            if (obj instanceof Statement) {
                GYconnPool.returnConnection(((java.sql.Statement) obj).getConnection());
            } else if (obj instanceof PreparedStatement) {
                GYconnPool.returnConnection(((PreparedStatement) obj).getConnection());
            } else if (obj instanceof ResultSet) {
                GYconnPool.returnConnection(((ResultSet) obj).getStatement().getConnection());
            } else if (obj instanceof Connection) {
                GYconnPool.returnConnection((Connection) obj);// 连接使用完后释放连接到连接池
            }
//            logger.info("关闭高院连接："+obj);
        } catch (SQLException ex) {
            log.error(ex.toString());
            ex.printStackTrace();
        }
    }
}
