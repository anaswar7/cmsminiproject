package com.group10.cms;

import javafx.scene.control.CheckBox;

import java.sql.ResultSet;
import java.sql.SQLException;

public class stud{
    String regno;
    String name;
    int rollno;
    String course;
    String semester;
    String dob;
    double sgpa;
    private CheckBox present;


    public stud(String regno, String name, int rollno,String course,String semester,String dob) {
        this.regno = regno;
        this.name = name;
        this.rollno = rollno;
        this.course = course;
        this.semester = semester;
        this.dob = dob;
        this.present = new CheckBox();
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

    public int getGradePoints(double totalMarksPercentage) {
        if (totalMarksPercentage >= 90) {
            return 10;
        } else if (totalMarksPercentage >= 80) {
            return 9;
        } else if (totalMarksPercentage >= 70) {
            return 8;
        } else if (totalMarksPercentage >= 60) {
            return 7;
        } else if (totalMarksPercentage >= 50) {
            return 6;
        } else if (totalMarksPercentage >= 40) {
            return 5;
        } else if (totalMarksPercentage >= 30) {
            return 4;
        } else if (totalMarksPercentage >= 20) {
            return 3;
        } else if (totalMarksPercentage >= 10) {
            return 2;
        } else {
            return 1;
        }
    }


    public double sgpa(String reg,String sem) {
        double sgpa = 0.0;
        admin ad = new admin();
        double[] internal1 = new double[100];
        double[] internal2 = new double[100];
        double[] external = new double[100];
        try {
            ResultSet rs = ad.studentfetch(String.format("select m.subject_code,round((m.marks_obtained/m.max_marks)*100,2) AS percentage"+
                    " from Marks m join Subjects s on m.subject_code=s.subject_code"+
                    " where m.regno = '%s' AND s.semester = '%s' AND m.exam_type = 'Internal1' order by m.subject_code;",reg,sem));
            int i = 0;
            if (rs != null) {
                while (rs.next()) {
                    internal1[i] = rs.getDouble(2)/10;
                    ++i;
                }
            }
            rs = ad.studentfetch(String.format("select m.subject_code,round((m.marks_obtained/m.max_marks)*100,2) AS percentage"+
                    " from Marks m join Subjects s on m.subject_code=s.subject_code"+
                    " where m.regno = '%s' AND s.semester = '%s' AND m.exam_type = 'Internal2' order by m.subject_code;",reg,sem));
            int j = 0;
            if (rs != null) {
                while (rs.next()) {
                    internal2[j] = rs.getDouble(2)/10;
                    ++j;
                }
            }
            rs = ad.studentfetch(String.format("select m.subject_code,m.marks_obtained,m.max_marks,s.credits"+
                    " from Marks m join Subjects s on m.subject_code=s.subject_code"+
                    " where m.regno = '%s' AND s.semester = '%s' AND m.exam_type = 'External' order by m.subject_code;",reg,sem));
            int k = 0;
            int[] credits = new int[100];
            if (rs != null) {
                while (rs.next()) {
                    external[k] = rs.getDouble(2);
                    credits[k] = rs.getInt(4);
                    ++k;
                }
            }

            double[] internals = new double[100];
            double finexternal=0.0;
            int creditsum = 0;
            for (int g = 0;g<k;g++) {
                creditsum = creditsum + credits[g];
                internals[g] = internal1[g]+internal2[g];
                external[g] = (this.getGradePoints(external[g]+ internals[g]+20))*credits[g];
                finexternal = finexternal + external[g];
            }
            sgpa = finexternal/creditsum;



        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sgpa;
    }

    public CheckBox getPresent() {
        return present;
    }

    public void setPresent(CheckBox present) {
        this.present = present;
    }
}
