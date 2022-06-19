package rectangles;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamAlgorithms {

  /**
   * Returns a stream of rectangles by translating (moving) each rectangle according to the given
   * distance vector.
   *
   * @param rectangles The rectangles to be translated
   * @param vector     The distance vector
   * @return The translated rectangles
   */
  public static Stream<Rectangle> translate(Stream<Rectangle> rectangles, Point vector) {
    return rectangles.map(rec ->
        new Rectangle(
            rec.getTopLeft().add(vector),
            rec.getWidth(),
            rec.getHeight()));
  }

  /**
   * Returns a stream of rectangles by scaling each rectangle by a given amount.
   *
   * @param rectangles The rectangles to be scaled
   * @param factor     A non-negative scale factor
   * @return The scaled rectangles
   */
  public static Stream<Rectangle> scale(Stream<Rectangle> rectangles, int factor) {
    return rectangles.map(rec ->
        new Rectangle(
            rec.getTopLeft(),
            rec.getWidth() * factor,
            rec.getHeight() * factor));
  }

  //a version of scale that allows downscaling
  public static Stream<Rectangle> scale(Stream<Rectangle> rectangles, double factor) {
    return rectangles.map(rec ->
        new Rectangle(
            rec.getTopLeft(),
            (int) (rec.getWidth() * factor),
            (int) (rec.getHeight() * factor)));
  }

  /**
   * Returns a stream containing, in order, the bottom-left point of each input rectangle.
   */
  public static Stream<Point> getBottomLeftPoints(Stream<Rectangle> rectangles) {
    return rectangles.map(Rectangle::getBottomLeft);
  }

  /**
   * Returns a stream containing all rectangles that intersect with the given rectangle.
   *
   * @param rectangles A stream of rectangles to be checked for intersection
   * @param rectangle  The rectangle against which intersection should be checked
   * @return All rectangles that do intersect with the given rectangle
   */
  public static Stream<Rectangle> getAllIntersecting(
      Stream<Rectangle> rectangles, Rectangle rectangle) {
    return rectangles.filter(rectangle::intersects);
  }

  /**
   * Returns a stream containing all rectangles with a bigger area than the given rectangle.
   *
   * @param rectangles A stream of rectangles whose area is to be checked
   * @param rectangle  The rectangle against which areas are to be compared
   * @return All rectangles that have a larger area than the given rectangle
   */
  public static Stream<Rectangle> getAllWithBiggerAreaThan(
      Stream<Rectangle> rectangles, Rectangle rectangle) {
    int area = rectangle.area();
    return rectangles.filter(rec -> rec.area() > area);
  }

  /**
   * Returns the largest area among the given rectangles.
   */
  public static int findLargestArea(Stream<Rectangle> rectangles) {
    return rectangles.map(Rectangle::area).reduce(0, Integer::max);
  }

  /**
   * Returns the largest height among all the given rectangles.
   */
  public static int findMaxHeight(Stream<Rectangle> rectangles) {
    return rectangles.map(Rectangle::getHeight).reduce(0, Integer::max);
  }

  /**
   * Computes the sum of areas of all the given rectangles.
   */
  public static int getSumOfAreas(Stream<Rectangle> rectangles) {
    return rectangles.map(Rectangle::area).reduce(0, Integer::sum);
  }

  /**
   * Computes the sum of areas of all rectangles that intersect with the given rectangle.
   *
   * @param rectangles The rectangles whose areas to be considered and summed
   * @param rectangle  The rectangle with which intersection is to be checked
   * @return The sum of areas of all rectangles that do intersect with the given rectangle
   */
  public static int getSumOfAreasOfAllIntersecting(
      Stream<Rectangle> rectangles, Rectangle rectangle) {
    return StreamAlgorithms.getSumOfAreas(
        StreamAlgorithms.getAllIntersecting(rectangles, rectangle));
  }

  /**
   * Returns collection that maps each rectangle to its computed area.
   */
  public static Map<Rectangle, Integer> getAreaMap(Stream<Rectangle> rectangles) {
    return rectangles.collect(Collectors.toMap(r -> r, Rectangle::area));
  }

  /**
   * Returns an Optional that contains the common intersection of all rectangles in the stream.
   */
  public static Optional<Rectangle> intersectAll(Stream<Rectangle> rectangles) {
    return rectangles.map(Optional::of).reduce((r1, r2) -> {
      if (r1.isPresent() && r2.isPresent()) {
        return r1.get().intersection(r2.get());
      }
      return Optional.empty();
    }).orElse(Optional.empty());
  }

}
