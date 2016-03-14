package digimodel.mars.iitr;

import java.awt.FlowLayout;
import java.util.Iterator;
import java.util.List;
import javax.swing.JSlider;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.videoio.VideoCapture;

public class ImageProcessTest {
	long c=0;
	Mat mR,mRThreshold,mB,mG,mContours;
	int val = 100;
	ImageFrame imageFrameContours,imageFrameOrg;
	int rangeH = 0,rangeS = 0,rangeV = 0;
    Mat frame = new Mat();
	Mat mHierarchy = new Mat();
	VideoCapture camera = new VideoCapture();
	Rect rect;
	double area;
	Iterator<MatOfPoint> each;
	MatOfPoint tmpMatOfPoints;
	List<MatOfPoint> contours;
	MatOfPoint wrapper;
	double maxArea = 0;
	public Scalar mLowerBound;// = new Scalar(0,0,0);
	public Scalar mUpperBound;// = new Scalar(50,125,75);
	Mat mMask = new Mat();
	Mat mMat1=new Mat();
	Mat mMat2=new Mat();
	int cam = 0;
	Mat mDilatedMask = new Mat();
	public ImageProcessTest()
	{
		mRThreshold = new Mat();
	    imageFrameContours=new ImageFrame();
	    imageFrameContours.setTitle("Countours");
	    imageFrameOrg = new ImageFrame();
	    imageFrameOrg.setTitle("Original");
	    // imageFrameContours.addKeyListener(listener);
	    JSlider sliderHL = new JSlider(JSlider.VERTICAL, 0, 255, 0);
	    JSlider sliderSL = new JSlider(JSlider.VERTICAL, 0, 255, 0);
	    JSlider sliderVL = new JSlider(JSlider.VERTICAL, 0, 255, 0);
	    JSlider sliderHH = new JSlider(JSlider.VERTICAL, 0, 255, 0);
	    JSlider sliderSH = new JSlider(JSlider.VERTICAL, 0, 255, 0);
	    JSlider sliderVH = new JSlider(JSlider.VERTICAL, 0, 255, 0);
	    imageFrameContours.Window.add(sliderHL);
	    imageFrameContours.Window.add(sliderHH);
	    imageFrameContours.Window.add(sliderSL);
	    imageFrameContours.Window.add(sliderSH);
	    imageFrameContours.Window.add(sliderVL);
	    imageFrameContours.Window.add(sliderVH);
	    imageFrameContours.Window.setLayout(new FlowLayout());
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
	    	
//	    	maxArea = 0;
		    camera.read(frame);
		    imageFrameOrg.show(frame);
//		    Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGB2HSV_FULL);
		    mLowerBound = new Scalar(sliderHL.getValue(),sliderSL.getValue(),sliderVL.getValue());
		    mUpperBound = new Scalar(sliderHH.getValue(),sliderSH.getValue(),sliderVH.getValue());
//		    System.out.println("H : "+rangeH+" , "+rangeH+50+"::::S : "+rangeS+" , "+rangeS+50+"::::V : "+rangeV+" , "+rangeV+50);
			Core.inRange(frame, mLowerBound, mUpperBound, mMask);
		    imageFrameContours.show(mMask);
//			Core.inRange(frame, new Scalar(0, 53, 185, 0), new Scalar(15, 255, 255, 0), mMat1);
//			Core.inRange(frame, new Scalar(180, 53, 185, 0), new Scalar(180, 255, 255, 0), mMat2);
			
//			Imgproc.dilate(mMask, mDilatedMask, new Mat());
//			Core.split(frame, lRgb);
//			mR=lRgb.get(2);
//			mB = lRgb.get(0);
//			mG = lRgb.get(1);
//			Core.subtract(mR, mG, mG);
//			Imgproc.threshold(mG, mG, 50, 255, 0);
//			Core.subtract(mR, mB, mB);
//			Imgproc.threshold(mB, mB, 50, 255, 0);
//			Core.bitwise_and(mG, mB, mRThreshold);
//		    Imgproc.erode(mRThreshold, mRThreshold, myKernel);
//		    Imgproc.dilate(mRThreshold, mRThreshold, myKernel);
//		    mContours=mDilatedMask.clone();
//		    contours = new ArrayList<MatOfPoint>();
//		    Imgproc.findContours(mDilatedMask, contours, mHierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
//			each = contours.iterator();
//			tmpMatOfPoints = new MatOfPoint();
//			//checking through each contour for tracing the red object
//			while (each.hasNext()) {
//				wrapper = each.next();
//				area = Imgproc.contourArea(wrapper);
//				if(area>maxArea){
//					maxArea = area;
//					tmpMatOfPoints = wrapper;     
//				}
//			}
//			
//			int x = 0,y = 0;
//			for(int k=0;k<tmpMatOfPoints.rows();k++)
//			{
//				x += tmpMatOfPoints.row(k).get(0, 0)[0];
//				y += tmpMatOfPoints.row(k).get(0, 0)[1];
//			}
//			try{
//			x /= tmpMatOfPoints.rows();
//			y /= tmpMatOfPoints.rows();
//			//imageFrameContours.show(mContours);
//			}
//			catch(Exception e){
//			}
	    }
	}
	
//	KeyListener listener = new KeyListener() {
//		
//		@Override
//		public void keyTyped(KeyEvent arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//		@Override
//		public void keyReleased(KeyEvent arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//		@Override
//		public void keyPressed(KeyEvent arg0) {
//			// TODO Auto-generated method stub
//			if(arg0.getKeyChar() == 'w')
//				rangeH++;
//			if(arg0.getKeyChar() == 's')
//				rangeH--;
//			if(arg0.getKeyChar() == 'e')
//				rangeS++;
//			if(arg0.getKeyChar() == 'd')
//				rangeS--;
//			if(arg0.getKeyChar() == 'r')
//				rangeV++;
//			if(arg0.getKeyChar() == 'f')
//				rangeV--;
//		}
//	}; 
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		new ImageProcessTest();
	}
}



//package digimodel.mars.iitr;
//
//import java.awt.FlowLayout;
//import java.awt.GradientPaint;
//import java.awt.LayoutManager;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.swing.GroupLayout;
//import javax.swing.JSlider;
//
//import org.opencv.core.Core;
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfPoint;
//import org.opencv.core.Rect;
//import org.opencv.core.Scalar;
//import org.opencv.imgproc.Imgproc;
//import org.opencv.videoio.VideoCapture;
//
//public class ImageProcessTest {
//	int cam = 0;
//	Mat mR,mRThreshold,mB,mG,mContours;
//	ImageFrame imageFrameContours;
//    Mat frame = new Mat();
//    List<Mat> lRgb = new ArrayList<Mat>(3);
////	Mat mHierarchy = new Mat();
//	VideoCapture camera = new VideoCapture();
////	Mat myKernel = new Mat();
////	Rect rect;
////	long c2=0;
////	double area;
////	Iterator<MatOfPoint> each;
////	MatOfPoint tmpMatOfPoints;
////	List<MatOfPoint> contours;
////	MatOfPoint wrapper;
////	double maxArea = 0;
//	public ImageProcessTest()
//	{
//		mRThreshold = new Mat();
//	    imageFrameContours=new ImageFrame();
//	    imageFrameContours.setTitle("Countours");
//	   // imageFrameContours.addKeyListener(listener);
//	    JSlider sliderHL = new JSlider(JSlider.VERTICAL, 0, 255, 0);
//	    JSlider sliderSL = new JSlider(JSlider.VERTICAL, 0, 255, 0);
//	    JSlider sliderVL = new JSlider(JSlider.VERTICAL, 0, 255, 0);
//	    JSlider sliderHH = new JSlider(JSlider.VERTICAL, 0, 255, 0);
//	    JSlider sliderSH = new JSlider(JSlider.VERTICAL, 0, 255, 0);
//	    JSlider sliderVH = new JSlider(JSlider.VERTICAL, 0, 255, 0);
//	    imageFrameContours.Window.add(sliderHL);
//	    imageFrameContours.Window.add(sliderHH);
//	    imageFrameContours.Window.add(sliderSL);
//	    imageFrameContours.Window.add(sliderSH);
//	    imageFrameContours.Window.add(sliderVL);
//	    imageFrameContours.Window.add(sliderVH);
//	    imageFrameContours.Window.setLayout(new FlowLayout());
//    	try{
//	    	camera.open(cam);
//	    	System.out.println("Opened 0");
//	    }
//	    catch(Exception e){
//	    	System.out.println(e.getMessage());
//	    }
//	    
//	    if(!camera.isOpened()){
//	        System.out.println("Camera Error");
//	    }
//	    else{
//	        System.out.println("Camera OK?");
//	    }
//	    while(true)
//	    {
////	    	maxArea = 0;
//		    camera.read(frame);			
//			Core.split(frame, lRgb);
//			mR=lRgb.get(2);
//			mB = lRgb.get(0);
//			mG = lRgb.get(1);
//			Core.subtract(mR, mG, mG);
//			System.out.println(sliderHH.getValue()+" : "+sliderHL.getValue());
//			Imgproc.threshold(mG, mG, sliderHH.getValue(), 255, 0);
//			Core.subtract(mR, mB, mB);
//			Imgproc.threshold(mB, mB, sliderHL.getValue(), 255, 0);
//			Core.bitwise_and(mG, mB, mRThreshold);
//		    Imgproc.erode(mRThreshold, mRThreshold, new Mat());
//		    Imgproc.dilate(mRThreshold, mRThreshold, new Mat());
//		    imageFrameContours.show(mRThreshold);
////		    mContours=mDilatedMask.clone();
////		    contours = new ArrayList<MatOfPoint>();
////		    Imgproc.findContours(mDilatedMask, contours, mHierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
////			each = contours.iterator();
////			tmpMatOfPoints = new MatOfPoint();
////			//checking through each contour for tracing the red object
////			while (each.hasNext()) {
////				wrapper = each.next();
////				area = Imgproc.contourArea(wrapper);
////				if(area>maxArea){
////					maxArea = area;
////					tmpMatOfPoints = wrapper;     
////				}
////			}
////			
////			int x = 0,y = 0;
////			for(int k=0;k<tmpMatOfPoints.rows();k++)
////			{
////				x += tmpMatOfPoints.row(k).get(0, 0)[0];
////				y += tmpMatOfPoints.row(k).get(0, 0)[1];
////			}
////			try{
////			x /= tmpMatOfPoints.rows();
////			y /= tmpMatOfPoints.rows();
////			//imageFrameContours.show(mContours);
////			}
////			catch(Exception e){
////			}
//	    }
//	}
//	
////	KeyListener listener = new KeyListener() {
////		
////		@Override
////		public void keyTyped(KeyEvent arg0) {
////			// TODO Auto-generated method stub
////			
////		}
////		
////		@Override
////		public void keyReleased(KeyEvent arg0) {
////			// TODO Auto-generated method stub
////			
////		}
////		
////		@Override
////		public void keyPressed(KeyEvent arg0) {
////			// TODO Auto-generated method stub
////			if(arg0.getKeyChar() == 'w')
////				rangeH++;
////			if(arg0.getKeyChar() == 's')
////				rangeH--;
////			if(arg0.getKeyChar() == 'e')
////				rangeS++;
////			if(arg0.getKeyChar() == 'd')
////				rangeS--;
////			if(arg0.getKeyChar() == 'r')
////				rangeV++;
////			if(arg0.getKeyChar() == 'f')
////				rangeV--;
////		}
////	}; 
//	
//	public static void main(String[] args) {
//		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//		new ImageProcessTest();
//	}
//}
