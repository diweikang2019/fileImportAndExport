package com.kang.excel.easyexcel.bo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.kang.excel.easyexcel.converter.CustomSexConverter;

import java.util.Date;

/**
 * @author weikang.di
 * @date 2022/12/18 12:46 PM
 */
public class User {

    @ExcelProperty("ID")
    private Integer id;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty(value = "性别", converter = CustomSexConverter.class)
    private Integer sex;

    @ExcelProperty("年龄")
    private Integer age;

    /**
     * 宽度为40
     */
    @ColumnWidth(40)
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    @ExcelProperty("出生日期")
    private Date birthday;

    @ExcelProperty("分数")
    private Double score;

    @ExcelProperty("住址")
    @ExcelIgnore
    private String address;

    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;

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

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIgnore() {
        return ignore;
    }

    public void setIgnore(String ignore) {
        this.ignore = ignore;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", birthday=" + birthday +
                ", score=" + score +
                ", address='" + address + '\'' +
                ", ignore='" + ignore + '\'' +
                '}';
    }
}
