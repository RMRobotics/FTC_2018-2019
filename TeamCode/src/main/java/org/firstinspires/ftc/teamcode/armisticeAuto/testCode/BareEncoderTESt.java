package org.firstinspires.ftc.teamcode.armisticeAuto.testCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Angela on 1/17/2019.
 */
@Autonomous(name = "bareEncoderTest", group = "autotest")
public class BareEncoderTESt extends LinearOpMode{

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
        BR.setDirection(DcMotor.Direction.REVERSE);

        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
    }

    protected void setDrive(double p) {
        FL.setPower(p);
        FR.setPower(p);
        BL.setPower(p);
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

    protected void moveEncodersMod(double distanceInches, double pwr){
        int currentPos1 = FL.getCurrentPosition();
        int currentPos2 = FR.getCurrentPosition();
        int currentPos3 = BL.getCurrentPosition();
        int currentPos4 = BR.getCurrentPosition();
        //distanceTics is num of tics it needs to travel
        int distanceTics = (int)(distanceInches * CPI);

        int targetDistance = FL.getCurrentPosition() + distanceTics;

        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        setMode(DcMotor.RunMode.RUN_USING_ENCODER);/*
//        setMode(DcMotor.RunMode.RUN_TO_POSITION);

        /*FL.setTargetPosition(currentPos1 + distanceTics);
        FR.setTargetPosition(currentPos2 + distanceTics);
        BL.setTargetPosition(currentPos3 + distanceTics);
        BR.setTargetPosition(currentPos4 + distanceTics);*/
        int count = 0;

        while ((targetDistance - FL.getCurrentPosition()) > 5 && !gamepad1.b){

            setDrive(pwr);

            telemetry.addData("FL encoder: " , FL.getCurrentPosition());
            telemetry.addData("BL encoder: " , BL.getCurrentPosition());
            telemetry.addData("FR encoder: " , FR.getCurrentPosition());
            telemetry.addData("BR encoder: " , BR.getCurrentPosition());
            telemetry.update();
        }

        setDrive(0);

//        setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("dope",String.valueOf(count));
        telemetry.update();
    }

    protected void moveEncoders(double distanceInches){

        int currentPos1 = FL.getCurrentPosition();
        int currentPos2 = FR.getCurrentPosition();
        int currentPos3 = BL.getCurrentPosition();
        int currentPos4 = BR.getCurrentPosition();
        int[] currentPoses = {currentPos1,currentPos2,currentPos3,currentPos4};
        //distanceTics is num of tics it needs to travel
        int distanceTics = (int)(distanceInches * CPI);

        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FL.setTargetPosition(currentPos1 + distanceTics);
        FR.setTargetPosition(currentPos2 + distanceTics);
        BL.setTargetPosition(currentPos3 + distanceTics);
        BR.setTargetPosition(currentPos4 + distanceTics);

        telemetry.addData(String.valueOf(distanceTics),"");
        for (int i : currentPoses)
        {
            telemetry.addData(String.valueOf(i + distanceTics),"");
        }
        telemetry.update();
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        while (timer.seconds()<3){

        }

        if (distanceInches>0)
            setDrive(0.5);
        else
            setDrive(-0.5);

        int count = 0;

        while (FL.isBusy() && !gamepad1.b){
            count++;
            telemetry.addData("FL Encoder", FL.getCurrentPosition());
            telemetry.addData("BL Encoder", BL.getCurrentPosition());
            telemetry.addData("FR Encoder", FR.getCurrentPosition());
            telemetry.addData("BR Encoder", BR.getCurrentPosition());
            telemetry.update();
        }

        setDrive(0);

        setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("dope",String.valueOf(count));
        telemetry.update();
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


        /*FL.setPower(.5);
        timer.reset();
        while (timer.seconds()<5)
        {

        }
        setDrive(0);

        FR.setPower(.5);
        timer.reset();
        while (timer.seconds()<5)
        {

        }
        setDrive(0);

        BL.setPower(.5);
        timer.reset();
        while (timer.seconds()<5)
        {

        }
        setDrive(0);

        BR.setPower(.5);*/
        timer.reset();
        while (timer.seconds()<5)
        {

        }
        setDrive(0);

//        moveEncodersMod(48, 0.4);
//        moveEncoders(48);

    }
}
