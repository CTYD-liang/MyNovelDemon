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
 * @author 86178
 */
public class ManageLogin extends JFrame {

    private JPanel contentPane;
    private JTextField countText;
    private JPasswordField passwordText;
    private Jdbctool jdbctool = new Jdbctool();
    private ManageDao manageDao = new ManageDao();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ManageLogin frame = new ManageLogin();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public ManageLogin() {
        setTitle("管理员登录页面");
        setIconImage(Toolkit.getDefaultToolkit().getImage(ManageLogin.class.getResource("/imagine/imer.png")));
        setResizable(false);
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

        countText = new JTextField();
        countText.setBounds(154, 92, 209, 21);
        contentPane.add(countText);
        countText.setColumns(10);

        passwordText = new JPasswordField();
        passwordText.setBounds(155, 148, 208, 21);
        contentPane.add(passwordText);

        JButton btnNewButton = new JButton("登录");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //登录事件处理
                manageLogin(e);
            }
        });
        btnNewButton.setBounds(154, 193, 209, 23);
        contentPane.add(btnNewButton);

        JButton btnNewButton1 = new JButton("返回");
        btnNewButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnAction(e);
            }
        });
        btnNewButton1.setBounds(316, 247, 93, 23);
        contentPane.add(btnNewButton1);

        JButton btnNewButton2 = new JButton("重置");
        btnNewButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
     * @param e
     */
    private void manageLogin(ActionEvent e) {
        String managename = countText.getText();
        String password = String.valueOf(passwordText.getPassword());
        if(StringUtils.isEmpty(managename)){
            JOptionPane.showMessageDialog(null,"账号不能为空！");
        }else if(StringUtils.isEmpty(password)){
            JOptionPane.showMessageDialog(null,"密码不能为空！");
        }else{
            Manage manage = new Manage(managename,password);
            Connection con = null;
            try {
                con = jdbctool.getconnection();
                ResultSet res = manageDao.selectManage(con,manage);
                if(res.next()){
                    JOptionPane.showMessageDialog(null,"欢迎"+ managename +"来到管理员页面！");
                    dispose();
                    new ManageMenu(managename).setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null,"账号或密码输入错误！");
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
        this.countText.setText("");
        this.passwordText.setText("");
    }
}

