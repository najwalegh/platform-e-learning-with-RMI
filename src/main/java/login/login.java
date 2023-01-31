package login;

//import com.example.db.ConnexionData;
import database.connexion;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class login extends Application {
    String s=null;
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("e-Learning Platform");
        GridPane grid = new GridPane();
        primaryStage.show();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 500, 275);
        primaryStage.setScene(scene);

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label firstName = new Label("Fisrt Name:");
        grid.add(firstName, 0, 1);

        TextField firstTextField = new TextField();
        grid.add(firstTextField, 1, 1);

        Label lastName = new Label("Last Name:");
        grid.add(lastName, 0, 2);

        TextField lastTextField = new TextField();
        grid.add(lastTextField, 1, 2);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 3);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 3);

        //Type of users:students or profossors
        // create a tile pane
        TilePane r = new TilePane();

        // create a toggle group
        ToggleGroup tg = new ToggleGroup();

        // create radiobuttons
        RadioButton r1 = new RadioButton("Professor");
        RadioButton r2 = new RadioButton("Student");

        // add radiobuttons to toggle group
        r1.setToggleGroup(tg);
        r2.setToggleGroup(tg);

        // add label
        r.getChildren().add(r1);
        r.getChildren().add(r2);

        // add a change listener
        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {
                RadioButton rb = (RadioButton)tg.getSelectedToggle();
                if (rb != null) {
                    s = rb.getText();
                }
            }
        });

        HBox box = new HBox(20, r1,r2);
        grid.add(box,1,4);

        //button sign in
        Button btn = new Button("Sign in");
        //stylesheet's button
        btn.setStyle("-fx-background-color: #1dbf73");

        //button registerStudent
        Button btn2 = new Button("Register");

        HBox hbBtn2 = new HBox(20, btn, btn2);
        hbBtn2.setAlignment(Pos.CENTER);
        grid.add(hbBtn2, 1, 5);

        //sign in
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent a) {
                if (s==null) {
                    showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Form Error!", "Please,you are a Professor or Student?");
                    return;
                }
                new connexion().login(firstTextField,lastTextField, pwBox,s);
                primaryStage.close();
            }
        });

        //registerStudent
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent a) {
                if (s==null) {
                    showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Form Error!", "Please,you are a Professor or Student?");
                    return;
                }
                if(s.equals("Student")) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // Your class that extends Application
                            try {
                                new registerStudent().start(new Stage());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }else if (s.equals("Professor")){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // Your class that extends Application
                            try {
                                new registerProfessor().start(new Stage());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
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

//    public void appel() {
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                // class "REGISTER" that extends Application
//                try {
//                    new registerStudent().start(new Stage());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

}
