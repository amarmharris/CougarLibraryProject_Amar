package sample;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class createAccountController implements Initializable {

    private boolean isMyComboBoxEmpty;
    public JFXButton createAccountBtn;
    public TextField passwordTXt;
    public TextField userNameTxt;
    public TextField confirmPasswordTXt;
    public ComboBox accountTypeCombo;
    public ObservableList<String> accountTypes = FXCollections.observableArrayList();

    public String DB_URL = "Jdbc:mysql://cougarlibrary.cyt69uqxe34i.us-east-2.rds.amazonaws.com?useSSL=false";
    public String userName = "amar";
    public String password = "Cougar2020";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        accountTypeCombo.setItems(accountTypes);

        try {
            Connection conn = DriverManager.getConnection(DB_URL, userName, password);
            ResultSet rs = null;
            PreparedStatement stmt = null;

            stmt = conn.prepareStatement("SELECT * FROM COUGARLIBRARY.ACCOUNT_TYPES");
            rs = stmt.executeQuery();
            while (rs.next()) {
                accountTypes.addAll(rs.getString("ACCOUNT_TYPE"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createAccount(ActionEvent actionEvent) {

        String getUsername = userNameTxt.getText();
        String getPassword = passwordTXt.getText();
        String getAccountType = (String) accountTypeCombo.getValue();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        if(validateText()) {
            try {

                Connection conn = DriverManager.getConnection(DB_URL, userName, password);

                String sql0="insert into COUGARLIBRARY.ACCOUNTS (USER_NAME, USER_PASSWORD, ACCOUNT_TYPE) values (?, ?, ?);";
                String sql1="select * from COUGARLIBRARY.ACCOUNTS WHERE ACCOUNT_ID=(SELECT last_insert_id());";
                int lastAccountID = 0;
                String lastAccountType = null;
                String sqlUserInsert="insert into COUGARLIBRARY.USERS (ACCOUNT_ID) VALUES (?);";
                String sqlAdminInsert = "insert into COUGARLIBRARY.ADMIN (ACCOUNT_ID) VALUES (?);";

                stmt = conn.prepareStatement(sql0);
                stmt.setString(1, getUsername);
                stmt.setString(2, getPassword);
                stmt.setString(3, getAccountType);
                stmt.executeUpdate();

                stmt=conn.prepareStatement(sql1);
                rs=stmt.executeQuery();
                while (rs.next()) {
                    lastAccountID=rs.getInt(1);
                    lastAccountType=rs.getString("ACCOUNT_TYPE");
                }

                if (lastAccountType.equals("ADMIN")) {
                    stmt = conn.prepareStatement(sqlAdminInsert);
                    stmt.setInt(1, lastAccountID);
                    stmt.executeUpdate();

                    System.out.println("inserted accountID " + lastAccountID + " into ACCOUNTS & ADMIN table");

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("adminHome.fxml"));
                    Parent GUI = loader.load();
                    Scene scene = new Scene(GUI);
                    adminHomeController controller = loader.getController();
                    controller.passData(lastAccountID);
                    Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.show();

                } else {
                    stmt = conn.prepareStatement(sqlUserInsert);
                    stmt.setInt(1, lastAccountID);
                    stmt.executeUpdate();

                    System.out.println("inserted accountID " + lastAccountID + " into ACCOUNTS & ADMIN table ");

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("userHomePage.fxml"));
                    Parent GUI = loader.load();
                    Scene scene = new Scene(GUI);
                    userHomePageController controller = loader.getController();
                    controller.passData(lastAccountID);
                    Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public boolean validateText() {
        if (userNameTxt.getText().isEmpty() || passwordTXt.getText().isEmpty() || accountTypeCombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Fields");
            alert.setHeaderText(null);
            alert.setContentText("Please fill out all the fields");
            alert.showAndWait();
            return false;
        }
        return true;
    }

}
