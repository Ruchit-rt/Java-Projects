package advancedstreams;

import static advancedstreams.IsPalindrome.isPalindrome;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class CubeSupplier implements Supplier<Integer> {

  private Integer counter;

  public CubeSupplier() {
    counter = 1;
  }

  private void increment() {
    counter++;
  }

  @Override
  public Integer get() {
    int cube = counter * counter * counter;
    if (cube < 0) {    // this indicates overflow.
      throw new NoSuchElementException();
    }
    increment();
    return cube;
  }

  public static Stream<Integer> cubeStream() {
    return Stream.generate(new CubeSupplier()::get);
  }

  public static Stream<Integer> boundedCubeStream(int start, int end) {
    int startCube = start * start * start;
    int endCube = end * end * end;
    return cubeStream().dropWhile(cube -> cube < startCube).takeWhile(cube -> cube <= endCube);
  }

  public static Stream<Integer> palindromicCubes(int start, int end) {
    return boundedCubeStream(start, end).filter(cube -> isPalindrome(cube.toString()));
  }
}
