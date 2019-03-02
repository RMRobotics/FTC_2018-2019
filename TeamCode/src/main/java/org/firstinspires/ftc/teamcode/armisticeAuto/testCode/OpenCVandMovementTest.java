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

        //extendHook();



        //See Qube
        GoldAlignDetector detector = new GoldAlignDetector(); // Create detector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance(), 1, false); // Initialize it with the app context and camera
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

        Direction direction = detector.getAligned();
        while (direction.equals(Direction.UNKNOWN)) {
            telemetry.addData("direction: ", direction);
            telemetry.update();
        }

        telemetry.addData("direction: ", direction);
        telemetry.update();

        holdUp(1);

        int dir;
        boolean flag = true;

        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int currentPos1 = FL.getCurrentPosition();
        int currentPos2 = FR.getCurrentPosition();
        int currentPos3 = BL.getCurrentPosition();
        int currentPos4 = BR.getCurrentPosition();

        //Move forward to see Qube
        moveEncoders(30,0.4);

        if (direction.equals(Direction.CENTER))
            dir = 0;

        while (!detector.getAligned().equals(Direction.CENTER)) {
            holdUp(5);
            if (direction.equals(Direction.LEFT)) {
                setStrafe(0.3);
                dir = 1;
                }
            else if (direction.equals(Direction.RIGHT)) {
                setStrafe(-0.3);
                dir = -1;
                }
            else {
                telemetry.addData("UNKNOWN","");
                telemetry.update();


            }
        }
        setDrive(0);
        moveEncoders(20, 0.4);
/*
        if (FL.getCurrentPosition()<currentPos1)
            strafeEncoders(currentPos1, currentPos2, currentPos3, currentPos4, -0.4);
        else
            strafeEncoders(currentPos1, currentPos2, currentPos3, currentPos4, 0.4);
        imuTurn(
*/
        //drop of marker
        dropMarker();
        holdUp(0.5);
        raiseMarker();

        moveEncodersCount(-8600, 0.4);


    }
}