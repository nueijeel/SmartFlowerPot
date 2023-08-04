package com.example.smartflowerpot;

public class Data {
    private String title;
    private String content;
    private String email;
    private String date;

    public Data(){

    }

    public Data(String title, String content, String email, String date){
        this.title = title;
        this.content = content;
        this.email = email;
        this.date = date;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() { return content; }
    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
