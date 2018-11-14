package org.firstinspires.ftc.teamcode;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.*;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 11/13/2018.
 */

public class OpenCVUtils {
    //TODO - Create constructors so the class can act as a universal camera streaming adapter that can use different sources
    //For now methods in this class are all public static for outside-of-class use


    //Precondition: Working Camera telemetry source
    public static Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        // Take the CvCameraViewFrame and get the RGB Matrix
        Mat originalFrame = inputFrame.rgba();
        //Binary frame essentially means having items in a frame differentiated by black and white.
        Mat binaryFrame = processFrame(inputFrame.rgba());

        //Initialize contours and hierarchy for later use
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat(); //Not used tho

        //Search the binary frame and output a list of vectors representing contours - this is represented
        //by a list of type "MatOfPoint"
        Imgproc.findContours(binaryFrame,contours,hierarchy,Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);
        if(contours.size() > 0){ // Check to see if there are any regions of interest(ROI)

            //Use the contours object to determine coords and dimensions of a box surrounding ROI
            Rect box = trackContours(contours);
            //If no SIGNIFICANT(of a certain size) contours are found, there will be no coords/dimensions
            //thus the box object will be 'empty': In this case, no bounding box is drawn.
            if(!box.empty()){
                //Since we have coordinates and dimensions of the box, lets use them to draw the bounding box
                //over top the original frame.
                Imgproc.rectangle(originalFrame,new Point(box.x,box.y),new Point(box.width,box.height),
                        new Scalar(0,255,0),2);
                return originalFrame; // finally return the edited frame
            }
        }
        return originalFrame; // otherwise, just output the unedited original image to the screen
    }

    //Using a list of essentially vectors from a frame, the method uses them to derive
    // the appropriate coordinates and dimensions if the contours meet requirements
    public static Rect trackContours(List<MatOfPoint> contours){
        // of the contours, this is the one we care about
        MatOfPoint cnt = contours.get(0);

        //Get the area inside the complete contour
        Moments M = Imgproc.moments(cnt);
        //Check its area to make sure its of a certain size
        if(M.m00 > 300) {
            //Get coordinate and dimensions
            Rect rect = Imgproc.boundingRect(cnt);
            //Combine the x and width as well as y + height to get final width and height. Return the box
            return new Rect(rect.x, rect.y, rect.width + rect.x, rect.height + rect.y);
        }else{
            //Otherwise just empty Rect object
            return new Rect();
        }
    }


    public static Mat processFrame(Mat frame){
        //Initialize hsvFrame matrix
        Mat hsvFrame = new Mat();
        //Convert from RGB to HSV
        Imgproc.cvtColor(frame,hsvFrame,Imgproc.COLOR_BGR2HSV);


        //Create scalars for the lower and upper boundary values.
        Scalar lowerYellow = new Scalar(0,84,208);
        Scalar upperYellow = new Scalar(53,255,255);
        //Initialize thresholded Frame matrix for later assignment
        Mat threshedFrame = new Mat();
        //Perform Color filtering to create binary image
        Core.inRange(hsvFrame,lowerYellow,upperYellow,threshedFrame);


        //Define matrix for binary frame
        Mat binaryFrame = new Mat();
        //Use kernel for convolutions during morphological operations
        Mat kernel =  new Mat(5,5,CvType.CV_8UC(1),new Scalar(255));
        //Perform morphological operations using the kernal. The specific operation here is known as:
        // An Opening: Erosion followed by Dilation
        Imgproc.morphologyEx(threshedFrame,binaryFrame,2,kernel);
        return binaryFrame; // Return binary frame
    }
}
