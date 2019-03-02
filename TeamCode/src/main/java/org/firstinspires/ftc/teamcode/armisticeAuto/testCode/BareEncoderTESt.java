package org.firstinspires.ftc.teamcode.armisticeAuto.testCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.armisticeAuto.armisticeAutoSuper;

/**
 * Created by Angela on 1/17/2019.
 */
@Autonomous(name = "bareEncoderTest", group = "autotest")
public class BareEncoderTESt extends LinearOpMode {

    protected DcMotor FL;
    protected DcMotor FR;
    protected DcMotor BL;
    protected DcMotor BR;
    protected ElapsedTime timer = new ElapsedTime();
    double initTime;
    static double CPI = (1120.0 * 0.66666)/(4.0 * Math.PI);

    public void initialize() {
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");

        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);

        setZeroMode(DcMotor.ZeroPowerBehavior.BRAKE);
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
    }

    protected void setDrive(double p) {
        FL.setPower(p);
        FR.setPower(p);
        BL.setPower(p);
        BR.setPower(p);
    }

    protected void setDriveMod(double p) {
        FL.setPower(p);
        FR.setPower(-p);
        BL.setPower(-p);
        BR.setPower(p);
    }

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


    protected void setEnc(int p) {
        FL.setTargetPosition(FL.getCurrentPosition() + p);
        FR.setTargetPosition(FR.getCurrentPosition() + p);
        BL.setTargetPosition(BL.getCurrentPosition() + p);
        BR.setTargetPosition(BR.getCurrentPosition() + p);
    }

    protected void setEnc(int p1, int p2, int p3, int p4) {
        FL.setTargetPosition(FL.getCurrentPosition() + p1);
        FR.setTargetPosition(FR.getCurrentPosition() + p2);
        BL.setTargetPosition(BL.getCurrentPosition() + p3);
        BR.setTargetPosition(BR.getCurrentPosition() + p4);
    }

    protected void moveEncodersREAL(double distanceInches, double power){
        // Reset encoders
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int currentPos1 = FL.getCurrentPosition();
        int currentPos2 = FR.getCurrentPosition();
        int currentPos3 = BL.getCurrentPosition();
        int currentPos4 = BR.getCurrentPosition();
        //distanceTics is num of tics it needs to travel
        int distanceTics = (int)(distanceInches * CPI);

        // Prepare to drive to target position
        setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set target position and speed
        FL.setTargetPosition(currentPos1 + distanceTics);
        FR.setTargetPosition(currentPos2 + distanceTics);
        BL.setTargetPosition(currentPos3 + distanceTics);
        BR.setTargetPosition(currentPos4 + distanceTics);

        FL.setPower(power);
        FR.setPower(power);
        BL.setPower(power);
        BR.setPower(power);

        // Loop while we approach the target.  Display position as we go
        while(FR.isBusy() && FL.isBusy() && BL.isBusy() && BR.isBusy()) {
            telemetry.addData("FL Encoder", FL.getCurrentPosition());
            telemetry.addData("BL Encoder", BL.getCurrentPosition());
            telemetry.addData("FR Encoder", FR.getCurrentPosition());
            telemetry.addData("BR Encoder", BR.getCurrentPosition());
            telemetry.update();
        }

        // We are done, turn motors off and switch back to normal driving mode.
        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);
    }

    protected void strafeEncoders(double distanceInches, double pwr){

        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int currentPos1 = FL.getCurrentPosition();
        int currentPos2 = FR.getCurrentPosition();
        int currentPos3 = BL.getCurrentPosition();
        int currentPos4 = BR.getCurrentPosition();
        //distanceTics is num of tics it needs to travel
        int distanceTics = (int)(distanceInches * CPI);

        setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FL.setTargetPosition(currentPos1 + distanceTics);
        FR.setTargetPosition(currentPos2 - distanceTics);
        BL.setTargetPosition(currentPos3 - distanceTics);
        BR.setTargetPosition(currentPos4 + distanceTics);

        if (distanceInches>0) {
            BR.setPower(pwr);
            BL.setPower(-pwr);
            FL.setPower(-pwr);
            FR.setPower(pwr);
        }
        else {
            BR.setPower(-pwr);
            BL.setPower(pwr);
            FL.setPower(pwr);
            FR.setPower(-pwr);
        }
        int count = 0;

        while(FR.isBusy() && FL.isBusy() && BL.isBusy() && BR.isBusy()) {
            count++;
            telemetry.addData("FL Encoder", FL.getCurrentPosition());
            telemetry.addData("BL Encoder", BL.getCurrentPosition());
            telemetry.addData("FR Encoder", FR.getCurrentPosition());
            telemetry.addData("BR Encoder", BR.getCurrentPosition());
            telemetry.addData("count:",count);
            telemetry.update();
        }
        setDrive(0);
        holdUp(5);
    }

    protected void holdUp(double num)
    {
        timer.reset();
        while (timer.seconds()<num)
        {}
    }

    protected void setStrafe(double pwr)
    {
        BR.setPower(-pwr);
        BL.setPower(pwr);
        FL.setPower(-pwr);
        FR.setPower(pwr);
    }

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();

        initTime = timer.milliseconds();
        while (timer.milliseconds() - initTime < 4000) {
            setDrive(0.3);
            telemetry.addData("FL Encoder", FL.getCurrentPosition());
            telemetry.addData("BL Encoder", BL.getCurrentPosition());
            telemetry.addData("FR Encoder", FR.getCurrentPosition());
            telemetry.addData("BR Encoder", BR.getCurrentPosition());
            telemetry.update();
        }
        setDrive(0);

      /* while (timer.seconds() < 5){
            setStrafe(0.4);
        }
        holdUp(2);
        while (timer.seconds() < 12){
            setStrafe(-0.4);
        }*/


        // 24 goes 11, 48 goes 22 for straight
        // 48 goes 18 for strafing

    }
}