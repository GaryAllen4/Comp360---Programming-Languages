/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newbornairline;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Gary Allen
 * Assignment: COMP 360 Project 2
 * Due Date: 11/08/2020
 */
public class NewBornAirline extends Application {
    
   final Button button = new Button ("Reserve Ticket");
    final TextField firstName = new TextField(""); //user input for their first name
    final TextField lastName = new TextField(""); //user input for their la name

    String[] firstClassWindowSeats = {"W-L1", "W-R1", "W-L2", "W-R2"}; //all first class seats
    String[] firstClassAisleSeats = {"A-L1", "A-R1", "A-L2", "A-R2"}; //all first aisle seats 
    String[] businessClassWindowSeats = {"W-L3", "W-R3", "W-L4", "W-R4", "W-L5", "W-R5", "W-L6", "W-R6", "W-L7", "W-R7"}; //all business class window seats
    String[] businessClassAisleSeats = {"A-L3", "A-R3", "A-L4", "A-R4", "A-L5", "A-R5", "A-L6", "A-R6", "A-L7", "A-R7"}; //all business class aisle seats
    String[] econClassWindowSeats = {"W-L8", "W-R8", "W-L9", "W-R9", "W-L10", "W-R10", "W-L11", "W-R11", "W-L12", "W-R12"}; //all economy class window seats
    String[] econClassAisleSeats = {"A-L8", "A-R8", "A-L9", "A-R9", "A-L10", "A-R10", "A-L11", "A-R11", "A-L12", "A-R12"}; //all economy class aisle seats
    String[] selectedSeats111={}; //array to hold the seats that are taken on flight 111 
    String[] selectedSeats222={}; //array to hold the seats that are taken on flight 222
    String[] selectedSeats333={}; //array to hold the seats that are taken on flight 333
    String[] selectedSeats444={}; //array to hold the seats that are taken on flight 444
    boolean formError = false;
    
    @Override public void start(Stage stage) { 
        stage.setTitle("New Born Airline"); //Set title of the GUI application
        Scene scene = new Scene(new Group(), 1000, 600);
        
        final ComboBox destination = new ComboBox(); //combo box for the user to select their itinerary
        destination.getItems().addAll( 
            "Start: Greensboro, NC End: Queens, NY",
            "Start: Queens, NY End: Greensboro, NC"  
        );
        
        final ComboBox flight = new ComboBox(); //combo box for the user to select their flight time
        flight.getItems().addAll(
            "Time: 10:00 AM - 11:30 AM",
            "Time: 2:00 PM - 2:30 PM"
        );   
        
        final ComboBox seatClass = new ComboBox(); //combobox for the user to select their seat class
        seatClass.getItems().addAll(
            "Economy",
            "Business",
            "First Class"
        ); 
        
        final ComboBox seat = new ComboBox(); //combo box for the user to select if they want a window or aisle seat
        seat.getItems().addAll(
            "window",
            "aisle"
        ); 
        seat.setDisable(true); //disable the window or aisle seat selection
        
        final ComboBox availableSeats = new ComboBox(); //combo box for the available seats based on previous selections
        availableSeats.setDisable(true);
        
        final ComboBox snacks = new ComboBox(); //combo box to select snack
        snacks.getItems().addAll(
            "cookies",
            "nuts"
        ); 
        
        final ComboBox wine = new ComboBox(); //combo box to select if user wants wine
        wine.getItems().addAll(
            "Yes",
            "No"
        ); 

        flight.setValue("Select your flight"); 
        destination.setValue("Select your destination");
        
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        Label title = new Label("New Born Airline");
        title.setFont(new Font("Times New Roman", 20)); //font of text
        title.setTextFill(Color.BLACK); //text color
        
        /*add all components to the grid*/
        grid.add(title, 1, 0); 
        grid.add(new Label("Customer First Name: "), 0, 1);
        grid.add(firstName, 0,2); 
        grid.add(new Label("Customer Last Name: "), 2, 1);
        grid.add(lastName, 2, 2);  
        grid.add(new Label("Destination: "), 0, 3);
        grid.add(destination, 0, 4);
        grid.add(new Label("Flight: "), 2, 3);
        grid.add(flight, 2, 4);
        grid.add(new Label("Seat Class: "), 0, 5);
        grid.add(seatClass, 0, 6); 
        grid.add(new Label("Seat Section:"), 2, 5);
        grid.add(seat, 2, 6); 
        grid.add(new Label("Seat Selection:"), 0, 7);
        grid.add(availableSeats, 0, 8);
        grid.add(new Label("Snack Selection: (Business Class Customers Please Select An Option)"), 0, 9);
        grid.add(snacks, 0, 10);
        grid.add(new Label("Wine? (Business and First Class Customers Please Select An Option)"), 2, 9);
        grid.add(wine, 2, 10);
        grid.add(button, 1, 11);
        
        Group root = (Group)scene.getRoot();
        root.getChildren().add(grid);
        stage.setScene(scene);
        stage.show(); //show the app to the userr=
        
        seatClass.setOnAction(new EventHandler<ActionEvent>(){ //on action of the seat class combo box
            @Override public void handle(ActionEvent e) { 
                seat.setDisable(false); //enable the seat section combo box
                seat.getSelectionModel().clearSelection(); //clear the previous selection of the seat combo box
                availableSeats.getSelectionModel().clearSelection();
                availableSeats.setDisable(true); //disable the available seats combo box
            }
        });
        
        seat.setOnAction(new EventHandler<ActionEvent>(){ //when there is an action on the seat combo box
            @Override public void handle(ActionEvent e) {
                availableSeats.setDisable(false); //enable the available seats combo box
                
                /* set the available seats based on the user's selection for the seat and seat class combo boxes*/
                if((String) seat.getValue() == "window" && (String) seatClass.getValue() == "First Class") {
                    availableSeats.getItems().setAll(firstClassWindowSeats);
                } else if((String) seat.getValue() == "window" && (String) seatClass.getValue() == "Business") {
                    availableSeats.getItems().setAll(businessClassWindowSeats);
                } else if((String) seat.getValue() == "window" && (String) seatClass.getValue() == "Economy") {
                    availableSeats.getItems().setAll(econClassWindowSeats);
                } else if((String) seat.getValue() == "aisle" && (String) seatClass.getValue() == "First Class") {
                    availableSeats.getItems().setAll(firstClassAisleSeats);
                } else if((String) seat.getValue() == "aisle" && (String) seatClass.getValue() == "Business") {
                    availableSeats.getItems().setAll(businessClassAisleSeats);
                } else if((String) seat.getValue() == "aisle" && (String) seatClass.getValue() == "Economy") {
                    availableSeats.getItems().setAll(econClassAisleSeats);
                }
            }
        });
        
        button.setOnAction(new EventHandler<ActionEvent>() { //when the submit button is clicked
            @Override public void handle(ActionEvent e) {
                String sClass;
                String seatSection;
                String startCity;
                String endCity;
                String passengerName;
                String departureTime;
                String snackChoice;
                String wineChoice;
                String seatSelection;
               if((String) destination.getValue() != null && (String) flight.getValue() != null && 
                       firstName.getText() != "" && lastName.getText() != "" && (String) seatClass.getValue() != null &&
                       (String) seat.getValue() != null && (String) availableSeats.getValue() != null){ //check that all values have a value
                    passengerName = firstName.getText() + " " + lastName.getText(); //set the customer's name
                    
                    /*set the start and end city*/
                    if((String) destination.getValue() == "Start: Greensboro, NC End: Queens, NY"){ 
                     startCity = "Greensboro, NC";
                     endCity = "Queens, NY";
                    } else {
                     startCity = "Queens, NY";
                     endCity = "Greensboro, NC";
                    }
                    
                    /*set the customer's departure time*/
                    if((String) flight.getValue() == "Time: 10:00 AM - 11:30 AM") {
                     departureTime = "10:00 AM";
                    } else {
                     departureTime = "2:00 PM";
                    }
                    
                    /*set the seat class, section, and actual seat number*/
                    sClass = (String) seatClass.getValue();
                    seatSection = (String) seat.getValue();
                    seatSelection = (String) availableSeats.getValue();

                    /*check the user's class selection and try to reserve their ticket*/
                    if(sClass == "Economy") { 
                        EconomyClass econ = new EconomyClass(passengerName, startCity, endCity, seatSelection, departureTime, seatSection);
                            if(departureTime == "10:00 AM" && startCity == "Greensboro, NC") {
                                selectedSeats111 = econ.ReserveTicket(seatSelection, selectedSeats111);
                            } else if(departureTime == "10:00 AM" && startCity == "Queens, NY") {
                                selectedSeats222 = econ.ReserveTicket(seatSelection, selectedSeats222);
                            } else if(departureTime == "2:00 PM" && startCity == "Greensboro, NC") {
                                
                                selectedSeats333 = econ.ReserveTicket(seatSelection, selectedSeats333);
                            } else if(departureTime == "2:00 PM" && startCity == "Queens, NY") {
                                selectedSeats444 = econ.ReserveTicket(seatSelection, selectedSeats444);
                            }
                    } else if(sClass == "Business") {
                        if((String) snacks.getValue() == null) { //if they did not select their snack then show an error
                            formError = true;
                        } else {
                            snackChoice = (String) snacks.getValue();
                            BusinessClass businessClass = new BusinessClass(passengerName, startCity, endCity, seatSelection, departureTime, seatSection, snackChoice);
                            if(departureTime == "10:00 AM" && startCity == "Greensboro, NC") {
                                selectedSeats111 = businessClass.ReserveTicket(seatSelection, selectedSeats111);
                            } else if(departureTime == "10:00 AM" && startCity == "Queens, NY") {
                                selectedSeats222 = businessClass.ReserveTicket(seatSelection, selectedSeats222);
                            } else if(departureTime == "2:00 PM" && startCity == "Greensboro, NC") {
                                selectedSeats333 = businessClass.ReserveTicket(seatSelection, selectedSeats333);
                            } else if(departureTime == "2:00 PM" && startCity == "Queens, NY") {
                                selectedSeats444 = businessClass.ReserveTicket(seatSelection, selectedSeats444);
                            }
                        }
                         
                    } else if(sClass == "First Class") { 
                        if((String) snacks.getValue() == null || (String) wine.getValue() == null) { //if they did not select their snack or wine then show an error
                            formError = true;
                        } else {
                            snackChoice = (String) snacks.getValue();
                            wineChoice = (String) wine.getValue();
                            FirstClass firstClassTicket = new FirstClass(passengerName, startCity, endCity, seatSelection, departureTime, seatSection, snackChoice, wineChoice);
                            if(departureTime == "10:00 AM" && startCity == "Greensboro, NC") {
                                selectedSeats111 = firstClassTicket.ReserveTicket(seatSelection, selectedSeats111);
                            } else if(departureTime == "10:00 AM" && startCity == "Queens, NY") {
                                selectedSeats222 = firstClassTicket.ReserveTicket(seatSelection, selectedSeats222);
                            } else if(departureTime == "2:00 PM" && startCity == "Greensboro, NC") {
                                selectedSeats333 = firstClassTicket.ReserveTicket(seatSelection, selectedSeats333);
                            } else if(departureTime == "2:00 PM" && startCity == "Queens, NY") {
                                selectedSeats444 = firstClassTicket.ReserveTicket(seatSelection, selectedSeats444);
                            }
                        }
                    }
                } else { //show error
                   formError = true;
               }
               
           if(formError) { //error to be shown
            Stage errorStage = new Stage();
            Label errorMsg = new Label("There was an error with your input data. Please close this page and try again."); 
            final Button newButton = new Button ("Close Page");

            Scene scene = new Scene(new Group(), 650, 400); 
            GridPane grid = new GridPane();
            grid.setVgap(10);
            grid.setHgap(10);
            grid.setPadding(new Insets(5, 5, 5, 5));
            grid.add(errorMsg, 0, 1);
            grid.add(newButton, 0, 2);   
            Group root = (Group)scene.getRoot();
            root.getChildren().add(grid);
            errorStage.setScene(scene);
            errorStage.show();

            newButton.setOnAction(new EventHandler<ActionEvent>(){ //when the user presses the close page button, close the stage
                @Override public void handle(ActionEvent e) {
                    errorStage.close();
                    formError = false; //don't show the error anymore
                }
            });
           }
            
                /*clear all selections and input in the form, set back to initial state*/
               destination.getSelectionModel().clearSelection();
               flight.getSelectionModel().clearSelection();
               seatClass.getSelectionModel().clearSelection();
               seat.getSelectionModel().clearSelection();
               availableSeats.getSelectionModel().clearSelection();
               snacks.getSelectionModel().clearSelection();
               wine.getSelectionModel().clearSelection();
               firstName.clear();
               lastName.clear();
               seat.setDisable(true); 
            }
        });
    }    
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
