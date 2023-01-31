package login;

//import com.example.db.ConnexionData;
import database.connexion;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.ArrayList;

//import java.sql.*;

public class registerStudent extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Register Form");

        // Create the registration form grid pane
        GridPane gridPane = createRegistrationFormPane();
        // Add UI controls to the registration form grid pane
        addUIControls(gridPane);
        // Create a scene with registration form grid pane as the root node
        Scene scene = new Scene(gridPane, 800, 500);
        // Set the scene in primary stage
        primaryStage.setScene(scene);

        primaryStage.show();
    }


    private GridPane createRegistrationFormPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

        // Add Column Constraints

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        // Add Header
        Label headerLabel = new Label("Register Form");
        headerLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        // Add Name Label
        Label firstLabel = new Label("First Name : ");
        gridPane.add(firstLabel, 0, 1);

        // Add Name Text Field
        TextField firstField = new TextField();
        firstField.setPrefHeight(40);
        gridPane.add(firstField, 1, 1);

        // Add Name Label
        Label lastLabel = new Label("Last Name : ");
        gridPane.add(lastLabel, 0, 2);

        // Add Name Text Field
        TextField lastField = new TextField();
        lastField.setPrefHeight(40);
        gridPane.add(lastField, 1, 2);

        // Add password Label
        Label portLabel = new Label("Password : ");
        gridPane.add(portLabel, 0, 3);

        // Add password Text Field
        PasswordField portField = new PasswordField();
        portField.setPrefHeight(40);
        gridPane.add(portField, 1, 3);

        // Add Password confirmation Label
        Label passwordLabel = new Label("Confirm Password : ");
        gridPane.add(passwordLabel, 0, 4);

        // Add Password confirmation Field
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(40);
        gridPane.add(passwordField, 1, 4);

        // Add class Label
        Label classLabel = new Label("Select class : ");
        gridPane.add(classLabel, 0, 5);

        // Weekdays
        ArrayList<String> course=connexion.courses();

        // Create a combo box
        ComboBox combo_box = new ComboBox(FXCollections.observableArrayList(course));
        combo_box.setPrefHeight(35);
        combo_box.setPrefWidth(150);
        gridPane.add(combo_box,1,5);

        // Add Submit Button
        Button submitButton = new Button("Register");
        submitButton.setPrefHeight(40);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 0, 6, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));
        //style
        submitButton.setStyle("-fx-background-color: #1dbf73");

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (firstField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your name");
                    return;
                }
                if (lastField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your name");
                    return;
                }
                if (portField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter port");
                    return;
                }
                if (passwordField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a password");
                    return;
                }
                //confirmation de psswd
                if (!passwordField.getText().equals(portField.getText())) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please confirm the password");
                    return;
                }
//                if (s==null) {
//                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please,you are a Professor or Student?");
//                    return;
//                }
                if (combo_box.getValue()==null) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please select the class you want to join ");
                    return;
                }

                //database
                new connexion().register(firstField,lastField,passwordField,combo_box,"Student");
                showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "friend Successful!", "Welcome " + lastField.getText());
            }
        });
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
