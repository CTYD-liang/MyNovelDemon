package com.liang.notes.entity;

/**
 *用户实体
 * @author 86178
 */
public class User {

    /**
     * 无参构造方法
     */
    public User() {
        super();
    }

    /**
     * 有参构造方法
     * @param userName
     * @param passWord
     */
    public User(String userName, String passWord) {
        super();
        this.userName = userName;
        this.passWord = passWord;
    }


    /**
     * 用户编号
     */
    private int id;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户登录密码
     */
    private String passWord;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }


}
