package uk.ac.imperial.matrixmult;

public class MatrixStrassen implements Matrix{

  private final double[][] elements;
  private final int realRow;
  private final int realCol;

  public MatrixStrassen(double[][] passed) {
    this.elements = addPadding(passed);
    realRow = passed.length;
    realCol = passed[0].length;
  }

  @Override
  public double get(int row, int column) {
    return elements[row][column];
  }

  @Override
  public void set(int row, int column, double value) {
    if (row >= realRow || column >= realCol){
      throw new IndexOutOfBoundsException();
    }
    elements[row][column] = value;
  }

  @Override
  public int getNumRows() {
    return elements.length;
  }

  @Override
  public int getNumColumns() {
    if (getNumRows() > 0) {
      return elements[0].length;
    }
    return 0;
  }

  @Override
  public int getRealCols() {
    return realCol;
  }

  @Override
  public int getRealRows() {
    return realRow;
  }


  private double[][] addPadding(double[][] passed) {
    int rows = passed.length;
    int cols = passed[0].length;
    if (rows == cols && rows % 2 == 0) {
      return passed;
    }
    if (cols == 1 && rows == 1) {
      return passed;
    }
    int n = 0;
    if (rows >= cols) {
      n = rows;
      while (n % 2 != 0) {
        n++;
      }
    } else {
      n = cols;
      while (n % 2 != 0) {
        n++;
      }
    }
    double[][] elems = new double[n][n];
    for (int i = 0; i < n; i ++) {
      for (int j = 0; j < n; j ++) {
        if (i < rows && j < cols) {
          elems[i][j] = passed[i][j];
        } else {
          elems[i][j] = 0;
        }
      }
    }
    return elems;
  }
}
