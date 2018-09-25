package ftc.vision;


import org.opencv.core.Mat;

/**
 * Created by Angela on 9/18/2018.
 */
public interface ImageProcessor<ResultType> {
    ImageProcessorResult<ResultType> process(long startTime, Mat rgbaFrame, boolean saveImages);
}
