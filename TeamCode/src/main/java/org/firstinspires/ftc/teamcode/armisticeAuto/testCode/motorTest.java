package org.firstinspires.ftc.teamcode.armisticeAuto.testCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.armisticeAuto.armisticeAutoSuper;

/**
 * Created by Angela on 1/10/2019.
 */
@Autonomous(name = "motor test", group = "autotest")
public class motorTest extends armisticeAutoSuper {

    public void runOpMode() {

        initialize(true);
        waitForStart();


        setDrive(0.25);
        holdUp(5);

        setDrive(0);
        holdUp(5);

        setDrive(0.5);
        holdUp(5);
    }
}
