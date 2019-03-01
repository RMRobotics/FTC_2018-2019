package org.firstinspires.ftc.teamcode.vuforia;

/*
Created on 1/16/19 by Neal Machado
Test Vuforia class with actual locations for each image
 */

//Z DISPLACEMENT STILL NEEDS TO BE ACCOUNTED FOR

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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

@Autonomous(name = "VuforiaOpLocations", group = "Vuforia")
public class VuforiaOpLocations extends LinearOpMode {

    float mmPerInch = 25.4f;
    float robotSize = 18 * mmPerInch;
    float mmFTCFieldWidth  = (12*12 - 2) * mmPerInch;

    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT; //uses the back phone camera
        params.vuforiaLicenseKey = "AW2QuFH/////AAABmWaIPHGonUtBiAByunLrGxcyyeTFYpDBVTYsP/A5yrSSQ7PX+/+pCet8bFzd5AWw983mUAycCFdAz/tNDXFvp5BJeqH2b5ZGPFwi08UznmQ9zrq+k3GiKBUSJj37HaPMGeOuE04icbwblA5FgZEThDkSAUyiUqL+tMPv/zkXNzpVWKJkjObucLS2gdYNljJm4calEVnr9JOLbmbcP0IU3hy53CJtkxFc65LSF7n+CcajbEEB2PVfTCS3JLwCHcSKYkoR/FrHO06YFyESC0f5itieL2hKKleOwqOFwiqpV77u5WlMj4y3UncYn0uiCob7f3uXTR//dCCqPAp9P2y5cowPQ5/G6jKyWmv3B+qyegux";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES; //show the xyz axis on the target (can be a teapot, building, axis, or none)

        VuforiaLocalizer vuforia = ClassFactory.getInstance().createVuforia(params); //create the vuforia object with the above params
        VuforiaTrackables images = vuforia.loadTrackablesFromAsset("RoverRuckus"); //access the .xml file with all the images

        //set location for the phone
        OpenGLMatrix phoneOnRobot = OpenGLMatrix
                .translation(0, robotSize / 2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 0, 90, 180));

        //set names for images
        images.get(0).setName("BluePerimeter");
        images.get(1).setName("RedPerimeter");
        images.get(2).setName("FrontPerimeter");
        images.get(3).setName("BackPerimeter");

        //define matricices for the locations of the images
        OpenGLMatrix blueImageOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth / 2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 0, 90));
        images.get(0).setLocation(blueImageOnField);
        ((VuforiaTrackableDefaultListener)(images.get(0)).getListener()).setPhoneInformation(phoneOnRobot, params.cameraDirection);

        OpenGLMatrix redImageOnField = OpenGLMatrix
                .translation(mmFTCFieldWidth / 2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 0, 270));
        images.get(1).setLocation(redImageOnField);
        ((VuforiaTrackableDefaultListener)(images.get(1)).getListener()).setPhoneInformation(phoneOnRobot, params.cameraDirection);

        OpenGLMatrix frontImageOnField = OpenGLMatrix
                .translation(0, -mmFTCFieldWidth / 2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 180, 0));
        images.get(2).setLocation(frontImageOnField);
        ((VuforiaTrackableDefaultListener)(images.get(2)).getListener()).setPhoneInformation(phoneOnRobot, params.cameraDirection);

        OpenGLMatrix backImageOnField = OpenGLMatrix
                .translation(0, mmFTCFieldWidth / 2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 0, 0));
        images.get(3).setLocation(backImageOnField);
        ((VuforiaTrackableDefaultListener)(images.get(3)).getListener()).setPhoneInformation(phoneOnRobot, params.cameraDirection);

        waitForStart();

        images.activate();

        OpenGLMatrix lastLocation; //stores last known location of robot

        while(opModeIsActive()){

            for(VuforiaTrackable i : images){
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)(i.getListener())).getPose();

                if(pose != null){ //if the image is found

                    //finds data about the image
                    VectorF translation = pose.getTranslation();
                    Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                    OpenGLMatrix robotLocationOnField = ((VuforiaTrackableDefaultListener)(i).getListener()).getUpdatedRobotLocation();
                    lastLocation = robotLocationOnField;

                    if(lastLocation != null){
                        telemetry.addData(i.getName(), " is visible");
//                        telemetry.addData("Pos ", format(lastLocation));

                        VectorF location = lastLocation.getTranslation();
                        Orientation locationRot = Orientation.getOrientation(lastLocation, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                        double tX = VuforiaUtil.round(location.get(0) / mmPerInch, 2);
                        double tY = VuforiaUtil.round(location.get(1) / mmPerInch, 2);
                        double tZ = VuforiaUtil.round(location.get(2) / mmPerInch, 2);

                        double rX, rY, rZ;

                        //find the rotational x and y components of the robot relative to the target, finds the z rotation of the robot relative to the field

                        switch(i.getName()){
                            case "BluePerimeter":
                                rX = -1 * VuforiaUtil.round(rot.secondAngle, 2);
                                rY = VuforiaUtil.round(rot.firstAngle, 2);
                                rZ = VuforiaUtil.round(VuforiaUtil.to180(-1 * rot.secondAngle + 180), 2);
                                break;
                            case "RedPerimeter":
                                rX = VuforiaUtil.round(rot.secondAngle, 2);
                                rY = -1 * VuforiaUtil.round(rot.firstAngle, 2);
                                rZ = VuforiaUtil.round(VuforiaUtil.to180(-1 * rot.secondAngle), 2);
                                break;
                            case "FrontPerimeter":
                                rX = -1 * VuforiaUtil.round(rot.firstAngle, 2);
                                rY = -1 * VuforiaUtil.round(rot.secondAngle, 2);
                                rZ = VuforiaUtil.round(VuforiaUtil.to180(-1 * rot.secondAngle + 270), 2);
                                break;
                            case "BackPerimeter":
                                rX = VuforiaUtil.round(rot.firstAngle, 2);
                                rY = VuforiaUtil.round(rot.secondAngle, 2);
                                rZ = VuforiaUtil.round(VuforiaUtil.to180(-1 * rot.secondAngle + 90), 2);
                                break;
                            default:
                                rX = 0;
                                rY = 0;
                                rZ = 0;
                        }

                        telemetry.addData("\n(Translations) X: " + tX + ", Y: " + tY + ", Z: " + tZ, "");
                        telemetry.addData("(Rotations) X: " + rX + ", Y: " + rY + ", Z: " + rZ, "");
                    }

                }

            }
            telemetry.update();
        }

    }

    String format(OpenGLMatrix transformationMatrix) {
        return transformationMatrix.formatAsTransform();
    }

}