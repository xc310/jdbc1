import com.mysql.cj.jdbc.Driver;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static java.lang.Class.forName;


/**
 * @author mobeiCanyue
 * Create  2022-01-26 22:17
 * Describe:
 */
public class connectiontest {
    @Test
    public void ConnectionTest1() throws SQLException {
//        driver:驱动
        Driver driver = new Driver();
        // url:http://localhost:8080/gmall/keyboard.jpg
        // jdbc:mysql:协议
        // localhost:ip地址
        // 3306：默认mysql的端口号
        // mysql:mysql的存储mysql本身一些信息的数据库

        String url = "jdbc:mysql://localhost:3306/mysql";
        // 将用户名和密码封装在Properties中
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456");//admin改成你自己的密码
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }

    //      方式二：对方式一的迭代:在如下的程序中不出现第三方的api,使得程序具有更好的可移植性
    @Test
    public void ConnectionTest2() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        // 1.获取Driver实现类对象：使用反射
        Class clazz = forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        // 2.提供要连接的数据库
        String url = "jdbc:mysql://localhost:3306/mysql";

        // 3.提供连接需要的用户名和密码
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456");//admin改成你自己的密码

        // 4.获取连接
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }

    @Test
    public void ConnectionTest3() throws Exception {
        // 1.获取Driver实现类对象：使用反射
        Class clazz = forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        // 2.提供要连接的数据库
        String url = "jdbc:mysql://localhost:3306/mysql";
        // 3.提供连接需要的用户名和密码
        String user = "root";
        String password = "123456";

        // 4.获取连接
        DriverManager.registerDriver(driver); //8.0版本好像不用注册直接可以使用
        Connection conn = DriverManager.getConnection(url,user,password);
        System.out.println(conn);
    }
    @Test
    public void ConnectionTest4() throws Exception {
        // 1.提供要连接的数据库、用户名和密码
        String url = "jdbc:mysql://localhost:3306/mysql";
        String user = "root";
        String password = "123456";
        // 2.获取Driver实现类对象：使用反射
        Class clazz = forName("com.mysql.cj.jdbc.Driver");
//        反射将driver对象加载到内存中时，会加载静态代码块，自动注册，不需要手动写
        /*
		 * 在mysql的Driver实现类中，声明了如下的操作：
		 * static {
				try {
					java.sql.DriverManager.registerDriver(new Driver());
				} catch (SQLException E) {
					throw new RuntimeException("Can't register driver!");
				}
			}
		 */

//        Driver driver = (Driver) clazz.newInstance();
//        DriverManager.registerDriver(driver);
        Connection conn = DriverManager.getConnection(url,user,password);
        System.out.println(conn);
    }
    @Test
    public void ConnectionTest5() throws Exception {
        // 1.读取文件中的流
        InputStream is = connectiontest.class.getClassLoader().getResourceAsStream("jdbc.properties");
//        2.
        Properties pro = new Properties();
        pro.load(is);
        String user = pro.getProperty("user");
        String password= pro.getProperty("password");
        String url= pro.getProperty("url");
        String driverClass= pro.getProperty("driverClass");
//        3.加载驱动
        Class.forName(driverClass);

//        4.获取连接
        Connection conn = DriverManager.getConnection(url,user,password);
        System.out.println(conn);
    }
}
