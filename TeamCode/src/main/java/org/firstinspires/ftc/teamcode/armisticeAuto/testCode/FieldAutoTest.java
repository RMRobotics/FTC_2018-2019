package org.firstinspires.ftc.teamcode.armisticeAuto.testCode;

import com.disnodeteam.dogecv.detectors.roverrukus.*;

import com.disnodeteam.dogecv.detectors.roverrukus.Direction;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.armisticeAuto.armisticeAutoSuper;

/**
 * Created by Angela on 11/24/2018.
 */

@Autonomous(name = "FieldAuto Test", group = "autotest")
public class FieldAutoTest extends armisticeAutoSuper {

    private GoldAlignDetector detector;
    private com.disnodeteam.dogecv.detectors.roverrukus.Direction position = Direction.UNKNOWN;


    public void runOpMode(){

        //initialization
        initialize(true);
        waitForStart();

        //Get off lander

        //Move forward to see Qube
        moveEncoders(10.0);

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


        while (detector.isFound() == false && timer.seconds() < 5){
            strafeEncoders(3,direction, .25);
            count++;

            if (change%2 == 1) {
                totalDistance--;
            }
            else {
                totalDistance++;
            }

            if (count > 5) {
                strafeEncoders(14.5, -direction, .25);
                count = 0;
                totalDistance = 0;
                direction = -direction;
                change++;
            }
        }
        if (detector.isFound() == true){
            position = detector.getAligned();
            strafeEncoders(20, 1, .25);
        }
        else
        {
            if (totalDistance < 0) {
                totalDistance = -totalDistance + 20;
            }
            strafeEncoders(totalDistance, 1, .25);
        }
        moveEncoders(5); //knock off yellow mineral
        moveEncoders(-5);
        imuTurn(45, 0.4);
        moveEncoders(20);
        //drop off flag
        imuTurn(180, 0.4);
        moveEncoders(30);
    }
}
