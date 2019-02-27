package org.firstinspires.ftc.teamcode.armisticeAuto.testCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Angela on 2/27/2019.
 */
@Autonomous(name = "Arm Calibration")
public class armCalibration extends LinearOpMode {

    protected DcMotor arm;

    public void initialize() {
        arm = hardwareMap.dcMotor.get("arm");
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

    }

    public void extendArm() {
        telemetry.addData("Encoder Val", arm.getCurrentPosition());
        telemetry.update();

//        if (gamepad1.x) {
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setTargetPosition(-37500);
        arm.setPower(0.4);
        while (arm.isBusy()) {

        }
        arm.setPower(0);
    }
/*        else if (gamepad1.y) {
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            arm.setTargetPosition(0);
            arm.setPower(-0.4);
            while (arm.isBusy()) {

            }
            arm.setPower(0);
        }
        else
            arm.setPower(0);
            }
*/

    public void retractArm() {
        telemetry.addData("Encoder Val", arm.getCurrentPosition());
        telemetry.update();

        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setTargetPosition(0);
        arm.setPower(-0.4);
        while (arm.isBusy()) {

        }
        arm.setPower(0);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        extendArm();

    }
}
