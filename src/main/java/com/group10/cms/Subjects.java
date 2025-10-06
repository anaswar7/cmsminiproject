package com.group10.cms;

public class Subjects {
    private String code;
    private String name;
    private String semester;
    private String course;
    private int credits;

    public Subjects(String code, String name, String semester, String course, int credits) {
        this.code = code;
        this.name = name;
        this.semester = semester;
        this.course = course;
        this.credits = credits;
    }

    public String getName() { return name; }

    public String getCode() {
        return code;
    }

    public String getSemester() {
        return semester;
    }

    public String getCourse() {
        return course;
    }

    public int getCredits() {
        return credits;
    }

    @Override
    public String toString() {
        return name;

    }
}
