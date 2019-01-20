package org.firstinspires.ftc.teamcode.armisticeAuto;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.IIMU;

/**
 * Created by Angelita on 11/20/2018.
 */

public abstract class armisticeAutoSuper extends LinearOpMode {

    protected DcMotor FL;
    protected DcMotor FR;
    protected DcMotor BL;
    protected DcMotor BR;
    protected DcMotor lift;
    protected DcMotor arm;
    protected CRServo intake;
    protected ElapsedTime timer = new ElapsedTime();
    protected BNO055IMU rev;
    protected IIMU imu;
    protected DistanceSensor sensorRange;
    protected Orientation angles;
    protected Acceleration gravity;
    static double CPI = (1120.0 * 0.66666)/(4.0 * Math.PI);


    public void initialize (Boolean i) {
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");
        intake = hardwareMap.crservo.get("intake");

        lift = hardwareMap.dcMotor.get("lift");
        arm = hardwareMap.dcMotor.get("arm");
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intake.setPower(0);

        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);
//        sensorRange = hardwareMap.get(DistanceSensor.class, "sensor_range");
//        setZeroMode(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        rev = hardwareMap.get(BNO055IMU.class, "imu");
        imu = new RevIMU(rev);
        imu.initialize();
        imu.setOffset(0);

        waitForStart();
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

    protected void setStrafe(double pwr)
    {
        BR.setPower(pwr);
        BL.setPower(-pwr);
        FL.setPower(pwr);
        FR.setPower(-pwr);
    }

    protected void print(String message, double time)
    {
        telemetry.addData(message,"");
        telemetry.update();
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

    //fine adjustment aligning to the gold unobtainium
    protected void yellowAlign(GoldAlignDetector detector){

        //set the tolerance to something smaller than 100
        detector.setAlignSettings(0, 50);

        //check if the robot is on the left or on the right
        while(!(detector.getAligned().equals(Direction.CENTER))){

            //strafe based on position of the robot in relation to yellow block
            if(detector.getAligned().equals(Direction.LEFT)){
                FL.setPower(-.2);
                FR.setPower(.2);
                BL.setPower(.2);
                BR.setPower(-.2);
            }
            else{
                FL.setPower(.2);
                FR.setPower(-.2);
                BL.setPower(-.2);
                BR.setPower(.2);
            }
        }

        //stop in front of block
        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);
    }

    protected void dumbstrafeEncoders(double distanceInches, int dir, double pwr){
        double angle = imu.getZAngle();
        int currentPos1 = FL.getCurrentPosition();
        int currentPos2 = FR.getCurrentPosition();
        int currentPos3 = BL.getCurrentPosition();
        int currentPos4 = BR.getCurrentPosition();
        int distanceTics = dir*(int)(distanceInches * CPI);
        double tickRatio;
        int testCount = 0;

        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FL.setTargetPosition(currentPos1 + distanceTics);
        FR.setTargetPosition(currentPos2 - distanceTics);
        BL.setTargetPosition(currentPos3 - distanceTics);
        BR.setTargetPosition(currentPos4 + distanceTics);

        if (dir == 1) {
            BR.setPower(pwr);
            BL.setPower(-pwr);
            FL.setPower(pwr);
            FR.setPower(-pwr);
        }

        if (dir == -1) {
            BR.setPower(-pwr);
            BL.setPower(pwr);
            FL.setPower(-pwr);
            FR.setPower(pwr);
        }

        while(FL.isBusy() /*&& BL.isBusy() && BR.isBusy() && FL.isBusy()*/){
            telemetry.addData(String.valueOf(testCount)," " + String.valueOf(FL.getCurrentPosition()));
            telemetry.update();
            testCount++;
            if (Math.abs(-imu.getXAngle() - angle) >= 15){
                currentPos1 = FL.getCurrentPosition();
                imuTurn(-(imu.getXAngle() - angle),0.3);
                //wheelFL.setTargetPosition(wheelFL.getTargetPosition() + wheelFL.getCurrentPosition() - pos);
                angle = imu.getXAngle();
                if (dir == 1) {
                    BR.setPower(pwr);
                    BL.setPower(-pwr);
                    FL.setPower(pwr);
                    FR.setPower(-pwr);
                }
                if (dir == -1) {
                    BR.setPower(-pwr);
                    BL.setPower(pwr);
                    FL.setPower(-pwr);
                    FR.setPower(pwr);
                }
            }
        }
        FR.setPower(0);
        BR.setPower(0);
        FL.setPower(0);
        BL.setPower(0);
        double angleFinal = -(imu.getXAngle() - angle);
        imuTurn(angleFinal,0.3);
    }

    protected void moveEncoders(double distanceInches){

        int currentPos1 = FL.getCurrentPosition();
        int currentPos2 = FR.getCurrentPosition();
        int currentPos3 = BL.getCurrentPosition();
        int currentPos4 = BR.getCurrentPosition();
        //distanceTics is num of tics it needs to travel
        int distanceTics = (int)(distanceInches * CPI);

        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FL.setTargetPosition(currentPos1 + distanceTics);
        FR.setTargetPosition(currentPos2 + distanceTics);
        BL.setTargetPosition(currentPos3 + distanceTics);
        BR.setTargetPosition(currentPos4 + distanceTics);

        if (distanceInches>0)
            setDrive(0.8);
        else
            setDrive(-0.8);

        int count = 0;

        while (FL.isBusy()){
            count++;
            telemetry.addData(String.valueOf(count),"");
            telemetry.update();
        }

        setDrive(0);

        setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("dope","");
        telemetry.update();
    }

    protected void strafeEncoders(double distanceInches, double pwr){

        int currentPos1 = FL.getCurrentPosition();
        int currentPos2 = FR.getCurrentPosition();
        int currentPos3 = BL.getCurrentPosition();
        int currentPos4 = BR.getCurrentPosition();
        //distanceTics is num of tics it needs to travel
        int distanceTics = (int)(distanceInches * CPI);

        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FL.setTargetPosition(currentPos1 + distanceTics);
        FR.setTargetPosition(currentPos2 - distanceTics);
        BL.setTargetPosition(currentPos3 - distanceTics);
        BR.setTargetPosition(currentPos4 + distanceTics);

        if (distanceInches>0) {
            BR.setPower(pwr);
            BL.setPower(-pwr);
            FL.setPower(pwr);
            FR.setPower(-pwr);
        }
        else {
            BR.setPower(-pwr);
            BL.setPower(pwr);
            FL.setPower(-pwr);
            FR.setPower(pwr);
        }

        int count = 0;

        while (FL.isBusy()){
            count++;
            telemetry.addData(String.valueOf(count),"");
            telemetry.update();
        }

        setDrive(0);

        setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("dope","");
        telemetry.update();
    }

    protected void dankEncoders(double distanceInches, int dir){
        //dir of 1 will set left drive train's target to be negative
        double speed = 0.5 * dir;
        int currentPos = FR.getCurrentPosition();
        //distanceTics is num of tics it needs to travel
        int distanceTics = (int)(distanceInches * CPI);
        double tickRatio;

        FR.setTargetPosition(currentPos + distanceTics);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

//        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        BR.setTargetPosition(currentPos + distanceTics);
//        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        FL.setTargetPosition(currentPos + distanceTics);
//        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        BL.setTargetPosition(currentPos + distanceTics);
//        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        BR.setPower(speed);
        BL.setPower(speed);
        FL.setPower(speed);
        FR.setPower(speed);

        print("checkpoint 1",0.5);

        while(FR.isBusy()){ //  && BL.isBusy() && BR.isBusy() && FL.isBusy()){
            tickRatio = ((double)FR.getCurrentPosition() - (double)currentPos) / distanceTics;
//            speed = dir * ((-0.5) * (tickRatio) + 0.5);
            if (dir > 0){
                if (speed < 0.15)
                    speed = 0.15;
            }
            else if (speed > -0.15){
                    speed = -0.15;
            }
            print(String.valueOf(FR.getCurrentPosition())+" " +distanceTics,1);
            BR.setPower(speed);
            BL.setPower(speed);
            FL.setPower(speed);
            FR.setPower(speed);
        }
        FR.setPower(0);
        BR.setPower(0);
        FL.setPower(0);
        BL.setPower(0);

        print("checkpoint 2",0.5);
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
            if (Math.abs(imu.getXAngle()-degree)<err) {
                flag = false;
                FL.setPower(0);
                BL.setPower(0);
                FR.setPower(0);
                BR.setPower(0);
            }
            else if (dir_cw && imu.getXAngle()>degree)
            {
                FL.setPower(-1*pwr);
                BL.setPower(-1*pwr);
                FR.setPower(pwr);
                BR.setPower(pwr);
                count+=1;
                dir_cw = !dir_cw;
            }
            else if (!dir_cw && imu.getXAngle()<degree)
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