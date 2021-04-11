package com.liang.notes.dao;


import com.liang.notes.entity.Novel;
import com.liang.notes.entity.User;
import com.liang.notes.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 笔记实体Dao类
 * @author 86178
 *
 */
public class NovelDao {
    /**
     * 笔记添加
     * @param con
     * @param novel
     * @return
     * @throws Exception
     */
    public int upload(Connection con, Novel novel,String formname) throws Exception {
        String sql = "insert into "+ formname+ " values(null ,?,?,?)";
        PreparedStatement pre = con.prepareStatement(sql);
        //获得笔记作者
        pre.setString(1,novel.getNovelauthor());
        //获得笔记的标题
        pre.setString(2,novel.getNovelTitle());
        //获得笔记的内容
        pre.setString(3, novel.getNovelcontent());
        return pre.executeUpdate();
    }

    /**
     * 笔记初始化集合
     * @param con
     * @param novel
     * @return
     */
    public ResultSet list(Connection con, Novel novel) throws Exception{
        StringBuffer str = new StringBuffer("select * from novel");
        if(StringUtils.isNotEmpty(novel.getNovelTitle())){
            str.append("and title like '%" + novel.getNovelTitle() + "%'");
        }
        PreparedStatement pre = con.prepareStatement(str.toString().replaceFirst("and","where"));
        return pre.executeQuery();
    }

    /**
     * 查询公开笔记
     * @param con
     * @param user
     * @return
     * @throws Exception
     */
    public ResultSet search(Connection con, User user) throws  Exception{
        String sql = "SELECT title FROM novel where author = ?";
        PreparedStatement pre = con.prepareStatement(sql);
        pre.setString(1,user.getUserName());
        return pre.executeQuery();
    }

    /**
     * 查询私有笔记
     * @param con
     * @param user
     * @return
     * @throws Exception
     */
    public ResultSet search1(Connection con, User user) throws  Exception{
        String sql = "SELECT title FROM usernovel where author = ?";
        PreparedStatement pre = con.prepareStatement(sql);
        pre.setString(1,user.getUserName());
        return pre.executeQuery();
    }

    /**
     * 公众笔记查询集合
     * @param con
     * @param novel
     * @return
     */
    public ResultSet list1(Connection con, Novel novel) throws Exception{
        String sql = "select * from novel where author like '%" + novel.getNovelTitle() + "%' or title like '%" + novel.getNovelTitle() + "%' ";
        PreparedStatement pre = con.prepareStatement(sql);
        return pre.executeQuery();
    }


    /**
     *内容查询
     * @param con
     * @param novel
     * @return
     * @throws Exception
     */
    public ResultSet list2(Connection con,Novel novel,String formname) throws Exception{
        String sql = "select id,title,content from "+ formname +" where author = ? and title = ?";
        PreparedStatement pre = con.prepareStatement(sql);
        pre.setString(1,novel.getNovelauthor());
        pre.setString(2,novel.getNovelTitle());
        return  pre.executeQuery();
    }

    /**
     * 删除笔记
     * @param con
     * @param id
     * @return
     * @throws Exception
     */
    public int deletenovel (Connection con,String id,String formname) throws Exception {
        String sql = "delete from "+ formname+" where id = ?";
        PreparedStatement pre = con.prepareStatement(sql);
        pre.setString(1,id);
        return pre.executeUpdate();
    }

    /**
     * 修改笔记
     * @param con
     * @param novel
     * @return
     * @throws Exception
     */
    public int updatenovel(Connection con,Novel novel,String formname)throws Exception{
        String sql = "update "+ formname +" set title = ?,content = ? where id = ?";
        PreparedStatement pre = con.prepareStatement(sql);
        pre.setString(1,novel.getNovelTitle());
        pre.setString(2,novel.getNovelcontent());
        pre.setInt(3,novel.getId());
        return pre.executeUpdate();
    }
}