package com.group10.cms.faculty;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class facultyApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Absolute path from resources folder
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group10/cms/facultyLogin.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Faculty Login - CMS");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
