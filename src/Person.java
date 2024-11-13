public class Person {
    private String personName;
    private String personSurname;
    private String personEmail;

    /*
    * A constructor that takes 3 parameters (name, surname and email all of datatype String)
    * To save the person information
    */
    public Person(String personName, String personSurname, String personEmail){
        this.personName = personName;
        this.personSurname = personSurname;
        this.personEmail = personEmail;
    }

    public String getPersonName(){
        return personName;
    }

    public void setPersonName(String personName){
        this.personName = personName;
    }

    public String getPersonSurname(){
        return personSurname;
    }

    public void setPersonSurname(String personSurname){
        this.personSurname = personSurname;
    }

    public String getPersonEmail(){
        return personEmail;
    }
    public void setPersonEmail(String personEmail){
        this.personEmail = personEmail;
    }

    /*
    * Method to print person information.
    * Called when ticket information is called to printed
     */
    public void printPersonInformation() {
        System.out.println("Name: " + personName);
        System.out.println("Surname: " + personSurname);
        System.out.println("Email: " + personEmail);
    }
}
