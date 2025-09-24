package com.group10.cms.student;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StudentApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Tell the loader to open "login.fxml"
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(loader.load());

        primaryStage.setTitle("Student Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}