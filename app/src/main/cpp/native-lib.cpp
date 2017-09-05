#include <jni.h>
#include <string>


extern "C"
JNIEXPORT jstring

JNICALL
Java_nz_org_winters_android_matrixmethodcompare_MainActivity_stringFromJNI(
    JNIEnv *env,
    jobject /* this */) {
  std::string hello = "Hello from C++";
  return env->NewStringUTF(hello.c_str());
}

class FloatArrayGetter {
public:
    static void* Get(JNIEnv* _env, jfloatArray array, jboolean* is_copy) {
      return _env->GetFloatArrayElements(array, is_copy);
    }
};
class DoubleArrayGetter {
public:
    static void* Get(JNIEnv* _env, jdoubleArray array, jboolean* is_copy) {
      return _env->GetDoubleArrayElements(array, is_copy);
    }
};

class FloatArrayReleaser {
public:
    static void Release(JNIEnv* _env, jfloatArray array, jfloat* data, jint mode) {
      _env->ReleaseFloatArrayElements(array, data, mode);
    }
};
class DoubleArrayReleaser {
public:
    static void Release(JNIEnv* _env, jdoubleArray array, jdouble* data, jint mode) {
      _env->ReleaseDoubleArrayElements(array, data, mode);
    }
};

template<class JArray, class T, class ArrayGetter, class ArrayReleaser>
class ArrayHelper {
public:
    ArrayHelper(JNIEnv* env, JArray ref, jint offset, jint minSize) {
      mEnv = env;
      mRef = ref;
      mOffset = offset;
      mMinSize = minSize;
      mBase = 0;
      mReleaseParam = JNI_ABORT;
    }
    ~ArrayHelper() {
      if (mBase) {
        ArrayReleaser::Release(mEnv, mRef, mBase, mReleaseParam);
      }
    }
    // We seperate the bounds check from the initialization because we want to
    // be able to bounds-check multiple arrays, and we can't throw an exception
    // after we've called GetPrimitiveArrayCritical.
    // Return true if the bounds check succeeded
    // Else instruct the runtime to throw an exception
    bool check() {
      if ( ! mRef) {
//        doThrowIAE(mEnv, "array == null");
        return false;
      }
      if ( mOffset < 0) {
//        doThrowIAE(mEnv, "offset < 0");
        return false;
      }
      mLength = mEnv->GetArrayLength(mRef) - mOffset;
      if (mLength < mMinSize ) {
//        doThrowIAE(mEnv, "length - offset < n");
        return false;
      }
      return true;
    }
    // Bind the array.
    void bind() {
      mBase = (T*) ArrayGetter::Get(mEnv, mRef, (jboolean *) 0);
      mData = mBase + mOffset;
    }
    void commitChanges() {
      mReleaseParam = 0;
    }
    T* mData;
    int mLength;
private:
    T* mBase;
    JNIEnv* mEnv;
    JArray mRef;
    jint mOffset;
    jint mMinSize;
    int mReleaseParam;
};




#define I(_i, _j) ((_j)+ 4*(_i))
typedef ArrayHelper<jfloatArray, float, FloatArrayGetter, FloatArrayReleaser> FloatArrayHelper;
typedef ArrayHelper<jdoubleArray , double, DoubleArrayGetter, DoubleArrayReleaser> DoubleArrayHelper;

static
void multiplyMM(float* r, const double* lhs, const double* rhs)
{
  for (int i=0 ; i<4 ; i++) {
    const double rhs_i0 = rhs[ I(i,0) ];
    double ri0 = lhs[ I(0,0) ] * rhs_i0;
    double ri1 = lhs[ I(0,1) ] * rhs_i0;
    double ri2 = lhs[ I(0,2) ] * rhs_i0;
    double ri3 = lhs[ I(0,3) ] * rhs_i0;
    for (int j=1 ; j<4 ; j++) {
      const double rhs_ij = rhs[ I(i,j) ];
      ri0 += lhs[ I(j,0) ] * rhs_ij;
      ri1 += lhs[ I(j,1) ] * rhs_ij;
      ri2 += lhs[ I(j,2) ] * rhs_ij;
      ri3 += lhs[ I(j,3) ] * rhs_ij;
    }
    r[ I(i,0) ] = (float)ri0;
    r[ I(i,1) ] = (float)ri1;
    r[ I(i,2) ] = (float)ri2;
    r[ I(i,3) ] = (float)ri3;
  }
}

extern "C"
JNIEXPORT void JNICALL
Java_nz_org_winters_android_matrixmethodcompare_DoubleMatrix_multiplyMMNative
//Java_nz_org_winters_android_matrixmethodcompare_DoubleMatrix_multiplyMMNative___3FI_3DI_3DI
(
    JNIEnv *env,
                                                                              jclass instance,
                                                                              jfloatArray result_ref,
                                                                              jint resultOffset,
                                                                              jdoubleArray lhs_ref,
                                                                              jint lhsOffset,
                                                                              jdoubleArray rhs_ref,
                                                                              jint rhsOffset) {

  FloatArrayHelper resultMat(env, result_ref, resultOffset, 16);
  DoubleArrayHelper lhs(env, lhs_ref, lhsOffset, 16);
  DoubleArrayHelper rhs(env, rhs_ref, rhsOffset, 16);

  bool checkOK = resultMat.check() && lhs.check() && rhs.check();
  if ( !checkOK ) {
    return;
  }
  resultMat.bind();
  lhs.bind();
  rhs.bind();
  multiplyMM(resultMat.mData, lhs.mData, rhs.mData);
  resultMat.commitChanges();
}