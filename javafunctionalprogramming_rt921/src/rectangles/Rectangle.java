package rectangles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Rectangle {

  private final int x1;
  private final int y1;
  private final int x2;
  private final int y2;

  public Rectangle(Point topLeft, int width, int height) {
    this(topLeft, new Point(topLeft.getX() + width, topLeft.getY() + height));
  }

  public Rectangle(Point p1, Point p2) {
    if (p1.isLeftOf(p2)) {
      x1 = p1.getX();
      x2 = p2.getX();
    } else {
      x1 = p2.getX();
      x2 = p1.getX();
    }
    if (p1.isAbove(p2)) {
      y1 = p1.getY();
      y2 = p2.getY();
    } else {
      y1 = p2.getY();
      y2 = p1.getY();
    }
  }

  public Rectangle(int w, int h) {
    this(new Point(0, 0), new Point(w, h));
  }

  public int getWidth() {
    return x2 - x1;
  }

  public int getHeight() {
    return y2 - y1;
  }

  public Rectangle setWidth(int newWidth) {
    if (newWidth >= 0) {
      return new Rectangle(this.getTopLeft(), newWidth, this.getHeight());
    }
    throw new IllegalArgumentException("Cannot have negative width.");
  }

  public Rectangle setHeight(int newHeight) {
    if (newHeight >= 0) {
      return new Rectangle(this.getTopLeft(), this.getWidth(), newHeight);
    }
    throw new IllegalArgumentException("Cannot have negative height.");
  }


  public Point getTopLeft() {
    return new Point(x1, y1);
  }

  public Point getTopRight() {
    return new Point(x2, y1);
  }

  public Point getBottomLeft() {
    return new Point(x1, y2);
  }

  public Point getBottomRight() {
    return new Point(x2, y2);
  }

  public int area() {
    return this.getHeight() * this.getWidth();
  }

  public boolean contains(Point p) {
    return (x1 <= p.getX() && x2 >= p.getX()) && (y1 <= p.getY() && y2 >= p.getY());
  }

  public boolean onBoundary(Point p) {
    if ((x1 == p.getX() || x2 == p.getX()) && y1 <= p.getY() && y2 >= p.getY()) {
      return true;
    }
    return (y1 == p.getY() || y2 == p.getY()) && x1 <= p.getX() && x2 >= p.getX();
  }

  // considering that intersection refers to common area between rectangles.
  public boolean intersects(Rectangle other) {
    for (int i = x1; i <= x2; i++) {
      for (int j = y1; j <= y2; j++) {
        if (other.contains(new Point(i, j))) {
          return true;
        }
      }
    }
    return false;
  }

  public static List<Point> commonPoints(Rectangle r1, Rectangle r2) {
    List<Point> common = new ArrayList<>();
    for (int i = r1.x1; i <= r1.x2; i++) {
      for (int j = r1.y1; j <= r1.y2; j++) {
        Point point = new Point(i, j);
        if (r2.contains(point)) {
          common.add(point);
        }
      }
    }
    return common;
  }

  public Optional<Rectangle> intersection(Rectangle other) {
    if (intersects(other)) {
      List<Point> common = commonPoints(this, other);
      Point topLeft = common.get(0);
      Point bottomRight = common.get(0);
      for (Point p : common) {
        int sum = p.sumCoords();
        if (sum < topLeft.sumCoords()) {
          topLeft = p;
        }
        if (sum > bottomRight.sumCoords()) {
          bottomRight = p;
        }
      }
      return Optional.of(new Rectangle(topLeft, bottomRight));
    }
    return Optional.empty();
  }

}
