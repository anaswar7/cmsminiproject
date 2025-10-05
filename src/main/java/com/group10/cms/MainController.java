package com.group10.cms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private AnchorPane mainRoot;

    private void loadNewScene(String fxmlPath) {
        try {
            Stage stage = (Stage) mainRoot.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading FXML file: " + fxmlPath);
        }
    }

    @FXML
    private void handleAdminLogin(ActionEvent event) {
        loadNewScene("loginpage2.fxml");
    }

    @FXML
    private void handleFacultyLogin(ActionEvent event) {
        loadNewScene("facultyLogin.fxml");
    }

    @FXML
    private void handleStudentLogin(ActionEvent event) {
        loadNewScene("student/login.fxml");
    }
}