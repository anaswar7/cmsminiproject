package com.group10.cms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

import java.io.IOException;
import java.io.InputStream;

public class main extends Application {
    public static void main(String[] args) {
        launch();
    }

    public void start(Stage stage) throws IOException {
        fontloader fl = new fontloader();
        FXMLLoader root = new FXMLLoader(getClass().getResource("loginpage2.fxml"));
        Scene scene = new Scene(root.load());
        Image img = new Image("file:src/main/resources/com/group10/cms/cms.png");
        stage.getIcons().add(img);
        stage.setResizable(false);
        stage.setTitle("CMS");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
