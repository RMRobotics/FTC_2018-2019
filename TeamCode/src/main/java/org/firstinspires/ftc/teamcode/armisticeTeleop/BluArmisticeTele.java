package org.firstinspires.ftc.teamcode.armisticeTeleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DanCV.Detection.MineralDetector;
import org.firstinspires.ftc.teamcode.DanCV.Enums.RelativePosition;
import org.firstinspires.ftc.teamcode.DanCV.UI.CVViewActivity;

@TeleOp(name = "BluArmistice",group = "tele")
public class BluArmisticeTele extends OpMode {

    protected DcMotor FL, FR, BL, BR; //Good
    protected DcMotor liftLeft,liftRight; //Good
    protected DcMotor armExtend,armPivot;
    protected CRServo intake;
    //protected MineralDetector detector;
    protected boolean autoRunning;
    protected boolean intakeOn;
    protected double max;//max motor value

    protected ElapsedTime timer = new ElapsedTime();

    protected void setMode(DcMotor.RunMode r) {
        FL.setMode(r);
        FR.setMode(r);
        BL.setMode(r);
        BR.setMode(r);
    }

    protected void setZeroMode(DcMotor.ZeroPowerBehavior z) {
        FL.setZeroPowerBehavior(z);
        FR.setZeroPowerBehavior(z);
        BL.setZeroPowerBehavior(z);
        BR.setZeroPowerBehavior(z);
    }

    /**
     * Sets all drivetrain motors' to move at the same given power
     */
    protected void setDrive(double p) {
        FL.setPower(p);
        FR.setPower(p);
        BL.setPower(p);
        BR.setPower(p);
    }


    /**
     * Causes the robot to strafe by applying the same power value but negated to the back left and front right motors
     */
    protected void setStrafe(double pwr)
    {
        BR.setPower(pwr);
        BL.setPower(-pwr);
        FL.setPower(pwr);
        FR.setPower(-pwr);
    }

    @Override
    public void init() {

        //maps motors
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");

        //sets left side of drivetrain to reverse
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        //Reset encoders and set to run without them.
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //sets drivetrain to float instead of brake
        setZeroMode(DcMotor.ZeroPowerBehavior.FLOAT);


        //Lifts
        liftLeft = hardwareMap.dcMotor.get("liftLeft");
        liftRight = hardwareMap.dcMotor.get("liftRight");

        //LIFT DIRECTION
        liftLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        liftRight.setDirection(DcMotorSimple.Direction.FORWARD);

        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //ARM Pivot and Extender
        armExtend = hardwareMap.dcMotor.get("armPivot");
        armPivot = hardwareMap.dcMotor.get("armExtend");

        armExtend.setDirection(DcMotorSimple.Direction.FORWARD);
        armPivot.setDirection(DcMotorSimple.Direction.FORWARD);

        armExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Initialize Detector
        //detector.init(hardwareMap.appContext,CVViewActivity.getInstance(), 1);

        //Intake
        intake = hardwareMap.crservo.get("intake");


        //Primitive Variable Initializations
        autoRunning = false;
        max = 1;
        intakeOn = false;
    }

    /*
        prints message to telemetry
    */
    protected void addTelemetry(String msg) {
        telemetry.addData(msg, "");
        telemetry.update();
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
        telemetry.addData("FL Encoder", FL.getCurrentPosition());
        telemetry.addData("BL Encoder", BL.getCurrentPosition());
        telemetry.addData("FR Encoder", FR.getCurrentPosition());
        telemetry.addData("BR Encoder", BR.getCurrentPosition());
        telemetry.addData("Power", FL.getPower());
        telemetry.update();

        //=============================================GAME-PAD 1===================================
        //Three parameters for the motor speed
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
/*

        if (!autoRunning && gamepad1.y) {
            autoRunning = true;
            detectBlock();
            autoRunning = false;
        }
*/
        //vroom vroom driving
        FL.setPower((forward + strafe + rotate) / -max);
        FR.setPower((forward - strafe - rotate) / -max);
        BL.setPower((forward - strafe + rotate) / -max);
        BR.setPower((forward + strafe - rotate) / -max);

        //Controls the intake servo
        if(gamepad1.b){
            intakeOn = !intakeOn;

        }


        if(intakeOn){
            intake.setPower(0.5);
        }
        //=============================================GAME-PAD 2===================================
        if (gamepad1.x) {
            armExtend.setPower(0.2);
        } else {
            armExtend.setPower(0);
        }

        if (gamepad1.left_trigger != 0 && gamepad1.right_trigger == 0) {
            armPivot.setPower(-gamepad1.left_trigger);
        } else if (gamepad1.right_trigger != 0 && gamepad1.left_trigger == 0) {
            armPivot.setPower(gamepad1.right_trigger);
        } else {
            armPivot.setPower(0);
        }

        if (gamepad1.left_bumper) {
            liftLeft.setPower(0.2);
            liftRight.setPower(0.2);
        } else if (gamepad1.right_bumper) {
            liftLeft.setPower(-0.2);
            liftRight.setPower(-0.2);
        } else {
            liftLeft.setPower(0);
            liftRight.setPower(0);
        }


    }
    /*protected void detectBlock(){
        detector.activate();
        while(!detector.isReady()){ }
        while(!detector.isCentered()){
            switch (detector.getRelativePos()){
                case RIGHT:setStrafe(0.3);
                    break;
                case LEFT:setStrafe(-0.3);
                    break;
            }
        }
        setStrafe(0);
        setDrive(0.5);
        holdUp(2);
        setDrive(0);
        detector.deactivate();
    }
    */

    protected void holdUp(double num)
    {
        timer.reset();
        while (timer.seconds()<num)
        {}
    }
}