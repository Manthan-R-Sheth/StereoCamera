package digimodel.mars.iitr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class Cam2Coords {
	long millis;
	Mat mR,mRThreshold,mB,mG,mContours;
	int val = 100;
	ImageFrame imageFrameR,imageFrameContours;
    Mat frame = new Mat();
    List<Mat> lRgb = new ArrayList<Mat>(3);
	Mat mHierarchy = new Mat();
	VideoCapture camera = new VideoCapture();
	Mat myKernel = new Mat();
	Rect rect;
	long c2=0;
	double area;
	Iterator<MatOfPoint> each;
	MatOfPoint tmpMatOfPoints;
	List<MatOfPoint> contours;
	MatOfPoint wrapper;
	double maxArea = 0;
	static ObtainCoordinates2 obtainCoordinates2;
	
	public interface ObtainCoordinates2{
		void getCoordinates2(int x,int y,long c2);
	}
	
	
	public Cam2Coords(int cam)
	{
		mRThreshold = new Mat();
		imageFrameR = new ImageFrame();
	    imageFrameR.setTitle("mRThresholded");
	    imageFrameContours=new ImageFrame();
	    imageFrameContours.setTitle("Countours");
    	myKernel.create(6,6,CvType.CV_8S); // 8-bit single channel image
    	for (int i=0; i<6; i++)
    	{
    	    for(int j=0; j<6; j++)
    	    {
    	         myKernel.put(i, j, 1);
    	    }
    	}
    	try{
	    	camera.open(cam);
	    	System.out.println("Opened 1");
	    }
	    catch(Exception e){
	    	System.out.println(e.getMessage());
	    }
	    
	    if(!camera.isOpened()){
	        System.out.println("Camera Error");
	    }
	    else{
	        System.out.println("Camera OK?");
	    }
	    while(true)
	    {
	    	millis = System.currentTimeMillis();
	    	maxArea = 0;
		    camera.read(frame);
			Core.split(frame, lRgb);
			mR=lRgb.get(2);
			mB = lRgb.get(0);
			mG = lRgb.get(1);
			Core.subtract(mR, mG, mG);
			Imgproc.threshold(mG, mG, 50, 255, 0);
			Core.subtract(mR, mB, mB);
			Imgproc.threshold(mB, mB, 50, 255, 0);
			Core.bitwise_and(mG, mB, mRThreshold);
		    Imgproc.erode(mRThreshold, mRThreshold, myKernel);
		    Imgproc.dilate(mRThreshold, mRThreshold, myKernel);
		    mContours=mRThreshold.clone();
		    contours = new ArrayList<MatOfPoint>();
		    Imgproc.findContours(mRThreshold, contours, mHierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
			each = contours.iterator();
			tmpMatOfPoints = new MatOfPoint();
			//checking through each contour for tracing the red object
			while (each.hasNext()) {
				wrapper = each.next();
				area = Imgproc.contourArea(wrapper);
				if(area>maxArea){
					maxArea = area;
					tmpMatOfPoints = wrapper;     
				}
			}
			
			int x = 0,y = 0;
			for(int k=0;k<tmpMatOfPoints.rows();k++)
			{
				x += tmpMatOfPoints.row(k).get(0, 0)[0];
				y += tmpMatOfPoints.row(k).get(0, 0)[1];
			}
			try{
			x /= tmpMatOfPoints.rows();
			y /= tmpMatOfPoints.rows();
			c2++;
			c2=c2%100;
			obtainCoordinates2.getCoordinates2(x,y,c2);
			imageFrameContours.show(mContours);
			}
			catch(Exception e){
				continue;
			}
	    }
	}


	public static void setOnObtainPositionListener(ObtainCoordinates2 listener) {
		obtainCoordinates2=listener;
	}
}
	
