package museumvisit;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Visitor implements Runnable {

  private final String name;
  private MuseumSite currentRoom;

  public Visitor(String name, MuseumSite initialRoom) {
    this.name = name;
    this.currentRoom = initialRoom;
    initialRoom.enter();
  }

  public void run() {
    while (thereAreMoreSitesToVisit()) {
      simulateVisitToCurrentRoom();
      Turnstile turnstile = pickRandomTurnstile();
      MuseumSite destination = turnstile.getDestinationRoom();
      MuseumSite firstSite;
      MuseumSite secondSite;
      Optional<MuseumSite> nextRoom;
      if (currentRoom.hashCode() < destination.hashCode()) {
        firstSite = currentRoom;
        secondSite = destination;
      } else {
        firstSite = destination;
        secondSite = currentRoom;
      }
      synchronized (firstSite) {
        synchronized (secondSite) {
          nextRoom = turnstile.passToNextRoom();
        }
      }

      if (nextRoom.isPresent()) {
        currentRoom = nextRoom.get();
      } else {
        waitSomeTimeBeforeRetrying();
      }

    }
  }


  private boolean thereAreMoreSitesToVisit() {
    return !currentRoom.getExitTurnstiles().isEmpty();
  }

  private Turnstile pickRandomTurnstile() {
    List<Turnstile> exitTurnstiles = currentRoom.getExitTurnstiles();
    assert !exitTurnstiles.isEmpty();

    Collections.shuffle(exitTurnstiles); // Random shuffle of the list of
    // turnstiles
    return exitTurnstiles.stream().findAny().get();
  }

  private void simulateVisitToCurrentRoom() {
    System.out.println("Visitor " + name + ": visiting room: " + currentRoom.getName());
    final int randomVisitTimeInMillis = (int) (Math.random() * 200) + 1;
    // wait between 1  and  200 millis
    try {
      Thread.sleep(randomVisitTimeInMillis);
    } catch (InterruptedException e) {
      System.out.println(name + " is stuck in room - " + currentRoom);
    }
  }

  private void waitSomeTimeBeforeRetrying() {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      System.out.println(name + " is stuck in room - " + currentRoom);
    }
  }

  @Override
  public String toString() {
    return "Visitor " + name;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Visitor) {
      return this.name.equals(((Visitor) obj).name);
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(getClass(), name);
  }
}
