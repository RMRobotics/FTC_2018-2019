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
        boolean flag = false;
        waitForStart();

        while (opModeIsActive() && !flag)
        {
            telemetry.addData("dpad-left", "imuTurn 90 degrees");
            telemetry.addData("dpad-right", "strafeEncoders 6 in");
            telemetry.addData("dpad-up", "switch to driving");
            telemetry.addData("dpad-down", "straight at 0.4");
            telemetry.addData("right trigger", "intake crservo");
            telemetry.addData("right joystick y", "arm power");
            telemetry.addData("X", "moveEncoders 5 in");
            telemetry.addData("B", "Cancel");
            telemetry.addData("both bumpers","end OpMode");
            telemetry.update();

            arm.setPower(gamepad1.right_stick_y);

            if (gamepad1.dpad_left) {
                imuTurn(90, 0.4);
            }
            else if (gamepad1.dpad_up)
            {
                double max = 1;
                while (!gamepad1.b) {
                    double forward, strafe, rotate;
                    forward = -gamepad1.left_stick_y;
                    strafe = gamepad1.left_stick_x;
                    rotate = gamepad1.right_stick_x;

                    if (gamepad1.a)
                    {
                        if (max==1) {
                            max = 2;
                            telemetry.addData("half speed","");
                        }
                        else {
                            max = 1;
                            telemetry.addData("normal","");
                        }
                        telemetry.update();
                    }

                    FL.setPower((forward + strafe + rotate) / max);
                    FR.setPower((forward - strafe - rotate) / max);
                    BL.setPower((forward - strafe + rotate) / max);
                    BR.setPower((forward + strafe - rotate) / max);
                }
            }
            else if (gamepad1.dpad_right)
            {
                strafeEncoders(6,0.5);
            }
            else if (gamepad1.dpad_down)
            {
                while (!gamepad1.b)
                    setDrive(0.4);
                setDrive(0);
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
            else if (gamepad1.right_bumper && gamepad1.left_bumper)
            {
                flag = true;
            }

            intake.setPower(0);
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
