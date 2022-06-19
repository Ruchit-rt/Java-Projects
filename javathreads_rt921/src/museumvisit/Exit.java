package museumvisit;

import java.util.ArrayList;
import java.util.List;

public class Exit extends MuseumSite {

  public Exit() {
    super("Exit");
  }

  public List<Turnstile> getExitTurnstiles() {
    return new ArrayList<>();
  }

  @Override
  boolean hasAvailability() {
    return true;
  }
}
