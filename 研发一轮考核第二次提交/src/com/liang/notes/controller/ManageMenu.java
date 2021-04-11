package com.liang.notes.controller;

import com.liang.notes.dao.ManageDao;
import com.liang.notes.entity.User;
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

public class ManageMenu extends JFrame {

    private JPanel contentPane;
    private JTable usertable;
    private JTextArea showannouccement;
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
                    ManageMenu frame = new ManageMenu("admin");
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
    public ManageMenu(String name) {
        setTitle("管理员菜单");
        setResizable(false);
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

        JScrollPane scrollPane = new JScrollPane();
        panel.add(scrollPane, BorderLayout.CENTER);

        usertable = new JTable();
        usertable.setModel(new DefaultTableModel(
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
        scrollPane.setViewportView(usertable);

        JLabel lblNewLabel = new JLabel("所有用户：");
        lblNewLabel.setBounds(53, 10, 76, 29);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel1 = new JLabel("编辑公告：");
        lblNewLabel1.setBounds(60, 292, 69, 15);
        contentPane.add(lblNewLabel1);

        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(60, 333, 515, 208);
        contentPane.add(scrollPane2);
        showannouccement = new JTextArea();
        scrollPane2.setViewportView(showannouccement);

        JButton btnNewButton = new JButton("发布公告");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //先从数据库获取公告，显示在文本域，按下按钮，修改公告！
                int n = JOptionPane.showConfirmDialog(null,"确认发布公告？");
                if(n==0){
                    postAnnoucment(e,name);
                }
            }
        });
        btnNewButton.setBounds(131, 591, 93, 23);
        contentPane.add(btnNewButton);

        JButton btnNewButton1 = new JButton("安全退出");
        btnNewButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //退出事件事件处理
                int n = JOptionPane.showConfirmDialog(null,"确认退出管理员页面？");
                if(n==0){
                    dispose();
                }
            }
        });
        btnNewButton1.setBounds(396, 591, 93, 23);
        contentPane.add(btnNewButton1);

        //设置文本域边框
        showannouccement.setBorder(new LineBorder(new java.awt.Color(127, 157, 185),1,false));
        this.fillMessage(new User("洪哥"));
        //显示公告
        this.showText(name);
    }

    /**
     * 发布公告时间
     * @param event
     */
    private void postAnnoucment(ActionEvent event,String name) {
        String post = showannouccement.getText();
        if(StringUtils.isEmpty(post)){
            post = " ";
        }
        Connection con = null;
        try {
            con = jdbctool.getconnection();
            int n = manageDao.updateAccounement(con,post);
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


    //表格初始化，在管理员登录的时候将表格填充
    private void fillMessage(User user){
        DefaultTableModel model =(DefaultTableModel)usertable.getModel();
        model.setRowCount(0);
        Connection con = null;
        try {
            con = jdbctool.getconnection();
            ResultSet res = manageDao.require(con);
            while(res.next()){
                Vector vector = new Vector();
                vector.add(res.getString("id"));
                vector.add(res.getString("user_name"));
                vector.add(res.getString("password"));
                vector.add(res.getString("age"));
                vector.add(res.getString("sex"));
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
     */
    private void showText(String manager){
        Connection con = null;
        try {
            con = jdbctool.getconnection();
            ResultSet res = manageDao.searchAccountment(con,manager);
            while(res.next()){
                String message = res.getString("announcement");
                this.showannouccement.setText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        }
    }
}
