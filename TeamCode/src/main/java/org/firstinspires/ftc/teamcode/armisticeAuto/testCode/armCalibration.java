package org.firstinspires.ftc.teamcode.armisticeAuto.testCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Angela on 2/27/2019.
 */
@Autonomous(name = "Arm Calibration")
public class armCalibration extends LinearOpMode {

    protected DcMotor hook, intake;
    protected ElapsedTime timer = new ElapsedTime();

    public void initialize() {
        hook = hardwareMap.dcMotor.get("hook");
        hook.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hook.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hook.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
    }

    protected void holdUp(double num)
    {
        timer.reset();
        while (timer.seconds()<num)
        {}
    }

    public void extendArm() {
        telemetry.addData("Encoder Val", hook.getCurrentPosition());
        telemetry.update();

//        if (gamepad1.x) {
        hook.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hook.setTargetPosition(-37500);
        hook.setPower(1);
        while (hook.isBusy()) {

        }
        hook.setPower(0);
    }

    public void retractArm() {
        telemetry.addData("Encoder Val", hook.getCurrentPosition());
        telemetry.update();

        hook.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hook.setTargetPosition(0);
        hook.setPower(-1);
        while (hook.isBusy()) {

        }
        hook.setPower(0);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        extendArm();
        holdUp(2);
        retractArm();


    }
}
