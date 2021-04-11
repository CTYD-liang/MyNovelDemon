package com.liang.notes.view;

import com.liang.notes.dao.UserDao;
import com.liang.notes.entity.User;
import com.liang.notes.entity.UserMessage;
import com.liang.notes.util.Jdbctool;
import com.liang.notes.util.StringUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 * @author 86178
 */
public class Updateperson extends JInternalFrame {
    private JTextField ageText;
    private JTextField sexText;
    private JPasswordField oldpasswordText;
    private JPasswordField newpasswordText;
    private JTextArea persondescribeText;
    private UserDao userDao = new UserDao();
    private Jdbctool jdbctool = new Jdbctool();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Updateperson frame = new Updateperson("洪哥");
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
    public Updateperson(String author) {
        setIconifiable(true);
        setClosable(true);
        setTitle("修改个人信息");
        setBounds(100, 100, 564, 490);
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("性别：");
        lblNewLabel.setBounds(42, 77, 54, 15);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel1 = new JLabel("年龄：");
        lblNewLabel1.setBounds(42, 35, 54, 15);
        getContentPane().add(lblNewLabel1);

        JLabel lblNewLabel2 = new JLabel("个人描述：");
        lblNewLabel2.setBounds(42, 121, 69, 15);
        getContentPane().add(lblNewLabel2);

        ageText = new JTextField();
        ageText.setBounds(89, 32, 66, 21);
        getContentPane().add(ageText);
        ageText.setColumns(10);

        sexText = new JTextField();
        sexText.setBounds(89, 74, 66, 21);
        getContentPane().add(sexText);
        sexText.setColumns(10);


        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setBounds(42, 166, 460, 182);
        getContentPane().add(scrollPane1);

        persondescribeText = new JTextArea();
        scrollPane1.setViewportView(persondescribeText);

        JLabel lblNewLabel3 = new JLabel("修改密码");
        lblNewLabel3.setIcon(new ImageIcon(Updateperson.class.getResource("/imagine/insignpassword.png")));
        lblNewLabel3.setBounds(388, 35, 86, 15);
        getContentPane().add(lblNewLabel3);

        JLabel lblNewLabel4 = new JLabel("旧密码：");
        lblNewLabel4.setBounds(324, 77, 54, 15);
        getContentPane().add(lblNewLabel4);

        JLabel lblNewLabel5 = new JLabel("新密码：");
        lblNewLabel5.setBounds(324, 121, 54, 15);
        getContentPane().add(lblNewLabel5);

        oldpasswordText = new JPasswordField();
        oldpasswordText.setBounds(388, 74, 114, 21);
        getContentPane().add(oldpasswordText);
        oldpasswordText.setColumns(10);

        newpasswordText = new JPasswordField();
        newpasswordText.setBounds(388, 118, 114, 21);
        getContentPane().add(newpasswordText);
        newpasswordText.setColumns(10);

        JButton btnNewButton = new JButton("修改个人信息");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //修改个人信息,先从数据库中查找到个人信息并显示出来
                updateMessage(e, author);
            }
        });
        btnNewButton.setBounds(89, 390, 139, 23);
        getContentPane().add(btnNewButton);

        JButton btnNewButton1 = new JButton("修改个人密码");
        btnNewButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //修改个人密码，判断和原来的密码是否相符合
                updatePassword(e, author);
            }
        });
        btnNewButton1.setBounds(342, 390, 132, 23);
        getContentPane().add(btnNewButton1);

        //设置文本域边框
        persondescribeText.setBorder(new LineBorder(new java.awt.Color(127, 157, 185), 1, false));

        //填充用户个人信息
        this.showMessage(author);
    }

    /**
     * 修改用户密码
     *
     * @param e
     * @param author
     */
    private void updatePassword(ActionEvent e, String author) {
        String oldpass = String.valueOf(oldpasswordText.getPassword());
        String newpass = String.valueOf(newpasswordText.getPassword());
        if(StringUtils.isEmpty(oldpass)){
            JOptionPane.showMessageDialog(null, "旧密码不能为空！");
        }else if(StringUtils.isEmpty(newpass)){
            JOptionPane.showMessageDialog(null, "新密码不能为空！");
        }else{
        User user1 = new User(author, oldpass);
        User user2 = new User(author, newpass);
        Connection con1 = null;
        Connection con2 = null;
        int n = JOptionPane.showConfirmDialog(null, "是否确认修改个人信息？");
          if (n == 0) {
             try {
                con1 = jdbctool.getconnection();
                con2 = jdbctool.getconnection();
                ResultSet res = userDao.judge(con1, user1);
                if (res.next()) {
                    if (newpass.equals(oldpass)) {
                        JOptionPane.showMessageDialog(null, "新旧密码相同，请重新输入！");
                        this.newpasswordText.setText("");
                    }else{
                    int n1 = userDao.updatepassword(con2,user2);
                    if(n1==1){
                        JOptionPane.showMessageDialog(null, "密码修改成功，请牢记新密码！");
                        this.restValue();
                    }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "旧密码错误！");
                    this.oldpasswordText.setText("");
                }
             } catch (Exception exception) {
                exception.printStackTrace();
             } finally {
                try {
                    jdbctool.release(con1);
                    jdbctool.release(con2);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
          }
        }
    }

    /**
     * 修改用户个人信息
     *
     * @param event
     * @param author
     */
    private void updateMessage(ActionEvent event, String author) {
        String useage = ageText.getText();
        String usesex = sexText.getText();
        String userdescribe = persondescribeText.getText();
        UserMessage userMessage = new UserMessage(author, useage, usesex, userdescribe);
        Connection con = null;
        int n = JOptionPane.showConfirmDialog(null, "是否确认修改个人信息？");
        if (n == 0) {
            try {
                con = jdbctool.getconnection();
                int n1 = userDao.updateperson(con, userMessage);
                if (n1 == 1) {
                    JOptionPane.showMessageDialog(null, "修改成功！");
                    this.showMessage(author);
                } else {
                    JOptionPane.showMessageDialog(null, "操作失败！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "操作失败！");
            } finally {
                try {
                    jdbctool.release(con);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 显示个人信息
     *
     * @param name
     */
    private void showMessage(String name) {
        Connection con = null;
        try {
            con = jdbctool.getconnection();
            ResultSet res = userDao.research1(con, name);
            while (res.next()) {
                String userage = res.getString("age");
                String usersex = res.getString("sex");
                String persondescribe = res.getString("persondescribe");
                ageText.setText(userage);
                sexText.setText(usersex);
                persondescribeText.setText(persondescribe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                jdbctool.release(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 刷新用户密码框
     */
    private void restValue() {
        this.oldpasswordText.setText("");
        this.newpasswordText.setText("");
    }

}
