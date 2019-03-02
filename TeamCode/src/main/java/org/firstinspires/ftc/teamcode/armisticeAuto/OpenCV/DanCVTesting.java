package org.firstinspires.ftc.teamcode.armisticeAuto.OpenCV;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DanCV.Detection.MineralDetector;
import org.firstinspires.ftc.teamcode.DanCV.UI.CVViewActivity;

/**
 * Created by Angela on 3/2/2019.
 */
@Autonomous(name = "DanCVw/oRobo", group = "auto")
public class DanCVTesting extends LinearOpMode {

    protected ElapsedTime timer = new ElapsedTime();

    protected MineralDetector DanCVMineralDetector(MineralDetector detector){
        detector = new MineralDetector();
        detector.init(hardwareMap.appContext, CVViewActivity.getInstance(),1);
        detector.activate();
        return detector;
    }

    protected void holdUp(double num)
    {
        timer.reset();
        while (timer.seconds()<num)
        {}
    }


    @Override
    public void runOpMode() throws InterruptedException {
        MineralDetector detector = new MineralDetector();

        //Check armisticeAutoSuper for the implementation
        detector = DanCVMineralDetector(detector);

        //The methods outlined here should provide all the functionality needed to do the auto. But, if openCV runs slow
        //I included the ability to resize the frames to be smaller during processing. It lowers the resolution but ups the speed significantly
        //Try running without it first to see the difference.
        detector.setResizeVal(0.5);

        //This simply shows the status of the detector in terms of whether its ready to detect or not
        telemetry.addData("Is Ready", detector.isReady());
        //Shows status of whether detector has finished its initialization.
        //When the detector is ready, it can be used for detection.isInited() serves to just provide feedback during initialization
        telemetry.addData("Init Done", detector.isInited());

        //Once its ready, we can access various important methods such as:


            telemetry.update();

        waitForStart();

        while (!detector.isCentered()) {

            //This method returns a value from the Relative Position enum - see Enum Package folder in DanCV for more info
            //Can yield LEFT,RIGHT,CENTER, or UNKNOWN (not visible)
            telemetry.addData("Detected Position", detector.getRelativePos().name());

            //Yields whether object is centered or not
            telemetry.addData("Centered? ", detector.isCentered());

            //Whether its visible (may have problems, but should work)
            telemetry.addData("Visible", detector.isVisible());

            //Update Telemetry
            telemetry.update();



        }
        //During the full auto program, once the OpenCV part of the program is done, run this to stop and close the camera
        //Note: It cannot be used again after its been closed without creating another object
        //This is done purposefully to prevent multiple camera listeners from spawning up and causing memory issues
        detector.deactivate();
    }
}