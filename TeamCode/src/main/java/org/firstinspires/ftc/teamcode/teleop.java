package org.firstinspires.ftc.teamcode;

//It's Rover Ruckus Time BOIS

/**
 * Created by Rithik on 9/18/2018.
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "teleop")
public class teleop extends OpMode {

    protected DcMotor FL, FR, BL, BR;
    protected DcMotor lift, turn;
    protected CRServo intake;
    protected Servo hook;

    @Override
    public void init() {
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");
        lift = hardwareMap.dcMotor.get("lift");
        turn = hardwareMap.dcMotor.get("turn");
        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        turn.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        hook = hardwareMap.servo.get("hook");
        intake = hardwareMap.crservo.get("intake");
    }

    protected void addTelemetry() {
        telemetry.update();
    }

    protected void setDrive(double p1, double p2, double p3, double p4) {
        FL.setPower(p1);
        FR.setPower(p2);
        BL.setPower(p3);
        BR.setPower(p4);
    }

    @Override
    public void loop() {

        double forward, strafe, rotate;
        forward = -gamepad1.right_stick_y;
        strafe = gamepad1.right_stick_x;
        rotate = gamepad1.left_stick_x;

        double max = 1;
        FL.setPower((forward + strafe + rotate) / max);
        FR.setPower((forward - strafe - rotate) / max);
        BL.setPower((forward - strafe + rotate) / max);
        BR.setPower((forward + strafe - rotate) / max);

        if (gamepad2.right_trigger!=0 || gamepad2.left_trigger==0)
            intake.setPower(gamepad2.right_trigger);
        else if (gamepad2.right_trigger ==0 || gamepad2.left_trigger !=0)
            intake.setPower(gamepad2.left_trigger/-1);
        else
            intake.setPower(0);

        if (gamepad1.right_bumper)
            hook.setPosition(1);

        if (gamepad1.left_bumper)
            hook.setPosition(0);

        lift.setPower(gamepad2.right_stick_y);

        if (gamepad1.right_trigger!=0 || gamepad1.left_trigger==0)
            turn.setPower(gamepad1.right_trigger);
        else if (gamepad1.right_trigger ==0 || gamepad1.left_trigger !=0)
            turn.setPower(gamepad1.left_trigger/-1);
        else
            turn.setPower(0);
    }
}