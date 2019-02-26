package backup;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.armisticeAuto.armisticeAutoSuper;

@Autonomous (name = "backupDepot", group = "backup")
public class BackupDepot extends armisticeAutoSuper {

    public void runOpMode()
    {
        initialize(false);

        moveEncoders(5,0.4);


//        arm.setPower(0.3);
//        holdUp(2);
//        arm.setPower(0);

        print("done",5);
    }

}
