package com.example.coursework.scenemanager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    /**
     *
     * @param fxmlFile necessary to work
     *
     */
    public void changeScene(String fxmlFile, Stage oldWindow){
        if(oldWindow!=null)
            try {
                Stage newWindow = new Stage();
                newWindow.setTitle("Choose a tour");
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                newWindow.setScene(new Scene(loader.load()));
                newWindow.show();
                oldWindow.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}
