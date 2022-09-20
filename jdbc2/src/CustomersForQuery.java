import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

public class CustomersForQuery {
    @Test
    public void test1(){
        String sql="select id,name,birth,email from customers where id = ?";
        Customer customer = queryFroCustomers(sql, 6);
        System.out.println(customer);
    }
    public Customer queryFroCustomers(String sql,Object...args){
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
                Customer cust = new Customer();
    //            处理每一行的每一列
                for(int i=0;i<columnCount;i++){
                    Object colunmvalue= rs.getObject(i + 1);
    //                获取列名
                    String columnName = rsmd.getColumnName(i + 1);
                    // 给cust对象指定的columnName属性赋值columValue
                    Field field = Customer.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(cust,colunmvalue);

                }
            return cust;

        }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            jdbcutils.closeResource(conn,ps,rs);

        }
        return null;

}
@Test
    public void testQuery1(){
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet resultSet = null;
    try {
        conn = jdbcutils.getConnection();
        String sql = "select id,name,email,birth from customers where id = ?";
        ps = conn.prepareStatement(sql);
        ps.setObject(1,6);
//    执行并且返回结果集
        resultSet = ps.executeQuery();
//    处理结果集
        if(resultSet.next()){//判断结果集的下一条是否有数据
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String email = resultSet.getString(3);
            Date birth = resultSet.getDate(4);
    //    直接显示
    //        1.System.out.println(id+name+email+birth);
    //        2.Object[] data = new Object[]{id,name,email,birth};
    //     方式三
            Customer cust = new Customer(id, name, email, birth);
            System.out.println(cust);

        }
    } catch (Exception e) {
        throw new RuntimeException(e);
    } finally {
        //    关闭资源
        jdbcutils.closeResource(conn,ps,resultSet);
    }


}



}
