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

    protected DcMotor arm;

    public void init() {
        arm = hardwareMap.dcMotor.get("arm");
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    @Override
    public void loop(){

        if (gamepad1.left_trigger>0){
            arm.setPower(gamepad1.left_trigger);
        }
        else if (gamepad1.right_trigger>0)
            arm.setPower(-gamepad1.right_trigger);
        else
            arm.setPower(0);
    }
}