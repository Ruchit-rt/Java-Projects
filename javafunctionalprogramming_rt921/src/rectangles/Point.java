package rectangles;

public class Point {

  private final int coordX;
  private final int coordY;

  public Point(int x, int y) {
    if (x >= 0 && y >= 0) {

      this.coordX = x;
      this.coordY = y;
    } else {
      throw new IllegalArgumentException("Cannot have negative co-ordinates");
    }
  }

  public Point() {
    this(0, 0);
  }

  public Point(int x) {
    this(x, 0);
  }

  public int getX() {
    return coordX;
  }

  public int getY() {
    return coordY;
  }

  public Point setX(int newX) {
    return new Point(newX, this.coordY);
  }

  public Point setY(int newY) {
    return new Point(this.coordX, newY);
  }

  public boolean isLeftOf(Point other) {
    return this.coordX < other.coordX;
  }

  public boolean isRightOf(Point other) {
    return this.coordX > other.coordX;
  }

  public boolean isAbove(Point other) {
    return this.coordY < other.coordY;
  }

  public boolean isBelow(Point other) {
    return this.coordY > other.coordY;
  }

  public Point add(Point vector) {
    return new Point(coordX + vector.coordX, coordY + vector.coordY);
  }

  public int sumCoords() {
    return getX() + getY();
  }
}
