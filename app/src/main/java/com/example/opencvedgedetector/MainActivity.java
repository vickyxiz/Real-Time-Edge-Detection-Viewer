package com.example.opencvedgedetector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Button;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    // For FPS Counter
    int frameCount = 0;
    long startTime = 0;
    private static final String TAG = "MainActivity";
    private CameraBridgeViewBase cameraBridgeViewBase;
    private boolean applyFilter = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }

        cameraBridgeViewBase = findViewById(R.id.camera_view);
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);

        Button toggleButton = findViewById(R.id.toggle_button);
        toggleButton.setOnClickListener(v -> {
            applyFilter = !applyFilter;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The modern OpenCV 4 initialization
        if (OpenCVLoader.initDebug()) {
            Log.d(TAG, "OpenCV is configured successfully");
            cameraBridgeViewBase.enableView();
        } else {
            Log.d(TAG, "OpenCV did not load");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cameraBridgeViewBase != null) {
            cameraBridgeViewBase.disableView();
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {}

    @Override
    public void onCameraViewStopped() {}

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        // FPS Counter logic starts
        if (startTime == 0) {
            startTime = System.currentTimeMillis();
        }
        frameCount++;
        long currentTime = System.currentTimeMillis();
        if (currentTime - startTime > 1000) {
            Log.d(TAG, "FPS: " + frameCount);
            startTime = 0;
            frameCount = 0;
        }
        // FPS Counter logic ends

        Mat frame = inputFrame.rgba();
        processFrame(frame.getNativeObjAddr(), applyFilter); // Pass the boolean here
        return frame;
    }

    public native void processFrame(long matAddr, boolean applyFilter);

    static {
        System.loadLibrary("native-lib");
    }
}