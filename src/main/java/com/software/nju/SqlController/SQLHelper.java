package com.software.nju.SqlController;

import com.software.nju.SqlController.connection.ConnectionPool;
import com.software.nju.SqlController.connection.ConnectionPoolUtils;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLHelper {
    /**
     * 驱动
     */
    public static String driver = "com.sybase.jdbc4.jdbc.SybDriver";

    public static String url = "jdbc:sybase:Tds:130.20.1.1:5000/JUDGE?charset=cp936";

    public static String user = "fymis";

    public static String password = "nju362225L572L2L55";

    //下面是信访数据库信息
    public static String Gyurl = "jdbc:sybase:Tds:130.1.1.27:5000/XF?charset=cp936";

    public static String Gyuser = "sa";

    public static String Gypassword = "xieche";

    public static volatile Connection gyconnection = null;

    //静海数据库
    static ConnectionPool connPool= ConnectionPoolUtils.GetPoolInstance();//单例模式创建连接池对象
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
    public static Connection getConnection() {
        try{
            Connection connection = connPool.getConnection();
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
     * @return 如果获取失败将返回 null,调用时记得检查返回值
     */
    public static Statement getStatement() {
        Connection conn = getConnection();
        if (conn == null) {
            return null;
        }
        try {
            return (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            // 设置数据集可以滚动,可以更新
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
            close(conn);
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
            return (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            // 设置数据集可以滚动,可以更新
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * 获取一个带参数的 PreparedStatement
     * 该 PreparedStatement 已经设置数据集 可以滚动,可以更新
     *
     * @param cmdText   需要 ? 参数的 SQL 语句
     * @param cmdParams SQL 语句的参数表
     * @return 如果获取失败将返回 null,调用时记得检查返回值
     */
    public static PreparedStatement getPreparedStatement(String cmdText, Object... cmdParams) {
        Connection conn = getConnection();
        if (conn == null) {
            return null;
        }
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(cmdText, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            int i = 1;
            for (Object item : cmdParams) {
                pstmt.setObject(i, item);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            close(conn);
        }finally {
            connPool.returnConnection(conn);
        }
        return pstmt;
    }

    /**
     * 获取一个带参数的 PreparedStatement
     * 该 PreparedStatement 已经设置数据集 可以滚动,可以更新
     *
     * @param conn      数据库连接
     * @param cmdText   需要 ? 参数的 SQL 语句
     * @param cmdParams SQL 语句的参数表
     * @return 如果获取失败将返回 null,调用时记得检查返回值
     */
    public static PreparedStatement getPreparedStatement(Connection conn, String cmdText, Object... cmdParams) {
        if (conn == null) {
            return null;
        }
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(cmdText, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            int i = 1;
            for (Object item : cmdParams) {
                pstmt.setObject(i, item);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            close(pstmt);
        }
        return pstmt;
    }

    /**
     * 返回一个 ResultSet
     *
     * @param cmdText SQL 语句
     * @return
     */
    public static ResultSet getResultSet(String cmdText) {
        Statement stmt = getStatement();
        if (stmt == null) {
            return null;
        }
        try {
            return ((java.sql.Statement) stmt).executeQuery(cmdText);
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
            closeConnection(stmt);
        }finally {
            closeConnection(stmt);
        }
        return null;
    }

    /**
     * 返回一个 ResultSet
     *
     * @param conn
     * @param cmdText SQL 语句
     * @return
     */
    public static ResultSet getResultSet(Connection conn, String cmdText) {
        Statement stmt = getStatement(conn);
        System.out.println(conn);
        if (stmt == null) {
            return null;
        }
        try {
            return ((java.sql.Statement) stmt).executeQuery(cmdText);
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            closeGYConnection(conn);
        }
        return null;
    }




    private static void close(Object obj) {
        if (obj == null) {
            return;
        }
        try {
            if (obj instanceof Statement) {
                ((Connection) obj).close();
            } else if (obj instanceof PreparedStatement) {
                ((PreparedStatement) obj).close();
            } else if (obj instanceof ResultSet) {
                ((ResultSet) obj).close();
            } else if (obj instanceof Connection) {
//                ((Connection) obj).close();
                connPool.returnConnection((Connection) obj);// 连接使用完后释放连接到连接池
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    private static void closeConnection(Object obj) {
        if (obj == null) {
            return;
        }
        try {
            if (obj instanceof Statement) {
//                ((java.sql.Statement) obj).getConnection().close();
                connPool.returnConnection(((java.sql.Statement) obj).getConnection());
            } else if (obj instanceof PreparedStatement) {
//                ((PreparedStatement) obj).getConnection().close();
                connPool.returnConnection(((PreparedStatement) obj).getConnection());
            } else if (obj instanceof ResultSet) {
//                ((ResultSet) obj).getStatement().getConnection().close();
                connPool.returnConnection(((ResultSet) obj).getStatement().getConnection());
            } else if (obj instanceof Connection) {
//                ((Connection) obj).close();
                connPool.returnConnection((Connection) obj);// 连接使用完后释放连接到连接池
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
