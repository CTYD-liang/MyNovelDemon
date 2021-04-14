package com.liang.notes.dao;

import com.liang.notes.entity.User;

import java.sql.*;

/**
 * @author 86178
 * 用户Dao类
 */
public class UserDao {
    private PreparedStatement pstmt = null;

    /**
     * 用户登录验证
     * @param connection
     * @param user
     * @return
     * @throws Exception
     */
    public User login(Connection connection, User user) throws Exception {
        User restUser = null;
        String sql = "select * from user_message where user_name = ? and password = ?";
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
            restUser.setUserName(resultSet.getString("user_name"));
            restUser.setPassWord(resultSet.getString("password"));
        }
        return restUser;
    }

    /**
     * 用户注册
     * @param name
     * @param password
     * @throws Exception
     */
    public int Insign(Connection connection, String name,String password) throws Exception {
        //sql语句
        String sql = "INSERT INTO user_message(user_name,password) VALUES(?,?)";
        //创建执行对象
        pstmt = connection.prepareStatement(sql);
        pstmt.setString(1,name);
        pstmt.setString(2,password);
        return pstmt.executeUpdate();
    }

    /**
     * 查询用户信息
     * @param connection
     * @param name
     * @return
     * @throws Exception
     */
    public ResultSet research(Connection connection, String name) throws Exception{
        String sql = "select * from user_message where user_name = ?";
        pstmt = connection.prepareStatement(sql);
        pstmt.setString(1,name);
        return pstmt.executeQuery();
    }

    /**
     * 修改用户信息
     * @param con
     * @param user
     * @return
     * @throws Exception
     */
    public int updateperson(Connection con, User user) throws Exception {
        String sql = "UPDATE user_message SET age = ?,sex = ?,person_describe = ? WHERE user_name = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1,user.getAge());
        pstmt.setString(2,user.getSex());
        pstmt.setString(3,user.getUserdescribe());
        pstmt.setString(4,user.getUserName());

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
        String sql = "select * from user_message where user_name = ? and password = ?";
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
        String sql = "update user_message set password = ? where user_name = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1,user.getPassWord());
        pstmt.setString(2,user.getUserName());
        return pstmt.executeUpdate();
    }
}
