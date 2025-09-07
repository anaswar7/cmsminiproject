package com.group10.cms;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class sessionpagecontroller {
    @FXML Text regnolab;
    @FXML Text namelab;
    @FXML Text rollnolab;
    @FXML Text courselab;
    @FXML Text semesterlab;
    @FXML Text doblab;
    @FXML Text addresslab;
    @FXML TextField regno;
    @FXML TextField name;
    @FXML TextField rollno;
    @FXML TextField course;
    @FXML TextField semester;
    @FXML TextField dob;
    @FXML TextField address;
    @FXML Text successful;
    @FXML Text empty;
    @FXML Text welcome;
    String admname;
    Text[] attributes;
    TextField[] attributefields;


    public void initialize() {
        attributes = new Text[]{regnolab, namelab, rollnolab,courselab,semesterlab,doblab,addresslab};
        attributefields = new TextField[]{regno,name,rollno,course,semester,dob,address};
    }

    public void initData(String name) {
        admname = name;
        welcome.setText("Welcome, "+name);
        welcome.autosize();
    }

    public void addstudent(ActionEvent e) {
        for (Text attribute : attributes) {
            attribute.setDisable(false);
            attribute.setVisible(true);
        }
        for (TextField attributefield : attributefields) {
            attributefield.setDisable(false);
            attributefield.setVisible(true);
        }
    }

    public void studentdets(ActionEvent e) {
        boolean empty = false;
        for (TextField attributefield : attributefields) {
            String info = attributefield.getText();
            if (info.isEmpty()) {
                empty = true;
            }
        }
        if (empty) {
            this.empty.setText("press ENTER after entering all details");
        }
        else {
            admin ad = new admin();
            int rollno = Integer.parseInt(this.rollno.getText());
            String res = ad.studentadd(regno.getText(),name.getText(),rollno,course.getText(),semester.getText(),dob.getText(),address.getText());
            if (res.equals("")) {
                for (Text attribute : attributes) {
                    attribute.setDisable(true);
                    attribute.setVisible(false);
                }
                for (TextField attributefield : attributefields) {
                    attributefield.setDisable(true);
                    attributefield.setVisible(false);
                }
                successful.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(5));
                pause.setOnFinished(actionEvent -> {successful.setVisible(false);});
                pause.play();
            } else {
                this.empty.setText(res);
                this.empty.autosize();
            }

        }
    }
    public void viewstudents(ActionEvent e) {
        try {
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            FXMLLoader root = new FXMLLoader(getClass().getResource("studentview.fxml"));
            Scene scene = new Scene(root.load());
            studentviewcontroller controller = root.getController();
            controller.initData(admname);
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void logout(ActionEvent e) {
        try {
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            FXMLLoader root = new FXMLLoader(getClass().getResource("loginpage2.fxml"));
            Scene scene = new Scene(root.load());
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
