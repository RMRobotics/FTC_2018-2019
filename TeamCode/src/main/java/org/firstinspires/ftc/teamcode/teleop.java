package org.firstinspires.ftc.teamcode;

/**
 * Created by Angela on 9/18/2018.
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
    protected DcMotor lift, liftTurn;
    protected CRServo intake;
    protected Servo hook;

    @Override
    public void init() {
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");
        hook = hardwareMap.servo.get("hook");
        intake = hardwareMap.crservo.get("intake");
        lift = hardwareMap.dcMotor.get("lift");
        liftTurn = hardwareMap.dcMotor.get("liftTurn");
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

    protected void setDrive(double p) {
        setDrive(p);
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

//It's Rover Ruckus Time BOIS
    }
}