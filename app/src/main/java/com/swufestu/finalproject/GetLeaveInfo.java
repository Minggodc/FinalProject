package com.swufestu.finalproject;

public class GetLeaveInfo {
    private String id,teacher_id,start,end,reason,stu_tel,cancel;

    public GetLeaveInfo(){
        super();
        id = "";
        teacher_id = "";
        start = "";
        end = "";
        reason = "";
        stu_tel = "";
        cancel = "";
    }

    public GetLeaveInfo(String id,String teacher_id,String start,String end,
                        String reason,String stu_tel,String cancel){
        this.id = id;
        this.teacher_id = teacher_id;
        this.start = start;
        this.end = end;
        this.stu_tel = stu_tel;
        this.reason = reason;
        this.cancel = cancel;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStu_tel() {
        return stu_tel;
    }

    public void setStu_tel(String stu_tel) {
        this.stu_tel = stu_tel;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }
}