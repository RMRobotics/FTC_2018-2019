package org.firstinspires.ftc.teamcode.vuforia;

/*
Created on 1/3/19 by Neal Machado
Test Vuforia class using FIXIT 3491 tutorial
 */


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

@Autonomous(name = "VuforiaOp")
public class VuforiaOp extends LinearOpMode {

    float mmPerInch = 25.4f;

    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK; //uses the back phone camera
        params.vuforiaLicenseKey = "AW2QuFH/////AAABmWaIPHGonUtBiAByunLrGxcyyeTFYpDBVTYsP/A5yrSSQ7PX+/+pCet8bFzd5AWw983mUAycCFdAz/tNDXFvp5BJeqH2b5ZGPFwi08UznmQ9zrq+k3GiKBUSJj37HaPMGeOuE04icbwblA5FgZEThDkSAUyiUqL+tMPv/zkXNzpVWKJkjObucLS2gdYNljJm4calEVnr9JOLbmbcP0IU3hy53CJtkxFc65LSF7n+CcajbEEB2PVfTCS3JLwCHcSKYkoR/FrHO06YFyESC0f5itieL2hKKleOwqOFwiqpV77u5WlMj4y3UncYn0uiCob7f3uXTR//dCCqPAp9P2y5cowPQ5/G6jKyWmv3B+qyegux";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES; //show the xyz axis on the target (can be a teapot, building, axis, or none)

        VuforiaLocalizer vuforia = ClassFactory.getInstance().createVuforia(params); //create the vuforia object with the above params

        VuforiaTrackables images = vuforia.loadTrackablesFromAsset("RoverRuckus"); //access the .xml file with all the images

        //set names for images
        images.get(0).setName("BluePerimeter");
        images.get(1).setName("RedPerimeter");
        images.get(2).setName("FrontPerimeter");
        images.get(3).setName("BackPerimeter");

        waitForStart();

        images.activate();

        while(opModeIsActive()){
            for(VuforiaTrackable i : images){
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)(i.getListener())).getPose();

                if(pose != null){ //if the image is found

                    VectorF translation = pose.getTranslation(); //finds data about the image
                    Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                    //find the xyz components of the offset relative to robot, rounds to 2 decimal places, and converts to inches
                    double tX = translation.get(0) / mmPerInch;
                    double tY = translation.get(1) / mmPerInch;
                    double tZ = translation.get(2) / mmPerInch;

//                  //find the rotational components of the target relative to robot
//                    double rX = rot.firstAngle;
//                    double rY = rot.secondAngle;
//                    double rZ = rot.thirdAngle;
//
                    telemetry.addData("(Translations) X: " + tX + ", Y: " + tY + ", Z: " + tZ, "");
//                    telemetry.addData("(Rotations) X: " + rX + ", Y: " + rY + ", Z: " + rZ, "");


//                    telemetry.addData(i.getName() + "-Translation", translation.toString());

                    telemetry.addData("y/z", (tY/tZ));
                    double degreesToTurn = Math.toDegrees(Math.atan2(translation.get(1), translation.get(2))); //displays the angle (roudned to 2 decimal places) needed to turn to the image
                    telemetry.addData(i.getName() + "-Degrees", degreesToTurn);
                }
            }
            telemetry.update();
        }







    }

}
