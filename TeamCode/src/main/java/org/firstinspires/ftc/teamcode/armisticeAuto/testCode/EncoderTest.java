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

@Autonomous(name = "Encoder Test", group = "autotest")
public class EncoderTest extends armisticeAutoSuper {

    @Override
    public void runOpMode() throws InterruptedException {
        initialize(true);

        moveEncoders(30, 0.4);
    }
}
