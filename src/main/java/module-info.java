module com.example.test3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.desktop;
    requires java.sql;

    opens com.example.test3 to javafx.fxml;
    exports Server;
    exports professor;
    exports whiteboard_remote;
    exports student;
    exports login;
    exports database;
}