package com.group10.cms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

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
    ImageView[] attributeimages;
    String admname;
    String[] columns;
    @FXML
    ImageView regnoi = new ImageView();
    @FXML
    ImageView namei = new ImageView();
    @FXML
    ImageView rollnoi = new ImageView();
    @FXML
    ImageView coursei = new ImageView();
    @FXML
    ImageView semesteri = new ImageView();
    @FXML
    ImageView dobi = new ImageView();
    @FXML
    ImageView addressi = new ImageView();
    @FXML
    Button deletebut = new Button();

    @FXML
    Circle profpic = new Circle();


    public void initialize() {
        columns = new String[]{"regno","name","rollno","course","semester","dob","address"};
        attributes = new Text[]{regno,name,rollno,course,semester,dob,address};
        attributefields = new TextField[]{regnof,namef,rollnof,coursef,semesterf,dobf,addressf};
        attributeimages = new ImageView[]{regnoi,namei,rollnoi,coursei,semesteri,dobi,addressi};
        Image img = new Image("file:src/main/resources/com/group10/cms/edit.png");
        for (ImageView attributeimage : attributeimages ) {
            attributeimage.setImage(img);
        }
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

    public void edit(MouseEvent e) {
        if (e.getSource() == regnoi) {
            regnof.setVisible(true);
            regnof.setDisable(false);
            regno.setVisible(false);
            regnof.setText(regno.getText());
        }
        else if (e.getSource() == namei) {
            namef.setVisible(true);
            namef.setDisable(false);
            name.setVisible(false);
            namef.setText(name.getText());
        }
        else if (e.getSource() == rollnoi) {
            rollnof.setVisible(true);
            rollnof.setDisable(false);
            rollno.setVisible(false);
            rollnof.setText(rollno.getText());
        }
        else if (e.getSource() == coursei) {
            coursef.setVisible(true);
            coursef.setDisable(false);
            course.setVisible(false);
            coursef.setText(course.getText());
        }
        else if (e.getSource() == semesteri) {
            semesterf.setVisible(true);
            semesterf.setDisable(false);
            semester.setVisible(false);
            semesterf.setText(semester.getText());
        }
        else if (e.getSource() == dobi) {
            dobf.setVisible(true);
            dobf.setDisable(false);
            dob.setVisible(false);
            dobf.setText(dob.getText());
        }
        else if (e.getSource() == addressi) {
            addressf.setVisible(true);
            addressf.setDisable(false);
            address.setVisible(false);
            addressf.setText(address.getText());
        }
        for (ImageView attributeimage : attributeimages ) {
            attributeimage.setDisable(true);
            attributeimage.setVisible(false);
        }
    }

    public void editfin(ActionEvent e) {
        admin ad = new admin();
        String sql;
        int res;

        if (e.getSource() == regnof) {
            sql = "update student set regno = ? where regno = ?;";
            try (PreparedStatement preparedStatement = ad.connection.prepareStatement(sql)) {
                preparedStatement.setString(1,regnof.getText());
                preparedStatement.setString(2,regno.getText());
                res = preparedStatement.executeUpdate();
                if (res > 0) {
                    regno.setVisible(true);
                    regno.setDisable(false);
                    regnof.setDisable(true);
                    regnof.setVisible(false);
                    regno.setText(regnof.getText());
                } else {
                    System.out.println("failed to update : " + res);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } else {
        for (int i = 1; i< columns.length;i++) {
            if (e.getSource() == attributefields[i]) {
                System.out.println(attributes[i].getText().toLowerCase());
                sql = "UPDATE student SET "+columns[i]+" = ? WHERE regno = ?;";
                try (PreparedStatement preparedStatement = ad.connection.prepareStatement(sql)) {
                    if (e.getSource()!=rollnof) {
                        preparedStatement.setString(1, attributefields[i].getText());
                    } else {
                        preparedStatement.setInt(1, Integer.parseInt(attributefields[i].getText()));
                    }
                    preparedStatement.setString(2, regno.getText());
                    res = preparedStatement.executeUpdate();
                    if (res > 0) {
                        attributes[i].setVisible(true);
                        attributes[i].setDisable(false);
                        attributefields[i].setDisable(true);
                        attributefields[i].setVisible(false);
                        attributes[i].setText(attributefields[i].getText());
                    } else {
                        System.out.println("failed to update : " + res);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        }}
        for (ImageView attributeimage : attributeimages ) {
            attributeimage.setDisable(false);
            attributeimage.setVisible(true);
        }
    }

    public void delete(ActionEvent e) {
        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
        al.setTitle("Confirmation");
        al.setHeaderText("Are you sure you want to proceed with deletion?");
        al.setContentText("This action will delete the record");
        Optional<ButtonType> choice = al.showAndWait();
        if (choice.get() == ButtonType.OK) {
            admin ad = new admin();
            String res = ad.updatestudent(String.format("delete from student where regno = '%s';",regno.getText()));
            try {
                Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                FXMLLoader root = new FXMLLoader(getClass().getResource("studentview.fxml"));
                Scene scene = new Scene(root.load());
                studentviewcontroller controller = root.getController();
                controller.initData(this.admname);
                stage.setScene(scene);
                stage.show();
                stage.centerOnScreen();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    public void goback(ActionEvent e) {
        try {
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            FXMLLoader root = new FXMLLoader(getClass().getResource("studentview.fxml"));
            Scene scene = new Scene(root.load());
            studentviewcontroller controller = root.getController();
            controller.initData(this.admname);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
