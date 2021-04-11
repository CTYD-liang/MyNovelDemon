package com.liang.notes.view;

import com.liang.notes.dao.NovelDao;
import com.liang.notes.entity.Novel;
import com.liang.notes.entity.User;
import com.liang.notes.util.Jdbctool;
import com.liang.notes.util.StringUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
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

public class Managenovel extends JInternalFrame {

    private JTextField idText;
    private JTextField titleText,textBelong;
    private JTable contenttable,contenttable1;
    private JTextArea showcontent;
    private Jdbctool jdbctool = new Jdbctool();
    private NovelDao novelDao = new NovelDao();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Managenovel frame = new Managenovel("梁某");
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
    public Managenovel(String author) {
        setEnabled(false);
        setIconifiable(true);
        setClosable(true);
        setTitle("文章管理页面\r\n");
        setBounds(100, 100, 688, 592);
        getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(64, 50, 247, 150);
        getContentPane().add(scrollPane);

        contenttable = new JTable();
        contenttable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //表格行点击，获取文章标题，从公有数据库查找该作者和文章
                String data1 = "novel";
                showContentAction(e,author,contenttable,data1);
            }
        });
        contenttable.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "\u516C\u6709\u6587\u7AE0"
                }
        ) {
            boolean[] columnEditables = new boolean[] {
                    false, false
            };
            @Override
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        contenttable.getColumnModel().getColumn(0).setPreferredWidth(220);
        scrollPane.setViewportView(contenttable);

        JButton btnNewButton = new JButton("修改");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //修改笔记事件
                updateAction(e,author);
            }
        });
        btnNewButton.setBounds(40, 529, 93, 23);
        getContentPane().add(btnNewButton);

        JButton btnNewButton1 = new JButton("删除");
        btnNewButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //删除笔记事件
                deleteAction(e,author);
            }
        });
        btnNewButton1.setBounds(208, 529, 93, 23);
        getContentPane().add(btnNewButton1);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "\u6587\u7AE0\u8BE6\u60C5", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setBounds(64, 225, 538, 280);
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("文章编号：");
        lblNewLabel.setBounds(24, 30, 66, 22);
        panel.add(lblNewLabel);

        idText = new JTextField();
        idText.setEditable(false);
        idText.setBounds(91, 31, 66, 21);
        panel.add(idText);
        idText.setColumns(10);

        JLabel lblNewLabel1 = new JLabel("文章标题：");
        lblNewLabel1.setBounds(242, 32, 66, 18);
        panel.add(lblNewLabel1);

        titleText = new JTextField();
        titleText.setBounds(318, 31, 167, 21);
        panel.add(titleText);
        titleText.setColumns(10);

        JLabel lblNewLabel2 = new JLabel("文章内容：");
        lblNewLabel2.setBounds(24, 74, 66, 15);
        panel.add(lblNewLabel2);

        JLabel lblNewLabel4 = new JLabel("属性 ：");
        lblNewLabel4.setBounds(242, 74, 54, 15);
        panel.add(lblNewLabel4);

        textBelong = new JTextField();
        textBelong.setEnabled(false);
        textBelong.setBounds(318, 71, 66, 21);
        panel.add(textBelong);
        textBelong.setColumns(10);

        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(24, 100, 487, 155);
        panel.add(scrollPane2);
        showcontent = new JTextArea();
        scrollPane2.setViewportView(showcontent);

        JButton btnNewButton2 = new JButton("公开");
        btnNewButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //修改笔记属性为公有，插入到公有，删除私有
                String uploadform = "novel";
                String deleteform = "usernovel";
                changAction(e,author,uploadform,deleteform);
            }
        });
        btnNewButton2.setBounds(381, 529, 93, 23);
        getContentPane().add(btnNewButton2);

        JButton btnNewButton3 = new JButton("私密");
        btnNewButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //修改笔记属性为私有，插入到私有，删除公有
                String uploadform = "usernovel";
                String deleteform = "novel";
                changAction(e,author,uploadform,deleteform);
            }
        });
        btnNewButton3.setBounds(542, 529, 93, 23);
        getContentPane().add(btnNewButton3);

        JLabel lblNewLabel3 = new JLabel("我的文章 ：");
        lblNewLabel3.setBounds(64, 25, 93, 15);
        getContentPane().add(lblNewLabel3);

        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setBounds(356, 50, 247, 150);
        getContentPane().add(scrollPane1);

        contenttable1 = new JTable();
        contenttable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //表格行点击，获取文章标题，从私有数据库查找该作者和文章
                String data = "usernovel";
                showContentAction(e,author,contenttable1,data);
            }
        });
        contenttable1.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "\u79C1\u6709\u6587\u7AE0"
                }
        ) {
            boolean[] columnEditables = new boolean[] {
                    false
            };
            @Override
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        scrollPane1.setViewportView(contenttable1);
        //设置文本域边框
        showcontent.setBorder(new LineBorder(new java.awt.Color(127, 157, 185),1,false));
        User user = new User();
        user.setUserName(author);
        this.fillFrom(user);
        this.fillFrom1(user);
    }


    /**
     * 修改属性
     * @param event
     */
    private void changAction(ActionEvent event,String author,String uploadform,String deleteform) {
        String id = idText.getText();
        String noveltitle = titleText.getText();
        String novelcontent = showcontent.getText();
        String belong =  textBelong.getText();
        Novel reseatnovel = new Novel(author,noveltitle,novelcontent);
        if(StringUtils.isEmpty(id)){
            JOptionPane.showMessageDialog(null, "请点击要修改的文章！");
            return;
        }
        if(belong.equals(deleteform)){
            belong = uploadform;
            int n = JOptionPane.showConfirmDialog(null, "确认修改文章属性？");
            if(n==0){
                Connection con1 = null;
                Connection con2 = null;
                try {
                    con1 = jdbctool.getconnection();
                    con2 = jdbctool.getconnection();
                    int n1 = novelDao.upload(con1,reseatnovel,belong);
                    int n2 = novelDao.deletenovel(con2,id,deleteform);
                    if(n1 == 1&&n2 ==1){
                        JOptionPane.showMessageDialog(null,"修改成功！");
                        this.restValue();
                        this.fillFrom(new User(author));
                        this.fillFrom1(new User(author));
                    }else{
                        JOptionPane.showMessageDialog(null,"操作失败！");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null,"操作失败！");
                }finally {
                    try {
                        jdbctool.release(con1);
                        jdbctool.release(con2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            JOptionPane.showMessageDialog(null, "属性未改变！");
            return;
        }
    }

    /**
     * 删除事件
     * @param event
     * @param author
     */
    private void deleteAction(ActionEvent event, String author) {
        String id = idText.getText();
        String belong = textBelong.getText();
        if(StringUtils.isEmpty(id)){
            JOptionPane.showMessageDialog(null, "请点击要删除的文章！");
            return;
        }
        int n = JOptionPane.showConfirmDialog(null, "确认删除文章？");
        if(n==0){
            Connection con = null;
            try {
                con = jdbctool.getconnection();
                int modifynum = novelDao.deletenovel(con,id,belong);
                if(modifynum == 1&&belong.equals("novel")){
                    JOptionPane.showMessageDialog(null, "删除成功！");
                    this.restValue();
                    this.fillFrom(new User(author));
                }else if (modifynum == 1&&belong.equals("usernovel")){
                    JOptionPane.showMessageDialog(null, "删除成功！");
                    this.restValue();
                    this.fillFrom1(new User(author));
                }else{
                    JOptionPane.showMessageDialog(null, "操作失败！");
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

    /**
     * 重置事件
     */
    private void restValue(){
        this.idText.setText("");
        this.titleText.setText("");
        this.textBelong.setText("");
        this.showcontent.setText("");
    }

    /**
     * 修改事件
     * @param event
     */
    private void updateAction(ActionEvent event,String authorname) {
        String id =  idText.getText();
        String noveltitle = titleText.getText();
        String novelcontent = showcontent.getText();
        String belong = textBelong.getText();
        if(StringUtils.isEmpty(id)){
            JOptionPane.showMessageDialog(null, "请点击要修改的文章！");
            return;
        }
        if(StringUtils.isEmpty(noveltitle)){
            JOptionPane.showMessageDialog(null, "标题不能为空！");
            return;
        }
        Novel novel = new Novel();
        novel.setId(Integer.parseInt(id));
        novel.setNovelTitle(noveltitle);
        novel.setNovelcontent(novelcontent);
        Connection con = null;
        try {
            con = jdbctool.getconnection();
            int modifynum = novelDao.updatenovel(con,novel,belong);
            if(modifynum == 1&&belong.equals("novel")){
                JOptionPane.showMessageDialog(null, "修改成功！");
                this.restValue();
                this.fillFrom(new User(authorname));
            }else if (modifynum == 1&&belong.equals("usernovel")){
                JOptionPane.showMessageDialog(null, "修改成功！");
                this.restValue();
                this.fillFrom1(new User(authorname));
            }else{
                JOptionPane.showMessageDialog(null, "操作失败！");
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
     * 表格行点击事件
     * @param event
     */
    private void showContentAction(MouseEvent event,String name,JTable table,String formname) {
        int row = table.getSelectedRow();
        String str = (String)table.getValueAt(row,0);
        Novel novel = new Novel();
        novel.setNovelauthor(name);
        novel.setNovelTitle(str);
        Connection con = null;
        try {
            con = jdbctool.getconnection();
            ResultSet res = novelDao.list2(con, novel,formname);
            while(res.next()){
                String id = res.getString("id");
                String content = res.getString("content");
                String noveltitle = res.getString("title");
                showcontent.setText(content);
                idText.setText(id);
                titleText.setText(noveltitle);
                textBelong.setText(formname);
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
     * 公有文章初始化
     * @param user
     */
    private void fillFrom(User user) {
        DefaultTableModel model =(DefaultTableModel)contenttable.getModel();
        //填充列
        model.setRowCount(0);
        Connection con = null;
        try {
            con = jdbctool.getconnection();
            ResultSet res = novelDao.search(con, user);
            while(res.next()){
                Vector vector = new Vector();
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
     * 私有文章初始化
     * @param user
     */
    private void fillFrom1(User user) {
        DefaultTableModel model =(DefaultTableModel)contenttable1.getModel();
        //填充列
        model.setRowCount(0);
        Connection con = null;
        try {
            con = jdbctool.getconnection();
            ResultSet res = novelDao.search1(con, user);
            while(res.next()){
                Vector vector = new Vector();
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
