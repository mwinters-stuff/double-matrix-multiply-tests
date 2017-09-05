package nz.org.winters.android.matrixmethodcompare;

import android.content.Context;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
  static {
    System.loadLibrary("native-lib");
  }
  
  @Test
  public void test_float() throws Exception {
    float a[] = { 2,2,2,2, 2,2,2,2, 2,2,2,2, 2,2,2,2 };
    float b[] = { 5,5,5,5, 5,5,5,5, 5,5,5,5, 5,5,5,5 };
    float r[] = { 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0 };

    long start = SystemClock.elapsedRealtimeNanos();

    for(int i = 0; i < 1000000; i++){
      Matrix.multiplyMM(r,0,a,0,b,0);
      for(int y =0; y < r.length; y++){
        b[y] = r[y];
      }
    }

    long duration = SystemClock.elapsedRealtimeNanos() - start;
    Log.d("TEST","test_float took " + duration);

  }

  @Test
  public void test_double_java() throws Exception {
    double a[] = { 2,2,2,2, 2,2,2,2, 2,2,2,2, 2,2,2,2 };
    double b[] = { 5,5,5,5, 5,5,5,5, 5,5,5,5, 5,5,5,5 };
    float r[] = { 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0 };

    long start = SystemClock.elapsedRealtimeNanos();
    for(int i = 0; i < 1000000; i++){
      DoubleMatrix.multiplyMM(r,0,a,0,b,0);
      for(int y =0; y < r.length; y++){
        b[y] = r[y];
      }
    }

    long duration = SystemClock.elapsedRealtimeNanos() - start;
    Log.d("TEST","test_double_java took " + duration );
  }

  @Test
  public void test_double_native() throws Exception {
    double a[] = { 2,2,2,2, 2,2,2,2, 2,2,2,2, 2,2,2,2 };
    double b[] = { 5,5,5,5, 5,5,5,5, 5,5,5,5, 5,5,5,5 };
    float r[] = { 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0 };

    long start = SystemClock.elapsedRealtimeNanos();
    for(int i = 0; i < 1000000; i++){
      DoubleMatrix.multiplyMMNative(r,0,a,0,b,0);
      for(int y =0; y < r.length; y++){
        b[y] = r[y];
      }
    }

    long duration = SystemClock.elapsedRealtimeNanos() - start;
    Log.d("TEST","test_double_native took " + duration);
  }

}
