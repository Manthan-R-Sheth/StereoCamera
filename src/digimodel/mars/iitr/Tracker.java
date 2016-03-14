package digimodel.mars.iitr;

import org.opencv.core.Core;

import digimodel.mars.iitr.Cam1Coords.ObtainCoordinates1;
import digimodel.mars.iitr.Cam2Coords.ObtainCoordinates2;

public class Tracker implements ObtainCoordinates1,ObtainCoordinates2{
	long c1=0,c2=0;
	int x1,x2,y1,y2;
	double h,d1,d2,m1,m2,m3,m4,x,y,z;
	public Tracker()
	{
		Cam1Coords.setOnObtainPositionListener(this);
		Cam2Coords.setOnObtainPositionListener(this);
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
	public void calc(int x1,int y1,int x2,int y2){
		//this.z=y1;
		m1=x1/1325.0;
		m2=x2/1325.0;
		m3=y1/1325.0;
		m4=y2/1325.0;
		h=0.3/(m1-m2);
		d1=h*m1;
		d2=h*m2;
		x=(d1+d2)/2.0;
		y=((y1*3.48)+(y2*3.48))/(1325.0*2.0);
		int tracker;
		try{
			if(x>900||x<-900||y>650||y<-650||h<0||h>4000||
					Double.isInfinite(x)||Double.isNaN(x)||
					Double.isInfinite(y)||Double.isNaN(y)||
					Double.isInfinite(h)||Double.isNaN(h)){
				tracker=0;

			}
			else
			{
				tracker=1;
			}	
			System.out.println("HELLO   "+"  :  "+tracker);
			System.out.println((int)((x*1000)+925)+" : "+(int)((y*1000)-640)+" : "+(int)(h*1000));

		}
		catch (Exception e) {
			//System.out.println(e.getMessage());				
		}
	}
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		new Tracker();
	}
	@Override
	public void getCoordinates2(int x, int y, long c2) {
		// TODO Auto-generated method stub
		this.c2=c2;
		if(c1>=c2){
			calc(this.x1, this.y1, x, y);
			this.c2 = -1;
		}
		else{
			this.x2=x;
			this.y2=y;
		}
	}
	@Override
	public void getCoordinates1(int x, int y, long c) {
		// TODO Auto-generated method stub
		this.c1=c1;
		//System.out.println(x+"          :1            "+y);
		if(c2>=c1){
			calc(x,y, this.x2, this.y2);
			this.c2= -1;
		}
		else{
			this.x1=x;
			this.y1=y;
		}
	}
}
