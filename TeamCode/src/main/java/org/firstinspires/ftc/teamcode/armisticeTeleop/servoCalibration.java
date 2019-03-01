package org.firstinspires.ftc.teamcode.armisticeTeleop;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Angela on 2/27/2019.
 */
@TeleOp(name = "Calibration")
public class servoCalibration extends OpMode {

    protected DcMotor hook, intake;

    public void init() {
        hook = hardwareMap.dcMotor.get("hook");
        hook.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hook.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hook.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        /*intake = hardwareMap.dcMotor.get("intake");
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);*/
    }

    @Override
    public void loop(){

        telemetry.addData("Hook Encoder Val", hook.getCurrentPosition());
        //telemetry.addData("Intake Encoder Val", intake.getCurrentPosition());
        telemetry.update();

        if (gamepad1.left_trigger>0){
            hook.setPower(1);
        }
        else if (gamepad1.right_trigger>0)
            hook.setPower(-1);
        else
            hook.setPower(0);


        /*if (gamepad1.dpad_up){
            intake.setPower(0.5);
        }
        else if (gamepad1.dpad_down)
            intake.setPower(-0.5);
        else
            intake.setPower(0);*/
    }
}