package org.firstinspires.ftc.teamcode.vuforia;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

public class VuforiaUtil {

    float mmPerInch = 25.4f;

    public double[][] robotLocation(VuforiaTrackables images){

        double[][] returnArray = {
                {-1, 0, 0},              //the first number indicates the image the robot is near, if -1 no image is in sight
                {0, 0, 0},              //shows the x, y, and z components of the vector (in inches)
                {0, 0, 0}               //shows the x, y, and z rotation
        };

        int counter = 0;

        for(VuforiaTrackable i : images){
            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)(i.getListener())).getPose();

            if(pose != null){ //if the image is found

                VectorF translation = pose.getTranslation(); //finds data about the image
                Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                returnArray[0][0] = counter;
                returnArray[1][0] = translation.get(0) / mmPerInch;
                returnArray[1][1] = translation.get(1) / mmPerInch;
                returnArray[1][2] = translation.get(2) / mmPerInch;
                returnArray[2][0] = rot.firstAngle;
                returnArray[2][1] = rot.secondAngle;
                returnArray[2][2] = rot.thirdAngle;

            }
            counter++;
        }

        return returnArray;

    }

}
