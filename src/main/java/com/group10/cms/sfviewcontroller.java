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

public class sfviewcontroller {
    @FXML
    TextField staffidf = new TextField();
    @FXML
    TextField namef = new TextField();
    @FXML
    TextField branchf = new TextField();
    @FXML
    TextField subjectf = new TextField();
    @FXML
    TextField dobf = new TextField();
    @FXML
    TextField unusedf = new TextField();

    @FXML
    Text staffid = new Text();
    @FXML
    Text name = new Text();
    @FXML Text namemain = new Text();
    @FXML
    Text branch = new Text();
    @FXML
    Text subject = new Text();
    @FXML
    Text dob = new Text();
    @FXML
    Text unused = new Text();

    Text[] attributes;
    TextField[] attributefields;
    ImageView[] attributeimages;
    String admname;
    String[] columns;

    @FXML
    ImageView staffidi = new ImageView();
    @FXML
    ImageView namei = new ImageView();
    @FXML
    ImageView branchi = new ImageView();
    @FXML
    ImageView subjecti = new ImageView();
    @FXML
    ImageView dobi = new ImageView();
    @FXML
    ImageView unusedi = new ImageView();

    @FXML
    Button deletebut = new Button();

    @FXML
    Circle profpic = new Circle();


    public void initialize() {
        columns = new String[]{"staff_id","name","dob","branch","subject"};

        attributes = new Text[]{staffid, name, dob, branch, subject};

        attributefields = new TextField[]{staffidf, namef, dobf, branchf, subjectf};

        attributeimages = new ImageView[]{staffidi, namei, dobi, branchi, subjecti};

        Image img = new Image("file:src/main/resources/com/group10/cms/edit.png");
        for (ImageView attributeimage : attributeimages ) {
            attributeimage.setImage(img);
        }
    }

    public void initData(String s, Faculty faculty) {
        admname = s;
        staffid.setText(faculty.getStaffId());
        name.setText(faculty.getName());
        namemain.setText(faculty.getName());
        branch.setText(faculty.getBranch());
        subject.setText(faculty.getSubject());
        dob.setText(faculty.getDob());

        admin ad = new admin();
        try {
            Image img;
            ResultSet rs = ad.studentfetch(String.format("select profpic from faculty where staff_id = '%s';", faculty.getStaffId()));

            if (rs.next()) {
                if (rs.getString(1) != null) {
                    img = new Image("file:" + rs.getString(1));
                } else {
                    img = new Image("file:src/main/resources/com/group10/cms/students/default.jpg");
                }
                profpic.setFill(new ImagePattern(img));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Text attribute : attributes) {
            attribute.setVisible(true);
            attribute.setDisable(false);
        }
    }

    public void edit(MouseEvent e) {
        if (e.getSource() == staffidi) {
            staffidf.setVisible(true);
            staffidf.setDisable(false);
            staffid.setVisible(false);
            staffidf.setText(staffid.getText());
        }
        else if (e.getSource() == namei) {
            namef.setVisible(true);
            namef.setDisable(false);
            name.setVisible(false);
            namef.setText(name.getText());
        }
        else if (e.getSource() == branchi) {
            branchf.setVisible(true);
            branchf.setDisable(false);
            branch.setVisible(false);
            branchf.setText(branch.getText());
        }
        else if (e.getSource() == subjecti) {
            subjectf.setVisible(true);
            subjectf.setDisable(false);
            subject.setVisible(false);
            subjectf.setText(subject.getText());
        }
        else if (e.getSource() == dobi) {
            dobf.setVisible(true);
            dobf.setDisable(false);
            dob.setVisible(false);
            dobf.setText(dob.getText());
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

        if (e.getSource() == staffidf) {
            sql = "update faculty set staff_id = ? where staff_id = ?;";
            try (PreparedStatement preparedStatement = ad.connection.prepareStatement(sql)) {
                preparedStatement.setString(1,staffidf.getText());
                preparedStatement.setString(2,staffid.getText());
                res = preparedStatement.executeUpdate();
                if (res > 0) {
                    staffid.setVisible(true);
                    staffid.setDisable(false);
                    staffidf.setDisable(true);
                    staffidf.setVisible(false);
                    staffid.setText(staffidf.getText());
                } else {
                    System.out.println("failed to update : " + res);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } else {

            for (int i = 0; i< attributefields.length;i++) {
                if (e.getSource() == attributefields[i]) {
                    if (i == 0) continue;

                    sql = "UPDATE faculty SET "+columns[i]+" = ? WHERE staff_id = ?;";
                    try (PreparedStatement preparedStatement = ad.connection.prepareStatement(sql)) {
                        preparedStatement.setString(1, attributefields[i].getText());
                        preparedStatement.setString(2, staffid.getText());

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
        al.setContentText("This action will delete the faculty record");
        Optional<ButtonType> choice = al.showAndWait();
        if (choice.isPresent() && choice.get() == ButtonType.OK) {
            admin ad = new admin();
            String res = ad.updatestudent(String.format("delete from faculty where staff_id = '%s';",staffid.getText()));
            try {
                Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                FXMLLoader root = new FXMLLoader(getClass().getResource("facultyview.fxml"));
                Scene scene = new Scene(root.load());
                facultyviewcontroller controller = root.getController();
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
            FXMLLoader root = new FXMLLoader(getClass().getResource("facultyview.fxml"));
            Scene scene = new Scene(root.load());
            scene.getStylesheets().add(getClass().getResource("studentview.css").toExternalForm());
            facultyviewcontroller controller = root.getController();
            controller.initData(this.admname);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}