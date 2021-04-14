package com.liang.notes.view;

import com.liang.notes.service.Managenovel;
import com.liang.notes.service.Research;
import com.liang.notes.service.Updateperson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 菜单主页面
 */
public class Menu extends JFrame {
    /**
     * 设置一个可以让窗口运行的面板
     */
    private JDesktopPane table = null;


    /**
     *测试运行
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Menu frame = new Menu("lianghongrong");
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 创建菜单窗体
     */
    public Menu(String name) {
        setIconImage(Toolkit.getDefaultToolkit().getImage(Menu.class.getResource("/imagine/imenu.png")));
        setTitle("笔记系统菜单");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnNewMenu1 = new JMenu("基本功能");
        menuBar.add(mnNewMenu1);

        JMenu mnNewMenu = new JMenu("笔记管理");
        mnNewMenu1.add(mnNewMenu);

        JMenuItem menuItem2 = new JMenuItem("笔记添加");
        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Editor addcontent = new Editor(name);
                addcontent.setVisible(true);
                table.add(addcontent);
            }
        });
        mnNewMenu.add(menuItem2);

        JMenuItem menuItem3 = new JMenuItem("笔记设置");
        menuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Managenovel manage = new Managenovel(name);
                manage.setVisible(true);
                table.add(manage);
            }
        });
        mnNewMenu.add(menuItem3);


        JMenuItem menu3 = new JMenuItem("笔记广场");
        menu3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Research research = new Research(name);
                research.setVisible(true);
                table.add(research);
            }
        });
        mnNewMenu1.add(menu3);

        JMenuItem menuItem1 = new JMenuItem("安全退出");
        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //弹出确认窗口
                int result = JOptionPane.showConfirmDialog(null, "是否退出本地最牛笔记系统？");
               //如果输入为是，则返回1，销毁窗口
                if(result == 0) {
                    dispose();
                }
            }
        });
        mnNewMenu1.add(menuItem1);

        JMenu menu5 = new JMenu("当前用户为："+ name);
        menu5.setIcon(new ImageIcon(Menu.class.getResource("/imagine/insign.png")));
        menuBar.add(menu5);

        JMenuItem menuItem = new JMenuItem("修改个人信息");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Updateperson update = new Updateperson(name);
                update.setVisible(true);
                table.add(update);
            }
        });
        menu5.add(menuItem);
        getContentPane().setLayout(new BorderLayout(0, 0));

        table = new JDesktopPane();
        getContentPane().add(table, BorderLayout.CENTER);
        //设置最大化
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}