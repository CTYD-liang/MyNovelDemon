package com.liang.notes.view;


import com.liang.notes.dao.NovelDao;
import com.liang.notes.entity.Novel;
import com.liang.notes.util.Jdbctool;
import com.liang.notes.util.StringUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;

/**
 * 编辑笔记页面
 * @author 86178
 */
public class Editor extends JInternalFrame {
    /**
     * 标题框
     */
    private JTextField novelTitleTxt;
    /**
     *内容框
     */
    private JTextArea novelContentTxt;
    /**
     * 数据库连接工具类
     */
    private Jdbctool jdbctool = new Jdbctool();
    /**
     * 笔记类
     */
    private NovelDao novelDao = new NovelDao();
    /**
     * 文件类
     */
    private File openFile;
    /**
     * 字节文件输入流
     */
    private FileInputStream fileInput;
    /**
     * 字节文件输出流
     */
    private FileOutputStream fileOutput;
    /**
     * 缓冲输入流
     */
    private BufferedInputStream bs;
    /**
     * 缓冲输出流
     */
    private BufferedOutputStream out;
    /**
     * 字符文件输出流
     */
    private OutputStreamWriter outputWriter;
    /**
     * 文件路径文本框
     */
    private JTextField browsh;
    /**
     * 文件内容
     */
    private String content;
    /**
     * 文件路径内容
     */
    private String Absoulate;


    /**
     * 创建一个添加笔记的内部窗体
     */
    public Editor(String author) {
        setFrameIcon(new ImageIcon(Editor.class.getResource("/imagine/iEditor.png")));
        //提示为空
        setToolTipText("");
        setTitle("笔记添加页面");
        //设置图标可见
        setIconifiable(true);
        //设置可关闭的
        setClosable(true);
        setBounds(100, 100, 722, 653);
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("文章标题 ：");
        lblNewLabel.setIcon(new ImageIcon(Editor.class.getResource("/imagine/ititle.png")));
        lblNewLabel.setBounds(10, 15, 106, 30);
        getContentPane().add(lblNewLabel);

        //标题框实例
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

        //内容框
        novelContentTxt = new JTextArea();
        //设置单词在一行不足容纳时换行
        novelContentTxt.setWrapStyleWord(true);
        //设置文本编辑区自动换行默认为true,即会"自动换行"
        novelContentTxt.setLineWrap(true);
        scrollPane.setViewportView(novelContentTxt);

        JButton button = new JButton("私密");
        button.setIcon(new ImageIcon(Editor.class.getResource("/imagine/iadd.png")));

        button.setBounds(10, 157, 106, 23);
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
               resetForm();
            }
        });
        btnNewButton.setBounds(10, 341, 106, 23);
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
        btnNewButton1.setBounds(10, 251, 106, 23);
        getContentPane().add(btnNewButton1);

        JButton btnNewButton2 = new JButton("浏览");
        btnNewButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              browshAction(e);
            }
        });
        btnNewButton2.setBounds(138, 586, 93, 23);
        getContentPane().add(btnNewButton2);

        JButton btnNewButton3 = new JButton("读取");
        btnNewButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               readAction(e);
            }
        });
        btnNewButton3.setBounds(356, 586, 93, 23);
        getContentPane().add(btnNewButton3);

        JButton btnNewButton4 = new JButton("保存");
        btnNewButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               saveAction(e);
            }
        });
        btnNewButton4.setBounds(577, 586, 93, 23);
        getContentPane().add(btnNewButton4);

        JLabel lblNewLabel2 = new JLabel("文件路径：");
        lblNewLabel2.setBounds(39, 513, 77, 15);
        getContentPane().add(lblNewLabel2);
        JLabel lblNewLabel3 = new JLabel("提示：保存时先点浏览，获取文章路径，可以自定义路径，并设置文件名！");
        lblNewLabel3.setBounds(131, 493, 447, 15);
        getContentPane().add(lblNewLabel3);

        browsh = new JTextField();
        browsh.setBounds(131, 510, 539, 30);
        getContentPane().add(browsh);
        browsh.setColumns(10);
        //设置文本域边框
        novelContentTxt.setBorder(new LineBorder(new java.awt.Color(127, 157, 185),1,false));
    }

    /**
     * 文件保存为草稿事件
     * @param event
     */
    private void saveAction(ActionEvent event) {
        //判断文件路径
        if(StringUtils.isEmpty(browsh.getText())){
            JOptionPane.showMessageDialog(null,"文件路径不能为空！");
        }else {
            openFile = new File(browsh.getText());
            int n = JOptionPane.showConfirmDialog(null, "是否保存文件？");
            if (n == 0) {
                try {
                    //如果文件不存在
                    if (!openFile.exists()) {
                        //创建文件
                        openFile.createNewFile();
                    }
                    fileOutput = new FileOutputStream(openFile);
                    //将字节输出流放到缓冲流
                    out = new BufferedOutputStream(fileOutput);
                    outputWriter = new OutputStreamWriter(out, "utf-8");
                    content = novelContentTxt.getText();
                    outputWriter.write(content);
                    //清空缓存
                    outputWriter.flush();
                    //关闭文件字符输出流
                    outputWriter.close();
                    //关闭缓冲流
                    out.close();
                    //关闭文件字节输出流
                    fileOutput.close();
                    JOptionPane.showMessageDialog(null, "保存成功！");
                    this.resetForm();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        }
    }


    /**
     * 文件浏览事件
     * @param event
     */
    private void browshAction(ActionEvent event) {
        //文件选择
        JFileChooser chooser = new JFileChooser();
        //如果人为关闭，不抛出异常
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            //获取选择的文件
                openFile = chooser.getSelectedFile();
                if (openFile.isFile()) {
                    Absoulate = openFile.getPath();
                } else if (openFile.isDirectory()) {
                    Absoulate = openFile.getPath();
                }else{
                    JOptionPane.showMessageDialog(null, "不是文件或者文件夹！");
                }
                //获取选择文件的路径
                browsh.setText(Absoulate);
        }else{

        }
    }

    /**
     * 文件读取事件
     * @param event
     */
    private void readAction(ActionEvent event) {
        this.browshAction(event);
        //如果文件不存在
        if (openFile.exists()) {
            try {
                //创建文件输入流
                fileInput = new FileInputStream(openFile);
                //创建缓冲流
                bs = new BufferedInputStream(fileInput);
                //定义文件大小的字节数据
                byte b[] = new byte[(int) openFile.length()];
                //将文件数据存储在b数组
                int read = bs.read(b);
                //将字节数据转换为UTF-8编码的字符串
                content = new String(b, "UTF-8");
                //文本区显示文件内容
                novelContentTxt.setText(content);
                //关闭文件输入流
                fileInput.close();
                //关闭缓冲流
                bs.close();
                browsh.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(null, "已关闭！");
        }
    }


    /**
     * 文章发表事件
     * @param event
     */
    private void upLoadAction(ActionEvent event,String authorname,String form) {
        //获取文章标题
        String title = this.novelTitleTxt.getText();
        //获取文章内容
        String content = this.novelContentTxt.getText();
        //判断标题是否为空
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
                //判断是否操作成功
                int n = novelDao.upload(connection, novel, form);
                if (n == 1) {
                    JOptionPane.showMessageDialog(null, "操作成功！");
                    this.resetForm();
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
     * 重置表单
     */
    private void resetForm() {
        this.novelTitleTxt.setText("");
        this.novelContentTxt.setText("");
        this.browsh.setText(" ");
    }
}