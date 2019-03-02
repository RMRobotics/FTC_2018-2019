package org.firstinspires.ftc.teamcode.armisticeAuto;

import com.disnodeteam.dogecv.detectors.roverrukus.*;
import com.disnodeteam.dogecv.detectors.roverrukus.Direction;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.DanCV.Detection.MineralDetector;
import org.firstinspires.ftc.teamcode.DanCV.Enums.RelativePosition;

/**
 * Created by Neal on 12/6/2018.
 */

@Autonomous(name = "AutoCrater", group = "auto")
public class AutoCrater extends armisticeAutoSuper{

    private GoldAlignDetector detector;

    public void runOpMode(){

        //initialization
        initialize(true);

        MineralDetector detector = new MineralDetector();
        detector = DanCVMineralDetector(detector);
        detector.setResizeVal(0.5);

        telemetry.addData("Is Ready", detector.isReady());
        telemetry.addData("Init Done", detector.isInited());
        telemetry.update();

        waitForStart();

        //Get off lander
        extendHook();
        strafeEncoders(20, 0.4);

        while (opModeIsActive()) {
            //This method returns a value from the Relative Position enum - see Enum Package folder in DanCV for more info
            //Can yield LEFT,RIGHT,CENTER, or UNKNOWN (not visible)
            telemetry.addData("Detected Position", detector.getRelativePos().name());

            //Yields whether object is centered or not
            telemetry.addData("Centered? ", detector.isCentered());

            //Whether its visible (may have problems, but should work)
            telemetry.addData("Visible", detector.isVisible());

            //Update Telemetry
            telemetry.update();
        }
        RelativePosition direction = detector.getRelativePos();
        telemetry.addData("direction", detector.getRelativePos());
        telemetry.update();

/*

        //Move forward to see Qube
        moveEncoders(30, 0.4);

        //align with mineral
        int dir;
        boolean flag = true;

        if (direction.equals(Direction.CENTER))
            dir = 0;

        while (!detector.getRelativePos().equals(Direction.CENTER)) {
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

        //knock off cube
        moveEncoders(20, 0.4);

        if (direction.equals(Direction.LEFT)) {
            strafeEncoders();
        }
        else if (direction.equals(Direction.RIGHT)) {
            strafeEncoders();
        }
        else if (direction.equals(Direction.CENTER)) {
            strafeEncoders();
        }

        imuTurn(90, 0.4);

        //Vuforia stuff

        //drop of marker
        dropMarker();
        holdUp(0.5);
        raiseMarker();

        moveEncodersCount(-8600, 0.4);
*/
    }
}
