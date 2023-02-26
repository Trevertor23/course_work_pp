package com.example.coursework;

import com.example.coursework.logs.Logs;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;

public class HelloController {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passField;
    @FXML
    private Text wrongText;
    private double x;
    private Logs logs = Logs.getInstance();

    public void login(ActionEvent e){
        String loginText = loginField.getText();
        String passText = passField.getText();
        try{
            logs.writeLog("Logging in....");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/vacations_db","mysql","mysql");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT id,login from users WHERE login='" + loginText + "' and " +
                    "password = '" + passText + "';");
            if(rs.next()){
            //if(true){
                File f;
                Path file = Path.of(System.getProperty("user.dir") + "/user_id.txt");

                if(!new File(file.toUri()).exists()){
                    f = new File(file.toUri());
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                }

                Files.writeString(file,rs.getString(1));
                //Files.writeString(file,"122333");
                logs.writeLog("Logging success!");
                Stage newWindow = new Stage();
                Stage oldWindow = (Stage) loginField.getScene().getWindow();
                newWindow.setTitle("Menu");
                newWindow.getIcons().add(new Image("file:icon.png"));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("menu-view.fxml"));
                newWindow.setScene(new Scene(loader.load()));
                newWindow.show();
                oldWindow.close();
            }
            else{
                logs.writeLog("Logging failed (User error)");
                wrongText.setVisible(true);
            }
            con.close();
        }catch(Exception exc){ System.out.println(exc);}
    }
}