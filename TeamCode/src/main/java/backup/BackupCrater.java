package backup;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.armisticeAuto.armisticeAutoSuper;

@Autonomous (name = "backupCrater", group = "backup")
public class BackupCrater extends armisticeAutoSuper {

    public void runOpMode()
    {
        initialize(false);

        moveEncoders(5);


//        arm.setPower(0.3);
//        holdUp(2);
//        arm.setPower(0);

        print("done",5);
    }

}
