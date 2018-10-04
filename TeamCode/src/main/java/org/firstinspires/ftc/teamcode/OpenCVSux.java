package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;

import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class OpenCVSux {


    private Mat GetCameraImage() {
        Mat mat = null;
        while (mat == null) {
            try {
                VuforiaLocalizer.CloseableFrame frame = vuforia.getFrameQueue().take(); //takes the frame at the head of the queue
                Image rgb = null;
                long numImages = frame.getNumImages();
                for (int i = 0; i < numImages; i++) { //finds a frame that is in color, not grayscale
                    if (frame.getImage(i).getFormat() == PIXEL_FORMAT.RGB888) {
                        rgb = frame.getImage(i);
                        break;
                    }
                }
                if (rgb != null && rgb.getPixels() != null) {
                    ByteBuffer bb = rgb.getPixels();
                    byte[] b = new byte[bb.remaining()];
                    bb.get(b);

                    mat = new Mat(rgb.getBufferHeight(), rgb.getBufferWidth(), CvType.CV_8UC3);
                    mat.put(0, 0, b);

                    frame.close();

                    Imgproc.resize(mat, mat, new Size(852, 480));
                    Core.flip(mat, mat, 1);
                }
            } catch (InterruptedException ex) {
                sleep(10);
            }
        }
        return mat;
    }

    private void DisplayImage(Mat img) {
        // Scale down x2
        Core.flip(img, img, -1);
        mImageMap = Bitmap.createBitmap(img.cols(), img.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img, mImageMap);

        ((FtcRobotControllerActivity) hardwareMap.appContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mImageView.setImageBitmap(mImageMap);
            }
        });
    }

}
