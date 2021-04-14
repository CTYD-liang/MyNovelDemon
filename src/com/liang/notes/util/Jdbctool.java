package com.liang.notes.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *数据库连接工具类
 * @author 86178
 */
public class Jdbctool {

       /**
        * 驱动
        */
       private String driver = "com.mysql.jdbc.Driver";
       /**
        * 地址
        */
       private String url = "jdbc:mysql://localhost:3306/novelblog? useUnicode=true&characterEncoding=utf8&useSSL=false";
       /**
        * 用户名
        */
       private String username = "root";
       /**
        * 密码
        */
       private String password = "123456";

       /**
        * 获取数据库连接
        * @return
        * @throws Exception
        */
       public Connection getconnection() throws Exception {
              Class.forName(driver);
              Connection connection = DriverManager.getConnection(url,username,password);
              return connection;
       }

       /**
        * 关闭数据库连接
        * @param connection
        * @throws Exception
        */
       public void release(Connection connection ) throws Exception {
              if(connection != null){
                     connection.close();
              }
       }


       /**
        * 测试数据库连接
        * @param args
        */
       public static void main(String[] args) {
              Jdbctool jdbctool = new Jdbctool();
              try {
                     jdbctool.getconnection();
                     System.out.println("数据库连接成功！");
              } catch (Exception e) {
                     e.printStackTrace();
                     System.out.println("数据库连接失败！");
              }
       }
}
