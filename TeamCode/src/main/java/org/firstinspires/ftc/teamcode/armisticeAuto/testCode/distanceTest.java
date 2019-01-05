package org.firstinspires.ftc.teamcode.armisticeAuto.testCode;

import com.disnodeteam.dogecv.detectors.roverrukus.Direction;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.armisticeAuto.armisticeAutoSuper;

/**
 * Created by Neal on 12/11/2018.
 */

@Autonomous(name = "Distance Test", group = "autotest")
public class distanceTest extends armisticeAutoSuper {
//tests if it goes the same distance every time
    private GoldAlignDetector detector;
    private Direction position = Direction.UNKNOWN;


    public void runOpMode(){

        //initialization
        initialize(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();

        //Get off lander

        //Move forward one foot
        moveEncoders(12, 1);

        sleep(10000);

        moveEncoders(12, 1);

        sleep(10000);

        moveEncoders(12, 1);

        sleep(10000);



    }
}
