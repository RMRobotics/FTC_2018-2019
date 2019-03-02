package org.firstinspires.ftc.teamcode.DanCV.Detection;



import android.app.Activity;
import android.content.Context;
import android.view.Surface;
import android.view.View;


//The last remenent of DogeCV, I had to borrow cause its annoying to do myself
import com.disnodeteam.dogecv.OpenCVLoader;

import org.firstinspires.ftc.teamcode.DanCV.Enums.RelativeDistance;
import org.firstinspires.ftc.teamcode.DanCV.Enums.RelativePosition;
import org.firstinspires.ftc.teamcode.DanCV.UI.CVViewActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.core.Core;
import org.opencv.core.Mat;

public abstract class Detector implements CameraBridgeViewBase.CvCameraViewListener2 {
    static {
        try {
            System.loadLibrary("opencv_java3");
        } catch (UnsatisfiedLinkError e) {
            OpenCVLoader.loadOpenCV();
        }
    }

    protected Context context;
    protected JavaCameraView mOpenCvCameraView;
    private boolean initDone;
    private CVViewActivity view;
    private boolean initStarted = false;

    protected abstract Mat processFrame(Mat frame);
    protected abstract Mat getCurrentFrame();
    public abstract RelativeDistance getRelativeDistance();
    public abstract RelativePosition getRelativePos();
    public abstract boolean isVisible();
    //public abstract double getNoiseLevel();
    public abstract boolean isCentered();
    public abstract boolean isReady();
    public abstract boolean isInited();

    static final int FRAME_WIDTH_REQUEST = 352;
    static final int FRAME_HEIGHT_REQUEST = 288;

    public void init(Context context,CVViewActivity view, final int cameraId){
        this.initStarted = true;
        this.context = context;


        this.view = view;
        final Activity activity = (Activity) context;
        final Context finalContext = context;
        final CameraBridgeViewBase.CvCameraViewListener2 self = this;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mOpenCvCameraView = new JavaCameraView(finalContext,cameraId);
                mOpenCvCameraView.setCameraIndex(cameraId);
                mOpenCvCameraView.setCvCameraViewListener(self);
                mOpenCvCameraView.setMinimumWidth(FRAME_WIDTH_REQUEST);
                mOpenCvCameraView.setMinimumHeight(FRAME_HEIGHT_REQUEST);
                mOpenCvCameraView.setMaxFrameSize(FRAME_WIDTH_REQUEST, FRAME_HEIGHT_REQUEST);

                initDone = true;
            }
        });
    }
    public JavaCameraView getCameraView(){
        return mOpenCvCameraView;
    }


    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat rgba = new Mat();

        switch (((Activity) context).getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
                Core.rotate(inputFrame.rgba(), rgba, Core.ROTATE_90_CLOCKWISE);

                break;
            case Surface.ROTATION_90:
                rgba = inputFrame.rgba();

                break;
            case Surface.ROTATION_270:
                Core.rotate(inputFrame.rgba(), rgba, Core.ROTATE_180);

                break;
        }
        return processFrame(rgba);
    }

    public void activate(){
        if (!initStarted) throw new IllegalStateException("init() needs to be called before an OpenCVPipeline can be enabled!");
        try {
            while (!initDone) Thread.sleep(10);
        } catch (InterruptedException e) { return; }
        mOpenCvCameraView.enableView();
        view.setCurrentView(context, getCameraView());
    }

    public void deactivate(){
        mOpenCvCameraView.disableView();
        view.removeCurrentView(context);
    }




}
