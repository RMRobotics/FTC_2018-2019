package org.firstinspires.ftc.teamcode.armisticeAuto;

import com.disnodeteam.dogecv.detectors.roverrukus.*;
import com.disnodeteam.dogecv.detectors.roverrukus.Direction;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Neal on 12/6/2018.
 */

@Autonomous(name = "FieldAutoTest")
public class AutoDepot extends armisticeAutoSuper{

    private GoldAlignDetector detector;
    private com.disnodeteam.dogecv.detectors.roverrukus.Direction position = Direction.UNKNOWN;

    public void runOpMode(){

        //initialization
        initialize(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();

        //Get off lander

        //Move forward to see Qube
        moveEncoders(10, 1);

        //See Qube
        detector = new GoldAlignDetector();
        DogeCVYellowDetector(detector);

        //Vars
        int count = 0;
        int change = 0;
        int direction = 1;
        double totalDistance = 0;
        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        if (detector.getAligned().equals(Direction.CENTER)){
            moveEncoders(2, 1);
            //push mineral w/ arm
        }
        else if (detector.getAligned().equals(Direction.LEFT)){
            while(detector.isFound()==false){
                strafeEncoders(2, -1, .25);
            }
        }
        else if (detector.getAligned().equals(Direction.RIGHT)){
            while(detector.isFound()==false){
                strafeEncoders(2, 1, .25);
            }
        }
        else
        {
            moveEncoders(2, -1);
        }

//        while (detector.isFound() == false && timer.seconds() < 5){
//            strafeEncoders(3,direction, .25);
//            count++;
//
//            if (change%2 == 1) {
//                totalDistance--;
//            }
//            else {
//                totalDistance++;
//            }
//
//            if (count > 5) {
//                strafeEncoders(14.5, -direction, .25);
//                count = 0;
//                totalDistance = 0;
//                direction = -direction;
//                change++;
//            }
//        }
//        if (detector.isFound() == true){
//            position = detector.getAligned();
//            strafeEncoders(20, 1, .25);
//        }
//        else
//        {
//            if (totalDistance < 0) {
//                totalDistance = -totalDistance + 20;
//            }
//            strafeEncoders(totalDistance, 1, .25);
//        }


        //knock off yellow mineral
        moveEncoders(5, 1);

        //go back to initial pos and turn
        moveEncoders(5, -1);
        imuTurn(90, 0.4);

        //move to turn point and turn
        moveEncoders(55, 1);
        imuTurn(135,0.4);

        //move to home depot and drop flag
        moveEncoders(60,1);

        //turn arouuuuuuund every now and then i get a little bit lonely
        imuTurn(180, 0.4);

        //travel from depot to crater
        moveEncoders(69, 1);

        //drop arm in crater

    }
}
