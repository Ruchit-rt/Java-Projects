package rectangles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListAlgorithms {

  /**
   * Returns a new list of rectangles by translating (moving) each rectangle according to the given
   * distance vector.
   *
   * @param rectangles The rectangles to be translated
   * @param vector     The distance vector
   * @return The translated rectangles
   */
  public static List<Rectangle> translate(List<Rectangle> rectangles, Point vector) {
    List<Rectangle> translated = new ArrayList<>();
    for (Rectangle rec : rectangles) {
      translated.add(new Rectangle(rec.getTopLeft().add(vector), rec.getWidth(), rec.getHeight()));
    }
    return translated;
  }

  /**
   * Returns a new list of rectangles by scaling each rectangle by a given amount.
   *
   * @param rectangles The rectangles to be scaled
   * @param factor     A non-negative scale factor
   * @return The scaled rectangles
   */
  public static List<Rectangle> scale(List<Rectangle> rectangles, int factor) {
    List<Rectangle> scaled = new ArrayList<>();
    for (Rectangle rec : rectangles) {
      scaled.add(rec.setHeight(rec.getHeight() * factor).setWidth(rec.getWidth() * factor));
    }
    return scaled;
  }

  // a version of scale that takes double enabling downscaling.
  public static List<Rectangle> scale(List<Rectangle> rectangles, double factor) {
    List<Rectangle> scaled = new ArrayList<>();
    for (Rectangle rec : rectangles) {
      scaled.add(rec.setHeight((int) (rec.getHeight() * factor))
          .setWidth((int) (rec.getWidth() * factor)));
    }
    return scaled;
  }


  /**
   * Returns a list containing, in order, the bottom-left point of each input rectangle.
   */
  public static List<Point> getBottomLeftPoints(List<Rectangle> rectangles) {
    List<Point> points = new ArrayList<>();
    for (Rectangle rec : rectangles) {
      points.add(rec.getBottomLeft());
    }
    return points;
  }

  /**
   * Returns a list containing all rectangles that intersect with the given rectangle.
   *
   * @param rectangles A list of rectangles to be checked for intersection
   * @param rectangle  The rectangle against which intersection should be checked
   * @return All rectangles that do intersect with the given rectangle
   */
  public static List<Rectangle> getAllIntersecting(
      List<Rectangle> rectangles, Rectangle rectangle) {
    List<Rectangle> intersecting = new ArrayList<>();
    for (Rectangle rec : rectangles) {
      if (rectangle.intersects(rec)) {
        intersecting.add(rec);
      }
    }
    return intersecting;
  }

  /**
   * Returns a list containing all rectangles with a bigger area than the given rectangle.
   *
   * @param rectangles A list of rectangles whose area is to be checked
   * @param rectangle  The rectangle against which areas are to be compared
   * @return All rectangles that have a larger area than the given rectangle
   */
  public static List<Rectangle> getAllWithBiggerAreaThan(
      List<Rectangle> rectangles, Rectangle rectangle) {
    List<Rectangle> bigger = new ArrayList<>();
    int area = rectangle.area();
    for (Rectangle rec : rectangles) {
      if (rec.area() > area) {
        bigger.add(rec);
      }
    }
    return bigger;
  }

  /**
   * Returns the largest area among the given rectangles.
   */
  public static int findLargestArea(List<Rectangle> rectangles) {
    int area = 0;
    for (Rectangle rec : rectangles) {
      if (rec.area() > area) {
        area = rec.area();
      }
    }
    return area;
  }

  /**
   * Returns the largest height among all the given rectangles.
   */
  public static int findMaxHeight(List<Rectangle> rectangles) {
    int height = 0;
    for (Rectangle rec : rectangles) {
      if (rec.getHeight() > height) {
        height = rec.getHeight();
      }
    }
    return height;
  }

  /**
   * Computes the sum of areas of all the given rectangles.
   */
  public static int getSumOfAreas(List<Rectangle> rectangles) {
    int sum = 0;
    for (Rectangle rec : rectangles) {
      sum += rec.area();
    }
    return sum;
  }

  /**
   * Computes the sum of areas of all rectangles that intersect with the given rectangle.
   *
   * @param rectangles The rectangles whose areas to be considered and summed
   * @param rectangle  The rectangle with which intersection is to be checked
   * @return The sum of areas of all rectangles that do intersect with the given rectangle
   */
  public static int getSumOfAreasOfAllIntersecting(
      List<Rectangle> rectangles, Rectangle rectangle) {
    return getSumOfAreas(getAllIntersecting(rectangles, rectangle));
  }

  /**
   * Returns collection that maps each rectangle to its computed area.
   */
  public static Map<Rectangle, Integer> getAreaMap(List<Rectangle> rectangles) {
    Map<Rectangle, Integer> areaMap = new HashMap<>();
    for (Rectangle rec : rectangles) {
      areaMap.put(rec, rec.area());
    }
    return areaMap;
  }
}
