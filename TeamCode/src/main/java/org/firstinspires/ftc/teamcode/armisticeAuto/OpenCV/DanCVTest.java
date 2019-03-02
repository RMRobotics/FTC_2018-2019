package org.firstinspires.ftc.teamcode.armisticeAuto.OpenCV;

import android.util.Log;

import com.disnodeteam.dogecv.detectors.roverrukus.Direction;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.DanCV.Detection.Detector;
import org.firstinspires.ftc.teamcode.DanCV.Detection.MineralDetector;
import org.firstinspires.ftc.teamcode.DanCV.Enums.RelativePosition;
import org.firstinspires.ftc.teamcode.armisticeAuto.armisticeAutoSuper;



@Autonomous(name = "DanCV-HelloWorld", group = "auto")
public class DanCVTest extends armisticeAutoSuper {

    @Override
    public void runOpMode() {
        initialize(true);

        MineralDetector detector = new MineralDetector();
        //Check armisticeAutoSuper for the implementation
        DanCVMineralDetector(detector);


        waitForStart();


        int  count = 0;
        while (count < 1000) {
            telemetry.addData("Is Ready", detector.isReady());
            telemetry.addData("Init Done", detector.isInited());
            telemetry.addData("Detected Position", detector.getRelativePos().name());
            telemetry.addData("Count",count);
            telemetry.update();
        }
        detector.deactivate();
        stop();

       // while(true){
        //    telemetry.addData("Detected Position",detector.getRelativePos().name());
        //}

        //Log.i("Telemetry", "Detected Position" + detector.getRelativePos());

        //Log.i("hi", "SOMETHINGS HAPPENING");
        //stop();

    }
}
