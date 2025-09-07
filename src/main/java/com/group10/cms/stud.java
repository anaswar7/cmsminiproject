package com.group10.cms;

public class stud{
    String regno;
    String name;
    int rollno;
    String course;
    String semester;
    String dob;


    public stud(String regno, String name, int rollno,String course,String semester,String dob) {
        this.regno = regno;
        this.name = name;
        this.rollno = rollno;
        this.course = course;
        this.semester = semester;
        this.dob = dob;
    }

    public String getRegno() {
        return regno;
    }

    public String getName() {
        return name;
    }

    public int getRollno() {
        return rollno;
    }

    public String getCourse() {
        return course;
    }

    public String getSemester() {
        return semester;
    }

    public String getDob() {
        return dob;
    }
}
