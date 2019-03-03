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
    protected org.firstinspires.ftc.teamcode.armisticeAuto.Direction Direction;

    public void runOpMode(){

        //initialization
        initialize(true);

        imu.initialize();
        imu.setOffset(0);



       /* MineralDetector detector = new MineralDetector();
        detector = DanCVMineralDetector(detector);
        detector.setResizeVal(0.5);*/

        /*telemetry.addData("imu angle:", imu.getZAngle());
        telemetry.addData("Is Ready", detector.isReady());
        telemetry.addData("Init Done", detector.isInited());
        telemetry.update();*/

        waitForStart();

        //Get off lander
/*        extendHook();
        RelativePosition direction = detector.getRelativePos();
        strafeEncoders(20, 0.4);
        imuTurn(-imu.getZAngle(),0.4,false);

            //This method returns a value from the Relative Position enum - see Enum Package folder in DanCV for more info
            //Can yield LEFT,RIGHT,CENTER, or UNKNOWN (not visible)
            telemetry.addData("Detected Position", detector.getRelativePos().name());

            //Yields whether object is centered or not
            telemetry.addData("Centered? ", detector.isCentered());

            //Whether its visible (may have problems, but should work)
            telemetry.addData("Visible", detector.isVisible());


        telemetry.addData("direction", detector.getRelativePos());
        telemetry.update();

        print("check1",1);
*/

       /* RelativePosition direction = detector.getRelativePos();
        telemetry.addData("direction", direction);
        telemetry.update();
        holdUp(5);*/

        //Move forward to see Qube
        moveEncodersCount(-2500, 0.4);

        //align with mineral
       /* int dir;
        boolean flag = true;

        if (direction.equals(Direction.CENTER))
            dir = 0;

        telemetry.addData("check","123");

        timer.reset();*/
       /* while (!detector.getRelativePos().equals(Direction.CENTER) && timer.seconds()<5) {
            holdUp(1);
            if (direction.equals(Direction.LEFT)) {
                setStrafe(0.3);
                dir = 1;
                telemetry.addData("dir","left");
            }
            else if (direction.equals(Direction.RIGHT)) {
                setStrafe(-0.3);
                dir = -1;
                telemetry.addData("dir","right");
            }
            else {
                telemetry.addData("UNKNOWN","");
            }
            telemetry.update();
        }
        setDrive(0);*/

        //knock off cube
//        moveEncoders(20, 0.4);
//        holdUp(1);
//        moveEncoders(-10,0.4);

        /*if (direction.equals(Direction.LEFT)) {
            strafeEncodersCount(2500, 0.4, Direction.LEFT);
        }
        else if (direction.equals(Direction.RIGHT)) {
            strafeEncodersCount(6500, 0.4, Direction.LEFT);
        }
        else if (direction.equals(Direction.CENTER)) {
            strafeEncodersCount(4500, 0.4, Direction.LEFT);
        }*/

        strafeEncoders(150, 0.4);

        imuTurn(45, 0.4,true);

        //Vuforia stuff

        //temp in place of Vuf
        moveEncodersCount(5000,0.4);

        //drop of marker
        dropMarker();
        holdUp(0.5);
        raiseMarker();

        moveEncodersCount(-8600, 0.4);

    }
}
