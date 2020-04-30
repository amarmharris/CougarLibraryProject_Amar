package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.classes.User;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.io.*;

import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    //String DB_URL = "jdbc:derby:memory:~/CougLibrary;create=true";
    String DB_URL = "Jdbc:mysql://cougarlibrary.cyt69uqxe34i.us-east-2.rds.amazonaws.com?useSSL=false";
    String DB_userName = "amar";
    String DB_password = "Cougar2020";

    public JFXButton logInBtn;
    public JFXButton signUpBtn;
    public TextField passwordTXt;
    public TextField userNameTxt;

    public User user = new User();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    public void logIn(ActionEvent actionEvent) throws SQLException {
        String userName = userNameTxt.getText();
        String password = passwordTXt.getText();
        int accountID;
        String accountType;

        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_userName, DB_password);

            PreparedStatement stmt= conn.prepareStatement("SELECT * FROM COUGARLIBRARY.ACCOUNTS WHERE USER_NAME = ? and USER_PASSWORD = ?");
            stmt.setString(1, userName);
            stmt.setString(2, password);
            ResultSet getAccountInfo = stmt.executeQuery();

             if (getAccountInfo.next()) {

                 accountID = getAccountInfo.getInt("ACCOUNT_ID");
                 accountType = getAccountInfo.getString("ACCOUNT_TYPE");

                 if (accountType.equals("ADMIN")) {

                     FXMLLoader loader = new FXMLLoader();
                     loader.setLocation(getClass().getResource("adminHome.fxml"));
                     Parent GUI = loader.load();
                     Scene scene = new Scene(GUI);
                     adminHomeController controller = loader.getController();
                     controller.passData(accountID);
                     Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                     window.setScene(scene);
                     window.show();

                     System.out.println("Logged in as " + getAccountInfo.getString(2));

                 } else {

                     FXMLLoader loader = new FXMLLoader();
                     loader.setLocation(getClass().getResource("userHomePage.fxml"));
                     Parent GUI = loader.load();
                     Scene scene = new Scene(GUI);
                     userHomePageController controller = loader.getController();
                     controller.passData(accountID);
                     Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                     window.setScene(scene);
                     window.show();

                     System.out.println("Logged in as " + getAccountInfo.getString(2));

                 }

             } else {
                 JOptionPane.showMessageDialog(null,"Incorrect Username or Password", "Login Error", JOptionPane.ERROR_MESSAGE);
             }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void signUp(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("createAccountForm.fxml"));
        Parent GUI = loader.load();
        Scene scene = new Scene(GUI);

        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
