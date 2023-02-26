package com.example.coursework;

import com.example.coursework.logs.Logs;
import com.example.coursework.vacation.VacationItem;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MakingController implements Initializable {

    @FXML
    private TableView<VacationItem> table;

    @FXML
    private TableColumn<VacationItem, String> name;
    @FXML
    private TableColumn<VacationItem, String> descr;
    @FXML
    private TableColumn<VacationItem, String> type;
    @FXML
    private TableColumn<VacationItem, String> transport;
    @FXML
    private TableColumn<VacationItem, String> days;
    @FXML
    private TableColumn<VacationItem, Byte> food;
    @FXML
    private TableColumn<VacationItem, Integer> price;

    @FXML
    private Pane listPane;
    @FXML
    private Pane detailsPane;
    @FXML
    private Pane finalPane;

    @FXML
    private TextArea detailsArea;

    @FXML
    private ChoiceBox transportChoice;
    @FXML
    private ChoiceBox daysChoice;
    @FXML
    private CheckBox foodCheckBox;

    private Logs logs = Logs.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        name.setCellValueFactory(new PropertyValueFactory<VacationItem,String>("name"));
        descr.setCellValueFactory(new PropertyValueFactory<VacationItem,String>("descr"));
        type.setCellValueFactory(new PropertyValueFactory<VacationItem,String>("type"));
        transport.setCellValueFactory(new PropertyValueFactory<VacationItem,String>("transport"));
        days.setCellValueFactory(new PropertyValueFactory<VacationItem,String>("days"));
        food.setCellValueFactory(new PropertyValueFactory<VacationItem,Byte>("food"));
        price.setCellValueFactory(new PropertyValueFactory<VacationItem,Integer>("price"));
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/vacations_db","mysql","mysql");
            Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=stmt.executeQuery("select * from vacations;");
            ArrayList<VacationItem> vacations = new ArrayList<VacationItem>();
            while(rs.next()){
                vacations.add(new VacationItem(rs.getInt(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),rs.getByte(7),
                        rs.getInt(8)));
            }
            table.setItems(FXCollections.observableArrayList(vacations));
            logs.writeLog("Vacations table fetched!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void getCell(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        VacationItem item = table.getSelectionModel().getSelectedItem();
        if(item==null) {
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("Choose the tour to continue!");
            alert.showAndWait();
        }
        else{
            listPane.setVisible(false);
            detailsPane.setVisible(true);
            String details = item.toString();
            detailsArea.setText(details);
        }
    }

    public void back(){
        listPane.setVisible(true);
        detailsPane.setVisible(false);
    }

    public void finalNext(){
        detailsPane.setVisible(false);
        finalPane.setVisible(true);
        VacationItem item = table.getSelectionModel().getSelectedItem();

        String[] trAr = item.getTransport().split(",");
        ArrayList<String> transports = new ArrayList<String>();
        for(int i=0;i<trAr.length;i++){
            transports.add(trAr[i]);
        }
        transportChoice.setValue("--Choose--");
        transportChoice.setItems(FXCollections.observableArrayList(transports));

        String[] dAr = item.getDays().split(",");
        ArrayList<String> days = new ArrayList<>();
        for(int i=0;i<dAr.length;i++){
            days.add(dAr[i]);
        }
        daysChoice.setValue("--Choose--");
        daysChoice.setItems(FXCollections.observableArrayList(days));

        if(item.getFood()==0){
            foodCheckBox.setDisable(true);
        }
    }

    public void book(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        if(transportChoice.getValue().toString()=="--Choose--" || daysChoice.getValue().toString()=="--Choose--"){
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("Specify the parameters of the tour to continue!");
            alert.showAndWait();
            return;
        }
        else{
            try{
                logs.writeLog("Trying to book a tour....");
                VacationItem item = table.getSelectionModel().getSelectedItem();
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con= DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/vacations_db","mysql","mysql");
                Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String transport = transportChoice.getValue().toString(), days = daysChoice.getValue().toString();
                Boolean food = foodCheckBox.isSelected();
                Path file = Path.of(System.getProperty("user.dir") + "/user_id.txt");
                String userId = Files.readString(file);
                stmt.execute("INSERT INTO bookings (user_id,vacation_id,transport,days,food) VALUES (" +
                        userId + "," + item.getId() + ",'" + transport + "','" + days + "'," + food + ");");
                if(true) {
                    alert1.setTitle("Success!");
                    alert1.setHeaderText(null);
                    alert1.setContentText("You have booked a tour! Check it out at 'See my bookings' page!");
                    alert1.showAndWait();

                    logs.writeLog("Booking successful!");

                    Stage newWindow = new Stage();
                    Stage oldWindow = (Stage) table.getScene().getWindow();
                    newWindow.setTitle("Menu");
                    newWindow.getIcons().add(new Image("file:icon.png"));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("menu-view.fxml"));
                    newWindow.setScene(new Scene(loader.load()));
                    newWindow.show();
                    oldWindow.close();
                }
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void backToMenu() throws IOException {
        logs.writeLog("Back to Main menu....");
        Stage newWindow = new Stage();
        Stage oldWindow = (Stage) table.getScene().getWindow();
        newWindow.setTitle("Menu");
        newWindow.getIcons().add(new Image("file:icon.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu-view.fxml"));
        newWindow.setScene(new Scene(loader.load()));
        newWindow.show();
        oldWindow.close();
    }
}
