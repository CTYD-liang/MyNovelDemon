package com.liang.notes.controller;

import com.liang.notes.dao.ManageDao;
import com.liang.notes.entity.Manage;
import com.liang.notes.util.Jdbctool;
import com.liang.notes.util.StringUtils;
import com.liang.notes.view.UserLogin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 * 管理员登录页面
 * @author 86178
 */
public class ManageLogin extends JFrame {

    private JPanel contentPane;
    /**
     * 账号框
     */
    private JTextField countText;
    /**
     * 密码框
     */
    private JPasswordField passwordText;
    /**
     * 数据库操作工具类
     */
    private Jdbctool jdbctool = new Jdbctool();
    /**
     * 管理员类
     */
    private ManageDao manageDao = new ManageDao();


    /**
     * 初始化登录窗体
     */
    public ManageLogin() {
        setTitle("管理员登录页面");
        //设置窗口图标
        setIconImage(Toolkit.getDefaultToolkit().getImage(ManageLogin.class.getResource("/imagine/imer.png")));
        //设置窗体大小不可变
        setResizable(false);
        //窗口可关闭
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 479, 340);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("管理员登录");
        lblNewLabel.setBounds(219, 49, 86, 15);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel1 = new JLabel("账号：");
        lblNewLabel1.setBounds(97, 95, 54, 15);
        contentPane.add(lblNewLabel1);

        JLabel lblNewLabel2 = new JLabel("密码：");
        lblNewLabel2.setBounds(97, 151, 54, 15);
        contentPane.add(lblNewLabel2);

        //账号框实例
        countText = new JTextField();
        countText.setBounds(154, 92, 209, 21);
        contentPane.add(countText);
        countText.setColumns(10);

        //密码框实例
        passwordText = new JPasswordField();
        passwordText.setBounds(155, 148, 208, 21);
        contentPane.add(passwordText);

        //登录按钮
        JButton btnNewButton = new JButton("登录");
        //登录监听事件
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //登录事件处理函数
                manageLogin(e);
            }
        });
        btnNewButton.setBounds(154, 193, 209, 23);
        contentPane.add(btnNewButton);

        //返回按钮
        JButton btnNewButton1 = new JButton("返回");
        //返回事件监听，按下可以返回主登录窗口
        btnNewButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //返回事件处理函数
                returnAction(e);
            }
        });
        btnNewButton1.setBounds(316, 247, 93, 23);
        contentPane.add(btnNewButton1);

        //重置按钮
        JButton btnNewButton2 = new JButton("重置");
        //重置事件监听，按下可以将账号和密码框的文本清空
        btnNewButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //重置事件处理函数
                restValue(e);
            }
        });
        btnNewButton2.setBounds(97, 247, 93, 23);
        contentPane.add(btnNewButton2);

        JLabel lblNewLabel3 = new JLabel("");
        lblNewLabel3.setIcon(new ImageIcon(ManageLogin.class.getResource("/imagine/inac.png")));
        lblNewLabel3.setBounds(63, 34, 54, 15);
        contentPane.add(lblNewLabel3);
    }

    /**
     * 登录事件处理
     * @param event
     */
    private void manageLogin(ActionEvent event) {
        //获取账号名
        String managename = countText.getText();
        //获取密码，并转换为字符
        String password = String.valueOf(passwordText.getPassword());
        //判断账号密码框都不能为空才能进行登录验证操作
        if(StringUtils.isEmpty(managename)){
            JOptionPane.showMessageDialog(null,"账号不能为空！");
        }else if(StringUtils.isEmpty(password)){
            JOptionPane.showMessageDialog(null,"密码不能为空！");
        }else{
            //实例化一个对象
            Manage manage = new Manage(managename,password);
            Connection con = null;
            try {
                con = jdbctool.getconnection();
                //将对象传入数据库进行查找
                ResultSet res = manageDao.selectManage(con,manage);
                //如果找到了，就进入菜单页面
                if(res.next()){
                    JOptionPane.showMessageDialog(null,"欢迎"+ managename +"来到管理员页面！");
                    //销毁登录窗口
                    dispose();
                    //创建菜单页面
                    new ManageMenu(managename).setVisible(true);
                }else{
                    //否则提示输入错误,并将账号密码框重置
                    JOptionPane.showMessageDialog(null,"账号或密码输入错误！");
                    this.restValue(event);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }finally {
                try {
                    //释放数据框连接
                    jdbctool.release(con);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }


        }
    }


    /**
     * 返回事件
     * @param event
     */
    private void returnAction(ActionEvent event) {
        //关闭注册窗口
        dispose();
        //打开登录窗体
        new UserLogin().setVisible(true);
    }

    /**
     * 重置事件
     */
    private void restValue(ActionEvent event){
        //设置账号密码框为空
        this.countText.setText("");
        this.passwordText.setText("");
    }
}

