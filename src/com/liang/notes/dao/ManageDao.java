package com.liang.notes.dao;

import com.liang.notes.entity.Manage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 管理员和数据库交互的工具集
 * @author 86178
 */
public class ManageDao {
    //创建执行对象
    private PreparedStatement pstmt = null;

    /**
     * 管理员登录验证
     * @param con
     * @param m
     * @return
     * @throws Exception
     */
    public ResultSet selectManage(Connection con, Manage m) throws Exception{
        String sql = "select * from manager where manager_name = ? and password = ?";
        //创建执行对象
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1,m.getName());
        pstmt.setString(2,m.getPassword());
        //返回查找到的对象
        return pstmt.executeQuery();
    }


    /**
     * 填充用户信息集合
     * 将所有用户信息从数据库中查找出来，并显示在管理员页面
     * @param con
     * @return
     * @throws Exception
     */
    public ResultSet require(Connection con) throws Exception{
        String sql = "select * from user_message ";
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
        String sql = "update manager set announcement = ?";
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
        String sql = "select announcement from manager where manager_name = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1,name);
        return pstmt.executeQuery();
    }
}
