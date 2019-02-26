package org.firstinspires.ftc.teamcode.armisticeTeleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Angela on 2/24/2019.
 */
@TeleOp(name = "PID")
public class pidTest extends teleop{

    private final double ticksPerRevolution = 1120;  // Get for your motor and gearing.
    private double prevTime;  // The last time loop() was called.
    private int prevLeftEncoderPosition;   // Encoder tick at last call to loop().
    private int prevRightEncoderPosition;  // Encoder tick at last call to loop().

    private final double drivePidKp = 1;     // Tuning variable for PID.
    private final double drivePidTi = 1.0;   // Eliminate integral error in 1 sec.
    private final double drivePidTd = 0.1;   // Account for error in 0.1 sec.
    // Protect against integral windup by limiting integral term.
//    private final double drivePidIntMax = maxWheelSpeed;  // Limit to max speed.
    private final double driveOutMax = 1.0;  // Motor output limited to 100%.

    @Override
    public void init() {
        prevTime = 0;
        prevLeftEncoderPosition = FL.getCurrentPosition();
        prevRightEncoderPosition = FR.getCurrentPosition();
    }

    @Override
    public void loop() {
        double deltaTime = time - prevTime;
        double leftSpeed = (FL.getCurrentPosition() - prevLeftEncoderPosition) /
                deltaTime;
        double rightSpeed = (FR.getCurrentPosition() - prevRightEncoderPosition) /
                deltaTime;
        // Track last loop() values.
        prevTime = time;
        prevLeftEncoderPosition = FL.getCurrentPosition();
        prevRightEncoderPosition = FR.getCurrentPosition();

    }
}
