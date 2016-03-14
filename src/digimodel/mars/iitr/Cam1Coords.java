package digimodel.mars.iitr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class Cam1Coords {
	long c=0;
	Mat mR,mB,mG,mContours;
	int val = 100;
	ImageFrame imageFrameContours;
    Mat frame = new Mat();
	Mat mHierarchy = new Mat();
	VideoCapture camera = new VideoCapture();
	Mat myKernel = new Mat();
	Rect rect;
	double area;
	Iterator<MatOfPoint> each;
	MatOfPoint tmpMatOfPoints;
	List<MatOfPoint> contours;
	MatOfPoint wrapper;
	double maxArea = 0;
	static ObtainCoordinates1 obtainCoordinates1;
	Mat mDilatedMask = new Mat();
	public Scalar mLowerBound = new Scalar(240,240,240);
	public Scalar mUpperBound = new Scalar(255,255,255);
	
	public interface ObtainCoordinates1{
		void getCoordinates1(int x,int y,long c);
	}
	
	
	public Cam1Coords(int cam)
	{
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
	    	System.out.println("Opened 0");
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
	    	maxArea = 0;
	    	camera.read(frame);
	    	Core.inRange(frame, mLowerBound, mUpperBound, mDilatedMask);
		    Imgproc.erode(mDilatedMask, mDilatedMask, myKernel);
		    Imgproc.dilate(mDilatedMask, mDilatedMask, myKernel);
		    mContours=mDilatedMask.clone();
		    contours = new ArrayList<MatOfPoint>();
		    Imgproc.findContours(mDilatedMask, contours, mHierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
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
			c++;
			c=c%100;
			obtainCoordinates1.getCoordinates1(x,y,c);
			imageFrameContours.show(mContours);
			}
			catch(Exception e){
				continue;
			}
	    }
	}


	public static void setOnObtainPositionListener(ObtainCoordinates1 listener) {
		obtainCoordinates1=listener;
	}
}
	
