package org.firstinspires.ftc.teamcode.armisticeAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Angela on 11/20/2018.
 */

@Autonomous (name = "Encoder Test")
public class encoderIMUTest extends armisticeAutoSuper{

    public void runOpMode(){

        initialize(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        moveEncoders(5, 1);
        strafeEncoders(4,1, .25);
        imuTurn(90, 0.5);
    }
}