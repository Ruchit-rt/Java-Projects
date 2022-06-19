package museumvisit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Museum {

  private final Entrance entrance;
  private final Exit exit;
  private final Set<MuseumSite> sites;

  public Museum(Entrance entrance, Exit exit, Set<MuseumSite> sites) {
    this.entrance = entrance;
    this.exit = exit;
    this.sites = sites;
  }

  public static void main(String[] args) {
    final int numberOfVisitors = 100; // solution works with any number of visitors
    final Museum museum = buildLoopyMuseum(); // buildSimpleMuseum();
    final List<Thread> visitors = new ArrayList<>();

    // create the threads for the visitors and get them moving
    for (int i = 0; i < numberOfVisitors; i++) {
      Visitor v = new Visitor("V" + i, museum.entrance);
      visitors.add(new Thread(v));
    }
    visitors.stream().forEach(t -> t.start());

    // wait for them to complete their visit
    visitors.stream().forEach(t -> {
      try {
        t.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    // Checking no one is left behind
    if (museum.getExit().getOccupancy() == numberOfVisitors) {
      System.out.println("\nAll the visitors reached the exit\n");
    } else {
      System.out.println(
          "\n"
              + (numberOfVisitors - museum.getExit().getOccupancy())
              + " visitors did not reach the exit. Where are they?\n");
    }

    System.out.println("Occupancy status for each room (should all be zero, but the exit site):");
    museum
        .getSites()
        .forEach(
            s -> {
              System.out.println("Site " + s.getName() + " final occupancy: " + s.getOccupancy());
            });
  }

  public static Museum buildSimpleMuseum() {
    Set<MuseumSite> sites = new HashSet<>();
    Entrance entrance = new Entrance();
    MuseumSite room = new ExhibitionRoom("Exhibition Room", 10);
    Exit exit = new Exit();
    new Turnstile(entrance, room);
    new Turnstile(room, exit);
    return new Museum(entrance, exit, sites);
  }

  public static Museum buildLoopyMuseum() {
    final Set<MuseumSite> sites = new HashSet<>();
    Entrance entrance = new Entrance();
    MuseumSite venomRoom = new ExhibitionRoom("Venom KillerAndCure Room", 10);
    MuseumSite whalesRoom = new ExhibitionRoom("Whales Exhibition Room", 10);
    final Exit exit = new Exit();
    new Turnstile(entrance, venomRoom);
    new Turnstile(venomRoom, whalesRoom);
    new Turnstile(whalesRoom, venomRoom);
    new Turnstile(venomRoom, exit);
    return new Museum(entrance, exit, sites);
  }

  public Entrance getEntrance() {
    return entrance;
  }

  public Exit getExit() {
    return exit;
  }

  public Set<MuseumSite> getSites() {
    return sites;
  }
}
