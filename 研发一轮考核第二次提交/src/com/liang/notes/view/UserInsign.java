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
 * @author 86178
 */
public class UserInsign extends JFrame {
    private JPanel contentPane;
    private JTextField userName;
    private JPasswordField password;
    private Connection connection = null;
    private Connection connection1 = null;
    private Connection connection2 = null;
    private Jdbctool jdbctool = new Jdbctool();
    private UserDao userDao = new UserDao();
    private ResultSet res = null;

    /**
     * 运行程序
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UserInsign frame = new UserInsign();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 注册界面的创建
     */
    public  UserInsign() {
        setResizable(false);
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

        userName = new JTextField();
        userName.setBounds(206, 82, 110, 21);
        userName.setColumns(10);
        contentPane.add(userName);

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
                             connection1 = jdbctool.getconnection();
                             connection2 = jdbctool.getconnection();
                             res = userDao.research(connection,username);
                             if(res.next()){
                                 JOptionPane.showMessageDialog(null, "该用户已存在!");
                                 this.resetFrom();
                             }else{
                                     int n = userDao.Insign(connection1, connection2, username, pass);
                                     if(n==2) {
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
                             jdbctool.release(connection1);
                             jdbctool.release(connection2);
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
                overActionPerfrom(e);
            }
        });
        button1.setBounds(254, 184, 89, 23);
        contentPane.add(button1);
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
