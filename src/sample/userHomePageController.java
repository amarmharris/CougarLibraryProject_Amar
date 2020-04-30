package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class userHomePageController implements Initializable {

    public String DB_URL = "Jdbc:mysql://cougarlibrary.cyt69uqxe34i.us-east-2.rds.amazonaws.com?useSSL=false";
    public String userName = "amar";
    public String password = "Cougar2020";

    public Label checkedOutItemsLabel;
    public Label booksCheckedOutLabel;
    public Label mediaCheckedOutLabel;
    public Label nameLabel;

    int cookieAccountID;

    public void passData(int cookieAccountID) {
        this.cookieAccountID = cookieAccountID;

        try {
            Connection conn = DriverManager.getConnection(DB_URL, userName, password);
            ResultSet rs = null;
            PreparedStatement stmt = null;

            stmt = conn.prepareStatement("select * from COUGARLIBRARY.USERS \n" +
                    "inner join COUGARLIBRARY.ACCOUNTS \n" +
                    "on ACCOUNTS.account_id=USERS.account_id\n" +
                    "where USERS.ACCOUNT_ID=?;");
            stmt.setInt(1, cookieAccountID);
            rs=stmt.executeQuery();

            while (rs.next()) {
                nameLabel.setText(rs.getString("USER_NAME"));
                mediaCheckedOutLabel.setText(String.valueOf(rs.getInt("NumOfMediaCheckedOut")));
                booksCheckedOutLabel.setText(String.valueOf(rs.getInt("NumOfBooksCheckedOut")));
                int numItems = rs.getInt("NumOfMediaCheckedOut") + rs.getInt("NumOfBooksCheckedOut");
                checkedOutItemsLabel.setText(String.valueOf(numItems));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(cookieAccountID);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    public void searchMedia(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("mediaCatalog.fxml"));
        Parent GUI = loader.load();
        Scene scene = new Scene(GUI);

        mediaCatalogController controller = loader.getController();
        controller.passData(cookieAccountID);

        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void searchBooks(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("bookCatalog.fxml"));
        Parent GUI = loader.load();
        Scene scene = new Scene(GUI);

        bookCatalogController controller = loader.getController();
        controller.passData(cookieAccountID);

        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void viewCheckedoutItems(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("userViewTables.fxml"));
        Parent GUI = loader.load();
        Scene scene = new Scene(GUI);

        userViewTablesController controller = loader.getController();
        controller.passData(cookieAccountID);

        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void logOut(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MainPage.fxml"));
        Parent GUI = loader.load();
        Scene scene = new Scene(GUI);

        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }
}
