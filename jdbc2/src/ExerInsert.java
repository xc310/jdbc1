import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class ExerInsert {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名");
        String name = scanner.next();
        System.out.println("请输入邮箱");
        String eamil = scanner.next();
        System.out.println("请输入生日");
        String birthday = scanner.next();
//        隐式的转换日期类型数据
        String sql = "insert into customers(name,email,birth)values(?,?,?)";
        int insertCount = update(sql, name, eamil, birthday);
        if(insertCount>0){
            System.out.println("添加成功");
        }else{
            System.out.println("添加失败");
        }
    }
    @Test

    public static int update(String sql, Object... arg) {
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
//            方式一：ps.execute();
//            如果执行的是查询操作，有返回结果，则返回true
//            如果执行的是增删改操作，没有返回结果，则返回false
//            返回的是影响的行数，如果没有任何返回就return 0
//            方式二
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //        5.资源的关闭
            jdbcutils.closeResource(conn,ps);
        }

    }
}
