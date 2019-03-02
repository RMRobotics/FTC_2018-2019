package org.firstinspires.ftc.teamcode.vuforia;

/*
Created on 1/18/19 by Neal Machado
Class testing VuforiaUtil methods
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

import java.util.Arrays;

@Autonomous(name = "anotherVuforiaOp", group = "Vuforia")

public class anotherVuforiaOp extends LinearOpMode{

    float mmPerInch = 25.4f;
    float robotSize = 18 * mmPerInch;
    float mmFTCFieldWidth  = (12*12 - 2) * mmPerInch;

    public void runOpMode(){
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK; //uses the back phone camera
        params.vuforiaLicenseKey = "AW2QuFH/////AAABmWaIPHGonUtBiAByunLrGxcyyeTFYpDBVTYsP/A5yrSSQ7PX+/+pCet8bFzd5AWw983mUAycCFdAz/tNDXFvp5BJeqH2b5ZGPFwi08UznmQ9zrq+k3GiKBUSJj37HaPMGeOuE04icbwblA5FgZEThDkSAUyiUqL+tMPv/zkXNzpVWKJkjObucLS2gdYNljJm4calEVnr9JOLbmbcP0IU3hy53CJtkxFc65LSF7n+CcajbEEB2PVfTCS3JLwCHcSKYkoR/FrHO06YFyESC0f5itieL2hKKleOwqOFwiqpV77u5WlMj4y3UncYn0uiCob7f3uXTR//dCCqPAp9P2y5cowPQ5/G6jKyWmv3B+qyegux";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES; //show the xyz axis on the target (can be a teapot, building, axis, or none)

        VuforiaLocalizer vuforia = ClassFactory.getInstance().createVuforia(params); //create the vuforia object with the above params
        VuforiaTrackables images = vuforia.loadTrackablesFromAsset("RoverRuckus"); //access the .xml file with all the images

        //set location for the phone
        OpenGLMatrix phoneOnRobot = OpenGLMatrix
                .translation(0, robotSize / 2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 0, 0));

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

        OpenGLMatrix redImageOnField = OpenGLMatrix
                .translation(mmFTCFieldWidth / 2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 0, 270));
        images.get(1).setLocation(redImageOnField);

        OpenGLMatrix frontImageOnField = OpenGLMatrix
                .translation(0, -mmFTCFieldWidth / 2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 180, 0));
        images.get(2).setLocation(frontImageOnField);

        OpenGLMatrix backImageOnField = OpenGLMatrix
                .translation(0, mmFTCFieldWidth / 2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 0, 0));
        images.get(3).setLocation(backImageOnField);

        waitForStart();

        images.activate();

//        while(opModeIsActive()){
//            double[][] info = VuforiaUtil.robotInformation(images);
//            telemetry.addData("Image ", VuforiaUtil.imageLocation((int)info[0][0]));
//            for(int i = 1; i < info.length; i++){
//                telemetry.addData("", Arrays.toString(info[i]));
//            }
//            telemetry.update();
//        }
    }


}
