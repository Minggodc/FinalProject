package com.swufestu.finalproject;

public class GetTeacherInfo {
    private String id,name,xueyuan,grade,tel,pwd;

    public GetTeacherInfo(){
        super();
        id = "";
        name = "";
        xueyuan = "";
        tel = "";
        grade = "";
        pwd = "";
    }

    public GetTeacherInfo(String id,String name,String xueyuan,String tel,String grade,String pwd){
        super();
        this.id = id;
        this.name = name;
        this.xueyuan = xueyuan;
        this.tel = tel;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getXueyuan() {
        return xueyuan;
    }

    public void setXueyuan(String xueyuan) {
        this.xueyuan = xueyuan;
    }

}

