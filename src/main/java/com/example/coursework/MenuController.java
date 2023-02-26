package com.example.coursework;

import com.example.coursework.logs.Logs;
import com.example.coursework.scenemanager.SceneManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private Button exitButton;

    private Logs logs = Logs.getInstance();

    public void test(){
        System.out.println("dfgldlfg");
        TextInputDialog td = new TextInputDialog("enter any text");
        td.setHeaderText("enter your name");
        td.showAndWait();
        System.out.println(td.getResult());
    }

    public void openMenu1(){
        try {
            logs.writeLog("Opening making-a-book menu....");
            Stage newWindow = new Stage();
            Stage oldWindow = (Stage) exitButton.getScene().getWindow();
            newWindow.setTitle("Choose a tour");
            newWindow.getIcons().add(new Image("file:icon.png"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("making-view.fxml"));
            newWindow.setScene(new Scene(loader.load()));
            newWindow.show();
            oldWindow.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openMenu2(){
        try {
            logs.writeLog("Opening bookings menu....");
            Stage newWindow = new Stage();
            Stage oldWindow = (Stage) exitButton.getScene().getWindow();
            newWindow.setTitle("Your bookings");
            newWindow.getIcons().add(new Image("file:icon.png"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bookings-view.fxml"));
            newWindow.setScene(new Scene(loader.load()));
            newWindow.show();
            oldWindow.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openMenu3(){
        try {
            logs.writeLog("Opening logs menu....");
            Stage newWindow = new Stage();
            Stage oldWindow = (Stage) exitButton.getScene().getWindow();
            newWindow.setTitle("Logs");
            newWindow.getIcons().add(new Image("file:icon.png"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("logs-view.fxml"));
            newWindow.setScene(new Scene(loader.load()));
            newWindow.show();
            oldWindow.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openMenu4(){
        try {
            logs.writeLog("Opening guide menu....");
            Stage newWindow = new Stage();
            Stage oldWindow = (Stage) exitButton.getScene().getWindow();
            newWindow.setTitle("Logs");
            newWindow.getIcons().add(new Image("file:icon.png"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("guide-view.fxml"));
            newWindow.setScene(new Scene(loader.load()));
            newWindow.show();
            oldWindow.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openMenu5(){
        try {
            logs.writeLog("Opening creating menu....");
            Stage newWindow = new Stage();
            Stage oldWindow = (Stage) exitButton.getScene().getWindow();
            newWindow.setTitle("Logs");
            newWindow.getIcons().add(new Image("file:icon.png"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("create-view.fxml"));
            newWindow.setScene(new Scene(loader.load()));
            newWindow.show();
            oldWindow.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void terminateApplication(){
        Platform.exit();
    }
}
