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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class facultyviewcontroller implements Initializable {
    @FXML
    TableView<Faculty> table;
    @FXML
    TableColumn<Faculty, String> staffId;
    @FXML
    TableColumn<Faculty, String> name;
    @FXML
    TableColumn<Faculty, String> dob;
    @FXML
    TableColumn<Faculty, String> branch;
    @FXML
    TableColumn<Faculty, String> subject;


    String admname;
    @FXML
    TextField ftext = new TextField();
    @FXML
    MenuItem fname = new MenuItem();
    @FXML
    MenuItem fbranch = new MenuItem();
    String filter;
    @FXML
    CheckBox mc = new CheckBox();
    @FXML
    Button back = new Button();
    Image backimg = new Image("file:src/main/resources/com/group10/cms/images/goback.png");
    ImageView backimgv = new ImageView(backimg);

    ObservableList<Faculty> list = FXCollections.observableArrayList(
    );


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        staffId.setCellValueFactory(new PropertyValueFactory<Faculty, String>("staffId"));
        name.setCellValueFactory(new PropertyValueFactory<Faculty, String>("name"));
        dob.setCellValueFactory(new PropertyValueFactory<Faculty, String>("dob"));
        branch.setCellValueFactory(new PropertyValueFactory<Faculty, String>("branch"));
        subject.setCellValueFactory(new PropertyValueFactory<Faculty, String>("subject"));



        admin ad = new admin();
        try {
            ResultSet rs = ad.studentfetch("select * from faculty;");
            while (rs.next()) {
                list.add(new Faculty(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.setItems(list);
        table.setFocusTraversable(true);
        table.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    Faculty selrec = table.getSelectionModel().getSelectedItem();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader root = new FXMLLoader(getClass().getResource("singlefacultyview.fxml"));
                    Scene scene = new Scene(root.load());
                    stage.setScene(scene);
                    sfviewcontroller controller = root.getController();
                    controller.initData(admname, selrec);
                    stage.show();
                    stage.centerOnScreen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        backimgv.getStyleClass().add("image-view");
        back.getStyleClass().add("hover-darken-button");
        backimgv.setFitHeight(40);
        backimgv.setFitWidth(150);
        back.setContentDisplay(ContentDisplay.LEFT);
        back.setGraphic(backimgv);
    }

    public void initData(String admname) {
        this.admname = admname;
    }

    public void goback(ActionEvent e) {
        try {
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
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
        ObservableList<Faculty> list2 = FXCollections.observableArrayList(
        );
        ftext.textProperty().addListener((Observable, oldValue, newValue) -> {
            admin ad = new admin();
            try {
                ResultSet rs;
                list2.clear();
                if (mc.isSelected()) {
                    rs = ad.studentfetch(String.format("select * from faculty where %s like '%%%s%%';", filtercol, newValue));
                } else {
                    rs = ad.studentfetch(String.format("select * from faculty where %s like '%s%%';", filtercol, newValue));
                }
                while (rs.next()) {
                    list2.add(new Faculty(rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5)));
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