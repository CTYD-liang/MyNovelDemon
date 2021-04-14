package com.liang.notes.controller;

import com.liang.notes.dao.ManageDao;
import com.liang.notes.util.Jdbctool;
import com.liang.notes.util.StringUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

/**
 * 管理员菜单页面
 * @author 86178
 */
public class ManageMenu extends JFrame {

    private JPanel contentPane;
    /**
     * 显示用户信息的表格
     */
    private JTable userTable;
    /**
     * 显示公告内容的文本域
     */
    private JTextArea showAnnouccement;
    /**
     * 数据库操作工具类
     */
    private Jdbctool jdbctool = new Jdbctool();
    /**
     * 管理员类
     */
    private ManageDao manageDao = new ManageDao();

    /**
     * 初始化管理员菜单窗口
     */
    public ManageMenu(String name) {
        setTitle("管理员菜单");
        //设置大小不可变
        setResizable(false);
        //设置可关闭
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 661, 690);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(53, 49, 522, 208);
        contentPane.add(panel);
        panel.setLayout(new BorderLayout(0, 0));

        //设置可下拉面板，放用户信息表
        JScrollPane scrollPane = new JScrollPane();
        panel.add(scrollPane, BorderLayout.CENTER);

        //用户信息表实例化
        userTable = new JTable();
        userTable.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "\u7528\u6237\u7F16\u53F7", "\u7528\u6237\u540D", "\u7528\u6237\u5BC6\u7801", "\u7528\u6237\u5E74\u9F84", "\u7528\u6237\u6027\u522B"
                }
        ) {
            boolean[] columnEditables = new boolean[] {
                    false, false, false, false, false
            };
            @Override
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        scrollPane.setViewportView(userTable);

        JLabel lblNewLabel = new JLabel("所有用户：");
        lblNewLabel.setBounds(53, 10, 76, 29);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel1 = new JLabel("编辑公告：");
        lblNewLabel1.setBounds(60, 292, 69, 15);
        contentPane.add(lblNewLabel1);

        //设置可变面板，放文本域
        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(60, 333, 515, 208);
        contentPane.add(scrollPane2);
        showAnnouccement = new JTextArea();
        //设置单词在一行不足容纳时换行
        showAnnouccement.setWrapStyleWord(true);
        //设置文本编辑区自动换行默认为true,即会"自动换行"
        showAnnouccement.setLineWrap(true);
        scrollPane2.setViewportView(showAnnouccement);

        //发布公告按钮
        JButton btnNewButton = new JButton("发布公告");
        //发布公告监听事件
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //先从数据库获取公告，显示在文本域，按下按钮，修改公告，将数据库中公告内容修改！
                int n = JOptionPane.showConfirmDialog(null,"确认发布公告？");
                //发布前确认提示信息
                if(n==0){
                    postAnnoucment(e,name);
                }
            }
        });
        btnNewButton.setBounds(131, 591, 93, 23);
        contentPane.add(btnNewButton);

        //退出按钮
        JButton btnNewButton1 = new JButton("安全退出");
        //退出事件监听
        btnNewButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //退出事件事件处理
                int n = JOptionPane.showConfirmDialog(null,"确认退出管理员页面？");
                //突出前确认信息
                if(n==0){
                    dispose();
                }
            }
        });
        btnNewButton1.setBounds(396, 591, 93, 23);
        contentPane.add(btnNewButton1);

        //设置文本域边框
        showAnnouccement.setBorder(new LineBorder(new java.awt.Color(127, 157, 185),1,false));
        //显示所又用户信息
        this.fillMessage();
        //显示公告
        this.showText(name);
    }

    /**
     * 发布公告事件
     * @param event
     */
    private void postAnnoucment(ActionEvent event,String name) {
        //获取文本域公告
        String post = showAnnouccement.getText();
        //如果为空，则设置为空
        if(StringUtils.isEmpty(post)){
            post = " ";
        }
        Connection con = null;
        try {
            con = jdbctool.getconnection();
            int n = manageDao.updateAccounement(con,post);
            //如果操作成功，会返回数字1
            if(n==1){
                JOptionPane.showMessageDialog(null,"发布成功！");
                this.showText(name);
            }else{
                JOptionPane.showMessageDialog(null,"操作失败！");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }finally {
            try {
                jdbctool.release(con);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }


    /**
     * 表格初始化，在管理员登录的时候将表格填充
     */
    private void fillMessage(){
        //获取模板
        DefaultTableModel model =(DefaultTableModel)userTable.getModel();
        //初始化0行
        model.setRowCount(0);
        Connection con = null;
        try {
            con = jdbctool.getconnection();
            ResultSet res = manageDao.require(con);
            //用户数据遍历
            while(res.next()){
                //将数据存到数组
                Vector vector = new Vector();
                vector.add(res.getString("id"));
                vector.add(res.getString("user_name"));
                vector.add(res.getString("password"));
                vector.add(res.getString("age"));
                vector.add(res.getString("sex"));
                //将数组的数据显示在表格
                model.addRow(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                jdbctool.release(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 显示公告内容！
     * @param manager
     */
    private void showText(String manager){
        Connection con = null;
        try {
            con = jdbctool.getconnection();
            ResultSet res = manageDao.searchAccountment(con,manager);
            while(res.next()){
                String message = res.getString("announcement");
                //将公告设置在文本域
                this.showAnnouccement.setText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        }
    }
}
