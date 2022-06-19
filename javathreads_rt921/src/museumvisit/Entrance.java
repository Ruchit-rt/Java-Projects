package museumvisit;

public class Entrance extends MuseumSite {

  public Entrance() {
    super("Entrance");
  }

  @Override
  boolean hasAvailability() {
    return true;
  }

}
