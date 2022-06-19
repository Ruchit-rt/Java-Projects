package aeroplane;

public class CrewMember extends Passenger {

  protected CrewMember(String firstname, String surname) {
    super(firstname, surname);
  }

  @Override
  public boolean isAdult() {
    return true;
  }

  @Override
  public String toString() {
    return "Crew member: " + firstname + " " + surname;
  }

}
