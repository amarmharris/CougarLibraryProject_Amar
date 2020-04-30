/**
 * Amar Harris
 * 1310247
 */

package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {

    public String DB_URL = "Jdbc:mysql://cougarlibrary.cyt69uqxe34i.us-east-2.rds.amazonaws.com?useSSL=false";
    public String userName = "amar";
    public String password = "Cougar2020";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        primaryStage.setTitle("Cougar Library");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {
        String DB_URL = "Jdbc:mysql://cougarlibrary.cyt69uqxe34i.us-east-2.rds.amazonaws.com?useSSL=false";
        String DB_userName = "amar";
        String DB_password = "Cougar2020";
        launch(args);
        try {
            //Class.forName("org.h2.Driver");
            //Connection conn = DriverManager.getConnection("jdbc:derby:C:/Users/amar/OneDrive-University Of Houston/University of Houston/2020 Spring UH/CIS 3368 (JAVA 2)/HW & assignments/LibraryManagementSystem_A_Harris/CougarLibrary;create=true");
            //Connection conn = DriverManager.getConnection("jdbc:derby:memory:~/CougLibrary;create=true");
            Connection conn = DriverManager.getConnection(DB_URL, DB_userName, DB_password);
            //Statement stmt = conn.createStatement();
            System.out.println("connected successfully");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
