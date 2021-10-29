package com.swufestu.finalproject;

public class StuGetRecord {

    private String id, name, start, end, result;

    public StuGetRecord(){
        super();
        id = "";
        name = "";
        start = "";
        end = "";
        result = "";
    }

    public StuGetRecord(String id,String name,String start,String end,String result){
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
        this.result = result;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
