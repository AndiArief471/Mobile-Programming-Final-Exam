package com.example.mobileprogfinalexam;

public class SubjectEnroll {
    private String name;
    private String credit;

    public SubjectEnroll() {}

    public SubjectEnroll(String name, String credit){
        this.name = name;
        this.credit = credit;
    }

    public String getSubjectName() {
        return name;
    }

    public void setSubjectName(String name) {
        this.name = name;
    }

    public String getSubjectCredit() {
        return credit;
    }

    public void setSubjectCredit(String credit) {
        this.credit = credit;
    }
}
