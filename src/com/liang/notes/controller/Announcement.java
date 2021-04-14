package com.liang.notes.controller;

import com.liang.notes.dao.ManageDao;
import com.liang.notes.util.Jdbctool;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 * 公告框页面
 * @author 86178
 */
public class Announcement extends JFrame {

    private JPanel contentPane;
    /**
     * 显示公告的文本域
     */
    private JTextArea showAnnouncement;
    /**
     * 数据库操作工具类
     */
    private Jdbctool jdbctool = new Jdbctool();
    /**
     * 管理员类
     */
    private ManageDao manageDao = new ManageDao();

    /**
     * 实例化公告栏
     */
    public Announcement() {
        setTitle("公告！");
        //设置可见
        setResizable(false);
        //设置可关闭
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 481, 405);
        //设置盛放文本域的面板
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        //设置可变长度面板，将文本域设置进去，才能使得公告长度可变
        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setBounds(69, 95, 337, 126);
        contentPane.add(scrollPane1);
        showAnnouncement = new JTextArea();
        //设置公告框不可编辑
        showAnnouncement.setEditable(false);
        scrollPane1.setViewportView(showAnnouncement);

        //设置文本域边框
        showAnnouncement.setBorder(new LineBorder(new java.awt.Color(127, 157, 185),1,false));

        //设置提示标签
        JLabel lblNewLabel = new JLabel("用户须知：");
        lblNewLabel.setBounds(68, 32, 77, 27);
        contentPane.add(lblNewLabel);

        //设置确定按钮，按下可以销毁公告窗口，返回登录页面
        JButton btnNewButton = new JButton("确定");
        //销毁窗口监听事件
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnNewButton.setBounds(182, 277, 93, 23);
        contentPane.add(btnNewButton);
        //窗体初始化的时候就将公告内容同时初始化出来，暂时默认只有一个管理员
        this.showText("admin");
    }

    /**
     * 显示公告内容函数
     */
    private void showText(String manager){
        Connection con = null;
        try {
            //获得连接
            con = jdbctool.getconnection();
            ResultSet res = manageDao.searchAccountment(con,manager);
            while(res.next()){
                //查到了就返回公告
                String message = res.getString("announcement");
                //将公告显示在文本域
                this.showAnnouncement.setText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                //释放连接
                jdbctool.release(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}