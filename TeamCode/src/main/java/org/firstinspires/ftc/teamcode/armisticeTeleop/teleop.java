package org.firstinspires.ftc.teamcode.armisticeTeleop;

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

@TeleOp(name = "armisticeTeleop")
public class teleop extends OpMode {

    protected DcMotor FL, FR, BL, BR;
    //intake spins surgical tubing
    //lift holds robot up before auto
    protected DcMotor lift, intake, slideExtension, launch;
    protected CRServo launchTube;
    protected Servo rampRotate;

    @Override
    public void init() {

        //maps motors
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");
        //lift = hardwareMap.dcMotor.get("lift");
        //intake = hardwareMap.dcMotor.get("intake");
//        slideExtension = hardwareMap.dcMotor.get("slideExtension");
//        launch = hardwareMap.dcMotor.get("launch");
//
//        //maps servos
//        launchTube = hardwareMap.crservo.get("launchTube");
//        rampRotate = hardwareMap.servo.get("rampRotate");

        //sets left side of drivetrain to reverse
        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);

        //sets motors to run with encoders
//        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
//        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        launch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        slideExtension.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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

       /* if (gamepad2.right_trigger!=0 || gamepad2.left_trigger==0)
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
            turn.setPower(0);*/
    }
}