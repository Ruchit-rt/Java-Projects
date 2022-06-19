package aeroplane;

public class EconomyClassPassenger extends NonCrew {

  public EconomyClassPassenger(String firstname, String surname, int age) {
    super(firstname, surname, age);
  }

  @Override
  public String toString() {
    return "Economy class passenger: " + super.toString();
  }
}
