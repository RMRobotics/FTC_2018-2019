package org.firstinspires.ftc.teamcode.vuforia;

import com.disnodeteam.dogecv.detectors.roverrukus.*;
import com.disnodeteam.dogecv.detectors.roverrukus.Direction;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.armisticeAuto.armisticeAutoSuper;
import org.firstinspires.ftc.teamcode.vuforia.Position;
import org.firstinspires.ftc.teamcode.vuforia.VuforiaUtil;
import org.firstinspires.ftc.teamcode.vuforia.VuforiaUtilFront;

/**
 * Created by Neal on 2/27/2019.
 */

public class VuforiaCraterRed extends armisticeAutoSuper {

    public static final double FTC_FIELD_WIDTH = 12*12 - 2;
    private VuforiaUtilFront Vuforia = new VuforiaUtilFront(true, VuforiaLocalizer.CameraDirection.BACK, VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES, 90, -90, 0);
    private Position robotToField;


    public void runOpMode(){

        //initialization
        initialize(true);
        waitForStart();

        //Vars
        boolean useVuforia = true;
        int initialEncoder = 0;
        int count = 0;
        int change = 0;
        int direction = 1;
        double totalDistance = 0;
        ElapsedTime timer = new ElapsedTime();
        timer.reset();


        imuTurn(-90,0.4);

        Vuforia.robotInformation();
        if(Vuforia.getRobotToFieldtX() == 0){
            setDrive(.4);
        }
        while(Vuforia.getRobotToFieldtX() == 0 && (initialEncoder + (12 * 2)) <  1){
            Vuforia.robotInformation();
        }
        setDrive(0);


        if(Vuforia.getRobotToImagetZ() > 12.5){
            setDrive(.2);
        }
        while(Vuforia.getRobotToImagetX() > 12.5 && Vuforia.getRobotToImagetY() > 12.5){
        }
        setDrive(0);
        Vuforia.stillRobotInformation();
        robotToField = new Position(Vuforia.getRobotToField());

        imuTurn(-(90-robotToField.getrZ()), 0.4); //turn towards the depot
        strafeEncoders(FTC_FIELD_WIDTH / 2 - robotToField.gettX(), 0.4); //move up against the wall

        moveEncoders(FTC_FIELD_WIDTH / 2 - robotToField.gettY() - 24, 0.4); //move to depot
        moveEncoders(-(FTC_FIELD_WIDTH - 48), 0.4);

    }


}



//package org.firstinspires.ftc.teamcode.armisticeAuto;
//
//        import com.disnodeteam.dogecv.detectors.roverrukus.*;
//        import com.disnodeteam.dogecv.detectors.roverrukus.Direction;
//        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//        import com.qualcomm.robotcore.hardware.DcMotor;
//        import com.qualcomm.robotcore.util.ElapsedTime;
//
//        import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//        import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
//        import org.firstinspires.ftc.teamcode.vuforia.Position;
//        import org.firstinspires.ftc.teamcode.vuforia.VuforiaUtil;
//        import org.firstinspires.ftc.teamcode.vuforia.VuforiaUtilFront;
//
///**
// * Created by Neal on 12/6/2018.
// */
//
//@Autonomous(name = "AutoCrater", group = "auto")
//public class AutoCrater extends armisticeAutoSuper{
//
//    public static final double FTC_FIELD_WIDTH = 12*12 - 2;
//
//    private GoldAlignDetector detector;
//    private com.disnodeteam.dogecv.detectors.roverrukus.Direction position = Direction.UNKNOWN;
//    private VuforiaUtilFront Vuforia = new VuforiaUtilFront(true, VuforiaLocalizer.CameraDirection.BACK, VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES, 90, -90, 0);
//    private Position robotToField;
//
//    public void runOpMode(){
//
//        //initialization
//        initialize(true);
//        waitForStart();
//
////        telemetry.addData("range", String.format("%.01f cm", sensorRange.getDistance(DistanceUnit.CM)));
////        telemetry.addData("range", String.format("%.01f in", sensorRange.getDistance(DistanceUnit.INCH)));
////        telemetry.update();
//
//        //Get off lander
//        //lmao we dont have this yet
//
//        //See Qube
//        detector = new GoldAlignDetector();
//        DogeCVYellowDetector(detector);
//        telemetry.addData("Detector X Pos: ", detector.goldPosCenterDiff());
//        telemetry.update();
//
//        //Move forward to see Qube
//        moveEncoders(10.0);
//
//        //Vars
//        int count = 0;
//        int change = 0;
//        int direction = 1;
//        double totalDistance = 0;
//        ElapsedTime timer = new ElapsedTime();
//        timer.reset();
//
//        //align with mineral
//        if (detector.getAligned().equals(Direction.LEFT)){
////            dumbstrafeEncoders(12, -1, 0.4);
//            while(detector.isFound()==false){
//                setStrafe(0.4);
//            }
//            setDrive(0);
//        }
//        else if (detector.getAligned().equals(Direction.RIGHT)){
//            dumbstrafeEncoders(12, 1, 0.4);
//            while(detector.isFound()==false){
//            }
//            setDrive(0);
//        }
//
//        moveEncoders(12);
//
//        moveEncoders(-12); //move back after knocking off mineral
//
//        imuTurn(-90,0.4);
//
//        Vuforia.robotInformation();
//        if(Vuforia.getRobotToFieldtX() == 0){
//            setDrive(.4);
//        }
//        while(Vuforia.getRobotToFieldtX() == 0){
//            Vuforia.robotInformation();
//        }
//        setDrive(0);
//
//        if(Vuforia.getRobotToImagetZ() > 12.5){
//            setDrive(.2);
//        }
//        while(Vuforia.getRobotToImagetX() > 12.5 && Vuforia.getRobotToImagetY() > 12.5){
//        }
//        setDrive(0);
//        Vuforia.stillRobotInformation();
//        robotToField = new Position(Vuforia.getRobotToField());
//
//        imuTurn(-(90-robotToField.getrZ()), 0.4); //turn towards the depot
//        strafeEncoders(FTC_FIELD_WIDTH / 2 - robotToField.gettX(), 0.4); //move up against the wall
//        moveEncoders(FTC_FIELD_WIDTH / 2 - robotToField.gettY() - 24); //move to depot
//        moveEncoders(-(FTC_FIELD_WIDTH - 48));
//
//
//
//
//
//
//
////        double distance = sensorRange.getDistance(DistanceUnit.INCH);
//
//        //knock off yellow mineral
//       /* moveEncoders(5);
//
//                                                            //go back to initial pos and turn
//        moveEncoders(5 * -1);
//        imuTurn(90, 0.4);
//
//                                                            //move to turn point and turn
//        moveEncoders(55);
//        imuTurn(45,0.4);
//
//                                                            //go to home depot
//        moveEncoders(32);
//
//                                                            //drop off flag
//        arm.setPower(0.3);
//        holdUp(2);
//        arm.setPower(0);
//
//        //turn arouuuuuuund every now and then i get a little bit lonely
//        imuTurn(180, 0.4);
//
//        //travel from depot to crater
//        moveEncoders(69);*/
//
//        //drop arm in crater
//    }
//}
