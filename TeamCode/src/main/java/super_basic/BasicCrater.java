package super_basic;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.armisticeAuto.armisticeAutoSuper;


@Autonomous (name = "basicCrater", group = "basic")
public class BasicCrater extends armisticeAutoSuper {

    public void runOpMode()
    {
        initialize(false);

        moveEncoders(10);

        arm.setPower(0.3);
        holdUp(2);
        arm.setPower(0);

        print("done",5);
    }
}
