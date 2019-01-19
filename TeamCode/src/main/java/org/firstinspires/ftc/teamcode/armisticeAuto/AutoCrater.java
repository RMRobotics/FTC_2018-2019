package org.firstinspires.ftc.teamcode.armisticeAuto;

import com.disnodeteam.dogecv.detectors.roverrukus.*;
import com.disnodeteam.dogecv.detectors.roverrukus.Direction;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by Neal on 12/6/2018.
 */

@Autonomous(name = "AutoCrater", group = "auto")
public class AutoCrater extends armisticeAutoSuper{

    private GoldAlignDetector detector;
    private com.disnodeteam.dogecv.detectors.roverrukus.Direction position = Direction.UNKNOWN;

    public void runOpMode(){

                                                            //initialization
        initialize(true);
        waitForStart();

//        telemetry.addData("range", String.format("%.01f cm", sensorRange.getDistance(DistanceUnit.CM)));
//        telemetry.addData("range", String.format("%.01f in", sensorRange.getDistance(DistanceUnit.INCH)));
//        telemetry.update();

                                                            //Get off lander
        //lmao we dont have this yet

                                                            //See Qube
        detector = new GoldAlignDetector();
        DogeCVYellowDetector(detector);
        telemetry.addData("Detector X Pos: ", detector.goldPosCenterDiff());
        telemetry.update();

        //Move forward to see Qube
//        moveEncoders(10.0);

        //Vars
        int count = 0;
        int change = 0;
        int direction = 1;
        double totalDistance = 0;
        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        //align with mineral
        if (detector.getAligned().equals(Direction.LEFT)){
            setStrafe(0.4);
            while(detector.isFound()==false){
            }
            setDrive(0);
        }
        else if (detector.getAligned().equals(Direction.RIGHT)){
            setStrafe(-0.4);
            while(detector.isFound()==false){
            }
            setDrive(0);
        }



//        if (detector.getAligned().equals(Direction.CENTER)){
//
//        }
//        else if (detector.getAligned().equals(Direction.LEFT)){
//            while(detector.isFound()==false){
//                strafeEncoders(2, 0.4);
//            }
//        }
//        else if (detector.getAligned().equals(Direction.RIGHT)){
//            while(detector.isFound()==false){
//                strafeEncoders(2, 0.4);
//            }
//        }
//        else
//        {
//            moveEncoders(-2);
//        }

//        double distance = sensorRange.getDistance(DistanceUnit.INCH);

                                                            //knock off yellow mineral
        moveEncoders(5);

                                                            //go back to initial pos and turn
        moveEncoders(5 * -1);
        imuTurn(90, 0.4);

                                                            //move to turn point and turn
        moveEncoders(55);
        imuTurn(45,0.4);

                                                            //go to home depot
        moveEncoders(32);

                                                            //drop off flag
        arm.setPower(0.3);
        holdUp(2);
        arm.setPower(0);

        //turn arouuuuuuund every now and then i get a little bit lonely
        imuTurn(180, 0.4);

        //travel from depot to crater
        moveEncoders(69);

        //drop arm in crater
    }
}
