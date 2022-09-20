

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;
public class jdbcutils {
//    连接操作
    public static Connection getConnection() throws Exception {
        // 1.读取文件中的流 shift+alt+z 包裹
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties pro = new Properties();
        pro.load(is);
        String user = pro.getProperty("user");
        String password = pro.getProperty("password");
        String url = pro.getProperty("url");
        String driverClass = pro.getProperty("driverClass");
//       2.加载驱动
            Class.forName(driverClass);

//       3.获取连接，预编译sql语句，返回Preperstatement实例
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }
//    关闭资源的操作
    public static void closeResource(Connection conn,Statement ps){
        try {
            if(ps!=null)
                ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            if(conn!=null)
                conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void closeResource(Connection conn,Statement ps,ResultSet rs){
        try {
            if(ps!=null)
                ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            if(conn!=null)
                conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            if(rs!=null)
                rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
