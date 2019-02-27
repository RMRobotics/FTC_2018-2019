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
    protected GoldAlignDetector detector = null;
    static double CPI = (1120.0 * 0.66666)/(4.0 * Math.PI);


    public void initialize (Boolean i) {
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");
//        intake = hardwareMap.crservo.get("intake");

        /*lift = hardwareMap.dcMotor.get("lift");
        arm = hardwareMap.dcMotor.get("arm");*/
//        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

//        intake.setPower(0);

//        sensorRange = hardwareMap.get(DistanceSensor.class, "sensor_range");
//        setZeroMode(DcMotor.ZeroPowerBehavior.BRAKE);

        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);

        setZeroMode(DcMotor.ZeroPowerBehavior.BRAKE);
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rev = hardwareMap.get(BNO055IMU.class, "imu");
        imu = new RevIMU(rev);
        imu.initialize();
        imu.setOffset(0);

        telemetry.addData("Status:", "IMU Initialized");
        telemetry.update();

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

        while(FL.isBusy() /*&& BL.isBusy() && BR.isBusy() && FL.isBusy()*/ && !gamepad1.b){
            telemetry.addData(String.valueOf(testCount)," " + String.valueOf(FL.getCurrentPosition()));
            telemetry.update();
            testCount++;
            if (Math.abs(-imu.getZAngle() - angle) >= 10){
                currentPos1 = FL.getCurrentPosition();
                imuTurn(-(imu.getZAngle() - angle),0.3);
                //wheelFL.setTargetPosition(wheelFL.getTargetPosition() + wheelFL.getCurrentPosition() - pos);
                angle = imu.getZAngle();
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

        while(FR.isBusy() && FL.isBusy() && BL.isBusy() && BR.isBusy()) {
            telemetry.addData("FL Encoder", FL.getCurrentPosition());
            telemetry.addData("BL Encoder", BL.getCurrentPosition());
            telemetry.addData("FR Encoder", FR.getCurrentPosition());
            telemetry.addData("BR Encoder", BR.getCurrentPosition());
            telemetry.update();
        }
        setDrive(0);
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

    protected void imuInfo(){
        telemetry.addData("Angle: ", imu.getZAngle());
        telemetry.update();
    }

    protected void imuTurn(double degree, double speed) {
        imu.initialize();
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

        print(String.valueOf(imu.getZAngle()),3);

        FL.setPower(0);
        BL.setPower(0);
        FR.setPower(0);
        BR.setPower(0);
    }
}