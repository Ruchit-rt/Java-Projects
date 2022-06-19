package aeroplane;

public class NonCrew extends Passenger{

  protected final int age;

  public NonCrew(String firstname, String surname, int age) {
    super(firstname, surname);
    assert age >= 0;
    this.age = age;
  }

  @Override
  public boolean isAdult() {
    return age >= 18;
  }

  @Override
  public String toString() {
    return firstname + " " + surname + ", age " + age;
  }
}
