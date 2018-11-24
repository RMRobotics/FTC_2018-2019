package org.firstinspires.ftc.teamcode.armisticeAuto;

import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import static org.firstinspires.ftc.teamcode.armisticeAuto.Direction.CENTER;

/**
 * Created by Angela on 11/24/2018.
 */

@Autonomous(name = "FieldAutoTest")
public class FieldAutoTest extends armisticeAutoSuper{

    private GoldAlignDetector detector;
    Direction position;

    public void runOpMode(){
        initialize(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        moveEncoders(10, 1);
        detector = new GoldAlignDetector();
        DogeCVYellowDetector(detector);
        if (detector.isFound() == true){
            position = CENTER;
        }
        while (detector.isFound() == false){

        }
            moveEncoders(5, 1); //knock off yellow mineral
            moveEncoders(5, -1);
        switch (position) {
            case CENTER:
                strafeEncoders(10, 1);
                break;
            case LEFT:
                strafeEncoders(5, 1);
                break;
            case RIGHT:
                strafeEncoders(15, 1);
                break;
        }
        moveEncoders(20, 1);
        //drop off flag
        imuTurn(180, 0.4);
        moveEncoders(30, 1);
    }
}
