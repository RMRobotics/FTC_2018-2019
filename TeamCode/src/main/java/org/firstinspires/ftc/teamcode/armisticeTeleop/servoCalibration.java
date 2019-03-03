package org.firstinspires.ftc.teamcode.armisticeTeleop;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Angela on 2/27/2019.
 */
@TeleOp(name = "Calibration")
public class servoCalibration extends OpMode {

    protected DcMotor hook, intake;
    protected CRServo arm1, arm2;
    protected Servo marker;
    protected ElapsedTime timer = new ElapsedTime();

    public void init() {
        hook = hardwareMap.dcMotor.get("hook");
        hook.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hook.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hook.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        marker = hardwareMap.servo.get("marker");

        hook = hardwareMap.dcMotor.get("hook");

        intake = hardwareMap.dcMotor.get("intake");
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        arm1 = hardwareMap.crservo.get("arm1");
        arm2 = hardwareMap.crservo.get("arm2");

    }

    @Override
    public void loop(){

        telemetry.addData("Hook Encoder Val", hook.getCurrentPosition());
        telemetry.addData("Marker Power", marker.getPosition());
        //telemetry.addData("Intake Encoder Val", intake.getCurrentPosition());
        telemetry.update();

        if (gamepad2.left_trigger>0){
            hook.setPower(1);
        }
        else if (gamepad2.right_trigger>0)
            hook.setPower(-1);
        else
            hook.setPower(0);


        if (gamepad2.left_stick_x > 0){
            intake.setPower(0.3);
        }
        else if (gamepad2.left_stick_x < 0){
            intake.setPower(-0.3);
        }
        else{
            intake.setPower(0);
        }


        if(gamepad2.right_stick_x > 0){
            arm1.setPower(0.3);
            arm2.setPower(-0.3);
        }
        else if (gamepad2.right_stick_x < 0){
            arm1.setPower(-0.3);
            arm2.setPower(0.3);
        }
        else{
            arm1.setPower(0);
            arm2.setPower(0);
        }


        if (gamepad2.right_bumper){
            marker.setPosition(0);
//            holdUp(0.5);
        }
        else if (gamepad2.left_bumper){
            marker.setPosition(0.75);
//            holdUp(0.5);
        }

        /*if (gamepad1.dpad_up){
            intake.setPower(0.5);
        }
        else if (gamepad1.dpad_down)
            intake.setPower(-0.5);
        else
            intake.setPower(0);*/
    }

    protected void holdUp(double num)
    {
        timer.reset();
        while (timer.seconds()<num)
        {}
    }
}