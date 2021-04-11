package com.liang.notes.dao;

import com.liang.notes.entity.Manage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ManageDao {
    private PreparedStatement pstmt = null;

    /**
     * 管理员登录
     * @param con
     * @param m
     * @return
     * @throws Exception
     */
    public ResultSet selectManage(Connection con, Manage m) throws Exception{
        String sql = "select * from manage where managename = ? and password = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1,m.getName());
        pstmt.setString(2,m.getPassword());
        return pstmt.executeQuery();
    }


    /**
     * 填充用户表格集合
     * @param con
     * @return
     * @throws Exception
     */
    public ResultSet require(Connection con) throws Exception{
        String sql = "SELECT u.`id`,u.`user_name`,l.`password`,`age`,`sex` FROM usermessage AS u INNER JOIN loading AS l ON u.`user_name`= l.`userName`";
        pstmt = con.prepareStatement(sql);
        return pstmt.executeQuery();
    }

    /**
     * 修改公告
     * @param con
     * @param accountment
     * @return
     * @throws Exception
     */
    public int updateAccounement(Connection con,String accountment) throws Exception{
        String sql = "update manage set announcement = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1,accountment);
        return pstmt.executeUpdate();
    }

    /**
     * 查询公告
     * @param con
     * @param name
     * @return
     * @throws Exception
     */
    public ResultSet searchAccountment(Connection con,String name) throws Exception{
        String sql = "select announcement from manage where managename = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1,name);
        return pstmt.executeQuery();
    }
}
