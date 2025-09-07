package com.group10.cms;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ssviewcontroller {
    @FXML
    TextField regnof = new TextField();
    @FXML
    TextField namef = new TextField();
    @FXML TextField rollnof = new TextField();
    @FXML TextField coursef = new TextField();
    @FXML TextField semesterf = new TextField();
    @FXML TextField dobf = new TextField();
    @FXML Text regno= new Text();
    @FXML Text name = new Text();
    @FXML Text rollno = new Text();
    @FXML Text course = new Text();
    @FXML Text semester = new Text();
    @FXML Text dob = new Text();
    @FXML Text address = new Text();
    @FXML TextField addressf = new TextField();
    Text[] attributes;
    TextField[] attributefields;
    String admname;

    @FXML
    Circle profpic = new Circle();


    public void initialize() {
        attributes = new Text[]{regno,name,rollno,course,semester,dob,address};
        attributefields = new TextField[]{regnof,namef,rollnof,coursef,semesterf,dobf};
    }

    public void initData(String s,stud student) {
        admname = s;
        regno.setText(student.regno);
        name.setText(student.name);
        rollno.setText(Integer.toString(student.rollno));
        course.setText(student.course);
        semester.setText(student.semester);
        dob.setText(student.dob);
        admin ad = new admin();
        try {
            Image img;
            ResultSet rs = ad.studentfetch(String.format("select address,profpic from student where name = '%s';", student.name));
            rs.next();
            address.setText(rs.getString(1));
            if (rs.getString(2) != null) {
                img = new Image("file:" + rs.getString(2));
            } else {
                img = new Image("file:src/main/resources/com/group10/cms/students/default.jpg");
                //hello mf
            }
            profpic.setFill(new ImagePattern(img));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Text attribute : attributes) {
            attribute.setVisible(true);
            attribute.setDisable(false);
        }
    }
}
