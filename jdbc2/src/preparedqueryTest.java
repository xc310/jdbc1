import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;


public class preparedqueryTest {
    @Test
    public void test1(){
        String sql="select id,name,email from customers where id=?";
        Customer customer = getInstance(Customer.class, sql, 6);
        System.out.println(customer);
        String sql1="SELECT order_id orderId FROM `order` WHERE order_id=?;";
        Order order = getInstance(Order.class, sql1, 1);
        System.out.println(order);
    }
    @Test
    public void test2(){
        String sql="select id,name,email from customers where id<?";
        List<Customer> list = getList(Customer.class, sql, 12);
        list.forEach(System.out::println);

    }
    /*
     * 实现对不同表的通用查询操作(返回表中的多条记录）
     * */
    public <T> List<T> getList(Class<T> calzz, String sql, Object...args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = jdbcutils.getConnection();
            ps = conn.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            rs = ps.executeQuery();//获取结果集
//        获取元数据，就是修饰性数据，就是name=tom中的name
            ResultSetMetaData rsmd = rs.getMetaData();
//        获取元数据的个数也就是列数
            int columnCount = rsmd.getColumnCount();
            ArrayList<T> list = new ArrayList<T>();
            while(rs.next()){
                //            先建一个对象
                T t = calzz.newInstance();
                //            处理每一行的每一列
                for(int i=0;i<columnCount;i++){
                    Object colunmvalue= rs.getObject(i + 1);
                    //                获取列名
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    // 给cust对象指定的columnName属性赋值columValue
                    Field field = calzz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,colunmvalue);

                }
                list.add(t);

            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            jdbcutils.closeResource(conn,ps,rs);

        }

    }
    /*
     * 实现对不同表的通用查询操作(返回表中的一条记录）
     * */
    public <T> T getInstance(Class<T> calzz,String sql,Object...args){
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = jdbcutils.getConnection();
                ps = conn.prepareStatement(sql);
                for(int i=0;i<args.length;i++){
                    ps.setObject(i+1,args[i]);
                }
                rs = ps.executeQuery();
//        获取元数据，就是修饰性数据，就是name=tom中的name
                ResultSetMetaData rsmd = rs.getMetaData();
//        获取元数据的个数也就是列数
                int columnCount = rsmd.getColumnCount();
                if(rs.next()){
                    //            先建一个对象
                    T t = calzz.newInstance();
                    //            处理每一行的每一列
                    for(int i=0;i<columnCount;i++){
                        Object colunmvalue= rs.getObject(i + 1);
                        //                获取列名
                        String columnLabel = rsmd.getColumnLabel(i + 1);
                        // 给cust对象指定的columnName属性赋值columValue
                        Field field = calzz.getDeclaredField(columnLabel);
                        field.setAccessible(true);
                        field.set(t,colunmvalue);

                    }
                    return t;

                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                jdbcutils.closeResource(conn,ps,rs);

            }
            return null;
    }

}
