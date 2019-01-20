package org.firstinspires.ftc.teamcode.armisticeAuto.testCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.armisticeAuto.armisticeAutoSuper;

/**
 * Created by Angela on 1/10/2019.
 */
@Autonomous(name = "testOp", group = "autotest")
public class motorTest extends armisticeAutoSuper {

    public void runOpMode() {

        initialize(true);
        boolean toggle = true;
        waitForStart();

        while (opModeIsActive())
        {
            if (gamepad2.a) {
                timer.reset();
                while (timer.seconds() < 2) {
                    arm.setPower(0.4);
                }
                arm.setPower(0);
            }




            if (gamepad1.a) {
                imuTurn(90, 0.5);
            }
            else if (gamepad1.b)
            {
                strafeEncoders(6,0.5);
            }
            else if (gamepad1.right_trigger!=0)
            {
                intake.setPower(gamepad1.right_trigger);
            }
            else if (gamepad1.x)
            {
                moveEncoders(5);
            }
            else if (gamepad1.y)
            {
                toggle = !toggle;
            }
            else if (gamepad1.left_trigger!=0)
            {
                if (toggle)
                    lift.setPower(gamepad1.left_trigger);
                else
                    lift.setPower(-gamepad1.left_trigger);
            }

            lift.setPower(0);
        }

//        setDrive(0.25);
//        holdUp(5);
//
//        setDrive(0);
//        holdUp(5);
//
//        setDrive(0.5);
//        holdUp(5);
    }
}
