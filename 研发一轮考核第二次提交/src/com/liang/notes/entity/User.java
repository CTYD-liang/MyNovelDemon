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
    }

    /**
     * 只获取名字的构造方法
     * @param userName
     */
    public User(String userName) {
        this.userName = userName;
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
     * 用户所有信息实体
     * @param userName
     * @param passWord
     * @param age
     * @param sex
     */
    public User(String userName,String passWord,String age,String sex){
        this.userName = userName;
        this.passWord = passWord;
        this.age = age;
        this.sex = sex;
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
    /**
     * 年龄
     */
    private String age;
    /**
     * 性别
     */
    private String sex;


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
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
