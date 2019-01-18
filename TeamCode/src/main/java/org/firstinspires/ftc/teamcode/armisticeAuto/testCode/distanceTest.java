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
        initialize(true);
        waitForStart();

        //Get off lander

        //Move forward one foot
        double speed = 0.5;
        int currentPos = BL.getCurrentPosition();
        //distanceTics is num of tics it needs to travel
        int distanceTics = (int)(12 * (1120.0 * 0.66666)/(4.0 * Math.PI));
        double tickRatio;

        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setTargetPosition(currentPos + distanceTics);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        BR.setPower(speed);
        BL.setPower(speed);
        FL.setPower(speed);
        FR.setPower(speed);

        telemetry.addData("checkpoint1","");
        holdUp(0.5);

        while(BL.getCurrentPosition()-currentPos<distanceTics /*BL.isBusy() /*&& BL.isBusy() && BR.isBusy() && FL.isBusy()*/){
            tickRatio = ((double)BL.getCurrentPosition() - (double)currentPos) / distanceTics;
            speed = ((-0.5) * (tickRatio) + 0.5);
            if (speed < 0.15)
                speed = 0.15;
            print(String.valueOf(BL.getCurrentPosition())+" " +distanceTics,1);
            BR.setPower(speed);
            BL.setPower(speed);
            FL.setPower(speed);
            FR.setPower(speed);
        }
        FR.setPower(0);
        BR.setPower(0);
        FL.setPower(0);
        BL.setPower(0);

        telemetry.addData("checkpoint2","");
        holdUp(0.5);
        telemetry.update();


//        moveEncoders(12, 1);



       /* holdUp(5);

        moveEncoders(12, -1);

        holdUp(5);

        moveEncoders(12, 1);

        holdUp(5);

        strafeEncoders(12, 1,0.4);

        holdUp(5);

        strafeEncoders(12, -1,0.4);

        holdUp(5);

        strafeEncoders(12, 1,0.4);

        holdUp(5);
*/
    }
}
