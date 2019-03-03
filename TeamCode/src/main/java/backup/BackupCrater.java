package backup;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.DanCV.Enums.RelativePosition;
import org.firstinspires.ftc.teamcode.armisticeAuto.armisticeAutoSuper;

@Autonomous (name = "backupCrater", group = "backup")
public class BackupCrater extends armisticeAutoSuper {

    public void runOpMode()
    {
        initialize(false);

        waitForStart();

        //Get off lander
        extendHook();
        RelativePosition direction = detector.getRelativePos();
        strafeEncoders(20, 0.4);
        imuTurn(-imu.getZAngle(),0.4,false);

        moveEncodersCount(-7000, 0.6);

//        arm.setPower(0.3);
//        holdUp(2);
//        arm.setPower(0);

        print("done",5);
    }

}
