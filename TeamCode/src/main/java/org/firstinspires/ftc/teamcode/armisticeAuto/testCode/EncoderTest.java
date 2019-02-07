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

        initialize(true);


       dumbstrafeEncoders(36, 1, 0.6);

        holdUp(2);

        setDrive(0);

//                strafeEncoders(4,1, .4);
        //        sleep(5000);
//        moveEncoders(4, 1);
//        moveEncoders(60);
        print("lmao, it actually worked",1);
//        sleep(5000);
        //        imuTurn(90, 0.5);
    }
}