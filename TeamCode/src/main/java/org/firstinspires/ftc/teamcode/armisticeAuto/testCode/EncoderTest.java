package org.firstinspires.ftc.teamcode.armisticeAuto.testCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.armisticeAuto.armisticeAutoSuper;

/**
 * Created by Angela on 11/20/2018.
 */

@Autonomous(name = "Encoder Test", group = "autotest")
public class EncoderTest extends armisticeAutoSuper {

    public void runOpMode(){

        initialize(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();

        //        strafeEncoders(4,1, .4);
        //        sleep(5000);
        moveEncoders(12, 1);
        sleep(5000);
        //        imuTurn(90, 0.5);
    }
}