package newbornairline;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Gary Allen
 */
public class BusinessClass extends EconomyClass {
String snack;
    public BusinessClass(String passengerName, String startCity, String destination, String seatNumber, String departureTime, String window, String snack){
        super(passengerName, startCity, destination, seatNumber, departureTime, window);
        this.snack = snack;
    }
    
    @Override void Display(boolean success) {
        if(success){
            final Button button = new Button ("Close Page");
            Stage stage = new Stage();
            Label flightNum = new Label();
            
            /*check the current flight selection and set the flght number*/
            if(this.startCity=="Greensboro, NC" && this.departureTime=="10:00 AM") {
                flightNum = new Label("Flight # : 111");
            } else if(this.startCity=="Queens, NY" && this.departureTime=="10:00 AM") {
                flightNum = new Label("Flight # : 222");
            }else if(this.startCity=="Greensboro, NC" && this.departureTime=="2:00 PM") {
                flightNum = new Label("Flight # : 333");
            } else if(this.startCity=="Queens, NY" && this.departureTime=="2:00 PM") {
                flightNum = new Label("Flight # : 444");
            }
            
            /*modules to show information about user's ticket reservation*/
            Label ticketReserved = new Label("Your ticket reservation has been reserved. The details are as follows:"); 
            Label name = new Label("Passenger Name: " + this.passengerName);
            Label itinerary = new Label("Itinerary: " + this.startCity + " to " + this.destination);
            Label departure = new Label("Departure Date: " + this.departureTime + ", 11/17");
            Label seat = new Label ("Seat: " + this.seatNumber); 
            Label cost = new Label("Total Cost: $100.99");

            Scene scene = new Scene(new Group(), 650, 400); 
            GridPane grid = new GridPane();
            grid.setVgap(10);
            grid.setHgap(10);
            grid.setPadding(new Insets(5, 5, 5, 5));
            grid.add(ticketReserved, 0, 0);
            grid.add(name, 0, 1);
            grid.add(itinerary, 0, 2);
            grid.add(flightNum, 0, 3);
            grid.add(departure, 0, 4);
            grid.add(seat, 0, 5);
            grid.add(cost,0,6);
            grid.add(button, 0, 7);    

            Group root = (Group)scene.getRoot();
            root.getChildren().add(grid);
            stage.setScene(scene);
            stage.show(); //show the user their ticket reservation

            button.setOnAction(new EventHandler<ActionEvent>(){ //when the user presses the close page button, remove the stage
                @Override public void handle(ActionEvent e) {
                    stage.close();
                }
            });
        } else {
            final Button button = new Button ("Close Page");
            Stage stage = new Stage();

            /*scene to show that the currently selected seat is already taken*/
            Scene scene = new Scene(new Group(), 650, 400); 
            GridPane grid = new GridPane();
            Label ticketReserved = new Label("The seat you chose " + this.seatNumber + " is taken. Please select another seat."); 
            grid.setVgap(10);
            grid.setHgap(10);
            grid.setPadding(new Insets(5, 5, 5, 5));
            grid.add(ticketReserved, 0, 0);
            grid.add(button, 0, 1);  

            Group root = (Group)scene.getRoot();
            root.getChildren().add(grid);
            stage.setScene(scene);
            stage.show();

            button.setOnAction(new EventHandler<ActionEvent>(){ //close the stage
                @Override public void handle(ActionEvent e) {
                    stage.close();
                }
            });
        }
    }
    
    @Override String[] ReserveTicket(String seat, String[] unavailableSeats){
        String[] tempArray = new String[unavailableSeats.length + 1]; //increase unavailable seats by one
        if(unavailableSeats.length == 0){ //if there are no values create a new temp array with one value and the seat
                this.Display(true); //show the ticket reservation is succesful
                tempArray[0] = seat;
                return tempArray;
        } 
        
        for(int i = 0; i < unavailableSeats.length; i++) { //loop through current unavaiable seats and confirm the selected seat is not taken
            tempArray[i] = unavailableSeats[i]; //set all currently unavailable seats to new array
            if(unavailableSeats[i]==seat){ //if seat unavailable show eror and return same unavailable seats array
                this.Display(false);
                return unavailableSeats;
            }
        }
        this.Display(true); //show ticket reservation 
        tempArray[tempArray.length - 1] = seat; //place the selected seat into the unavaialble seats array for future 
        
        return tempArray; //return the new array
    }
}
