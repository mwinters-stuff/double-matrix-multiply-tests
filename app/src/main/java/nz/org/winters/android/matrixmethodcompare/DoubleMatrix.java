package nz.org.winters.android.matrixmethodcompare;

/**
 * Created by mathew on 9/5/17.
 * In project MatrixMethodCompare
 */

public class DoubleMatrix {
  private static int I(int i, int j) {
    return j + 4*i;
  }

  public static void multiplyMM(float[] result, int resultOffset, double[] lhs, int lhsOffset, double[] rhs, int rhsOffset) {
    for (int i=0 ; i<4 ; i++) {
      double rhs_i0 = rhs[ rhsOffset + I(i,0) ];
      double ri0 = lhs[ lhsOffset +I(0,0) ] * rhs_i0;
      double ri1 = lhs[ lhsOffset +I(0,1) ] * rhs_i0;
      double ri2 = lhs[ lhsOffset +I(0,2) ] * rhs_i0;
      double ri3 = lhs[ lhsOffset +I(0,3) ] * rhs_i0;
      for (int j=1 ; j<4 ; j++) {
        double rhs_ij = rhs[ rhsOffset + I(i,j) ];
        ri0 += lhs[ lhsOffset +I(j,0) ] * rhs_ij;
        ri1 += lhs[ lhsOffset +I(j,1) ] * rhs_ij;
        ri2 += lhs[ lhsOffset +I(j,2) ] * rhs_ij;
        ri3 += lhs[ lhsOffset +I(j,3) ] * rhs_ij;
      }
      result[ resultOffset + I(i,0) ] = (float) ri0;
      result[ resultOffset + I(i,1) ] = (float) ri1;
      result[ resultOffset + I(i,2) ] = (float) ri2;
      result[ resultOffset + I(i,3) ] = (float) ri3;
    }
  }

  public static native void multiplyMMNative(float[] result, int resultOffset, double[] lhs, int lhsOffset, double[] rhs, int rhsOffset);
  public static native void multiplyMMNativeDouble(double[] result, int resultOffset, double[] lhs, int lhsOffset, double[] rhs, int rhsOffset);
}
