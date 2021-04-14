package com.liang.notes.service;

import com.liang.notes.dao.NovelDao;
import com.liang.notes.entity.Novel;
import com.liang.notes.util.Jdbctool;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

/**
 * @author 86178
 */
public class Research extends JInternalFrame {

    /**
     *  设置一个表格
     */
    private JTable novelTable;
    /**
     * 设置搜索框
     */
    private JTextField resnovel;
    /**
     * 设置文本域
     */
    private JTextArea showContent;
    /**
     * 数据库连接类
     */
    private Jdbctool jdbctool = new Jdbctool();
    /**
     * 实例化一个对象
     */
    private NovelDao novelDao = new NovelDao();


    /**
     * 创建笔记广场窗体
     */
    public Research(String author) {
        setFrameIcon(new ImageIcon(Research.class.getResource("/imagine/iresearch.png")));
        //设置图标可见
        setIconifiable(true);
        //设置可关闭的
        setClosable(true);
        setTitle("笔记广场页面");
        setBounds(100, 100, 688, 634);
        getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(75, 91, 527, 204);
        getContentPane().add(scrollPane);

        JLabel lblNewLabel = new JLabel("搜索内容 ：");
        lblNewLabel.setBounds(75, 34, 91, 31);
        getContentPane().add(lblNewLabel);

        resnovel = new JTextField();
        resnovel.setBounds(176, 37, 286, 26);
        getContentPane().add(resnovel);
        resnovel.setColumns(10);

        JButton btnNewButton = new JButton("搜索\r\n");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resnovelAction(e,author);
            }
        });
        btnNewButton.setBounds(509, 38, 93, 23);
        getContentPane().add(btnNewButton);

        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setViewportBorder(new TitledBorder(null, "\u6587\u7AE0\u5185\u5BB9", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        scrollPane1.setBounds(75, 324, 527, 209);
        getContentPane().add(scrollPane1);

        showContent = new JTextArea();
        showContent.setEditable(false);
        scrollPane1.setViewportView(showContent);

        JButton btnNewButton1 = new JButton("返回");
        btnNewButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //直接退出广场
                dispose();
            }
        });
        btnNewButton1.setBounds(273, 558, 99, 23);
        getContentPane().add(btnNewButton1);

        novelTable = new JTable();
        novelTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //通过点击事件获取值，然后创建一个对象对数据库的对象进行查询，并返回文本内容
                //将内容设置在文本域
                String str = "novel";
                showcontentAction(e,str);
            }
        });
        novelTable.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "\u6587\u7AE0\u7F16\u53F7","\u6587\u7AE0\u4F5C\u8005", "\u6587\u7AE0\u6807\u9898"
                }
        ) {
            boolean[] columnEditables = new boolean[] {
                    false, false, false, false
            };
            @Override
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        scrollPane.setViewportView(novelTable);
        this.fillFrom(new Novel());
    }


    /**
     * 表格行点击事件处理
     * @param event
     */
    private void showcontentAction(MouseEvent event,String formname) {
        //获取点击行
       int row = novelTable.getSelectedRow();
       //获取行的值
       String author = (String)novelTable.getValueAt(row,1);
       String title = (String)novelTable.getValueAt(row,2);
       //实例化一个对象
       Novel novel = new Novel(author,title);
       Connection con = null;
        try {
            con = jdbctool.getconnection();
            ResultSet res = novelDao.list2(con, novel,formname);
            while(res.next()){
            String content = res.getString("content");
            showContent.setText(content);
            }
        }catch(Exception e) {
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
     * 笔记搜索
     * @param event
     */
    private void resnovelAction(ActionEvent event,String authorname) {
        //获取搜索框的关键字
       String researchname =  this.resnovel.getText();
       //实例化笔记对象
       Novel novel = new Novel();
       novel.setNovelauthor(authorname);
       novel.setNovelTitle(researchname);
       this.fillFromAction(novel);
        this.showContent.setText("");
    }

    /**
     * 表单事件初始化
     * @param novel
     */
    private void fillFrom(Novel novel) {
        //获取空模型
        DefaultTableModel model =(DefaultTableModel)novelTable.getModel();
        model.setRowCount(0);
        Connection con = null;
        try {
            con = jdbctool.getconnection();
            ResultSet res = novelDao.list(con, novel);
            while(res.next()){
                Vector vector = new Vector();
                vector.add(res.getString("id"));
                vector.add(res.getString("author"));
                vector.add(res.getString("title"));
                model.addRow(vector);
            }
        }catch(Exception e) {
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
     * 搜索事件
     * 将搜索到的填充到表格
     * @param novel
     */
    private void fillFromAction(Novel novel) {
        DefaultTableModel model =(DefaultTableModel)novelTable.getModel();
        model.setRowCount(0);
        Connection con = null;
        try {
            con = jdbctool.getconnection();
            ResultSet res = novelDao.list1(con, novel);
            while(res.next()){
                Vector vector = new Vector();
                vector.add(res.getString("id"));
                vector.add(res.getString("author"));
                vector.add(res.getString("title"));
                model.addRow(vector);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                jdbctool.release(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}