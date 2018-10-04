package ftc.vision;


import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angela on 9/18/2018.
 */

public class MineralProcessor implements ImageProcessor<MineralColor> {
    private static final String TAG = "MineralProcessor";
    private static final double MIN_MASS = 6;
    
    @Override
    public ImageProcessorResult<MineralColor> process(long startTime, Mat rgbaFrame, boolean saveImages) {
        
        //<The code we write will go here>

        if (saveImages) {
            ImageUtil.saveImage(TAG, rgbaFrame,
                    Imgproc.COLOR_RGBA2BGR, "0_camera", startTime);
        }

        //convert image to hsv
        Mat hsv = new Mat();
        Imgproc.cvtColor(rgbaFrame, hsv, Imgproc.COLOR_RGB2HSV);
        // rgbaFrame is untouched

        //the h range is 0 to 179
        //the s range is 0 to 255
        //the v range is 0 to 255
        //the values are stored as a list of min HSV
        //and a list of max HSV
        List<Scalar> hsvMin = new ArrayList<>();
        List<Scalar> hsvMax = new ArrayList<>();
        //hsvMin.add(new Scalar( H, S, V ));
        hsvMin.add(new Scalar(300/2, 0, 255)); //white min
        hsvMax.add(new Scalar( 60/2, 20, 230)); //white  max
        hsvMin.add(new Scalar(40/2, 50, 150)); //yellow min
        hsvMax.add(new Scalar( 70/2, 255, 255)); //yellow  max
        hsvMin.add(new Scalar(300/2, 50, 150)); //red min
        hsvMax.add(new Scalar( 60/2, 255, 255)); //red max
        hsvMin.add(new Scalar( 60/2, 50, 150)); //green min
        hsvMax.add(new Scalar(180/2, 255, 255)); //green max
        hsvMin.add(new Scalar(180/2, 50, 150)); //blue min
        hsvMax.add(new Scalar(300/2, 255, 255)); //blue max

        // make a list of channels that are blank (used for combining binary images)
        List<Mat> rgbaChannels = new ArrayList<>();
        double [] maxMass = { Double.MIN_VALUE, Double.MIN_VALUE };
        //maxmass for left and right
        int[] maxMassIndex = { 3, 3}; // index of the max mass
        // We are about to loop over the filters and compute the "color mass" for each color on each side of the image.
        // These variables are used inside the loop:
        Mat maskedImage;
        Mat colSum = new Mat();
        double mass;
        int[] data = new int[3]; //used to read the colSum

        //loop through the rgb channels
        for(int i=0; i<3; i++) {
            //apply HSV thresholds
            maskedImage = new Mat();
            ImageUtil.hsvInRange(hsv, hsvMin.get(i), hsvMax.get(i), maskedImage);
            //copy the binary image to a channel of rgbaChannels
            rgbaChannels.add(maskedImage.clone());


            //apply a column sum to the (unscaled) binary image
            Core.reduce(maskedImage, colSum, 0, Core.REDUCE_SUM, 4);

            //loop through left and right to calculate mass
            int start = 0;
            int end = hsv.width() / 2;
            for (int j = 0; j < 2; j++) {
                //<insert sub-loop code here from Steps 5c(i) to 5c(ii)>
                start = end;
                end = hsv.width();

                //calculate the mass
                mass = 0;
                for (int x = start; x < end; x++) {
                    colSum.get(0, x, data);
                    mass += data[0];
                }
                //scale the mass by the image size
                mass /= hsv.size().area();

                //if the mass found is greater than the max for this side
                if (mass >= MIN_MASS && mass > maxMass[j]) {
                    //this mass is the new max for this side
                    maxMass[j] = mass;
                    //and this index is the new maxIndex for this side
                    maxMassIndex[j] = i;
                }

                start = end;
                end = hsv.width();
            }

        }
        //add empty alpha channel
        rgbaChannels.add(Mat.zeros(hsv.size(), CvType.CV_8UC1));
        //merge the 3 binary images and 1 alpha channel into one image
        Core.merge(rgbaChannels, rgbaFrame);

        //use the maxIndex array to get the left and right colors
        MineralColor.Color[] mineralColors = MineralColor.Color.values();
        MineralColor.Color left = mineralColors[maxMassIndex[0]];
        MineralColor.Color right = mineralColors[maxMassIndex[1]];

        //draw the color result bars
        int barHeight = hsv.height()/30;
        Imgproc.rectangle(rgbaFrame,
                new Point(0, 0),
                new Point(hsv.width()/2, barHeight),
                left.color, barHeight);
        Imgproc.rectangle(rgbaFrame,
                new Point(hsv.width()/2, 0),
                new Point(hsv.width(), barHeight),
                right.color, barHeight);

        if (saveImages) {
            ImageUtil.saveImage(TAG, rgbaFrame,
                    Imgproc.COLOR_RGBA2BGR, "1_binary", startTime);
        }

        //construct and return the result
        return new ImageProcessorResult<>(startTime, rgbaFrame,
                new MineralColor(left, right)
        );

    }



}
