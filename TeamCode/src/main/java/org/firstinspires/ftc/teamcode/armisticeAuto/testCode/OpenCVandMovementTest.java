package org.firstinspires.ftc.teamcode.armisticeAuto.testCode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.Direction;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.armisticeAuto.armisticeAutoSuper;

/**
 * Created by Angela on 1/5/2019.
 */
@Autonomous (name = "OpenCV w/ Movement", group = "autotest")
public class OpenCVandMovementTest extends armisticeAutoSuper {

    private com.disnodeteam.dogecv.detectors.roverrukus.Direction position = Direction.UNKNOWN;

    public void runOpMode() {

        initialize(true);

        //Move forward to see Qube
        moveEncoders(30,0.4);

        //See Qube
        detector = new GoldAlignDetector(); // Create detector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
        detector.useDefaults(); // Set detector to use default settings

        // Optional tuning
        detector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.4; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005; //

        detector.ratioScorer.weight = 5; //
        detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment

        detector.enable(); // Start the detector!

        boolean flag = true;

        while (flag) {
            //Vars
            int count = 0;
            int change = 0;
            int direction = 1;
            double totalDistance = 0;
            ElapsedTime timer = new ElapsedTime();
            timer.reset();

            if (detector.getAligned().equals(Direction.CENTER)) {
                moveEncoders(10 * 1, 0.4);
                flag = false;
                //push mineral w/ arm
            } else if (detector.getAligned().equals(Direction.LEFT)) {
                while (detector.isFound() == false) {
                    setStrafe(-0.3);
                }
            } else if (detector.getAligned().equals(Direction.RIGHT)) {
                while (detector.isFound() == false) {
                    setStrafe(0.3);
                }
            } else {
                telemetry.addData("UNKNOWN","");
                telemetry.update();
            }
        }
    }
}