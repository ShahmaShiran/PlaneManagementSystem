import java.util.Scanner;

public class w2051699_PlaneManagement {

    static final double[] ticketPrice = {200, 200, 200, 200, 150, 150, 150, 150, 150, 180, 180, 180, 180, 180};
    static final int[][] seats = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    static Ticket[] rowATickets = new Ticket[14];
    static Ticket[] rowBTickets = new Ticket[12];
    static Ticket[] rowCTickets = new Ticket[12];
    static Ticket[] rowDTickets = new Ticket[14];

    static Ticket[][] tickets = {rowATickets, rowBTickets, rowCTickets, rowDTickets};
    static Scanner input = new Scanner(System.in);
    static double totalPrice = 0;
    static char rowLetter;
    static int seatNumber;

    public static void main(String[] args) {

        System.out.println("Welcome to the Plane Management");
        int userOption;
        while (true) { //loop until the user quits the program
            System.out.println("****************************************");
            System.out.println("*             MENU OPTIONS             *");
            System.out.println("****************************************");
            String userMenu = "1) Buy a seat\n2) Cancel a seat\n3) Find first available seat\n4) Show seating plan\n5) Print ticket information and total sales\n6) Search ticket\n0) Quit";
            System.out.println(userMenu);
            System.out.println("****************************************");
            try {
                String option;
                System.out.println("Please enter an option: ");
                option = input.nextLine();
                if(option.length() != 1 || (option.charAt(0) < '0' && option.charAt(0) > '6')) {  //checks if there is more than 1 letter entered or if it's an alphabet
                    System.out.println("Invalid option entered. Enter a valid option between 1 - 6.\n");
                    continue;
                }
                userOption = Integer.parseInt(option);

                switch (userOption) {
                    case 1:
                        System.out.println("You have selected the option to buy a seat\n");
                        buy_seat();
                        break;
                    case 2:
                        System.out.println("You have selected the option to cancel a seat\n");
                        cancel_seat();
                        break;
                    case 3:
                        first_seat_available();
                        break;
                    case 4:
                        show_seating_plan();
                        break;
                    case 5:
                        print_tickets_info();
                        break;
                    case 6:
                        System.out.println("You have selected the option to search a seat");
                        search_ticket();

                        break;
                    case 0:
                        System.out.println("Thank you,\nYou have quited the system.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid option entered. Enter a valid option between 1 - 6.\n");
                        break;
                }

                if(userOption == ' '){
                    System.out.println("You haven't entered an option.");
                }
            } catch(NumberFormatException exception) {
            System.out.println("Invalid input. Enter a valid integer option between 1 - 6.\n");
            }
        }
    }

    //OPTION 1 - BUY A SEAT
    /*
    * A method for the process of booking a seat.
    * Asks user for a valid row letter and seat number and books the seat if it's available
    * Prompts for the user details if seat is available
    * If it is not, prints that the seat is not available.
    * Saves the ticket and person details in a file
    * Adds the ticket and user details to the ticket array
    */
    private static void buy_seat() {
        Scanner scanner = new Scanner(System.in);
        rowLetter = getRowLetter();
        seatNumber = getSeatNumber(rowLetter);
        int[] row = seats[rowLetter - 'A'];  //gets the specific row array from the rows of seats
        Ticket[] ticketRow = getTicketRow(rowLetter);  //gets the specific array of tickets from the array of tickets

        if (row[seatNumber - 1] == 0) {
            row[seatNumber - 1] = 1;
            System.out.println("Seat is available. Enter your details");
            double ticketPrice = getTicketPrice(seatNumber);

            String personName;
            do {
                System.out.println("Enter name: ");
                String name = scanner.nextLine();
                personName = validateNames(name);
                if (personName == null) {
                    System.out.println("Invalid name format. Please enter a valid name.");
                }
            }while(personName== null || personName.isEmpty());

            String personSurname;
            do {
                System.out.println("Enter surname: ");
                String surname = scanner.nextLine();
                personSurname = validateNames(surname);
                if (personSurname == null) {
                    System.out.println("Invalid name format. Please enter a valid name.");
                }
            }while(personSurname== null || personSurname.isEmpty());

            String personEmail;
            boolean validEmail;
            // accessed from https://www.geeksforgeeks.org/check-email-address-valid-not-java/
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            do {
                System.out.println("Enter email: ");
                personEmail = input.nextLine();
                validEmail = personEmail.matches(emailRegex);
                if (!validEmail) {
                    System.out.println("Invalid email format. Please enter a valid email.");
                }
            } while (!validEmail);

            System.out.println("Seat is booked successfully.\n");

            Person user = new Person(personName, personSurname, personEmail);  // creates a person object for the buyer
            Ticket ticket = new Ticket(rowLetter, seatNumber, ticketPrice, user);  // creates a ticket object for the booked seat

            ticket.save(rowLetter, seatNumber);
            ticketRow[seatNumber - 1] = ticket; //saves the ticket in the ticket array

        } else {
            System.out.println("Seat is already booked.");
        }
    }

    //OPTION 2 - CANCEL A SEAT
    /*
     * Method for cancelling a booked seat.
     * Asks the user for a valid row letter and seat number, cancels the seat if its booked or informs the user that its available
     */

    private static void cancel_seat() {
        rowLetter = getRowLetter();
        seatNumber = getSeatNumber(rowLetter);
        int[] row = seats[rowLetter - 'A'];
        Ticket[] ticketRow = getTicketRow(rowLetter);

        if (row[seatNumber - 1] == 1) {
            row[seatNumber - 1] = 0;
            //deletes the ticket information from the ticket array
            ticketRow[seatNumber-1] = null;
            Ticket ticket = new Ticket(); //creates a new ticket instance
            ticket.delete(rowLetter, seatNumber); // deletes the ticket file
            System.out.println("Seat is cancelled successfully\n");
            totalPrice -= getTicketPrice(seatNumber); // deducts the price of cancelled seat from the total price
        } else {
            System.out.println("Seat is already available.");
        }
    }

    //OPTION 3 - GETS THE FIRST AVAILABLE SEAT
    /*
    * A method to find the first available seat
    * Iterates through the 2D array named seats
    * If a seat is available prints the row letter and seat number of the first available seat
    */
    private static void first_seat_available() {

        rowLetter = ' ';
        seatNumber = 0;
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                int seat = seats[i][j];

                if (seat == 0) { // Checks if the seat is available
                    if (i == 0) {
                        rowLetter = 'A';
                    } else if (i == 1) {
                        rowLetter = 'B';
                    } else if (i == 2) {
                        rowLetter = 'C';
                    } else {
                        rowLetter = 'D';
                    }
                    seatNumber = j + 1; //Calculates the seat number
                    break; //Breaks out of inner loop
                }
            }
            if (seatNumber != 0) { // If a seat was found, breaks out of the outer loop
                break;
            }
        }
        if (seatNumber != 0) { //If the seat is found prints its details
            System.out.println("The first available seat number is: " + rowLetter + seatNumber+"\n");
        } else {
            System.out.println("No available seats found.");
        }
    }

    //OPTION 4 - SHOWS THE SEATING PLAN
    /*
    * A method to print the seating plan
    * Iterates through the 2D array named "seats".
    * Displays 'O' for available seats and 'X' for unavailable seats.
    */
    private static void show_seating_plan() {
        for (int[] row : seats) {
            for (int seat : row) {
                if (seat == 0) {
                    System.out.print("O ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }

    //OPTION 5 - PRINTS ALL BOOKED TICKET INFORMATION AND TOTAL PRICE OF BOOKED TICKETS
    /*
    * A method that checks if the ticket array is not null by iterating through it and then prints the information of all the booked tickets and buyer.
    * It also prints the total price of all the sold tickets.
    */

    private static void print_tickets_info() {
        for(Ticket[] ticketRow: tickets){
            for(Ticket userTicket : ticketRow){
                if (userTicket != null){
                    userTicket.printTicketInformation();
                }
            }
        }
        System.out.println("Total price of tickets: " + totalPrice+"\n");
    }

    //OPTION 6 - SEARCH FOR A TICKET
    /*
    * A method to search if a seat is available depending on the user's input.
    * If the seat is booked, it prints all the information of the ticket and buyer of the seat booked.
    * If it is not, prints that its available.
    */
    private static void search_ticket() {
        rowLetter = getRowLetter();
        seatNumber = getSeatNumber(rowLetter);
        Ticket[] ticketRow = getTicketRow(rowLetter);
        int[] rowArray = seats[rowLetter - 'A'];
        if(rowArray[seatNumber-1] == 1){
            System.out.println("Seat is booked already\n");
            ticketRow[seatNumber-1].printTicketInformation();
        }else{
            System.out.println("This seat is available");
        }
    }

    /*
    * A method to get a valid row letter from the user
    * Prompts user to enter a row letter.
    * returns the uppercase of the letter.
    * Re-prompts until the letter is valid.
    */
    private static char getRowLetter() {
        Scanner userInputValue = new Scanner(System.in);
        char rowIndex = ' ';

        do {
            System.out.println("Enter a row letter (A, B, C or D): ");
            //Converts the user input to uppercase.
            String userInput = userInputValue.nextLine().toUpperCase();
            if(userInput.isEmpty()){
                System.out.println("You haven't entered a letter.");
                continue;
            }
            if(userInput.length() != 1 || !Character.isLetter(userInput.charAt(0))){  //checks if there is more than 1 letter entered or if it's an alphabet
                System.out.println("Invalid input entered.");
                continue;
            }

            rowIndex = userInput.charAt(0);
            if (rowIndex < 'A' || rowIndex > 'D') {
                System.out.println("Invalid letter entered.");
            }
        }while(rowIndex < 'A' || rowIndex > 'D');
        //userInputValue.close();
        return rowIndex;
    }

    /*
    * A method to get a valid seat number from the user
    * Prompts until a valid seat is entered
    * returns the seat number
     */
    private static int getSeatNumber(char rowLetter) {
        int seatNum = -1;
        int numOfSeat = (rowLetter == 'A' || rowLetter == 'D') ? 14 : 12;  //ternary
        do {
            try {
                System.out.println("Enter a seat number (1 - " + numOfSeat + "): ");
                String userInput = input.nextLine();
                if(userInput.isEmpty()){
                    System.out.println("You haven't entered a number.");
                    continue;
                }
                 if(userInput.length()>3){
                     System.out.println("Invalid input entered.");
                     continue;
                 }
                seatNum = Integer.parseInt(userInput);
                if (seatNum < 1 || seatNum > numOfSeat) {
                    System.out.println("Seat number entered is out of range.");
                }
            } catch (NumberFormatException exception) {
                System.out.println("Invalid input entered.");
                seatNum = -1;
            }
        } while (seatNum < 1 || seatNum > numOfSeat);
        return seatNum;
    }

    /*
    * A method to return the price of a seat depending on the seat number.
    * Called when a user buys a seat to add the ticket price
    * Called when a user cancels a seat to deduct the ticket price
    * Calculates the total price of all booked tickets.
    */
    private static double getTicketPrice(int seatNumber){
        double price = ticketPrice[seatNumber-1];
        totalPrice += price; //adds all the ticket prices
        return price;
    }

    /*
    * A method to get the row of the ticket array to: save, delete or display the ticket and buyer information depending on the option the user selects.
     */
    private static Ticket[] getTicketRow(char rowLetter){
        if(rowLetter == 'A') {
           return rowATickets;
        }else if (rowLetter == 'B') {
            return rowBTickets;
        }else if(rowLetter == 'C') {
            return rowCTickets;
        }else{
            return rowDTickets;
        }
    }

    private static String validateNames(String name){
        boolean isValidName;
        // accessed from https://stackoverflow.com/questions/43935255/regular-expression-for-name-with-spaces-allowed-in-between-the-text-and-avoid-sp
        String nameRegex = "^[a-zA-Z]+(?:[\\s.]+[a-zA-Z]+)*$";
        do {
            isValidName = name.matches(nameRegex);
            if (!isValidName) {
                return null;
            }
        } while (!isValidName);
        return name;
    }
}
