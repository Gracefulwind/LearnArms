package com.gracefulwind.learnarms.reader.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @ClassName: StudentBean
 * @Author: Gracefulwind
 * @CreateDate: 2022/2/24
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2022/2/24
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Entity
public class StudentBean {

    @Id(autoincrement = true)
    Long id;
    @Unique
    int studentNo;//学号
    int age; //年龄
    String telPhone;//手机号
    String sex; //性别
    String name;//姓名
    String address;//家庭住址
    String schoolName;//学校名字
    String grade;//几年级

    @Generated(hash = 192246004)
    public StudentBean(Long id, int studentNo, int age, String telPhone, String sex,
            String name, String address, String schoolName, String grade) {
        this.id = id;
        this.studentNo = studentNo;
        this.age = age;
        this.telPhone = telPhone;
        this.sex = sex;
        this.name = name;
        this.address = address;
        this.schoolName = schoolName;
        this.grade = grade;
    }
    @Generated(hash = 2097171990)
    public StudentBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getStudentNo() {
        return this.studentNo;
    }
    public void setStudentNo(int studentNo) {
        this.studentNo = studentNo;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getTelPhone() {
        return this.telPhone;
    }
    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getSchoolName() {
        return this.schoolName;
    }
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
    public String getGrade() {
        return this.grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
}
