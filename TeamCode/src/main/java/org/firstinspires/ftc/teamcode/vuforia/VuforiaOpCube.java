package org.firstinspires.ftc.teamcode.vuforia;

/*
Created on 2/16/19 by Neal Machado
Testing homemade vumark
 */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.R;

@Autonomous(name = "VuforiaOpCube", group = "Vuforia")
public class VuforiaOpCube extends LinearOpMode {

    float mmPerInch = 25.4f;
    float robotSize = 18 * mmPerInch;
    float mmFTCFieldWidth  = (12*12 - 2) * mmPerInch;

    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK; //uses the back phone camera
        params.vuforiaLicenseKey = "AW2QuFH/////AAABmWaIPHGonUtBiAByunLrGxcyyeTFYpDBVTYsP/A5yrSSQ7PX+/+pCet8bFzd5AWw983mUAycCFdAz/tNDXFvp5BJeqH2b5ZGPFwi08UznmQ9zrq+k3GiKBUSJj37HaPMGeOuE04icbwblA5FgZEThDkSAUyiUqL+tMPv/zkXNzpVWKJkjObucLS2gdYNljJm4calEVnr9JOLbmbcP0IU3hy53CJtkxFc65LSF7n+CcajbEEB2PVfTCS3JLwCHcSKYkoR/FrHO06YFyESC0f5itieL2hKKleOwqOFwiqpV77u5WlMj4y3UncYn0uiCob7f3uXTR//dCCqPAp9P2y5cowPQ5/G6jKyWmv3B+qyegux";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES; //show the xyz axis on the target (can be a teapot, building, axis, or none)

        int n = 1;

        telemetry.addData("Check", n);
        telemetry.update();
        n++;

        VuforiaLocalizer vuforia = ClassFactory.getInstance().createVuforia(params); //create the vuforia object with the above params

        telemetry.addData("Check", n);
        telemetry.update();
        n++;

        VuforiaTrackables cube = vuforia.loadTrackablesFromAsset("RoverRuckus"); //access the .xml file with all the images

        telemetry.addData("Check", n);
        telemetry.update();
        n++;

        //set names for images
        cube.get(0).setName("cube");

        waitForStart();

        cube.activate();

        telemetry.addData("Check", n);
        telemetry.update();
        n++;

        OpenGLMatrix lastLocation; //stores last known location of robot

        while(opModeIsActive()){

            telemetry.addData("Check", n);
            telemetry.update();
            n++;

            for(VuforiaTrackable i : cube){
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)(i.getListener())).getPose();

                if(pose != null){ //if the image is found

                    //finds data about the image
                    VectorF translation = pose.getTranslation();
                    Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                    telemetry.addData(i.getName(), " is visible");
//                        telemetry.addData("Pos ", format(lastLocation));

                    double tX = VuforiaUtil.round(translation.get(0) / mmPerInch, 2);
                    double tY = VuforiaUtil.round(translation.get(1) / mmPerInch, 2);
                    double tZ = VuforiaUtil.round(translation.get(2) / mmPerInch, 2);

                    double rX = VuforiaUtil.to180(VuforiaUtil.round(rot.firstAngle, 2));
                    double rY = VuforiaUtil.to180(VuforiaUtil.round(rot.secondAngle, 2));
                    double rZ = VuforiaUtil.to180(VuforiaUtil.round(rot.thirdAngle, 2));

                    telemetry.addData("\n(Translations) X: " + tX + ", Y: " + tY + ", Z: " + tZ, "");
                    telemetry.addData("(Rotations) X: " + rX + ", Y: " + rY + ", Z: " + rZ, "");


                }

            }
            telemetry.update();
        }

    }

    String format(OpenGLMatrix transformationMatrix) {
        return transformationMatrix.formatAsTransform();
    }

}