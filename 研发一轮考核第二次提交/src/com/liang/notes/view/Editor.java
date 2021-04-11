package com.liang.notes.view;


import com.liang.notes.dao.NovelDao;
import com.liang.notes.entity.Novel;
import com.liang.notes.util.Jdbctool;
import com.liang.notes.util.StringUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

/**
 * 编辑笔记页面
 * @author 86178
 */
public class Editor extends JInternalFrame {
    private JTextField novelTitleTxt;
    private JTextArea novelContentTxt;
    private Jdbctool jdbctool = new Jdbctool();
    private NovelDao novelDao = new NovelDao();

    /**
     *测试运行
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Editor editor = new Editor("haojiahuo");
                    editor.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 创建一个添加笔记的内部窗体
     */
    public Editor(String author) {
        setFrameIcon(new ImageIcon(Editor.class.getResource("/imagine/iEditor.png")));
        setTitle("笔记添加页面");
        //设置图标可见
        setIconifiable(true);
        //设置可关闭的
        setClosable(true);
        setBounds(100, 100, 722, 512);
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("文章标题 ：");
        lblNewLabel.setIcon(new ImageIcon(Editor.class.getResource("/imagine/ititle.png")));
        lblNewLabel.setBounds(10, 15, 106, 30);
        getContentPane().add(lblNewLabel);

        novelTitleTxt = new JTextField();
        novelTitleTxt.setBounds(126, 20, 544, 34);
        getContentPane().add(novelTitleTxt);
        novelTitleTxt.setColumns(10);

        JLabel lblNewLabel1 = new JLabel("文本内容 ：");
        lblNewLabel1.setIcon(new ImageIcon(Editor.class.getResource("/imagine/icontent.png")));
        lblNewLabel1.setBounds(10, 65, 97, 21);
        getContentPane().add(lblNewLabel1);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(131, 64, 539, 408);
        getContentPane().add(scrollPane);


        novelContentTxt = new JTextArea();
        scrollPane.setViewportView(novelContentTxt);

        JButton button = new JButton("私密");
        button.setIcon(new ImageIcon(Editor.class.getResource("/imagine/iadd.png")));

        button.setBounds(10, 171, 106, 23);
        getContentPane().add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = "usernovel";
                upLoadAction(e,author,str);
            }
        });
        JButton btnNewButton = new JButton("重置");
        btnNewButton.setIcon(new ImageIcon(Editor.class.getResource("/imagine/ireturn.png")));
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetValue(e);
            }
        });
        btnNewButton.setBounds(10, 384, 106, 23);
        getContentPane().add(btnNewButton);

        JButton btnNewButton1 = new JButton("发布");
        btnNewButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = "novel";
                upLoadAction(e,author,str);
            }
        });
        btnNewButton1.setIcon(new ImageIcon(Editor.class.getResource("/imagine/igoto.png")));
        btnNewButton1.setBounds(10, 280, 106, 23);
        getContentPane().add(btnNewButton1);
        //设置文本域边框
        novelContentTxt.setBorder(new LineBorder(new java.awt.Color(127, 157, 185),1,false));
    }


    /**
     * 文章保存事件
     * @param event
     */
    private void upLoadAction(ActionEvent event,String authorname,String form) {
        String title = this.novelTitleTxt.getText();
        String content = this.novelContentTxt.getText();
        if(StringUtils.isEmpty(title)) {
            JOptionPane.showMessageDialog(null, "笔记标题不能为空！");
            return;
        }
        int n0 = JOptionPane.showConfirmDialog(null,"确定发表文章吗？");
        if(n0 ==0) {
            //实例化对象
            Novel novel = new Novel(authorname, title, content);
            Connection connection = null;
            try {
                connection = jdbctool.getconnection();
                int n = novelDao.upload(connection, novel, form);
                if (n == 1) {
                    JOptionPane.showMessageDialog(null, "操作成功！");
                    resetForm();
                } else {
                    JOptionPane.showMessageDialog(null, "操作失败！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "操作失败！");
            } finally {
                try {
                    jdbctool.release(connection);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 重置事件处理
     * @param event
     */
    private void resetValue(ActionEvent event) {
        this.resetForm();

    }

    /**
     * 重置表单
     */
    private void resetForm() {
        this.novelTitleTxt.setText("");
        this.novelContentTxt.setText("");
    }
}