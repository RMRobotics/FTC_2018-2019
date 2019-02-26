package org.firstinspires.ftc.teamcode.armisticeAuto.testCode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.IIMU;
import org.firstinspires.ftc.teamcode.armisticeAuto.RevIMU;
import org.firstinspires.ftc.teamcode.armisticeAuto.armisticeAutoSuper;

/**
 * Created by Angela on 11/20/2018.
 */

//@Autonomous(name = "Encoder Test", group = "autotest")
public class EncoderTest extends LinearOpMode {
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

        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

      /*  FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

*/
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

    protected void setStrafe(double pwr) {
        BR.setPower(pwr);
        BL.setPower(-pwr);
        FL.setPower(pwr);
        FR.setPower(-pwr);
    }

    protected void moveEncoders(double distanceInches){
        int currentPos1 = FL.getCurrentPosition();
        int currentPos2 = FR.getCurrentPosition();
        int currentPos3 = BL.getCurrentPosition();
        int currentPos4 = BR.getCurrentPosition();
        //distanceTics is num of tics it needs to travel
        int distanceTics = (int)(distanceInches * CPI);

        int targetDistance = FL.getCurrentPosition() + distanceTics;

//      setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
/*
        setMode(DcMotor.RunMode.RUN_TO_POSITION);
*/

       /* FL.setTargetPosition(currentPos1 + distanceTics);
        FR.setTargetPosition(currentPos2 + distanceTics);
        BL.setTargetPosition(currentPos3 + distanceTics);
        BR.setTargetPosition(currentPos4 + distanceTics)*/;

        int count = 0;

        while ((FL.getCurrentPosition() - targetDistance) > 5 && !gamepad1.b){
            count++;
            telemetry.addData("FL encoder: " , FL.getCurrentPosition());
            telemetry.addData("BL encoder: " , BL.getCurrentPosition());
            telemetry.addData("FR encoder: " , FR.getCurrentPosition());
            telemetry.addData("BR encoder: " , BR.getCurrentPosition());
            telemetry.update();
            if (distanceInches>0)
                setDrive(0.5);
            else
                setDrive(-0.5);
        }

        setDrive(0);

//        setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("dope",String.valueOf(count));
        telemetry.update();
    }

    protected void print(String message, double time){
        telemetry.addData(message,"");
        telemetry.update();
        holdUp(time);
    }

    protected void holdUp(double num){
        timer.reset();
        while (timer.seconds()<num)
        {}
    }

    protected void imuTurn(double degree, double speed) {
        imu.initialize();
        imu.setOffset(0);
        double err = 0.7, pwr = 0.5;

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
                FL.setPower(-1*pwr);
                BL.setPower(-1*pwr);
                FR.setPower(pwr);
                BR.setPower(pwr);
                count+=1;
                dir_cw = !dir_cw;
            }
            else if (!dir_cw && imu.getZAngle()<degree)
            {
                pwr = speed/count;
                if (pwr < 0.15)
                    pwr = 0.15;
                FL.setPower(pwr);
                BL.setPower(pwr);
                FR.setPower(-1*pwr);
                BR.setPower(-1*pwr);
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

    protected void imuInfo(){
        telemetry.addData("Angle: ", imu.getZAngle());
        telemetry.update();
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

    public void runOpMode() {

        initialize(true);

        while (opModeIsActive() && !gamepad1.b) {
            telemetry.addData("dpad left", "dumbStrafeEncoders 36 in 0.6 pwr");
            telemetry.addData("dpad down", "moveEncoders 12 in");
            telemetry.addData("dpad up", "setStrafe 4 sec 0.4 pwr");
            telemetry.addData("dpad right","setDrive 4 sec 0.4 pwr");
            telemetry.addData("B","EXIT");
            telemetry.update();

            if (gamepad1.dpad_left)
                dumbstrafeEncoders(36, 1, 0.6);
            else if (gamepad1.dpad_down)
                moveEncoders(12);
            else if (gamepad1.dpad_up) {
                setStrafe(0.4);
                holdUp(4);
            } else if (gamepad1.dpad_right) {
                setDrive(0.2);
                holdUp(4);
                setDrive(0);
            }
        }
           /* holdUp(2);

            setDrive(0);*/

//                strafeEncoders(4,1, .4);
            //        sleep(5000);
//        moveEncoders(4, 1);
//        moveEncoders(60);
            print("lmao, it actually worked", 1);
//        sleep(5000);
            //        imuTurn(90, 0.5);
    }
}
