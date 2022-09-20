import org.junit.Test;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class PreparedTest {
    @Test
    public void testcommonUpdate(){
        String sql="delete from customers where id=?";
        update(sql,5);

    }
//    通用的增删改操作
    public void update(String sql,Object ...arg) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
//        1.获取数据库连接
            conn = jdbcutils.getConnection();
//        2.预编译sql语句，返回preparedStatement实例
            ps = conn.prepareStatement(sql);
//        3.填充占位符
            for(int i=0;i<arg.length;i++){
                ps.setObject(i+1,arg[i]);
            }
//        4.执行
            ps.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //        5.资源的关闭
            jdbcutils.closeResource(conn,ps);
        }

    }
    //修改customers表中的记录
    @Test
    public void testUpdate()  {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
//        1.获取数据库连接

            conn = jdbcutils.getConnection();
//        2.预编译sql语句，返回preparedStatement实例
            String sql="update customers set name= ? where id= ? ";
            ps = conn.prepareStatement(sql);
//        3.填充占位符
            ps.setObject(1,"孙燕姿");
            ps.setObject(2,18);
//        4.执行
            ps.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
//            5.资源的关闭
            jdbcutils.closeResource(conn,ps);
        }



    }

    //向customers表中添加一条记录
    @Test
    public void tesrInsert() {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            // 1.读取文件中的流 shift+alt+z 包裹
            InputStream is = connectiontest.class.getClassLoader().getResourceAsStream("jdbc.properties");
//        2.
            Properties pro = new Properties();
            pro.load(is);
            String user = pro.getProperty("user");
            String password = pro.getProperty("password");
            String url = pro.getProperty("url");
            String driverClass = pro.getProperty("driverClass");
//        3.加载驱动
            Class.forName(driverClass);

//        4.获取连接，预编译sql语句，返回Preperstatement实例
            conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn);

            String sql = "insert into customers(name,email)values(?,?,?)";
            System.out.println("-------------");
            ps = conn.prepareStatement(sql);
            System.out.println("-------------");
//        5.填充占位符
            ps.setString(1, "aaa");
            System.out.println("-------------");
            ps.setString(2, "hhh");
            System.out.println("-------------");
//            SimpleDateFormat sr = new SimpleDateFormat("yyyy-MM-dd");
//            java.util.Date date = sr.parse("1998-03-10");
//            ps.setDate(3,new Date(date.getTime()));
//        执行sql操作
            ps.execute();
            System.out.println("-------------");
            System.out.println("插入成功");

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }
}




