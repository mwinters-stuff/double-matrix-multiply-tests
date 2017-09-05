package nz.org.winters.android.matrixmethodcompare;

import android.content.Context;
import android.opengl.Matrix;
import android.os.Debug;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
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

  public static final int ITERATIONS = 1000000;

  static {
    System.loadLibrary("native-lib");
  }

//  @Before
//  public void before(){
//    Debug.startMethodTracing();
//  }
//
//  @After
//  public void after(){
//    Debug.stopMethodTracing();
//  }
  
  @Test
  public void test_float() throws Exception {
    float a[] = { 2,2,2,2, 2,2,2,2, 2,2,2,2, 2,2,2,2 };
    float b[] = { 5,5,5,5, 5,5,5,5, 5,5,5,5, 5,5,5,5 };
    float r[] = { 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0 };

    long start = SystemClock.elapsedRealtimeNanos();

    for(int i = 0; i < ITERATIONS; i++){
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
    for(int i = 0; i < ITERATIONS; i++){
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
    for(int i = 0; i < ITERATIONS; i++){
      DoubleMatrix.multiplyMMNative(r,0,a,0,b,0);
      for(int y =0; y < r.length; y++){
        b[y] = r[y];
      }
    }

    long duration = SystemClock.elapsedRealtimeNanos() - start;
    Log.d("TEST","test_double_native took " + duration);
  }

  @Test
  public void test_double_native_return_double() throws Exception {
    double a[] = { 2,2,2,2, 2,2,2,2, 2,2,2,2, 2,2,2,2 };
    double b[] = { 5,5,5,5, 5,5,5,5, 5,5,5,5, 5,5,5,5 };
    double r[] = { 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0 };

    long start = SystemClock.elapsedRealtimeNanos();
    for(int i = 0; i < ITERATIONS; i++){
      DoubleMatrix.multiplyMMNativeDouble(r,0,a,0,b,0);
      for(int y =0; y < r.length; y++){
        b[y] = r[y];
      }
    }

    long duration = SystemClock.elapsedRealtimeNanos() - start;
    Log.d("TEST","test_double_native_return_double took " + duration);
  }

}
