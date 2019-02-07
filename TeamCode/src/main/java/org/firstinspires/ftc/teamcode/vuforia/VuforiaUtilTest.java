package org.firstinspires.ftc.teamcode.vuforia;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import java.util.Arrays;

@Autonomous(name = "VuforiaUtilTest", group = "group")

public class VuforiaUtilTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        VuforiaUtil testBOI = new VuforiaUtil(true, VuforiaLocalizer.CameraDirection.BACK);

        waitForStart();

        double[][] info;

        while(opModeIsActive()){
            info = testBOI.robotInformation();
            telemetry.addData("Image ", VuforiaUtil.imageLocation((int)info[0][0]));
            for(int i = 1; i < info.length; i++){
                telemetry.addData("", Arrays.toString(info[i]));
            }
            telemetry.update();
        }
    }
}
