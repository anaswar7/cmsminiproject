package com.group10.cms;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
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
    @FXML private Text regnolab;
    @FXML private Text namelab;
    @FXML private Text rollnolab;
    @FXML private Text courselab;
    @FXML private Text semesterlab;
    @FXML private Text doblab;
    @FXML private Text addresslab;
    @FXML private TextField regno;
    @FXML private TextField name;
    @FXML private TextField rollno;
    @FXML private TextField course;
    @FXML private TextField semester;
    @FXML private TextField dob;
    @FXML private TextField address;

    @FXML private Text fstaffidlab;
    @FXML private Text fnamelab;
    @FXML private Text fdoblab;
    @FXML private Text fbranchlab;
    @FXML private Text fsubjectlab;
    @FXML private TextField fstaffid;
    @FXML private TextField fname;
    @FXML private TextField fdob;
    @FXML private TextField fbranch;
    @FXML private TextField fsubject;


    @FXML private Text successful;
    @FXML private Text empty;
    @FXML private Text welcome;

    private String admname;
    private Text[] attributes;
    private TextField[] attributefields;
    private Text[] fattributes;
    private TextField[] fattributefields;

    @FXML private ImageView banner_addstud;
    @FXML private ImageView banner_viewstud;
    @FXML private ImageView banner_viewfac;
    @FXML private ImageView banner_addfac;

    private final Image imgpress = new Image("file:src/main/resources/com/group10/cms/images/dashboardbannerpress.png");
    private final Image img = new Image("file:src/main/resources/com/group10/cms/images/dashboardbanner.png");
    private final Image imghover = new Image("file:src/main/resources/com/group10/cms/images/dashboardbannerhover.png");

    @FXML private Text addstud;
    @FXML private Text viewstud;
    @FXML private Text addfac;
    @FXML private StackPane sp;
    @FXML private Text viewfac;


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

    @FXML
    private void hover(MouseEvent e) {
        if (e.getSource() == banner_addstud || e.getSource() == addstud) {
            banner_addstud.setImage(imghover);
        }
        else if (e.getSource() == banner_viewstud || e.getSource() == viewstud) {
            banner_viewstud.setImage(imghover);
        }
        else if (e.getSource() == banner_viewfac || e.getSource() == viewfac) {
            banner_viewfac.setImage(imghover);
        }
        else if (e.getSource() == banner_addfac || e.getSource() == addfac) {
            banner_addfac.setImage(imghover);
        }
    }

    @FXML
    private void hoverlose(MouseEvent e) {
        if (e.getSource() == banner_addstud || e.getSource() == addstud) {
            banner_addstud.setImage(img);
        }
        else if (e.getSource() == banner_viewstud || e.getSource() == viewstud) {
            banner_viewstud.setImage(img);
        }
        else if (e.getSource() == banner_viewfac || e.getSource() == viewfac) {
            banner_viewfac.setImage(img);
        }
        else if (e.getSource() == banner_addfac || e.getSource() == addfac) {
            banner_addfac.setImage(img);
        }
    }

    @FXML
    private void addstudent(MouseEvent e) {
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
        pause.setOnFinished(actionEvent -> {banner_addstud.setImage(img);});
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

    @FXML
    private void addfac(MouseEvent e) {
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

        banner_addfac.setImage(imgpress);
        PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
        pause.setOnFinished(actionEvent -> {banner_addfac.setImage(img);});
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

    @FXML
    private void studentdets(ActionEvent e) {
        boolean isAnyFieldEmpty = false;
        for (TextField attributefield : attributefields) {
            String info = attributefield.getText();
            if (info.isEmpty()) {
                isAnyFieldEmpty = true;
                break;
            }
        }
        if (isAnyFieldEmpty) {
            this.empty.setText("press ENTER after entering all details");
        }
        else {
            admin ad = new admin();
            try {
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
                        attributefield.setText("");
                    }
                    this.empty.setText("");
                    successful.setVisible(true);
                    PauseTransition pause = new PauseTransition(Duration.seconds(5));
                    pause.setOnFinished(actionEvent -> {successful.setVisible(false);});
                    pause.play();
                } else {
                    this.empty.setText(res);
                    this.empty.autosize();
                }
            } catch (NumberFormatException ex) {
                this.empty.setText("Roll number must be a valid integer.");
            }
        }
    }

    @FXML
    private void facultydets(ActionEvent e) {
        boolean isAnyFieldEmpty = false;
        for (TextField attributefield : fattributefields) {
            String info = attributefield.getText();
            if (info.isEmpty()) {
                isAnyFieldEmpty = true;
                break;
            }
        }
        if (isAnyFieldEmpty) {
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
                    attributefield.setText("");
                }
                this.empty.setText("");
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

    @FXML
    private void viewstudents(MouseEvent e) {
        try {
            banner_viewstud.setImage(imgpress);
            PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
            pause.setOnFinished(actionEvent -> {banner_viewstud.setImage(img);});
            pause.play();
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            FXMLLoader root = new FXMLLoader(getClass().getResource("studentview.fxml"));
            Scene scene = new Scene(root.load());
            scene.getStylesheets().add(getClass().getResource("studentview.css").toExternalForm());
            studentviewcontroller controller = root.getController();
            controller.initData(admname,"admin"," ");
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    @FXML
    private void viewfaculty(MouseEvent e) {
        try {
            banner_viewfac.setImage(imgpress);
            PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
            pause.setOnFinished(actionEvent -> {banner_viewfac.setImage(img);});
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

    @FXML
    private void logout(ActionEvent e) {
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