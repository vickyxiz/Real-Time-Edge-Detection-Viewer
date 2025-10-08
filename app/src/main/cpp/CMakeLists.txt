# Sets the minimum version of CMake required.
cmake_minimum_required(VERSION 3.18.1)

# Add the project() command to fix the warning.
project(native-lib)

# Set the path to the OpenCV SDK you imported.
# This path is critical and must be correct.
set(OpenCV_DIR "C:/Users/admin/AndroidStudioProjects/OpenCVEdgeDetector/sdk/native/jni")

# This command finds the OpenCV package using the path set above.
find_package(OpenCV REQUIRED)

# Add your native C++ library to the build.
add_library(
        native-lib
        SHARED
        native-lib.cpp)

# Find the Android logging library.
find_library(
        log-lib
        log)

# Link your library with OpenCV and the log library.
target_link_libraries(
        native-lib
        ${log-lib}
        ${OpenCV_LIBS})