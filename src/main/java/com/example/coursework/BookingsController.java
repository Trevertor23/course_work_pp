package com.example.coursework;

import com.example.coursework.logs.Logs;
import com.example.coursework.vacation.BookingItem;
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

public class BookingsController implements Initializable {
    @FXML
    private TableView<BookingItem> table;

    @FXML
    private TableColumn<BookingItem, String> name;
    @FXML
    private TableColumn<BookingItem, String> descr;
    @FXML
    private TableColumn<BookingItem, String> type;
    @FXML
    private TableColumn<BookingItem, String> transport;
    @FXML
    private TableColumn<BookingItem, String> days;
    @FXML
    private TableColumn<BookingItem, Byte> food;
    @FXML
    private TableColumn<BookingItem, Integer> price;

    @FXML
    private Pane listPane;
    @FXML
    private Pane detailsPane;

    @FXML
    private TextArea detailsArea;

    private Logs logs = Logs.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<BookingItem, String>("name"));
        descr.setCellValueFactory(new PropertyValueFactory<BookingItem, String>("descr"));
        type.setCellValueFactory(new PropertyValueFactory<BookingItem, String>("type"));
        transport.setCellValueFactory(new PropertyValueFactory<BookingItem, String>("transport"));
        days.setCellValueFactory(new PropertyValueFactory<BookingItem, String>("days"));
        food.setCellValueFactory(new PropertyValueFactory<BookingItem, Byte>("food"));
        try {
            Path file = Path.of(System.getProperty("user.dir") + "/user_id.txt");
            String userId = Files.readString(file);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/vacations_db", "mysql", "mysql");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM `bookings` INNER JOIN `vacations` " +
                    "ON bookings.vacation_id = vacations.id AND bookings.user_id=" + userId + ";");
            ArrayList<BookingItem> bookings = new ArrayList<BookingItem>();
            while (rs.next()) {
                bookings.add(new BookingItem(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                        rs.getString(4), rs.getString(5), rs.getByte(6), rs.getString(8),
                        rs.getString(9), rs.getString(10)));
            }
            table.setItems(FXCollections.observableArrayList(bookings));
            logs.writeLog("Table of user bookings fetched!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void back(){
        listPane.setVisible(true);
        detailsPane.setVisible(false);
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

    public void seeDetails(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        BookingItem item = table.getSelectionModel().getSelectedItem();
        if(item == null){
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("Choose the booking to continue!");
            alert.showAndWait();
        }
        else{
            listPane.setVisible(false);
            detailsPane.setVisible(true);
            String details = item.toString();
            detailsArea.setText(details);
        }
    }

    public void deleteBooking(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        BookingItem item = table.getSelectionModel().getSelectedItem();
        if(item == null){
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("Choose the booking to delete!");
            alert.showAndWait();
            return;
        }
        Alert alertDelete = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected booking?",
                ButtonType.YES, ButtonType.NO);
        alertDelete.showAndWait();

        if (alertDelete.getResult() == ButtonType.YES) {
            try {
                logs.writeLog("Deleting a booking....");
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con= DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/vacations_db","mysql","mysql");
                Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.execute("DELETE FROM bookings WHERE id = " + item.getId() + ";");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                Path file = Path.of(System.getProperty("user.dir") + "/user_id.txt");
                String userId = Files.readString(file);
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/vacations_db", "mysql", "mysql");
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery("SELECT * FROM `bookings` INNER JOIN `vacations` " +
                        "ON bookings.vacation_id = vacations.id AND bookings.user_id=" + userId + ";");
                ArrayList<BookingItem> bookings = new ArrayList<BookingItem>();
                while (rs.next()) {
                    bookings.add(new BookingItem(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                            rs.getString(4), rs.getString(5), rs.getByte(6), rs.getString(8),
                            rs.getString(9), rs.getString(10)));
                }
                table.setItems(FXCollections.observableArrayList(bookings));
                logs.writeLog("Booking deleted!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
