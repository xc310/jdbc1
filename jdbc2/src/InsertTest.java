import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/*
* 使用preparedStatement实现批量操作
*
*
* */
public class InsertTest {
    @Test
    public void testInsert1(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = jdbcutils.getConnection();
            String sql="insert into goods(name)values(?)";
            ps = conn.prepareStatement(sql);
            for(int i=0;i<20000;i++){
                ps.setObject(1,"name_"+i);
//                ps.execute();
//                1.改进为攒sql
                ps.addBatch();
                if(i%500==0){
//                    2.执行
                    ps.executeBatch();
//                    3.情况
                    ps.clearBatch();
                }
//
             }
            long end = System.currentTimeMillis();
            System.out.println("花费的时间为："+(end-start));
//            花费的时间为：32747
//            花费的时间为：835改进后
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            jdbcutils.closeResource(conn,ps);
        }

    }
    @Test
    public void testInsert2(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = jdbcutils.getConnection();
            conn.setAutoCommit(false);
//            设置不允许自动提交数据
            String sql="insert into goods(name)values(?)";
            ps = conn.prepareStatement(sql);
            for(int i=0;i<20000;i++){
                ps.setObject(1,"name_"+i);
//                ps.execute();
//                1.改进为攒sql
                ps.addBatch();
                if(i%500==0){
//                    2.执行
                    ps.executeBatch();
//                    3.情况
                    ps.clearBatch();
                }
//
            }
            conn.commit();//提交数据
            long end = System.currentTimeMillis();
            System.out.println("花费的时间为："+(end-start));
//            花费的时间为：827
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            jdbcutils.closeResource(conn,ps);
        }

    }

}
