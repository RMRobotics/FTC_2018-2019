package org.firstinspires.ftc.teamcode.armisticeAuto.testCode;

import com.disnodeteam.dogecv.detectors.roverrukus.Direction;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.armisticeAuto.armisticeAutoSuper;

/**
 * Created by Angela on 1/5/2019.
 */
@Autonomous (name = "OpenCV w/ Movement", group = "autotest")
public class OpenCVandMovementTest extends armisticeAutoSuper {

    private GoldAlignDetector detector;
    private com.disnodeteam.dogecv.detectors.roverrukus.Direction position = Direction.UNKNOWN;

    public void runOpMode() {

        initialize(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();

        //Move forward to see Qube
        moveEncoders(10, 1);

        //See Qube
        detector = new GoldAlignDetector();
        DogeCVYellowDetector(detector);

        //Vars
        int count = 0;
        int change = 0;
        int direction = 1;
        double totalDistance = 0;
        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        if (detector.getAligned().equals(Direction.CENTER)) {
            moveEncoders(2, 1);
            //push mineral w/ arm
        } else if (detector.getAligned().equals(Direction.LEFT)) {
            while (detector.isFound() == false) {
                strafeEncoders(2, -1, .25);
            }
        } else if (detector.getAligned().equals(Direction.RIGHT)) {
            while (detector.isFound() == false) {
                strafeEncoders(2, 1, .25);
            }
        } else {
            moveEncoders(2, -1);
        }
    }
}