package org.firstinspires.ftc.teamcode.armisticeAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by Angela on 12/18/2018.
 */
@Autonomous(name = "DistanceSensorTest", group = "autotest")
public class REVDistanceSensorTest extends armisticeAutoSuper{

    private DistanceSensor sensorRange;

    public void runOpMode(){

        //initialization
//        initialize(DcMotor.RunMode.RUN_USING_ENCODER);

        sensorRange = hardwareMap.get(DistanceSensor.class, "sensor_range");

        telemetry.addData(">>", "Press start to continue");
        telemetry.update();

        waitForStart();
        while(opModeIsActive()) {
            // generic DistanceSensor methods.
            telemetry.addData("deviceName",sensorRange.getDeviceName() );
            telemetry.addData("range", String.format("%.01f cm", sensorRange.getDistance(DistanceUnit.CM)));
            telemetry.addData("range", String.format("%.01f in", sensorRange.getDistance(DistanceUnit.INCH)));

           /* // Rev2mDistanceSensor specific methods.
            telemetry.addData("ID", String.format("%x", sensorTimeOfFlight.getModelID()));
            telemetry.addData("did time out", Boolean.toString(sensorTimeOfFlight.didTimeoutOccur()));*/

            telemetry.update();
        }



    }


}
