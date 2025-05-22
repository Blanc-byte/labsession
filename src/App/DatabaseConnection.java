package App;


import java.sql.Connection;
import java.sql.DriverManager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
public class DatabaseConnection {
    final String DB_URL = "jdbc:mysql://localhost/comlab";
    final String USER = "root";
    final String PASS = "";
    public String url;
    public Connection con = null;
    public Connection connect() {
        
        url = "jdbc:mysql://localhost:3306/comlab";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, USER, PASS);

            
            //root, root
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
        }
        
        return con;
    
    }
}
