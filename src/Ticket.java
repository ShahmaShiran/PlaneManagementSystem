import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private char seatRow;
    private int seatNumber;
    private double ticketPrice;
    private Person person;

    public char getSeatRow(){
        return seatRow;
    }

    public void setSeatRow(char seatRow){
        this.seatRow = seatRow;
    }

    public int getSeatNumber(){
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber){
        this.seatNumber = seatNumber;
    }

    public double getTicketPrice(){
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice){
        this.ticketPrice = ticketPrice;
    }

    public Person getPerson() {
        return person;
    }

    //Setter for object person created from Person class
    public void setPerson (Person person){
        this.person = person;
    }

    /*
    * A Constructor method that takes four parameters:
     (seatRow (char), seatNumber (int), ticketPrice (double) and person (Person instance))
    * Used when booking a seat to store person and ticket information
    */
    public Ticket(char seatRow, int seatNumber, double ticketPrice, Person person) {
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.ticketPrice = ticketPrice;
        this.person = person;
    }

    // Default constructor used to create a ticket instance to delete the file of a cancelled seat
    public Ticket(){

    }

    /*
    * A method to save the ticket and person information in a txt file when a seat is bought
    * Takes parameters rowLetter(char) and seatNumber(int)
    * Called when a user buys a seat (option 1)
     */
    public void save(char seatRow, int seatNumber){
        String fileName = seatRow+Integer.toString(seatNumber)+".txt";
        File ticketFile = new File(fileName);
        try {
            if(!ticketFile.exists()) {
                FileWriter ticketFileWriter = new FileWriter(ticketFile);
                ticketFileWriter.write("Seat Information:- \n");
                ticketFileWriter.write("Seat Row: " + getSeatRow() + "\n");
                ticketFileWriter.write("Seat Number: " + getSeatNumber() + "\n");
                ticketFileWriter.write("Ticket Price: £" + getTicketPrice() + "\n");
                ticketFileWriter.write("Person Information:- \n");
                ticketFileWriter.write("Buyer Name: " + person.getPersonName() + "\n");
                ticketFileWriter.write("Buyer Surname: " + person.getPersonSurname() + "\n");
                ticketFileWriter.write("Buyer Email: " + person.getPersonEmail() + "\n");
                ticketFileWriter.close();
            }else{
                System.out.println("File with "+fileName+ " already exists.");
            }
            //System.out.println("Ticket information saved to " + fileName);
        } catch (IOException exception) {
            System.out.println("An error occurred while saving the ticket information.");
        }
    }

    /*
     * A method to delete the txt file created while booking a seat
     * Takes parameters rowLetter(char) and seatNumber(int)
     * Called when a user cancels a booked ticket (option 2)
     */
    public void delete(char seatRow, int seatNumber) {
        String fileName = seatRow + Integer.toString(seatNumber) + ".txt";
        File ticketFile = new File(fileName);
        if (ticketFile.exists()){
            ticketFile.delete();
        }else{
            System.out.println("File does not exist");
        }
    }

    /*
    * A method that prints ticket and person information when the user selects option 5
     */
    public void printTicketInformation() {
        System.out.println("Ticket Information:");
        System.out.println("Seat: " + getSeatRow()+getSeatNumber());
        System.out.println("Price: £" + getTicketPrice());
        System.out.println("Person Information:");
        person.printPersonInformation(); // to print the person information
        System.out.println();
    }
}
