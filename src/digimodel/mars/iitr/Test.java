package digimodel.mars.iitr;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

import javax.naming.InitialContext;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import digimodel.mars.iitr.Cam1Coords.ObtainCoordinates1;
import digimodel.mars.iitr.Cam2Coords.ObtainCoordinates2;


public class Test implements ObtainCoordinates1,ObtainCoordinates2{
	long c1=0,c2=0;
	public static RunningAvg avgX,avgY,avgZ;
	public static int current = 0;
	public static int newCount = 0,tracker=0;
	int x1,x2,y1,y2;
	Calculate calculate=new Calculate();
	public Test() {
		//setting interfaces
		initialize();
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				new Cam1Coords(0);
			}
		});
		thread.start();
		
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				new Cam2Coords(1);
			}
		});
		thread2.start();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		avgX = new RunningAvg(5);
		avgY = new RunningAvg(5);
		avgZ = new RunningAvg(5);
		Cam1Coords.setOnObtainPositionListener(this);
		Cam2Coords.setOnObtainPositionListener(this);
	}


	@Override
	public void getCoordinates2(int x, int y,long c2) {
		// TODO Auto-generated method stub
		//System.out.println(x+"         :2           "+y);
		this.c2=c2;
		if(c1>=c2){
			calculate.calc(this.x1, this.y1, x, y);
			this.c2 = -1;
		}
		else{
			this.x2=x;
			this.y2=y;
		}
	}

	@Override
	public void getCoordinates1(int x, int y,long c1) {
		// TODO Auto-generated method stub
		this.c1=c1;
		//System.out.println(x+"          :1            "+y);
		if(c2>=c1){
			calculate.calc(x,y, this.x2, this.y2);
			this.c2= -1;
		}
		else{
			this.x1=x;
			this.y1=y;
		}
	}
	
}



