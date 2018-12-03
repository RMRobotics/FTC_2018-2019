package org.firstinspires.ftc.teamcode.armisticeAuto;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.IIMU;

/**
 * Created by Angelita on 11/20/2018.
 */

public abstract class armisticeAutoSuper extends LinearOpMode {

    protected DcMotor FL;
    protected DcMotor FR;
    protected DcMotor BL;
    protected DcMotor BR;
    protected ElapsedTime timer = new ElapsedTime();
    protected BNO055IMU rev;
    protected IIMU imu;
    static double CPI = (1120.0 * 0.66666)/(4.0 * Math.PI);

    public void initialize (DcMotor.RunMode r) {
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");
        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);
        setZeroMode(DcMotor.ZeroPowerBehavior.BRAKE);
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(r);
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

    protected void setDrive(double p) {
        FL.setPower(p);
        FR.setPower(p);
        BL.setPower(p);
        BR.setPower(p);
    }

    protected void print(String message, double time)
    {
        telemetry.log().add(message);
        holdUp(time);
    }

    protected void holdUp(double num)
    {
        timer.reset();
        while (timer.seconds()<num)
        {}
    }

    protected void DogeCVYellowDetector(GoldAlignDetector detector){
        // Set up detector
        detector = new GoldAlignDetector(); // Create detector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
        detector.useDefaults(); // Set detector to use default settings

        // Optional tuning
        detector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.4; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005; //

        detector.ratioScorer.weight = 5; //
        detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment

        detector.enable(); // Start the detector!
    }

    protected void strafeEncoders(double distanceInches, int dir){
        double angle = imu.getZAngle();
        int currentPos = FL.getCurrentPosition(), pos;
        int distanceTics = dir*(int)(distanceInches * CPI);
        double tickRatio;

        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setTargetPosition(currentPos + distanceTics);
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (dir == 1) {
            BR.setPower(1);
            BL.setPower(-1);
            FL.setPower(1);
            FR.setPower(-1);
        }

        if (dir == -1) {
            BR.setPower(-1);
            BL.setPower(1);
            FL.setPower(-1);
            FR.setPower(1);
        }

        while(FL.isBusy() /*&& BL.isBusy() && BR.isBusy() && FL.isBusy()*/){
            if (Math.abs(-imu.getZAngle() - angle) >= 15){
//                pos = FL.getCurrentPosition();
                imuTurn(-(imu.getZAngle() - angle),0.3);
                //wheelFL.setTargetPosition(wheelFL.getTargetPosition() + wheelFL.getCurrentPosition() - pos);
                angle = imu.getZAngle();
                if (dir == 1) {
                    BR.setPower(1);
                    BL.setPower(-1);
                    FL.setPower(1);
                    FR.setPower(-1);
                }
                if (dir == -1) {
                    BR.setPower(-1);
                    BL.setPower(1);
                    FL.setPower(-1);
                    FR.setPower(1);
                }
            }
        }
        FR.setPower(0);
        BR.setPower(0);
        FL.setPower(0);
        BL.setPower(0);
        double angleFinal = -(imu.getZAngle() - angle);
        imuTurn(angleFinal,0.3);
    }

    protected void moveEncoders(double distanceInches, int dir){
        //dir of 1 will set left drive train's target to be negative
        double speed = 0.25 * dir;
        int currentPos = FR.getCurrentPosition();
        //distanceTics is num of tics it needs to travel
        int distanceTics = (int)(distanceInches * CPI);
        double tickRatio;

        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setTargetPosition(currentPos + distanceTics);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        BR.setPower(speed);
        BL.setPower(speed);
        FL.setPower(speed);
        FR.setPower(speed);

        telemetry.addData("checkpoint1","");
        holdUp(0.5);

        while(FR.isBusy() /*&& BL.isBusy() && BR.isBusy() && FL.isBusy()*/){
            tickRatio = ((double)FR.getCurrentPosition() - (double)currentPos) / distanceTics;
            speed = dir * ((-0.5) * (tickRatio) + 0.5);
            if (dir > 0){
                if (speed < 0.15)
                    speed = 0.15;
            }
            if (dir < 0){
                if (speed > -0.15)
                    speed = -0.15;
            }
            print(String.valueOf(FR.getCurrentPosition()),0);
            BR.setPower(speed);
            BL.setPower(speed);
            FL.setPower(speed);
            FR.setPower(speed);
        }
        FR.setPower(0);
        BR.setPower(0);
        FL.setPower(0);
        BL.setPower(0);

        telemetry.addData("checkpoint2","");
        holdUp(0.5);
    }

    protected void imuTurn(double degree, double speed) {
        imu.setOffset(0);
        double err = 1.2, pwr = 0;
        int count = 0;
        boolean flag = true;
        boolean dir_cw;
        if (degree>0)
        {
            dir_cw = true;
            FL.setPower(speed);
            BL.setPower(speed);
            FR.setPower(-1*speed);
            BR.setPower(-1*speed);
        }
        else
        {
            dir_cw = false;
            FL.setPower(-1*speed);
            BL.setPower(-1*speed);
            FR.setPower(speed);
            BR.setPower(speed);
        }

        while (flag)
        {
            if (Math.abs(imu.getZAngle()-degree)<err) {
                flag = false;
                FL.setPower(0);
                BL.setPower(0);
                FR.setPower(0);
                BR.setPower(0);
            }
            else if (dir_cw && imu.getZAngle()>degree)
            {
                FL.setPower(-1*pwr);
                BL.setPower(-1*pwr);
                FR.setPower(pwr);
                BR.setPower(pwr);
                count+=1;
                dir_cw = !dir_cw;
            }
            else if (!dir_cw && imu.getZAngle()<degree)
            {
                FL.setPower(pwr);
                BL.setPower(pwr);
                FR.setPower(-1*pwr);
                BR.setPower(-1*pwr);
                count+=1;
                dir_cw = !dir_cw;
            }
        }
        FL.setPower(0);
        BL.setPower(0);
        FR.setPower(0);
        BR.setPower(0);
    }
}