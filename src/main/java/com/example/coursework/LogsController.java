package com.example.coursework;

import com.example.coursework.logs.Logs;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LogsController implements Initializable {

    @FXML
    private ListView list;
    @FXML
    private TextArea textArea;

    private Logs logs = Logs.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Scanner inp = new Scanner(System.in);
        String [] names = new String[]{};
        short i;
        File l = new File(System.getProperty("user.dir") + "\\logs");
        names = l.list();
        ArrayList<String> namesList;
        if(!Arrays.equals(names,new String[]{})){
            namesList = new ArrayList<String>(Arrays.asList(names));
            list.setItems(FXCollections.observableArrayList(namesList));
        }
        else{
            list.setItems(FXCollections.observableArrayList(new ArrayList<String>(){{add("No files");}}));
            list.setDisable(true);
        }
        try {
            logs.writeLog("Log-files fetched!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void watchLogs(){
        String chosen = (String) list.getSelectionModel().getSelectedItem();
        String logText = logs.readLog(chosen);
        textArea.setText(logText);
    }

    public void backToMenu() throws IOException {
        logs.writeLog("Back to Main menu....");
        Stage newWindow = new Stage();
        Stage oldWindow = (Stage) list.getScene().getWindow();
        newWindow.setTitle("Menu");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu-view.fxml"));
        newWindow.setScene(new Scene(loader.load()));
        newWindow.show();
        oldWindow.close();
    }

}
