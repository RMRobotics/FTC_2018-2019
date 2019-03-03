package org.firstinspires.ftc.teamcode.armisticeAuto;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.DanCV.Detection.Detector;
import org.firstinspires.ftc.teamcode.DanCV.UI.CVViewActivity;
import org.firstinspires.ftc.teamcode.IIMU;
import org.firstinspires.ftc.teamcode.DanCV.Detection.MineralDetector;

/**
 * Created by Angelita on 11/20/2018.
 */

public abstract class armisticeAutoSuper extends LinearOpMode {

    protected DcMotor FL;
    protected DcMotor FR;
    protected DcMotor BL;
    protected DcMotor BR;
    protected DcMotor lift;
    protected DcMotor hook;
    protected CRServo intake;
    protected Servo marker;
    protected ElapsedTime timer = new ElapsedTime();
    protected BNO055IMU rev;
    protected IIMU imu;
    protected DistanceSensor sensorRange;
    protected Orientation angles;
    protected Acceleration gravity;
    //Also if something not accounted for
    protected MineralDetector detector;
    public static double CPI = (1120.0 * 0.66666)/(4.0 * Math.PI);


    public void initialize (Boolean i) {
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");
//        intake = hardwareMap.crservo.get("intake");

        marker = hardwareMap.servo.get("marker");

        hook = hardwareMap.dcMotor.get("hook");
        hook.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hook.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hook.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        FR.setDirection(DcMotor.Direction.REVERSE);
        BR.setDirection(DcMotor.Direction.REVERSE);

       setZeroMode(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        rev = hardwareMap.get(BNO055IMU.class, "imu");
        imu = new RevIMU(rev);
        imu.initialize();
        imu.setOffset(0);


        //This is just in case something has not to be accounted for
        detector = new MineralDetector();
        detector.init(hardwareMap.appContext,CVViewActivity.getInstance(),1);
        //detector.activate();

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

    protected MineralDetector DanCVMineralDetector(MineralDetector detector){
        detector = new MineralDetector();
        detector.init(hardwareMap.appContext, CVViewActivity.getInstance(),1);
        detector.activate();
        return detector;
    }

    protected void DogeCVYellowDetector(GoldAlignDetector detector){
        // Set up detector
        detector = new GoldAlignDetector(); // Create detector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance(),1,false); // Initialize it with the app context and camera
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

    protected void moveEncoders(double distanceInches, double power){
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
        /* if (distanceInches>0) {
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
        }*/

        int count = 0;

        while (FL.isBusy() && !gamepad1.b){
            count++;
            telemetry.addData(String.valueOf(count),"");
            telemetry.update();
        }

        setDrive(0);

        setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("dope",count);
        telemetry.update();
    }

    protected void moveEncodersCount(int encoderCount, double power) {
// Reset encoders
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("FL Encoder", FL.getCurrentPosition());
        telemetry.addData("BL Encoder", BL.getCurrentPosition());
        telemetry.addData("FR Encoder", FR.getCurrentPosition());
        telemetry.addData("BR Encoder", BR.getCurrentPosition());
        telemetry.addData("power:", power);
        telemetry.update();
        holdUp(2);

        // Set target position and speed
        FL.setTargetPosition(encoderCount);
        FR.setTargetPosition(encoderCount);
        BL.setTargetPosition(encoderCount);
        BR.setTargetPosition(encoderCount);

        FL.setPower(power);
        FR.setPower(power);
        BL.setPower(power);
        BR.setPower(power);

//        int lastFL = FL.getCurrentPosition(), lastFR = FR.getCurrentPosition(), lastBL = BL.getCurrentPosition(), lastBR = BR.getCurrentPosition();

        // Loop while we approach the target.  Display position as we go
        while (FR.isBusy() && FL.isBusy() && BL.isBusy() && BR.isBusy()) {
            /*telemetry.addData("FL dif:", FL.getCurrentPosition() - lastFL);
            telemetry.addData("FR dif:", FR.getCurrentPosition() - lastFR);
            telemetry.addData("BL dif:", BL.getCurrentPosition() - lastBL);
            telemetry.addData("BR dif:", BR.getCurrentPosition() - lastBR);*/
            telemetry.addData("FL Encoder", FL.getCurrentPosition());
            telemetry.addData("BL Encoder", BL.getCurrentPosition());
            telemetry.addData("FR Encoder", FR.getCurrentPosition());
            telemetry.addData("BR Encoder", BR.getCurrentPosition());
            telemetry.update();
            /*lastFL = FL.getCurrentPosition();
            lastFR = FR.getCurrentPosition();
            lastBL = BL.getCurrentPosition();
            lastBR = BR.getCurrentPosition();*/
        }
    }

    protected void strafeEncodersCount(int encoderCount, double power, Direction dir) {
// Reset encoders
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        telemetry.addData("FL Encoder", FL.getCurrentPosition());
        telemetry.addData("BL Encoder", BL.getCurrentPosition());
        telemetry.addData("FR Encoder", FR.getCurrentPosition());
        telemetry.addData("BR Encoder", BR.getCurrentPosition());
        telemetry.addData("power:", power);
        telemetry.update();
        holdUp(2);

        if (dir.equals(Direction.LEFT)){
            FL.setTargetPosition(encoderCount);
            FR.setTargetPosition(-encoderCount);
            BL.setTargetPosition(-encoderCount);
            BR.setTargetPosition(encoderCount);

            FL.setPower(power);
            FR.setPower(-power);
            BL.setPower(-power);
            BR.setPower(power);
        }
        else {
            FL.setTargetPosition(-encoderCount);
            FR.setTargetPosition(encoderCount);
            BL.setTargetPosition(encoderCount);
            BR.setTargetPosition(-encoderCount);

            FL.setPower(-power);
            FR.setPower(power);
            BL.setPower(power);
            BR.setPower(-power);
        }


        // Loop while we approach the target.  Display position as we go
        while (FR.isBusy() && FL.isBusy() && BL.isBusy() && BR.isBusy()) {
            telemetry.addData("FL Encoder", FL.getCurrentPosition());
            telemetry.addData("BL Encoder", BL.getCurrentPosition());
            telemetry.addData("FR Encoder", FR.getCurrentPosition());
            telemetry.addData("BR Encoder", BR.getCurrentPosition());
            telemetry.update();
        }
    }

    protected void imuInfo(){
        telemetry.addData("Angle: ", imu.getZAngle());
        telemetry.update();
    }

    protected void dropMarker(){
        marker.setPosition(0.75);
        holdUp(0.25);
        marker.setPosition(0.25);
    }

    protected void raiseMarker(){
        marker.setPosition(0);
    }

    protected void extendHook() {
        telemetry.addData("Encoder Val", hook.getCurrentPosition());
        telemetry.update();
        hook.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hook.setTargetPosition(13500);
        hook.setPower(1);
        while (hook.isBusy()) {

            telemetry.addData("Encoder Val", hook.getCurrentPosition());
            telemetry.update();
        }
        hook.setPower(0);
    }

    public void retractHook(){
        telemetry.addData("Encoder Val", hook.getCurrentPosition());
        telemetry.update();

        hook.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hook.setTargetPosition(0);
        hook.setPower(-0.4);
        while (hook.isBusy()) {

        }
        hook.setPower(0);
    }

    protected void imuTurn(double degree, double speed, boolean reset) {
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        imu.initialize();
        if (reset)
            imu.setOffset(0);
        double err = 1.2, pwr = 0.5;
        speed = -speed;

        int count = 2;
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
            holdUp(1);
            FL.setPower(-1*speed);
            BL.setPower(-1*speed);
            FR.setPower(speed);
            BR.setPower(speed);
        }

        while (flag && !gamepad1.b)
        {
            telemetry.addData("imu X",imu.getXAngle());
            telemetry.addData("imu Y",imu.getYAngle());
            telemetry.addData("imu Z",imu.getZAngle());
            telemetry.update();
            if (Math.abs(imu.getZAngle()-degree)<err) {
                flag = false;
                FL.setPower(0);
                BL.setPower(0);
                FR.setPower(0);
                BR.setPower(0);
            }
            else if (dir_cw && imu.getZAngle()>degree)
            {
               pwr = speed/count;
                if (pwr < 0.15)
                    pwr = 0.15;
                FL.setPower(pwr);
                BL.setPower(pwr);
                FR.setPower(-pwr);
                BR.setPower(-pwr);
                count+=1;
                dir_cw = !dir_cw;
            }
            else if (!dir_cw && imu.getZAngle()<degree)
            {
               pwr = speed/count;
                if (pwr < 0.15)
                    pwr = 0.15;
                FL.setPower(-pwr);
                BL.setPower(-pwr);
                FR.setPower(pwr);
                BR.setPower(pwr);
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