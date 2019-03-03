package org.firstinspires.ftc.teamcode.armisticeAuto.testCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.armisticeAuto.armisticeAutoSuper;

/**
 * Created by Angela on 3/2/2019.
 */
@Autonomous(name = "Hard Code Encoder", group = "autotest")

public class hardCodeEncoder extends armisticeAutoSuper {
    @Override
    public void runOpMode() throws InterruptedException {
        moveEncodersCount(-3500, 0.4);
        holdUp(1);


    }
}
