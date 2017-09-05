package nz.org.winters.android.matrixmethodcompare;

import android.opengl.Matrix;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DoubleMatrixUnitTest {
  @Test
  public void test_float() throws Exception {
    float a[] = { 2,2,2,2, 2,2,2,2, 2,2,2,2, 2,2,2,2 };
    float b[] = { 5,5,5,5, 5,5,5,5, 5,5,5,5, 5,5,5,5 };
    float r[] = { 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0 };

    Matrix.multiplyMM(r,0,a,0,b,0);
    for(int i = 0; i < 1000000; i++){
      Matrix.multiplyMM(r,0,a,0,r,0);
    }

  }
}