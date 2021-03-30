package com.liang.notes.view;


import com.liang.notes.dao.UserDao;
import com.liang.notes.entity.User;
import com.liang.notes.util.JdbcUtils;
import com.liang.notes.util.Jdbctool;
import com.liang.notes.util.StringUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;


public class UserLogin extends JFrame {

    /**
     * 面板
     */
    private JPanel contentPane;
    /**
     * 账号输入框
     */
    private JTextField userName;
    /**
     * 密码输入框
     */
    private JPasswordField password;
    /**
     *封装类
     */
    private UserDao userdao = new UserDao();

    /**
     * 启动登录窗口
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UserLogin frame = new UserLogin();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 创建窗口
     */
    public UserLogin() {
        //设置窗口标签
        setIconImage(Toolkit.getDefaultToolkit().getImage(UserLogin.class.getResource("/imagine/system.png")));
        //设置窗口大小固定
        setResizable(false);
        //设置窗口标题
        setTitle("本地系统登录页面");
        //设置窗口可关闭
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口大小
        setBounds(100, 100, 450, 323);

        //创建一个面板容器到窗口中
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        //设置面板布局为表格布局，任意行，两列，水平间距，竖直间距为零
        contentPane.setLayout(new GridLayout(0, 2, 0, 0));

        //设置系统主页标签
        JLabel lblNewLabel = new JLabel("最牛笔记系统");
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 18));
        lblNewLabel.setIcon(new ImageIcon(UserLogin.class.getResource("/imagine/systema.png")));
        contentPane.add(lblNewLabel);

        //用面板填充
        JLabel label2 = new JLabel("");
        contentPane.add(label2);

        //用面板填充
        JLabel label = new JLabel("");
        contentPane.add(label);

        //用面板填充
        JLabel label1 = new JLabel("");
        contentPane.add(label1);

        //设置账号图标
        JLabel lblNewLabel1 = new JLabel("           用户名：");
        lblNewLabel1.setIcon(new ImageIcon(UserLogin.class.getResource("/imagine/count.png")));
        contentPane.add(lblNewLabel1);

        //设置账号输入框
        userName = new JTextField();
        userName.setColumns(10);
        contentPane.add(userName);

        //设置密码图标
        JLabel lblNewLabel2 = new JLabel("             密  码：");
        lblNewLabel2.setIcon(new ImageIcon(UserLogin.class.getResource("/imagine/password.png")));
        contentPane.add(lblNewLabel2);

        //设置密码输入框
        password = new JPasswordField();
        contentPane.add(password);

        //用面板填充
        JLabel label3 = new JLabel("");
        contentPane.add(label3);

        //用面板填充
        JLabel label4 = new JLabel("");
        contentPane.add(label4);

        //设置登录按钮
        JButton btnNewButton = new JButton("登录");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginAction(e);
            }
        });
        btnNewButton.setIcon(new ImageIcon(UserLogin.class.getResource("/imagine/login.png")));
        contentPane.add(btnNewButton);

        //设置重置按钮
        JButton btnNewButton1 = new JButton("重置");
        btnNewButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetValueAction(e);
            }
        });
        btnNewButton1.setIcon(new ImageIcon(UserLogin.class.getResource("/imagine/reset.png")));
        contentPane.add(btnNewButton1);

        //用面板填充
        JLabel label5 = new JLabel("");
        contentPane.add(label5);

        //用面板填充
        JLabel label6 = new JLabel("");
        contentPane.add(label6);

        //设置管理员登录按钮
        JButton btnNewButton4 = new JButton("管理员登录");
        contentPane.add(btnNewButton4);

        //设置注册按钮
        JButton btnNewButton3 = new JButton("用户注册");
        btnNewButton3.setIcon(new ImageIcon(UserLogin.class.getResource("/imagine/user_protection.png")));
        contentPane.add(btnNewButton3);

        //用面板填充
        JLabel label7 = new JLabel("");
        contentPane.add(label7);

        //设置公告按钮
        JButton btnNewButton2 = new JButton("公 告");
        btnNewButton2.setIcon(new ImageIcon(UserLogin.class.getResource("/imagine/announcement.png")));
        contentPane.add(btnNewButton2);
    }

    /**
     * 重置事件处理
     * @param event
     */
    private void resetValueAction(ActionEvent event) {
        this.userName.setText("");
        this.password.setText("");
    }

    /**
     * 登录事件处理
     * @param event
     */
    private void LoginAction(ActionEvent event) {
        //获取用户名
        String userName = this.userName.getText();
        //获取密码并转为字符
        String password = new String(this.password.getPassword());
        if(StringUtils.isEmpty(userName)) {
            JOptionPane.showMessageDialog(null, "用户名不能为空！");
        }else if(StringUtils.isEmpty(password)) {
            JOptionPane.showMessageDialog(null, "密码不能为空！");
        }else {
            return;
        }

        User user = new User(userName,password);
        Connection connection = null;
        try {
            //获取连接
            connection = JdbcUtils.getConnection();
            User currentuser = userdao.login(connection,user);
            if(currentuser != null){
                JOptionPane.showMessageDialog(null, "登录成功！");
            }
            else{
                JOptionPane.showMessageDialog(null, "用户名或密码错误！");
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }
}


