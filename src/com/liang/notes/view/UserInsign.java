package com.liang.notes.view;
import com.liang.notes.dao.UserDao;
import com.liang.notes.util.Jdbctool;
import com.liang.notes.util.StringUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 * 注册窗体
 * @author 86178
 */
public class UserInsign extends JFrame {
    private JPanel contentPane;
    /**
     * 账号框
     */
    private JTextField userName;
    /**
     * 密码框
     */
    private JPasswordField password;
    /**
     * 数据库连接
     */
    private Connection connection = null;
    /**
     * 数据库操做封装工具类
     */
    private Jdbctool jdbctool = new Jdbctool();
    /**
     * 用户数据操作工具类
     */
    private UserDao userDao = new UserDao();
    /**
     * 查询返回的集合
     */
    private ResultSet res = null;


    /**
     * 注册界面的创建
     */
    public  UserInsign() {
        setResizable(false);
        //设置图标
        setIconImage(Toolkit.getDefaultToolkit().getImage(UserInsign.class.getResource("/imagine/user_protection.png")));
        setTitle("注册界面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("用户注册");
        lblNewLabel.setBounds(180, 27, 89, 16);
        lblNewLabel.setIcon(new ImageIcon(UserInsign.class.getResource("/imagine/incount.png")));
        contentPane.add(lblNewLabel);

        //账号框实例
        userName = new JTextField();
        userName.setBounds(206, 82, 110, 21);
        userName.setColumns(10);
        contentPane.add(userName);

        //密码框实例
        password = new JPasswordField();
        password.setBounds(206, 121, 110, 21);
        contentPane.add(password);

        JLabel lblNewLabel1 = new JLabel("账  号：");
        lblNewLabel1.setBounds(89, 84, 68, 16);
        lblNewLabel1.setIcon(new ImageIcon(UserInsign.class.getResource("/imagine/insign.png")));
        contentPane.add(lblNewLabel1);

        JLabel lblNewLabel2 = new JLabel("密  码：");
        lblNewLabel2.setBounds(89, 123, 68, 16);
        lblNewLabel2.setIcon(new ImageIcon(UserInsign.class.getResource("/imagine/insignpassword.png")));
        contentPane.add(lblNewLabel2);

        JButton button = new JButton("注 册");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 if(e.getSource() == button){
                     try {
                         //获取注册名
                         String username = userName.getText();
                         //获取密码
                         String pass =String.valueOf(password.getPassword());
                         if(StringUtils.isEmpty(username)) {
                             JOptionPane.showMessageDialog(null, "用户名不能为空！");
                         }else if(StringUtils.isEmpty(pass)) {
                         JOptionPane.showMessageDialog(null, "密码不能为空！");
                         } else  {
                             //获取数据连接
                             connection = jdbctool.getconnection();
                             //查询用户
                             res = userDao.research(connection,username);
                             //如果用户存在，不能重复注册
                             if(res.next()){
                                 JOptionPane.showMessageDialog(null, "该用户已存在!");
                                 //重置账号，密码框
                                 this.resetFrom();
                             }else{
                                 //用户不存在，则将用户密码插入到数据库，，登录成功
                                 int n = userDao.Insign(connection, username, pass);
                                 if(n==1) {
                                     JOptionPane.showMessageDialog(null, "注册成功!");
                                     dispose();  //关闭注册窗体
                                     new UserLogin().setVisible(true);  //打开登录窗体
                                 }else{
                                     JOptionPane.showMessageDialog(null, "注册失败!");
                                 }
                             }
                         }
                     }catch (Exception e1){
                         e1.printStackTrace();
                     }finally {
                         try {
                             jdbctool.release(connection);
                         } catch (Exception exception) {
                             exception.printStackTrace();
                         }
                     }
                 }
            }

            /**
             * 重置表单处理
             */
            private void resetFrom(){
                userName.setText("");
                password.setText("");
            }
        });
        button.setBounds(111, 184, 74, 23);
        contentPane.add(button);

        JButton button1 = new JButton("返 回");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //返回登录窗口
                overActionPerfrom(e);
            }
        });
        button1.setBounds(254, 184, 89, 23);
        contentPane.add(button1);
        //设置窗体居中
        this.setLocationRelativeTo(null);
    }

    /**
     * 返回事件
     * @param event
     */
    private void overActionPerfrom(ActionEvent event) {
        //关闭注册窗口
        dispose();
        //打开登录窗体
        new UserLogin().setVisible(true);
    }
}
