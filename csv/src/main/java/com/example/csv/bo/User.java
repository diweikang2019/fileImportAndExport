package com.example.csv.bo;

import java.util.Date;

/**
 * @author weikang.di
 * @date 2022/12/15 1:37 PM
 */
public class User {

    private Integer id;

    private String name;

    private Integer sex;

    private Integer age;

    private Date birthday;

    private String address;

    public User() {
    }

    public User(Integer id, String name, Integer sex, Integer age, Date birthday, String address) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.birthday = birthday;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
