package org.firstinspires.ftc.teamcode.armisticeAuto.testCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.armisticeAuto.armisticeAutoSuper;

/**
 * Created by Angela on 1/31/2019.
 */
@Autonomous (name = "imuTurnTest", group = "autotest")
public class imuTurnTest extends armisticeAutoSuper {
    @Override
    public void runOpMode() throws InterruptedException {

        initialize(true);

        imuTurn(90, 0.4);
        holdUp(2);
        imuTurn(-90, 0.4);
//        imuTurn1(90, 1);

    }
}
