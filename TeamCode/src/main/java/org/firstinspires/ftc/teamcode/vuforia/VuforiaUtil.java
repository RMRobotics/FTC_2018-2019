package org.firstinspires.ftc.teamcode.vuforia;

/*
Created on 1/18/19 by Neal Machado
Vuforia Util Class
 */

/**********IMPORTS**********/
import android.util.Log;
import com.vuforia.Vuforia;
import java.util.ArrayList;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.R;

import java.util.List;

public class VuforiaUtil {

    /**********INSTANCE VARIABLES**********/
    public static final float MM_PER_INCH = 25.4f;
    private static final float robotSize = 18 * MM_PER_INCH;
    private static final float MM_FTC_FIELD_WIDTH = (12*12 - 2) * MM_PER_INCH;

    private VuforiaLocalizer.Parameters params;
    private VuforiaLocalizer vuforia;
    private VuforiaTrackables images;

//    private VuforiaTrackable blueImage;
//    private VuforiaTrackable redImage;
//    private VuforiaTrackable frontImage;
//    private VuforiaTrackable backImage;

    private OpenGLMatrix phoneOnRobot;
    private OpenGLMatrix blueImageOnField;
    private OpenGLMatrix redImageOnField;
    private OpenGLMatrix frontImageOnField;
    private OpenGLMatrix backImageOnField;

    //private OpenGLMatrix lastLocation;

    private double tX, tY, tZ, rX, rY, rZ;

    /**********CONSTRUCTORS**********/
    //default Vuforia parameters set to front camera, no visual feedback
    public VuforiaUtil(){
        this(false,  VuforiaLocalizer.CameraDirection.FRONT);
    }

    //constructor with defaulted visual feedback as the axes
    public VuforiaUtil(boolean visualFeedback, VuforiaLocalizer.CameraDirection cameraDirection){
        this(visualFeedback, cameraDirection, VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES);
    }

    //constructor
    public VuforiaUtil(boolean visualFeedback, VuforiaLocalizer.CameraDirection cameraDirection, VuforiaLocalizer.Parameters.CameraMonitorFeedback feedback){
        if(visualFeedback){
            params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
            params.cameraMonitorFeedback = feedback;
        }
        else{
            params = new VuforiaLocalizer.Parameters();
        }

        params.vuforiaLicenseKey = "AW2QuFH/////AAABmWaIPHGonUtBiAByunLrGxcyyeTFYpDBVTYsP/A5yrSSQ7PX+/+pCet8bFzd5AWw983mUAycCFdAz/tNDXFvp5BJeqH2b5ZGPFwi08UznmQ9zrq+k3GiKBUSJj37HaPMGeOuE04icbwblA5FgZEThDkSAUyiUqL+tMPv/zkXNzpVWKJkjObucLS2gdYNljJm4calEVnr9JOLbmbcP0IU3hy53CJtkxFc65LSF7n+CcajbEEB2PVfTCS3JLwCHcSKYkoR/FrHO06YFyESC0f5itieL2hKKleOwqOFwiqpV77u5WlMj4y3UncYn0uiCob7f3uXTR//dCCqPAp9P2y5cowPQ5/G6jKyWmv3B+qyegux";

        vuforia = ClassFactory.getInstance().createVuforia(params);

        images = vuforia.loadTrackablesFromAsset("RoverRuckus");

        images.get(0).setName("BluePerimeter");
        images.get(1).setName("RedPerimeter");
        images.get(2).setName("FrontPerimeter");
        images.get(3).setName("BackPerimeter");

        OpenGLMatrix phoneOnRobot = OpenGLMatrix
                .translation(0, robotSize / 2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 0, 0));

        OpenGLMatrix blueImageOnField = OpenGLMatrix
                .translation(-MM_FTC_FIELD_WIDTH / 2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 0, 90));
        images.get(0).setLocation(blueImageOnField);
        ((VuforiaTrackableDefaultListener)images.get(0).getListener()).setPhoneInformation(phoneOnRobot, params.cameraDirection);

        OpenGLMatrix redImageOnField = OpenGLMatrix
                .translation(MM_FTC_FIELD_WIDTH / 2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 0, 270));
        images.get(1).setLocation(redImageOnField);
        ((VuforiaTrackableDefaultListener)images.get(1).getListener()).setPhoneInformation(phoneOnRobot, params.cameraDirection);

        OpenGLMatrix frontImageOnField = OpenGLMatrix
                .translation(0, -MM_FTC_FIELD_WIDTH / 2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 180, 0));
        images.get(2).setLocation(frontImageOnField);
        ((VuforiaTrackableDefaultListener)images.get(2).getListener()).setPhoneInformation(phoneOnRobot, params.cameraDirection);

        OpenGLMatrix backImageOnField = OpenGLMatrix
                .translation(0, MM_FTC_FIELD_WIDTH / 2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 0, 0));
        images.get(3).setLocation(backImageOnField);
        ((VuforiaTrackableDefaultListener)images.get(3).getListener()).setPhoneInformation(phoneOnRobot, params.cameraDirection);

        tX = 0;
        tY = 0;
        tZ = 0;
        rX = 0;
        rY = 0;
        rZ = 0;
    }

    /**********METHODS**********/
    public double[][] robotInformation(){

        OpenGLMatrix lastLocation; //stores last known location of robot
        //boolean imageFound = false;

        double[][] returnArray = {
                {-1, 0, 0},             //the first number indicates the image the robot is near, if -1 no image is in sight
                {0, 0, 0},              //x, y, and z components of the vector (in inches) relative to the image being detected
                {0, 0, 0},              //x, y, and z rotation relative to the image being detected
                {0, 0, 0},              //x, y, and z coordinates (in inches) of the robot relative to the center of the field (using the FTC coordinate system)
                {0, 0, 0}               //x, y, and z rotations relative to the center of the field
        };

        int counter = 0;

        for(VuforiaTrackable i : images){
            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)(i.getListener())).getPose();

//            returnArray[0][0] = 7;
//            return returnArray;

            //if(pose != null && !imageFound){ //if the image is found

            if(pose != null){

                //imageFound = true;

                //vector with information of the robot relative to the image detected
                VectorF translation = pose.getTranslation(); //finds data about the image
                Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                returnArray[0][0] = counter; //indicates which image is detected
                returnArray[1][0] = round(translation.get(0) / MM_PER_INCH, 2);
                returnArray[1][1] = round(translation.get(1) / MM_PER_INCH, 2);
                returnArray[1][2] = round(translation.get(2) / MM_PER_INCH, 2);
                returnArray[2][0] = round(rot.firstAngle, 2);
                returnArray[2][1] = round(rot.secondAngle, 2);
                returnArray[2][2] = round(rot.thirdAngle, 2);

                OpenGLMatrix robotLocationOnField = ((VuforiaTrackableDefaultListener)(i).getListener()).getUpdatedRobotLocation();
                lastLocation = robotLocationOnField;

//                Log.d("mfyyeet","uuyhyhi");

                if(lastLocation != null){
                    VectorF location = lastLocation.getTranslation();
                    Orientation locationRot = Orientation.getOrientation(lastLocation, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                    returnArray[3][0] = round(location.get(0) / MM_PER_INCH, 2);
                    returnArray[3][1] = round(location.get(1) / MM_PER_INCH, 2);
                    returnArray[3][2] = round(location.get(2) / MM_PER_INCH, 2);
                    returnArray[4][0] = round(-1 * rot.firstAngle, 2);
                    returnArray[4][1] = round(-1 * rot.thirdAngle, 2);
                    returnArray[4][2] = round(-1 * rot.secondAngle, 2);
                }

            }
            counter++;
        }

        return returnArray;

    }


    public static double[][] robotInformation(VuforiaTrackables images){

        OpenGLMatrix lastLocation; //stores last known location of robot
        //boolean imageFound = false;

        double[][] returnArray = {
                {-1, 0, 0},             //the first number indicates the image the robot is near, if -1 no image is in sight
                {0, 0, 0},              //x, y, and z components of the vector (in inches) relative to the image being detected
                {0, 0, 0},              //x, y, and z rotation relative to the image being detected
                {0, 0, 0},              //x, y, and z coordinates (in inches) of the robot relative to the center of the field (using the FTC coordinate system)
                {0, 0, 0}               //x, y, and z rotations relative to the center of the field
        };

        int counter = 0;

        for(VuforiaTrackable i : images){
            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)(i.getListener())).getPose();

//            returnArray[0][0] = 7;
//            return returnArray;

            //if(pose != null && !imageFound){ //if the image is found

            if(pose != null){

                //imageFound = true;

                //vector with information of the robot relative to the image detected
                VectorF translation = pose.getTranslation(); //finds data about the image
                Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                returnArray[0][0] = counter; //indicates which image is detected
                returnArray[1][0] = round(translation.get(0) / MM_PER_INCH, 2);
                returnArray[1][1] = round(translation.get(1) / MM_PER_INCH, 2);
                returnArray[1][2] = round(translation.get(2) / MM_PER_INCH, 2);
                returnArray[2][0] = round(rot.firstAngle, 2);
                returnArray[2][1] = round(rot.secondAngle, 2);
                returnArray[2][2] = round(rot.thirdAngle, 2);

                OpenGLMatrix robotLocationOnField = ((VuforiaTrackableDefaultListener)(i).getListener()).getUpdatedRobotLocation();
                lastLocation = robotLocationOnField;

//                Log.d("mfyyeet","uuyhyhi");

                if(lastLocation != null){
                    VectorF location = lastLocation.getTranslation();
                    Orientation locationRot = Orientation.getOrientation(lastLocation, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                    returnArray[3][0] = round(location.get(0) / MM_PER_INCH, 2);
                    returnArray[3][1] = round(location.get(1) / MM_PER_INCH, 2);
                    returnArray[3][2] = round(location.get(2) / MM_PER_INCH, 2);
                    returnArray[4][0] = round(-1 * rot.firstAngle, 2);
                    returnArray[4][1] = round(-1 * rot.thirdAngle, 2);
                    returnArray[4][2] = round(-1 * rot.secondAngle, 2);
                }

            }
            counter++;
        }

        return returnArray;

    }

//    public void changeAngles(String imageName){
//
//        switch(imageName){
//            case "BluePerimeter":
//                rX = -1 * VuforiaUtil.round(rot.secondAngle, 2);
//                rY = VuforiaUtil.round(rot.firstAngle, 2);
//                rZ = VuforiaUtil.round(VuforiaUtil.to180(-1 * rot.secondAngle + 180), 2);
//                break;
//            case "RedPerimeter":
//                rX = VuforiaUtil.round(rot.secondAngle, 2);
//                rY = -1 * VuforiaUtil.round(rot.firstAngle, 2);
//                rZ = VuforiaUtil.round(VuforiaUtil.to180(-1 * rot.secondAngle), 2);
//                break;
//            case "FrontPerimeter":
//                rX = -1 * VuforiaUtil.round(rot.firstAngle, 2);
//                rY = -1 * VuforiaUtil.round(rot.secondAngle, 2);
//                rZ = VuforiaUtil.round(VuforiaUtil.to180(-1 * rot.secondAngle + 270), 2);
//                break;
//            case "BackPerimeter":
//                rX = VuforiaUtil.round(rot.firstAngle, 2);
//                rY = VuforiaUtil.round(rot.secondAngle, 2);
//                rZ = VuforiaUtil.round(VuforiaUtil.to180(-1 * rot.secondAngle + 90), 2);
//                break;
//            default:
//                rX = 0;
//                rY = 0;
//                rZ = 0;
//        }
//    }


    /**********STATIC UTILITY METHODS**********/
    //gives the general location of the image on the field given a number
    public static String imageLocation(int num){
        switch(num){
            case -1:
                return "No Image In Sight";
            case 0:
                return "BluePerimeter";
            case 1:
                return "RedPerimeter";
            case 2:
                return "FrontPerimeter";
            case 3:
                return "BackPerimeter";
            case 7:
                return "yuh";
            default:
                return "Invalid input";

        }
    }

    //takes a value with angle measurements of 0 to 360 and converts it to -180 to 180
    public static double to180(double angle){
        while(angle > 180) {
            angle -= 360;
        }
        while(angle <= -180){
            angle += 360;
        }
        return angle;
    }

    //rounds a double (num) to decPlaces decimal places
    public static double round(double num, int decPlaces){
        double roundedNum = 0;
        double numMult = num * Math.pow(10, decPlaces);
        numMult = Math.round(numMult);
        roundedNum = numMult / Math.pow(10, decPlaces);
        return roundedNum;
    }

}
