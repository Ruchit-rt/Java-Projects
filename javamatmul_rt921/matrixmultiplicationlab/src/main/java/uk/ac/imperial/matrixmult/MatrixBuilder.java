package uk.ac.imperial.matrixmult;


public class MatrixBuilder {

  public static Matrix build(double[][] source) {
    return new MatrixStrassen(source);
  }

  public static Matrix build(int nRows, int nCols) {
    return new MatrixStrassen(new double[nRows][nCols]);
  }

}
