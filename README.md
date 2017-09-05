# double-matrix-multiply-tests
Testing performance of a matrix multipliers on android native vs java.
1,000,000 iterations of a simple test.


## On a nexus 10
test_double_java   48s 240ms
test_double_native  9s 469ms
test_float_native   8s 289ms

## On a X86 Emulator, 
test_double_java       383ms
test_double_native  3s 138ms
test_float_native   3s 111ms

## Motorola Moto G5
test_double_java   1m 40s 660ms
test_double_native    16s 181ms
test_float_native     13s 892ms

test_float_native tests the native OpenGL.Matrix.MatrixMM method.
test_double_native tests a version of the float native method that has been changed to use doubles.
