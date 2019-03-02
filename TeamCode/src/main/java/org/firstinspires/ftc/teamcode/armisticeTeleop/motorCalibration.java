package org.firstinspires.ftc.teamcode.armisticeTeleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by Angela on 3/1/2019.
 */
@TeleOp(name = "Motor Calibration")
public class motorCalibration extends teleop {

    protected DcMotor FL, BL, FR, BR;
    protected int max;

    @Override
    public void init() {

        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");

        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void loop() {

        telemetry.addData("FL Encoder", FL.getCurrentPosition());
        telemetry.addData("BL Encoder", BL.getCurrentPosition());
        telemetry.addData("FR Encoder", FR.getCurrentPosition());
        telemetry.addData("BR Encoder", BR.getCurrentPosition());
        telemetry.addData("Power", FL.getPower());
        telemetry.update();

        double forward, strafe, rotate;
        forward = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        //toggles to slowmode
        if (gamepad1.a) {
            if (max == 1) {
                max = 2;
                telemetry.addData("half speed", "");
            } else {
                max = 1;
                telemetry.addData("normal", "");
            }
            telemetry.update();
        }

        FL.setPower((forward + strafe + rotate) / -max);
        FR.setPower((forward - strafe - rotate) / -max);
        BL.setPower((forward - strafe + rotate) / -max);
        BR.setPower((forward + strafe - rotate) / -max);

    }
}
