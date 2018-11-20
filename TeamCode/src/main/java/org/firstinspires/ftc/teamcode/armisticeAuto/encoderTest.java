package org.firstinspires.ftc.teamcode.armisticeAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Angela on 11/20/2018.
 */

@Autonomous (name = "Encoder Test")
public class encoderTest extends armisticeAutoSuper{

    static double CPI = (1120.0 * 0.66666)/(4.0 * Math.PI);

    public void runOpMode(){

        initialize(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        moveEncoders(12, 1);

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
}