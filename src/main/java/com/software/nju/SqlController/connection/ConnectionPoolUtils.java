package com.software.nju.SqlController.connection;

import com.software.nju.SqlController.SqlController;

import java.util.logging.Logger;

/*连接池工具类，返回唯一的一个数据库连接池对象,单例模式*/
public class ConnectionPoolUtils {
    private ConnectionPoolUtils(){};//私有静态方法
    private static ConnectionPool poolInstance = null;
    private static ConnectionPool GYpoolInstance = null;
    private static Logger logger = Logger.getLogger(ConnectionPoolUtils.class.getName());
    //静海法院数据库
    public static ConnectionPool GetPoolInstance(){
        if(poolInstance == null) {
            logger.info("创建静海数据库连接池");
            poolInstance = new ConnectionPool(
                    "com.sybase.jdbc4.jdbc.SybDriver",
                    "jdbc:sybase:Tds:130.20.1.1:5000/JUDGE?charset=cp936",
                    "fymis",
                    "nju362225L572L2L55");
            try {
                poolInstance.createPool();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return poolInstance;
    }
    //高院集中信访库
    public static ConnectionPool GetGYPoolInstance(){
        if(GYpoolInstance == null) {
            logger.info("创建高院数据库连接池");
            GYpoolInstance = new ConnectionPool(
                    "com.sybase.jdbc4.jdbc.SybDriver",
                    "jdbc:sybase:Tds:130.1.1.27:5000/XF?charset=cp936",
                    "sa",
                    "xieche");
            try {
                GYpoolInstance.createPool();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return GYpoolInstance;
    }
}