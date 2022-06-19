package uk.ac.imperial.matrixmult;

public class MatrixMultiplier {

  public static Matrix multiplyhelper(Matrix nota, Matrix notb) throws Exception {

    int len = Math.max(nota.getNumRows(), notb.getNumRows());
    double[][] aAR = new double[len][len];
    double[][] bAR = new double[len][len];

    for (int i = 0; i < len; i++) {
      for (int j = 0; j < len; j++) {
        if (i < nota.getNumRows() && j < nota.getNumColumns()) {
          aAR[i][j] = nota.get(i, j);
        } else {
          aAR[i][j] = 0;
        }
        if (i < notb.getNumRows() && j < notb.getNumColumns()) {
          bAR[i][j] = notb.get(i, j);
        } else {
          bAR[i][j] = 0;
        }
      }
    }

    Matrix a = MatrixBuilder.build(aAR);
    Matrix b = MatrixBuilder.build(bAR);

    if (a.getNumColumns() != b.getNumRows()) {
      throw new Exception();
    }
    if (a.getNumRows() == 1 && b.getNumColumns() == 1 && b.getNumRows() == 1) {
      double[][] res = new double[1][1];
      res[0][0] = a.get(0, 0) * b.get(0, 0);
      return MatrixBuilder.build(res);
    }
//    if (a.getNumRows() == 2 && b.getNumColumns() == 2 && b.getNumRows() == 2) {
//      // strassens base case
//
//      double a11 = a.get(0, 0);
//      double a12 = a.get(0, 1);
//      double a21 = a.get(1, 0);
//      double a22 = a.get(1, 1);
//
//      double b11 = a.get(0, 0);
//      double b12 = a.get(0, 1);
//      double b21 = a.get(1, 0);
//      double b22 = a.get(1, 1);
//
//      double x1 = (a11 + a22) * (b11 + b22);
//      double x2 = (a21 + a22) * b11;
//      double x3 = a11 * (b12 - b22);
//      double x4 = a22 * (b21 - b11);
//      double x5 = (a11 + a12) * b22;
//      double x6 = (a21 - a11) * (b11 + b12);
//      double x7 = (a12 - a22) * (b21 + b22);
//
//      double c11 = x1 + x4 + x7 - x5;
//      double c12 = x3 + x5;
//      double c21 = x2 + x4;
//      double c22 = x1 + x3 - x2 + x6;
//
//      double[][] res = new double[2][2];
//      res[0][0] = c11;
//      res[0][1] = c12;
//      res[1][0] = c21;
//      res[1][1] = c22;
//
//      return MatrixBuilder.build(res);
//    }
    int n = len / 2;
    double[][] a11 = getDiv(a, 0, 0);
    double[][] a12 = getDiv(a, 0, n);
    double[][] a21 = getDiv(a, n, 0);
    double[][] a22 = getDiv(a, n, n);

    double[][] b11 = getDiv(b, 0, 0);
    double[][] b12 = getDiv(b, 0, n);
    double[][] b21 = getDiv(b, n, 0);
    double[][] b22 = getDiv(b, n, n);


    Matrix A11 = MatrixBuilder.build(a11);
    Matrix A12 = MatrixBuilder.build(a12);
    Matrix A21 = MatrixBuilder.build(a21);
    Matrix A22 = MatrixBuilder.build(a22);

    Matrix B11 = MatrixBuilder.build(b11);
    Matrix B12 = MatrixBuilder.build(b12);
    Matrix B21 = MatrixBuilder.build(b21);
    Matrix B22 = MatrixBuilder.build(b22);

    Matrix x1 = MatrixMultiplier.multiplyhelper(adder(A11, A22, 1), adder(B11, B22, 1));
    Matrix x2 = MatrixMultiplier.multiplyhelper(adder(A21, A22, 1), B11);
    Matrix x3 = MatrixMultiplier.multiplyhelper(A11, adder(B12, B22, -1));
    Matrix x4 = MatrixMultiplier.multiplyhelper(A22, adder(B21, B11, -1));
    Matrix x5 = MatrixMultiplier.multiplyhelper(adder(A11, A12, 1), B22);
    Matrix x6 = MatrixMultiplier.multiplyhelper(adder(A21, A11, -1), adder(B11, B12, 1));
    Matrix x7 = MatrixMultiplier.multiplyhelper(adder(A12, A22, -1), adder(B21, B22, 1));

    Matrix c11 = adder(adder(adder(x1, x4, 1), x7, 1), x5, -1);
    Matrix c12 = adder(x3, x5, 1);
    Matrix c21 = adder(x2, x4, 1);
    Matrix c22 = adder(adder(adder(x1, x3, 1), x6, 1), x2, -1);

    double[][] res =
        new double[a.getNumRows()][b.getNumColumns()];

    for (int i = 0; i < res.length; i++) {
      for (int j = 0; j < res[0].length; j++) {
        if (i < res.length / 2 && j < res.length / 2) {
          res[i][j] = c11.get(i, j);
        }
        if (i < res.length / 2 && j >= res.length / 2) {
          res[i][j] = c12.get(i, j - res.length / 2);
        }
        if (i >= res.length / 2 && j < res.length / 2) {
          res[i][j] = c21.get(i - res.length / 2, j);
        }
        if (i >= res.length / 2 && j >= res.length / 2) {
          res[i][j] = c22.get(i - res.length / 2, j - res.length / 2);
        }
      }
    }

    return MatrixBuilder.build(res);
  }

  public static Matrix multiply(Matrix a, Matrix b) throws Exception {
    Matrix resMatrix = multiplyhelper(a, b);
    double[][] resClipped = new double[a.getRealRows()][b.getRealCols()];
    for (int i = 0; i < resClipped.length; i++) {
      for (int j = 0; j < resClipped[0].length; j++) {
        resClipped[i][j] = resMatrix.get(i,j);
      }
    }
    return MatrixBuilder.build(resClipped);
  }

  private static double[][] getDiv(Matrix m, int x, int y) {
    double[][] divArray = new double[m.getNumRows() / 2][m.getNumColumns() / 2];
    for (int i = 0; i < divArray.length; i++) {
      for (int j = 0; j < divArray[0].length; j++) {
//        System.out.println("x+i was " + (x+i));
//        System.out.println("y+j was " + (y+j));
        divArray[i][j] = m.get(x + i, y + j);
      }
    }
    return divArray;
  }

  private static Matrix adder(Matrix a, Matrix b, int sign) {
    double[][] res = new double[a.getNumRows()][a.getNumColumns()];
    for (int i = 0; i < res.length; i++) {
      for (int j = 0; j < res[0].length; j++) {
        res[i][j] = a.get(i, j) + sign * b.get(i, j);
      }
    }
    return MatrixBuilder.build(res);
  }

}
