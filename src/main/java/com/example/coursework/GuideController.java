package com.example.coursework;

import com.example.coursework.logs.Logs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class GuideController {

    Logs logs = Logs.getInstance();

    @FXML
    private Text mainText;

    public void backToMenu() throws IOException {
        logs.writeLog("Back to Main menu....");
        Stage newWindow = new Stage();
        Stage oldWindow = (Stage) mainText.getScene().getWindow();
        newWindow.setTitle("Menu");
        newWindow.getIcons().add(new Image("file:icon.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu-view.fxml"));
        newWindow.setScene(new Scene(loader.load()));
        newWindow.show();
        oldWindow.close();
    }

    public void showAuthor(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Author info");
        alert.setHeaderText(null);
        alert.setContentText("Author: Bohdan Velykokon, 'КН-11 с.п.'. This was made for the coursework (lpnu.ua)");
        alert.showAndWait();
    }
}
