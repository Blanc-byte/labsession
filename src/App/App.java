/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the main.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("/views/loginpanel.fxml"));

            // Set up the stage (window)
            Scene scene = new Scene(root); // Preferred size: 900x600
            primaryStage.setTitle("Main Menu");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading fxml. Check the file path.");
        }
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
