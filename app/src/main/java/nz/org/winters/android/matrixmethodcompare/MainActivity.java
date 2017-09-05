package nz.org.winters.android.matrixmethodcompare;

import android.opengl.Matrix;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
  int iterations = 1000000;

  @ViewById(R.id.textViewJavaDouble)
  TextView textViewJavaDouble;

  @ViewById(R.id.textViewNativeDouble)
  TextView textViewNativeDouble;

  @ViewById(R.id.textViewNativeFloat)
  TextView textViewNativeFloat;

  @ViewById(R.id.editTextIterations)
  EditText editTextIterations;

  // Used to load the 'native-lib' library on application startup.
  static {
    System.loadLibrary("native-lib");
  }

  void updateIterations(){
    iterations = Integer.parseInt(editTextIterations.getText().toString());
  }

  @Click(R.id.buttonNativeFloat)
  void onClickNativeFloat(){
    textViewNativeFloat.setText(R.string.running);
    updateIterations();
    test_float();
  }

  @Click(R.id.buttonJavaDouble)
  void onClickJavaDouble(){
    textViewJavaDouble.setText(R.string.running);

    updateIterations();
    test_double_java();

  }

  @Click(R.id.buttonNativeDouble)
  void onClickNativeDouble(){
    textViewNativeDouble.setText(R.string.running);
    updateIterations();
    test_double_native();

  }

  @UiThread
  void setText(TextView textView, long value){
    Calendar c = GregorianCalendar.getInstance();
    c.setTimeInMillis(value);

    textView.setText(String.format(Locale.getDefault(), "%d m %02d s %03d ms",c.get(Calendar.MINUTE),c.get(Calendar.SECOND), c.get(Calendar.MILLISECOND)));
  }

  @Background
  public void test_float()  {
    float a[] = { 2,2,2,2, 2,2,2,2, 2,2,2,2, 2,2,2,2 };
    float b[] = { 5,5,5,5, 5,5,5,5, 5,5,5,5, 5,5,5,5 };
    float r[] = { 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0 };

    long start = SystemClock.elapsedRealtime();

    for(int i = 0; i < iterations; i++){
      Matrix.multiplyMM(r,0,a,0,b,0);
      for(int y =0; y < r.length; y++){
        b[y] = r[y];
      }
    }

    long duration = SystemClock.elapsedRealtime() - start;
    setText(textViewNativeFloat, duration);
    Log.d("TEST","test_float took " + duration);

  }

  @Background
  public void test_double_java()  {
    double a[] = { 2,2,2,2, 2,2,2,2, 2,2,2,2, 2,2,2,2 };
    double b[] = { 5,5,5,5, 5,5,5,5, 5,5,5,5, 5,5,5,5 };
    float r[] = { 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0 };

    long start = SystemClock.elapsedRealtime();
    for(int i = 0; i < iterations; i++){
      DoubleMatrix.multiplyMM(r,0,a,0,b,0);
      for(int y =0; y < r.length; y++){
        b[y] = r[y];
      }
    }

    long duration = SystemClock.elapsedRealtime() - start;
    setText(textViewJavaDouble, duration);
    Log.d("TEST","test_double_java took " + duration );
  }

  @Background
  public void test_double_native()  {
    double a[] = { 2,2,2,2, 2,2,2,2, 2,2,2,2, 2,2,2,2 };
    double b[] = { 5,5,5,5, 5,5,5,5, 5,5,5,5, 5,5,5,5 };
    float r[] = { 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0 };

    long start = SystemClock.elapsedRealtime();
    for(int i = 0; i < iterations; i++){
      DoubleMatrix.multiplyMMNative(r,0,a,0,b,0);
      for(int y =0; y < r.length; y++){
        b[y] = r[y];
      }
    }

    long duration = SystemClock.elapsedRealtime() - start;
    setText(textViewNativeDouble, duration);
    Log.d("TEST","test_double_native took " + duration);
  }
  /**
   * A native method that is implemented by the 'native-lib' native library,
   * which is packaged with this application.
   */
  public native String stringFromJNI();
}
