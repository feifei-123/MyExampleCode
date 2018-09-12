# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

add_library(hello-lib SHARED src/main/cpp/hello-lib.c)

find_library(log-lib log)

target_link_libraries(hello-lib ${log-lib})