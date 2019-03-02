package org.firstinspires.ftc.teamcode.vuforia;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

@Autonomous(name = "VuforiaUtilTest", group = "Vuforia")

public class VuforiaUtilTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        VuforiaUtil test = new VuforiaUtil(true, VuforiaLocalizer.CameraDirection.FRONT, VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES, 90, 90, 90);

        waitForStart();

        while(opModeIsActive()){
            test.robotInformation();
            telemetry.addData("Image ", test.getCurrentImageName());
            telemetry.addData("\n(RTI_tran) X: " + test.getRobotToImagetX() + ", Y: " + test.getRobotToImagetY() + ", Z: " + test.getRobotToImagetZ(), "");
            telemetry.addData("(RTI_rot) X: " + test.getRobotToImagerX() + ", Y: " + test.getRobotToImagerY() + ", Z: " + test.getRobotToImagerZ(), "");
            telemetry.addData("\n(RTF_tran) X: " + test.getRobotToFieldtX() + ", Y: " + test.getRobotToFieldtY() + ", Z: " + test.getRobotToFieldtZ(), "");
            telemetry.addData("(RTF_rot) X: " + test.getRobotToFieldrX() + ", Y: " + test.getRobotToFieldrY() + ", Z: " + test.getRobotToFieldrZ(), "");
            telemetry.update();
        }
    }
}
