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

        FR.setDirection(DcMotor.Direction.REVERSE);
        BR.setDirection(DcMotor.Direction.REVERSE);

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

    protected void moveEncodersBasic(double distanceInches, double power){
        // Reset encoders
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Prepare to drive to target position
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set target position and speed
        FL.setTargetPosition(3440);
        FR.setTargetPosition(3440);
//        BL.setTargetPosition(1440);
//       BR.setTargetPosition(1440);
        FL.setPower(power);
        FR.setPower(power);
        BL.setPower(power);
        BR.setPower(power);
        telemetry.addData("checkpoint 2","");
        telemetry.update();

        boolean flag = true;

        timer.reset();
//        double BRtime=-1,BLtime=-1,FRtime=-1,FLtime=-1;

        // Loop while we approach the target.  Display position as we go
        while(FR.isBusy() && FL.isBusy()) {

           /* telemetry.addData("FL stop time:",FLtime);
            telemetry.addData("FR stop time:",FRtime);
            telemetry.addData("BL stop time:",BLtime);
            telemetry.addData("BR stop time:",BRtime);
            telemetry.addData("time:",timer.milliseconds());*/

            telemetry.addData("FL Encoder", FL.getCurrentPosition());
            telemetry.addData("BL Encoder", BL.getCurrentPosition());
            telemetry.addData("FR Encoder"  , FR.getCurrentPosition());
            telemetry.addData("BR Encoder", BR.getCurrentPosition());
            telemetry.update();
        }

        // We are done, turn motors off and switch back to normal driving mode.

        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);

//        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        timer.reset();
        while (timer.seconds()<3)
        {

        }

    }

    protected void moveEncoders(double distanceInches){

        int currentPos1 = FL.getCurrentPosition();
        int currentPos2 = FR.getCurrentPosition();
        int currentPos3 = BL.getCurrentPosition();
        int currentPos4 = BR.getCurrentPosition();
        //distanceTics is num of tics it needs to travel
        int distanceTics = (int)(distanceInches * CPI);

        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FL.setTargetPosition(currentPos1 + distanceTics);
        FR.setTargetPosition(currentPos2 + distanceTics);
        BL.setTargetPosition(currentPos3 + distanceTics);
//        BL.setTargetPosition(0);

        BR.setTargetPosition(currentPos4 + distanceTics);

        if (distanceInches>0)
            setDrive(0.4);
        else
            setDrive(-0.4);

        int count = 0;

        while (FL.isBusy() || FR.isBusy() || BL.isBusy() || BR.isBusy()){
                count++;
                telemetry.addData("FL Encoder", FL.getCurrentPosition());
                telemetry.addData("BL Encoder", BL.getCurrentPosition());
                telemetry.addData("FR Encoder", FR.getCurrentPosition());
                telemetry.addData("BR Encoder", BR.getCurrentPosition());
                telemetry.update();
            }

        setDrive(0);

//        setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("dope","");
        telemetry.update();

    }

    protected void driveEncoder(int val, double power) {
        double mag = Math.abs(power);
//        val = val*scale;
        double dir = Math.signum(val - FL.getCurrentPosition());
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//                setEnc(val);
        int shift = 0;
        // TODO: check to see if acceleration code functions properly
        while (Math.abs(FL.getCurrentPosition() - val) > 5 && opModeIsActive()) {
            telemetry.addData("FL Encoder", FL.getCurrentPosition());
            telemetry.addData("BL Encoder", BL.getCurrentPosition());
            telemetry.addData("FR Encoder", FR.getCurrentPosition());
            telemetry.addData("BR Encoder", BR.getCurrentPosition());
            telemetry.update();

            if (shift * 0.02 < mag) {
                setDrive(dir * shift * 0.05);
                shift++;
                sleep(200);
            } else {
                setDrive(dir * mag);
            }
        }
    }

        protected void moveEncodersGerm(double distanceInches, int dir){

            double speed = 0.5 * dir;
            int currentPos = FL.getCurrentPosition();
            int distanceTics = (int)(distanceInches * CPI);
            double tickRatio;

            FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            FL.setTargetPosition(currentPos + distanceTics);
            FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BR.setPower(speed);
            BL.setPower(speed);
            FL.setPower(speed);
            FR.setPower(speed);


            while(FL.isBusy() /*&& BL.isBusy() && BR.isBusy() && FL.isBusy()*/){
                telemetry.addData("FL Encoder", FL.getCurrentPosition());
                telemetry.addData("BL Encoder", BL.getCurrentPosition());
                telemetry.addData("FR Encoder", FR.getCurrentPosition());
                telemetry.addData("BR Encoder", BR.getCurrentPosition());
                telemetry.update();

                tickRatio = ((double)FL.getCurrentPosition() - (double)currentPos) / distanceTics;
                speed = dir * ((-0.5) * (tickRatio) + 0.5);
                if (dir > 0){
                    if (speed < 0.15)
                        speed = 0.15;
                }
                if (dir < 0){
                    if (speed > -0.15)
                        speed = -0.15;
                }
                BR.setPower(speed);
                BL.setPower(speed);
                FL.setPower(speed);
                FR.setPower(speed);
            }
            FR.setPower(0);
            BR.setPower(0);
            FL.setPower(0);
            BL.setPower(0);

        }


    @Override
    public void runOpMode() throws InterruptedException {

        initialize();

       /* initTime = timer.milliseconds();
        while (timer.milliseconds() - initTime < 4000) {
            setDrive(0.3);
            telemetry.addData("FL Encoder", FL.getCurrentPosition());
            telemetry.addData("BL Encoder", BL.getCurrentPosition());
            telemetry.addData("FR Encoder", FR.getCurrentPosition());
            telemetry.addData("BR Encoder", BR.getCurrentPosition());
            telemetry.update();
        }
        setDrive(0);*/

     /*setDrive(0.4);
        timer.reset();
        while (timer.seconds()<5)
        {

        }*/

      /*  FL.setPower(.5);
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

        BR.setPower(.5);
        timer.reset();
        while (timer.seconds()<5);
        {

        }
        setDrive(0);
*/



//       moveEncodersMod(48, 0.4);
//       moveEncoders(36);
//       driveEncoder(48,0.5);
       moveEncodersBasic(60, 0.6);
//         moveEncodersGerm(36,1);

    }
}