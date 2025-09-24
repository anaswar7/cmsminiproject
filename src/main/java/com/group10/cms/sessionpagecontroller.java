package com.group10.cms;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class sessionpagecontroller {
    //student stuff
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

    //faculty stuff
    @FXML Text fstaffidlab;
    @FXML Text fnamelab;
    @FXML Text fdoblab;
    @FXML Text fbranchlab;
    @FXML Text fsubjectlab;
    @FXML TextField fstaffid;
    @FXML TextField fname;
    @FXML TextField fdob;
    @FXML TextField fbranch;
    @FXML TextField fsubject;


    @FXML Text successful;
    @FXML Text empty;
    @FXML Text welcome;
    String admname;
    Text[] attributes;
    TextField[] attributefields;
    Text[] fattributes;
    TextField[] fattributefields;
    @FXML
    ImageView banner_addstud = new ImageView();
    @FXML ImageView banner_viewstud = new ImageView();
    @FXML ImageView banner_viewfac = new ImageView();
    @FXML ImageView banner_addfac = new ImageView();
    Image imgpress = new Image("file:src/main/resources/com/group10/cms/images/dashboardbannerpress.png");
    Image img = new Image("file:src/main/resources/com/group10/cms/images/dashboardbanner.png");
    Image imghover = new Image("file:src/main/resources/com/group10/cms/images/dashboardbannerhover.png");
    @FXML Text addstud = new Text();
    @FXML Text viewstud = new Text();
    @FXML Text addfac = new Text();
    @FXML
    StackPane sp = new StackPane();
    @FXML Text viewfac = new Text();



    public void initialize() {
        attributes = new Text[]{regnolab, namelab, rollnolab,courselab,semesterlab,doblab,addresslab};
        attributefields = new TextField[]{regno,name,rollno,course,semester,dob,address};

        fattributes = new Text[]{fstaffidlab, fnamelab, fdoblab,fbranchlab,fsubjectlab};
        fattributefields = new TextField[]{fstaffid,fname,fdob,fbranch,fsubject};

        banner_addstud.setImage(img);
        banner_addstud.setPreserveRatio(false);
        banner_viewstud.setImage(img);
        banner_viewstud.setPreserveRatio(false);
        banner_viewfac.setImage(img);
        banner_viewfac.setPreserveRatio(false);
        banner_addfac.setImage(img);
        banner_addfac.setPreserveRatio(false);

        Image image = new Image("file:src/main/resources/com/group10/cms/images/sessionpagebg2.jpg");


        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );
        Background background = new Background(backgroundImage);
        sp.setBackground(background);
    }

    public void initData(String name) {
        admname = name;
        welcome.setText("Welcome, "+name);
        welcome.autosize();
    }

    public void hover(MouseEvent e) {
        if (e.getSource() == banner_addstud||e.getSource() == addstud) {
            banner_addstud.setImage(imghover);
        }
        else if (e.getSource() == banner_viewstud||e.getSource() == viewstud) {
            banner_viewstud.setImage(imghover);
        }
        else if (e.getSource() == banner_viewfac||e.getSource() == viewfac) {
            banner_viewfac.setImage(imghover);
        }
        else if (e.getSource() == banner_addfac||e.getSource() == addfac) {
            banner_addfac.setImage(imghover);
        }
    }

    public void hoverlose(MouseEvent e) {
        if (e.getSource() == banner_addstud) {
            banner_addstud.setImage(img);
        }
        else if (e.getSource() == banner_viewstud) {
            banner_viewstud.setImage(img);
        }
        else if (e.getSource() == banner_viewfac) {
            banner_viewfac.setImage(img);
        }
        else if (e.getSource() == banner_addfac) {
            banner_addfac.setImage(img);
        }
    }

    public void addstudent(MouseEvent e) {

        if (!(fattributes[1].isDisabled())) {
            for (Text attribute : fattributes) {
                attribute.setDisable(true);
                attribute.setVisible(false);
            }
            for (TextField attributefield : fattributefields) {
                attributefield.setDisable(true);
                attributefield.setVisible(false);
            }
        }

        banner_addstud.setImage(imgpress);
        PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
        pause.setOnFinished(actionEvent -> {banner_addstud.setImage(img);;});
        pause.play();
        for (Text attribute : attributes) {
            attribute.setDisable(false);
            attribute.setVisible(true);
        }
        for (TextField attributefield : attributefields) {
            attributefield.setDisable(false);
            attributefield.setVisible(true);
        }
    }
    public void addfac(MouseEvent e) {
        if (!(attributes[1].isDisabled())) {
            for (Text attribute : attributes) {
                attribute.setDisable(true);
                attribute.setVisible(false);
            }
            for (TextField attributefield : attributefields) {
                attributefield.setDisable(true);
                attributefield.setVisible(false);
            }
        }
        banner_addstud.setImage(imgpress);
        PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
        pause.setOnFinished(actionEvent -> {banner_addstud.setImage(img);;});
        pause.play();
        for (Text attribute : fattributes) {
            attribute.setDisable(false);
            attribute.setVisible(true);
        }
        for (TextField attributefield : fattributefields) {
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

    public void facultydets(ActionEvent e) {
        boolean empty = false;
        for (TextField attributefield : fattributefields) {
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
            String res = ad.facultyadd(fstaffid.getText(),fname.getText(),fdob.getText(),fbranch.getText(),fsubject.getText());
            if (res.equals("")) {
                for (Text attribute : fattributes) {
                    attribute.setDisable(true);
                    attribute.setVisible(false);
                }
                for (TextField attributefield : fattributefields) {
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


    public void viewstudents(MouseEvent e) {
        try {
            banner_addstud.setImage(imgpress);
            PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
            pause.setOnFinished(actionEvent -> {banner_viewstud.setImage(img);;});
            pause.play();
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            FXMLLoader root = new FXMLLoader(getClass().getResource("studentview.fxml"));
            Scene scene = new Scene(root.load());
            scene.getStylesheets().add(getClass().getResource("studentview.css").toExternalForm());
            studentviewcontroller controller = root.getController();
            controller.initData(admname);
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void viewfaculty(MouseEvent e) {
        try {
            banner_addstud.setImage(imgpress);
            PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
            pause.setOnFinished(actionEvent -> {banner_viewfac.setImage(img);;});
            pause.play();
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            FXMLLoader root = new FXMLLoader(getClass().getResource("facultyview.fxml"));
            Scene scene = new Scene(root.load());
            scene.getStylesheets().add(getClass().getResource("studentview.css").toExternalForm());
            facultyviewcontroller controller = root.getController();
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
