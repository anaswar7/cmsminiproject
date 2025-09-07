package com.group10.cms;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class studentviewcontroller implements Initializable {
    @FXML
    TableView<stud> table;
    @FXML
    TableColumn<stud,String> regno;
    @FXML
    TableColumn<stud,String> name;
    @FXML
    TableColumn<stud,Integer> rollno;
    @FXML
    TableColumn<stud,String> course;
    @FXML
    TableColumn<stud,String> semester;
    @FXML
    TableColumn<stud,String> dob;
    String admname;
    @FXML
    TextField ftext = new TextField();
    @FXML
    MenuItem fname = new MenuItem();
    @FXML MenuItem fcourse = new MenuItem();
    String filter;
    @FXML CheckBox mc = new CheckBox();

    ObservableList<stud> list = FXCollections.observableArrayList(
            );


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        regno.setCellValueFactory(new PropertyValueFactory<stud,String>("regno"));
        name.setCellValueFactory(new PropertyValueFactory<stud,String>("name"));
        rollno.setCellValueFactory(new PropertyValueFactory<stud,Integer>("rollno"));
        course.setCellValueFactory(new PropertyValueFactory<stud,String>("course"));
        semester.setCellValueFactory(new PropertyValueFactory<stud,String>("semester"));
        dob.setCellValueFactory(new PropertyValueFactory<stud,String>("dob"));
        admin ad = new admin();
        try {
            ResultSet rs = ad.studentfetch("select * from student;");
            while (rs.next()) {
                list.add(new stud(rs.getString(1), rs.getString(2), rs.getInt(3),
                        rs.getString(4),rs.getString(5), rs.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.setItems(list);
        table.setFocusTraversable(true);
        table.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {

                stud selrec = table.getSelectionModel().getSelectedItem();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader root = new FXMLLoader(getClass().getResource("singlestudentview.fxml"));
                Scene scene = new Scene(root.load());
                stage.setScene(scene);
                ssviewcontroller controller = root.getController();
                controller.initData(admname,selrec);
                stage.show();
                stage.centerOnScreen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void initData(String admname) {
        this.admname = admname;
    }

    public void goback(ActionEvent e) {
        try {
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            FXMLLoader root = new FXMLLoader(getClass().getResource("sessionpage.fxml"));
            Scene scene = new Scene(root.load());
            sessionpagecontroller controller = root.getController();
            controller.initData(this.admname);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void filter(String filtercol) {
        ObservableList<stud> list2 = FXCollections.observableArrayList(
        );
        ftext.textProperty().addListener((Observable,oldValue,newValue)-> {
            admin ad = new admin();
            try {
                ResultSet rs;
                list2.clear();
                if (mc.isSelected()) {
                    rs = ad.studentfetch(String.format("select * from student where %s like '%%%s%%';",filtercol,newValue));
                } else {
                    rs = ad.studentfetch(String.format("select * from student where %s like '%s%%';", filtercol, newValue));
                }
                while (rs.next()) {
                    list2.add(new stud(rs.getString(1), rs.getString(2), rs.getInt(3),
                            rs.getString(4),rs.getString(5), rs.getString(6)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            table.setItems(list2);
        });
    }

    public void filterenter(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            ftext.setDisable(true);
            ftext.setVisible(false);
            mc.setDisable(true);
            mc.setVisible(false);
        }
    }

    public void filterby(ActionEvent e) {
        mc.setDisable(false);
        mc.setVisible(true);
        ftext.setDisable(false);
        ftext.setVisible(true);
        MenuItem but = (MenuItem) e.getSource();
        filter = but.getText();
        filter(filter);
    }

}
