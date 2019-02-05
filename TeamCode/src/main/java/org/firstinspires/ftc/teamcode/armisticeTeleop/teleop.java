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

@TeleOp(name = "armisticeTeleop",group = "tele")
public class teleop extends OpMode {

    protected DcMotor   FL, FR, BL, BR, //drivetrain
                        lift,           //used to hold up the robot on the lander
    arm;            //used for intake

    protected CRServo intake;           //spins surgical tubing for intake

    protected double max;//max motor value

    protected ElapsedTime timer = new ElapsedTime();

    @Override
    public void init() {

        //maps motors
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");
        lift = hardwareMap.dcMotor.get("lift");
        arm = hardwareMap.dcMotor.get("arm");

        //maps and inits intake servo
        intake = hardwareMap.crservo.get("intake");
        intake.setPower(0);

        //sets left side of drivetrain to reverse
        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);

        //sets drivetrain to float instead of brake
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        //sets other motors to brake
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //lmao
        max = 1;
    }

    /*
        prints message to telemetry
    */
    protected void addTelemetry(String msg) {
        telemetry.addData(msg, "");
        telemetry.update();
    }

    protected void armSlam(int sec) {

//        timer.reset();
//        double x = gamepad2.right_stick_y;
//        while (timer.seconds()<=sec || gamepad2.right_stick_y > 0){
//            if ((int)timer.seconds()==timer.seconds())
//                x/= 5;
//            arm.setPower(x);
//        }
    }

    /*
        this method sets the four wheels' powers to all the input values
     */
    protected void setDrive(double p1, double p2, double p3, double p4) {
        FL.setPower(p1);
        FR.setPower(p2);
        BL.setPower(p3);
        BR.setPower(p4);
    }

    @Override
    public void loop() {

        //Three parameters for the motor speed
        double forward, strafe, rotate;
        forward = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        //toggles to slowmode
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

        //vroom vroom driving
        FL.setPower((forward + strafe + rotate) / max);
        FR.setPower((forward - strafe - rotate) / max);
        BL.setPower((forward - strafe + rotate) / max);
        BR.setPower((forward + strafe - rotate) / max);

        //Controls the lift to latch onto the lander
        if (gamepad2.right_trigger!=0 && gamepad2.left_trigger==0)
            lift.setPower(gamepad2.right_trigger);
        else if (gamepad2.right_trigger==0 && gamepad2.left_trigger!=0)
            lift.setPower(-gamepad2.left_trigger);
        else
            lift.setPower(0);

        //Controls the arm
        arm.setPower(gamepad2.left_stick_y/1.5);

//        if (gamepad2.a)
//            armSlam(3);

        //Controls the intake servo
        if (gamepad1.right_trigger!=0 && gamepad1.left_trigger==0)
            intake.setPower(gamepad1.right_trigger);
        else if (gamepad1.right_trigger==0 && gamepad1.left_trigger!=0)
            intake.setPower(-gamepad1.left_trigger);
        else
            intake.setPower(0);

    }
}