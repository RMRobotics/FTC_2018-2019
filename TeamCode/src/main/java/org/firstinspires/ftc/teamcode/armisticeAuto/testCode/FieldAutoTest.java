package org.firstinspires.ftc.teamcode.armisticeAuto.testCode;

import com.disnodeteam.dogecv.detectors.roverrukus.*;

import com.disnodeteam.dogecv.detectors.roverrukus.Direction;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.armisticeAuto.armisticeAutoSuper;

/**
 * Created by Angela on 11/24/2018.
 */

@Autonomous(name = "FieldAuto Test", group = "autotest")
public class FieldAutoTest extends armisticeAutoSuper {

    private GoldAlignDetector detector;
    private com.disnodeteam.dogecv.detectors.roverrukus.Direction position = Direction.UNKNOWN;


    public void runOpMode(){

        //initialization
        initialize(true);
        waitForStart();

        //Get off lander

        //Move forward to see Qube
        moveEncoders(30,0.4);
        holdUp(1);

        //See Qube
        strafeEncoders(-48,0.4);
        holdUp(1);

        moveEncoders(-48,0.4);
        holdUp(1);

       /* strafeEncoders(48,0.4);
        holdUp(1);
        */

        /*moveEncoders(5,0.4); //knock off yellow mineral
        moveEncoders(-5,0.4);
        imuTurn(45, 0.4);
        moveEncoders(20,0.4);
        //drop off flag
        imuTurn(180, 0.4);
        moveEncoders(30,0.4);*/
    }
}
