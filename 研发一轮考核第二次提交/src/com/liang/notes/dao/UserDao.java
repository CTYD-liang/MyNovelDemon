package com.liang.notes.dao;

import com.liang.notes.entity.User;
import com.liang.notes.entity.UserMessage;


import java.sql.*;

/**
 * @author 86178
 * 用户Dao类
 */
public class UserDao {
    private PreparedStatement pstmt = null;
    private PreparedStatement pstmt1 = null;

    /**
     * 用户登录验证
     * @param connection
     * @param user
     * @return
     * @throws Exception
     */
    public User login(Connection connection, User user) throws Exception {
        User restUser = null;
        String sql = "select * from loading where userName = ? and password = ?";
        pstmt = connection.prepareStatement(sql);
        //用户传过来的数据
        pstmt.setString(1, user.getUserName());
        pstmt.setString(2, user.getPassWord());
        //执行SQL语句
        ResultSet resultSet = pstmt.executeQuery();

        //判断
        if (resultSet.next()) {
            //加入查到了就实例化
            restUser = new User();

            restUser.setId(resultSet.getInt("id"));
            restUser.setUserName(resultSet.getString("userName"));
            restUser.setPassWord(resultSet.getString("password"));
        }

        return restUser;
    }

    /**
     * 用户注册
     * @param connection1
     * @param connection2
     * @param name
     * @param password
     * @throws Exception
     */
    public int Insign(Connection connection1,Connection connection2, String name,String password) throws Exception {
        //sql语句
        String sql = "insert into loading(`Username`,`password`) values (?,?)";
        String sql1 = "INSERT INTO usermessage(user_name,password) VALUES(?,?)";
        //创建执行对象
        pstmt = connection1.prepareStatement(sql);
        pstmt.setString(1,name);
        pstmt.setString(2,password);
        pstmt1 = connection2.prepareStatement(sql1);
        pstmt1.setString(1,name);
        pstmt1.setString(2,password);
        return (pstmt.executeUpdate()+pstmt1.executeUpdate());
    }

    /**
     * 判断是否已经有同名的用户
     * @param connection
     * @param name
     * @return
     * @throws Exception
     */
    public ResultSet research(Connection connection, String name) throws Exception{
        String sql = "select * from loading where userName = ?";
        pstmt = connection.prepareStatement(sql);
        pstmt.setString(1,name);
        return pstmt.executeQuery();
    }


    /**
     * 查询用户信息
     * @param con
     * @param name
     * @return
     * @throws Exception
     */
    public ResultSet research1(Connection con,String name) throws Exception{
        String sql = "select * from usermessage where user_name = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1,name);
        return pstmt.executeQuery();
    }

    /**
     * 修改用户信息
     * @param con
     * @param message
     * @return
     * @throws Exception
     */
    public int updateperson(Connection con, UserMessage message) throws Exception {
        String sql = "UPDATE usermessage SET age = ?,sex = ?,persondescribe = ? WHERE user_name = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1,message.getAge());
        pstmt.setString(2,message.getSex());
        pstmt.setString(3,message.getUserdescribe());
        pstmt.setString(4,message.getName());

        return pstmt.executeUpdate();
    }

    /**
     * 判断是否密码相同
     * @param con
     * @param user
     * @return
     * @throws Exception
     */
    public ResultSet judge(Connection con,User user) throws Exception{
        String sql = "select * from loading where userName = ? and password = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1,user.getUserName());
        pstmt.setString(2,user.getPassWord());
        return pstmt.executeQuery();
    }

    /**
     * 修改用户密码
     * @param con
     * @param user
     * @return
     * @throws Exception
     */
    public int updatepassword(Connection con,User user) throws Exception{
        String sql = "update loading set password = ? where userName = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1,user.getPassWord());
        pstmt.setString(2,user.getUserName());
        return pstmt.executeUpdate();
    }
}
