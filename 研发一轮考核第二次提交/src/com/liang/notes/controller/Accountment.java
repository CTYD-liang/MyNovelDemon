package com.liang.notes.controller;

import com.liang.notes.dao.ManageDao;
import com.liang.notes.util.Jdbctool;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 * @author 86178
 */
public class Accountment extends JFrame {

    private JPanel contentPane;
    private JTextArea showacconuntment;
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
                    Accountment frame = new Accountment();
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
    public Accountment() {
        setTitle("公告！");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 481, 405);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setBounds(69, 95, 337, 126);
        contentPane.add(scrollPane1);
        showacconuntment = new JTextArea();
        showacconuntment.setEditable(false);
        scrollPane1.setViewportView(showacconuntment);

        showacconuntment.setBorder(new LineBorder(new java.awt.Color(127, 157, 185),1,false));

        JLabel lblNewLabel = new JLabel("用户须知：");
        lblNewLabel.setBounds(68, 32, 77, 27);
        contentPane.add(lblNewLabel);

        JButton btnNewButton = new JButton("确定");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnNewButton.setBounds(182, 277, 93, 23);
        contentPane.add(btnNewButton);
        this.showText("admin");
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
                this.showacconuntment.setText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        }
    }

}