package com.liang.notes.entity;

/**
 * @author 86178
 */
public class UserMessage {
    /**
     * 用户名
     */
    private String name;
    /**
     * 年龄
     */
    private String age;
    /**
     * 性别
     */
    private String sex;
    /**
     * 描述
     */
    private String userdescribe;

    public UserMessage() {
    }

    public UserMessage(String name,String age,String sex,String userdescribe) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.userdescribe = userdescribe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUserdescribe() {
        return userdescribe;
    }

    public void setUserdescribe(String userdescribe) {
        this.userdescribe = userdescribe;
    }
}
