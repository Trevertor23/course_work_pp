package com.example.coursework;

import com.example.coursework.logs.Logs;
import com.example.coursework.vacation.VacationItem;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextArea descrField;

    @FXML
    private ChoiceBox typeBox;

    @FXML
    private TextField transportField;

    @FXML
    private TextField daysField;

    @FXML
    private CheckBox foodBox;

    @FXML
    private TextField priceField;

    private ArrayList<Integer> typeIds = new ArrayList<>();
    private ArrayList<String> types = new ArrayList<>();

    private Logs logs = Logs.getInstance();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/vacations_db","mysql","mysql");
            Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=stmt.executeQuery("select * from types;");

            while (rs.next()){
                typeIds.add(rs.getInt(1));
                types.add(rs.getString(2));
            }
            typeBox.setValue("--Choose--");
            typeBox.setItems(FXCollections.observableArrayList(types));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTour(){
        if(ifNulls()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("Specify all the parameters of the tour to create!");
            alert.showAndWait();
            return;
        }

        int chosenType = 0;
        String chosenName = nameField.getText(),chosenDescr = descrField.getText(),chosenTranp = transportField.getText(),
                chosenDays = daysField.getText(), chosenPrice = priceField.getText();
        Boolean chosenFood = foodBox.isSelected();

        for(int i=0;i<types.size();i++){
            if(typeBox.getValue() == types.get(i)){
                chosenType = typeIds.get(types.indexOf(types.get(i)));
                break;
            }
        }

        try {
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Success!");
            success.setHeaderText(null);
            success.setContentText("Successfully created!");

            logs.writeLog("Creating a booking....");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/vacations_db","mysql","mysql");
            Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.execute("INSERT INTO vacations (name,descr,type_id,transport,days,food,start_price)" +
                    " VALUES ('" + chosenName + "','" + chosenDescr + "'," + chosenType + ",'" + chosenTranp
                    + "','" + chosenDays + "'," + chosenFood + "," + chosenPrice + ");");
            success.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Boolean ifNulls(){
        if(nameField.getText() == ""){
            return true;
        }
        if(descrField.getText() == ""){
            return true;
        }
        if(typeBox.getValue() == "--Choose--"){
            return true;
        }
        if(transportField.getText() == ""){
            return true;
        }
        if(daysField.getText() == ""){
            return true;
        }
        if(priceField.getText() == ""){
            return true;
        }

        return false;
    }

    public void backToMenu() throws IOException {
        logs.writeLog("Back to Main menu....");
        Stage newWindow = new Stage();
        Stage oldWindow = (Stage) nameField.getScene().getWindow();
        newWindow.setTitle("Menu");
        newWindow.getIcons().add(new Image("file:icon.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu-view.fxml"));
        newWindow.setScene(new Scene(loader.load()));
        newWindow.show();
        oldWindow.close();
    }

}
