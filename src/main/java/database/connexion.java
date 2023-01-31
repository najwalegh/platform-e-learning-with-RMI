package database;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.stage.Stage;
import professor.CreateClass;
import student.JoinClass;
import login.login;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;

public class connexion {
    static String user = "root";
    static String password = "";
    static String url = "jdbc:mysql://localhost/e-learning";
    static String driver = "com.mysql.cj.jdbc.Driver";

    static ArrayList<String> ActiveUsers = new ArrayList<>();

    public static ArrayList<String> getMyList() {
        return ActiveUsers;
    }

    //Connexion with DataBase
    public static Connection getCon() {
        Connection con = null;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return con;
    }

    //Authentication
    public void login(TextField firstTextField, TextField lastTextField, PasswordField pwBox, String s) {
        PreparedStatement st = null;
        PreparedStatement st2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        Connection con = connexion.getCon();
        int test = 0;

        try {
            st = con.prepareStatement("SELECT * FROM professors WHERE first_name =? AND last_name=? AND PASSWORD = ?");
            st.setString(1, firstTextField.getText());
            st.setString(2, lastTextField.getText());
            st.setString(3, pwBox.getText());

            st2 = con.prepareStatement("SELECT * FROM students WHERE first_name =? AND last_name=? AND PASSWORD = ?");
            st2.setString(1, firstTextField.getText());
            st2.setString(2, lastTextField.getText());
            st2.setString(3, pwBox.getText());

            rs = st.executeQuery();

            //the Professor is logged in
            if (s.equals("Professor")) {
                if (rs.next()) {
                    CreateClass classRoom;
                    try {
                        classRoom = new CreateClass(lastTextField.getText(), "localhost", "8000");
                        classRoom.connexion();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    test = 1;
                    return;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Login Error, Try AGAIN!", ButtonType.OK);
                    alert.show();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // Your class that extends Application
                            try {
                                new login().start(new Stage());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });                }
            } else {
                if (test == 0) {
                    rs2 = st2.executeQuery();
                    //the Student is logged in
                    if (rs2.next()) {
                        JoinClass jwb;
                        try {
                            jwb = new JoinClass(lastTextField.getText(), "localhost", "8000");
                            jwb.connexion();
                        } catch (RemoteException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Login Error, Try AGAIN!", ButtonType.OK);
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Register
    public void register(TextField firstField, TextField lastField, PasswordField passwordField, ComboBox combo_box, String s) {
        PreparedStatement st = null;
        ResultSet rs = null;
        Connection con = getCon();
        try {
            Statement statement = con.createStatement();
            String sql = null;
            String firstName = firstField.getText();
            String lastName = lastField.getText();
            String password = passwordField.getText();
            int id_cours = getIdCours((String) combo_box.getValue());

            if (s.equals("Student") && id_cours != 0) {
                sql = "INSERT INTO students (first_name,last_name,password,id_cours) VALUES('" + firstName + "','" + lastName + "','" + password + "','" + id_cours + "')";
                statement.executeUpdate(sql);
            } else {
                sql = "INSERT INTO professors (first_name,last_name,password) VALUES('" + firstName + "','" + lastName + "','" + password + "')";
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //get id cour
    public int getIdCours(String name) {
        PreparedStatement st = null;
        ResultSet rs = null;
        int id = 0;
        Connection con = connexion.getCon();
        try {
            st = con.prepareStatement("SELECT * FROM courses WHERE name =? ");
            st.setString(1, name);

            rs = st.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id_cours");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    //Get the course
    public static ArrayList<String> courses() {
        PreparedStatement st = null;
        ResultSet rs = null;
        ArrayList<String> courses = new ArrayList<String>();
        Connection con = connexion.getCon();
        try {
            st = con.prepareStatement("SELECT * FROM courses ");
            rs = st.executeQuery();
            while (rs.next()) {
                courses.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }


}
