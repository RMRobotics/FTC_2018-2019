package org.firstinspires.ftc.teamcode.armisticeAuto.testCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Angela on 2/24/2019.
 */
@Autonomous(group = "autotest", name = "basicBasic")
public class basicBasic extends LinearOpMode {


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
        FL.setDirection(DcMotor.Direction.FORWARD);
        BL.setDirection(DcMotor.Direction.FORWARD);

        waitForStart();
    }

    protected void setDrive(double p) {
        FL.setPower(p);
        FR.setPower(p);
        BL.setPower(p);
        BR.setPower(p);
    }

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();

        setDrive(0.4);
        Thread.sleep(5000);
        setDrive(0.8);
        Thread.sleep(5000);
        setDrive(-0.4);
        Thread.sleep(5000);
        setDrive(-0.8);
        Thread.sleep(5000);
    }
}
