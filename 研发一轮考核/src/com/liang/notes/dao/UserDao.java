package com.liang.notes.dao;

import com.liang.notes.entity.User;


import java.sql.*;

/**
 * @author 86178
 * 用户Dao类
 */
public class UserDao {

    /**
     * 用户登录验证
     *
     * @param connection
     * @param user
     * @return
     * @throws Exception
     */
    public User login(Connection connection, User user) throws Exception {
        User restUser = null;
        String sql = "select * from user where username = ? and password = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
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

}
