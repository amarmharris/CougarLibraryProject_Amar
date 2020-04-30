package sample.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DB_Connection {

    public String DB_URL = "Jdbc:mysql://cougarlibrary.cyt69uqxe34i.us-east-2.rds.amazonaws.com?useSSL=false";
    public String userName = "amar";
    public String password = "Cougar2020";

    Connection conn;

    public DB_Connection() {
        try {
            this.conn = DriverManager.getConnection(DB_URL, userName, password);
            if (this.conn!=null) {
                System.out.println("Connection successful");
            }
        } catch(SQLException e) {
            System.out.println("Connection failed");
        }
    }

}
