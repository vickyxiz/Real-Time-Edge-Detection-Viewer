#include <jni.h>
#include <opencv2/core.hpp>
#include <opencv2/imgproc.hpp>

extern "C" JNIEXPORT void JNICALL
// IMPORTANT: Make sure this package name matches your project's package name
Java_com_example_opencvedgedetector_MainActivity_processFrame(
        JNIEnv* env,
        jobject,
        jlong matAddr) {
    // Get the matrix from the address
    cv::Mat& frame = *(cv::Mat*)matAddr;
    cv::Mat gray;
    cv::Mat edges;

    // Convert the frame to grayscale for Canny
    cv::cvtColor(frame, gray, cv::COLOR_RGBA2GRAY);

    // Apply Canny Edge Detection
    cv::Canny(gray, edges, 80, 150);

    // Convert the black and white edge map back to a color image to display
    cv::cvtColor(edges, frame, cv::COLOR_GRAY2RGBA);
}