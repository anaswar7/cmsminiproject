module com.group10.cms {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    // Open packages for FXML loader
    opens com.group10.cms to javafx.fxml;
    opens com.group10.cms.student to javafx.fxml;
    opens com.group10.cms.faculty to javafx.fxml;

    // Export public packages
    exports com.group10.cms;
    exports com.group10.cms.student;
    exports com.group10.cms.faculty;
}