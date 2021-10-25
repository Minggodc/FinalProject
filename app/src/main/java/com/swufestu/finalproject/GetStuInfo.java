package com.swufestu.finalproject;

public class GetStuInfo {
    private String id,name,xueyuan,major,grade,pwd;

    public GetStuInfo(){
        super();
        id = "";
        name = "";
        xueyuan = "";
        major = "";
        grade = "";
        pwd = "";
    }

    public GetStuInfo(String id,String name,String xueyuan,String major,String grade,String pwd){
        super();
        this.id = id;
        this.name = name;
        this.xueyuan = xueyuan;
        this.major = major;
        this.grade = grade;
        this.pwd = pwd;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getXueyuan() {
        return xueyuan;
    }

    public void setXueyuan(String xueyuan) {
        this.xueyuan = xueyuan;
    }
}

