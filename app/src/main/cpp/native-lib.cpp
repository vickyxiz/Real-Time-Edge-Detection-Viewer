#include <jni.h>
#include <opencv2/core.hpp>
#include <opencv2/imgproc.hpp>

extern "C" JNIEXPORT void JNICALL
Java_com_example_opencvedgedetector_MainActivity_processFrame(
        JNIEnv* env,
        jobject,
        jlong matAddr,
        jboolean applyFilter) { // It now accepts the boolean flag

    // Get the reference to the image frame from the memory address
    cv::Mat& frame = *(cv::Mat*)matAddr;

    // Only apply the filter if the flag passed from Java is true
    if (applyFilter) {
        cv::Mat gray;
        cv::Mat edges;

        // Convert the frame to grayscale for Canny
        cv::cvtColor(frame, gray, cv::COLOR_RGBA2GRAY);

        // Apply Canny Edge Detection
        cv::Canny(gray, edges, 80, 150);

        // Convert the black and white edge map back to a color image to display
        cv::cvtColor(edges, frame, cv::COLOR_GRAY2RGBA);
    }
    // If applyFilter is false, this function does nothing,
    // and the original, unmodified frame is returned to be displayed.
}