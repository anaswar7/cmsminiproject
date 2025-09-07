package com.group10.cms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;

public class loginpagecontroller {
    @FXML
    PasswordField usernamead = new PasswordField();
    @FXML
    PasswordField passwordad = new PasswordField();
    @FXML
    Label loginpagelabel1 = new Label();

    public void clearlabel1(MouseEvent e) {
        loginpagelabel1.setText("");
    }

    public void nextfield(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            passwordad.requestFocus();
        }

    }

    public void credentials(ActionEvent e) {
        String user = usernamead.getText();
        String pass = passwordad.getText();
        usernamead.clear();
        passwordad.clear();
        admin ad = new admin();
        if (!(ad.adminaut(user,pass))) {
            System.out.println("WRONG FUCKING PASSWORD");
            loginpagelabel1.setText("Wrong username or password!");
        } else {
            try {
                Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                FXMLLoader root = new FXMLLoader(getClass().getResource("sessionpage.fxml"));
                Scene scene = new Scene(root.load());
                sessionpagecontroller controller = root.getController();
                controller.initData(user);
                stage.setScene(scene);
                stage.centerOnScreen();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }
}
