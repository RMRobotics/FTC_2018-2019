package org.firstinspires.ftc.teamcode.DanCV.Detection;


import android.content.Context;
import android.util.Log;


import org.firstinspires.ftc.teamcode.DanCV.Enums.DetectionMode;
import org.firstinspires.ftc.teamcode.DanCV.Enums.RelativeDistance;
import org.firstinspires.ftc.teamcode.DanCV.Enums.RelativePosition;

import org.firstinspires.ftc.teamcode.DanCV.UI.CVViewActivity;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.util.ArrayList;
import java.util.List;

public class MineralDetector extends Detector  {
    private Mat mFrame;
    private Mat mOriginalFrame;
    private MatOfPoint mCnt;
    private Mat kernel;
    private List<MatOfPoint> mContours;
    private static final String TAG = "Detected";
    private Scalar lower;
    private Scalar upper;
    private int centerBounds;
    private Rect buffer;
    private boolean showProcessFrame;
    private boolean inited;
    private boolean isReady = false;
    private double resizeVal;
    private DetectionMode detectionMethod;


    public MineralDetector(){
        this.lower = new Scalar(20,190,100);
        this.upper = new Scalar(60,255,255);
        this.centerBounds = 100;
        this.buffer = new Rect();
        this.showProcessFrame = false;
        mFrame = new Mat();
        mOriginalFrame = new Mat();
        mCnt = new MatOfPoint();
        kernel = new Mat(5,5,CvType.CV_8UC1,new Scalar(255));
        mContours = new ArrayList<>();
        resizeVal = 1.0;
        detectionMethod = DetectionMode.NORMAL_MODE;
    }

    public void toggleShowProcessFrame() {
        this.showProcessFrame = !showProcessFrame;
    }

    public void setUpper(Scalar upper) {
        this.upper = upper;
    }

    public void setLower(Scalar lower) {
        this.lower = lower;
    }

    public void setShowProcessFrame(boolean showProcessFrame) {
        this.showProcessFrame = showProcessFrame;
    }

    public void setResizeVal(double resizeVal) {
        this.resizeVal = resizeVal;
    }


    private void resizeFrame(Mat frame){
        Size initialSize = frame.size();
        if(resizeVal != 0.0){
            Size resize = new Size(initialSize.width*resizeVal,initialSize.height*resizeVal);
            Imgproc.resize(frame,frame,resize);
        }
    }

    private void reverseResize(Mat frame){
        Size initialSize = frame.size();
        Imgproc.resize(frame,frame,new Size(initialSize.width/resizeVal,initialSize.height/resizeVal));
    }

    public void setDetectMode(DetectionMode method) {
        this.detectionMethod = method;
    }

    @Override
    protected Mat processFrame(Mat frame) {
        frame.copyTo(mFrame);
        frame.copyTo(mOriginalFrame);
        frame.release();
        resizeFrame(mFrame);
        preProcessFrame();
        reverseResize(mFrame);
        Imgproc.findContours(mFrame,mContours,new Mat(),Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);
        //Imgproc.drawContours(mFrame,mContours,1,new Scalar(0,255,0),1);
        if(!(mContours == null) && mContours.size() > 0){

            Rect box = trackContours();

            mContours.clear();
            //Log.i("tracker", "" + box.x + " " + box.y);
            if(!box.empty()){

                box = tryBoxFromBuffer(box);
                Imgproc.rectangle(mFrame,new Point(box.x,box.y),new Point(box.width,box.height), new Scalar(0,255,0),2);
                String message = getRelPosition(mFrame,box).name();
                Imgproc.putText(mFrame,message,box.tl(),Core.FONT_HERSHEY_SIMPLEX,1,new Scalar(0,255,0),2,Core.LINE_AA);
                Imgproc.putText(mFrame,box.width + " x " + box.height,box.br(),Core.FONT_HERSHEY_SIMPLEX,1,new Scalar(0,255,0),2,Core.LINE_AA);


                Imgproc.rectangle(mOriginalFrame,new Point(box.x,box.y),new Point(box.width,box.height), new Scalar(0,255,0),2);
                Imgproc.putText(mOriginalFrame,message,box.tl(),Core.FONT_HERSHEY_SIMPLEX,1,new Scalar(0,255,0),2,Core.LINE_AA);
                Imgproc.putText(mOriginalFrame,box.width + " x " + box.height,box.br(),Core.FONT_HERSHEY_SIMPLEX,1,new Scalar(0,255,0),2,Core.LINE_AA);


                buffer = box;
                if(showProcessFrame){
                    return mFrame;
                }else{
                    return mOriginalFrame;
                }

            }
        }
        mContours.clear();
        if(showProcessFrame){
            return mFrame;
        }else{
            return mOriginalFrame;
        }
    }


    private void preProcessFrame(){

        //Convert from RGB to HSV
        Imgproc.cvtColor(mFrame,mFrame,Imgproc.COLOR_RGB2HSV_FULL);

        Imgproc.GaussianBlur(mFrame,mFrame,new Size(5,5),0);
        //Perform Color filtering to create binary image
        Core.inRange(mFrame,lower,upper,mFrame);


        //Use kernel for convolutions during morphological operations

        Imgproc.morphologyEx(mFrame,mFrame,2,kernel);
        //
        // Imgproc.morphologyEx(frame,frame,2,kernel);
        Imgproc.GaussianBlur(mFrame, mFrame,new Size(5,5),0);

    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mFrame = new Mat(height,width,CvType.CV_8UC4);
        mOriginalFrame = new Mat(height,width,CvType.CV_8UC4);
        mCnt = new MatOfPoint();
        mContours = new ArrayList<>();

    }

    @Override
    public void onCameraViewStopped() {
        mFrame.release();
        mOriginalFrame.release();
        mCnt.release();
        kernel.release();
    }

    @Override
    public void init(Context context, CVViewActivity view, int cameraId) {
        super.init(context, view, cameraId);
        inited = true;
    }

    @Override
    public void activate() {
        super.activate();
        isReady = true;

    }

    public boolean isInited() {
        return inited;
    }

    public boolean isReady(){
        return isReady;
    }

    private Rect trackContours(){

        int maxPos = 0;
        for(int i = 0 ; i < mContours.size(); i++){
            mCnt = mContours.get(i);
            Moments M = Imgproc.moments(mCnt);
            if(M.m00 > Imgproc.moments(mContours.get(maxPos)).m00){
                maxPos = i;
            }
        }
        mCnt = mContours.get(maxPos);
        Moments M = Imgproc.moments(mCnt);

        //Log.i(TAG, "" + "MOMENTS AREA: " + M.m00);
        if(M.m00 > 200) {
            Rect rect = Imgproc.boundingRect(mCnt);
            //Log.i(TAG, "lol " + rect.tl());
            return new Rect(rect.x, rect.y, rect.width + rect.x, rect.height + rect.y);
        }else{
            return new Rect();
        }
    }

    private Rect trackContours(List<MatOfPoint> contours){
        // of the contours, this is the one we care about
        MatOfPoint cnt = contours.get(0);

        //Get the area inside the complete contour
        Moments M = Imgproc.moments(cnt);
        //Check its area to make sure its of a certain size
        if(M.m00 > 300) {
            //Get coordinate and dimensions
            Rect rect = Imgproc.boundingRect(cnt);
            //Combine the x and width as well as y + height to get final width and height. Return the box
            return new Rect(rect.x, rect.y, rect.width + rect.x, rect.height + rect.y);
        }else{
            //Otherwise just empty Rect object
            return new Rect();
        }
    }



    @Override
    protected Mat getCurrentFrame() {
        return mFrame;
    }

    public RelativeDistance getRelDistance() {
        //RelativeDistance distance;
        int width = buffer.width;
        int height = buffer.height;

        Log.d(TAG, "width: " + width);
        Log.d(TAG, "height: " + height);
        return RelativeDistance.UNKNOWN;
    }

    @Override
    public RelativeDistance getRelativeDistance() {
        RelativeDistance distance;
        int width = buffer.width;
        int height = buffer.height;

        Log.d(TAG, "width: " + width);
        Log.d(TAG, "height: " + height);
        return RelativeDistance.UNKNOWN;
    }

    @Override
    public RelativePosition getRelativePos() {

        if(!(isReady() && isInited())){
            return RelativePosition.UNKNOWN;
        }
        int frameWidth = mOriginalFrame.width();
        int centerX = buffer.x;
        if(centerX > (frameWidth/2)+centerBounds){
            return RelativePosition.RIGHT;
        }else if(centerX < (frameWidth/2)-centerBounds){
            return RelativePosition.LEFT;

        }else if((centerX > (frameWidth/2)-centerBounds) && (centerX < (frameWidth/2)+centerBounds) ){
            return RelativePosition.CENTER;
        }else{
            return RelativePosition.UNKNOWN;
        }
        //Log.d(TAG, "Relative Position: " + position.name());
    }

    private RelativePosition getRelPosition(Mat frame, Rect box) {
        RelativePosition position;
        int frameWidth = frame.width();
        int centerX = box.x + (box.width/2);
        if(centerX > (frameWidth/2)+centerBounds){
            position = RelativePosition.RIGHT;
        }else if(centerX < (frameWidth/2)-centerBounds){
            position = RelativePosition.LEFT;

        }else{
            position = RelativePosition.CENTER;
        }
        return position;
    }

    @Override
    public boolean isVisible() {
        return (!buffer.empty());
    }

    @Override
    public boolean isCentered() {
        Log.d(TAG, "Position: " + getRelativePos());
        return (getRelPosition(mFrame,buffer) == RelativePosition.CENTER);

    }



    private boolean isWithinBox(Rect box1, Rect box2){
        return box1.contains(box2.br()) && box1.contains(box2.tl());
    }


    /*
    Method checks whether box 2 is a subset of box1
     */
    private Rect tryBoxFromBuffer(Rect box){
        if(isWithinBox(buffer,box) || isWithinBox(box,buffer)){
            return new Rect((buffer.x + box.x)/2,(buffer.y + box.y)/2,(buffer.width + box.width)/2,(buffer.height + box.height)/2);
        }else{
            return box;
        }
    }




}

