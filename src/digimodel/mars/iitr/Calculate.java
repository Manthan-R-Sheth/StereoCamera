package digimodel.mars.iitr;

import digimodel.mars.iitr.HttpURLConnectionExample.SendData;

public class Calculate implements SendData{
	float h,d1,d2,m1,m2,m3,m4,x,y;
	Thread server;
	float xlast,ylast,zlast;
	HttpURLConnectionExample httpObj;
	
	public Calculate(){
		HttpURLConnectionExample.setOnDataSent(Calculate.this);
	}
	
	public void calc(int x1,int y1,int x2,int y2){
		m1=x1/1325.0f;
		m2=x2/1325.0f;
		m3=y1/1325.0f;
		m4=y2/1325.0f;
		h=0.3f/(m1-m2);
		d1=h*m1;
		d2=h*m2;
		x=(d1+d2)/2.0f;
		y=((y1*3.48f)+(y2*3.48f))/(1325.0f*2.0f);
		x = ((x*1000)+1135-2212+2300);
		y = (y*1000)-653+22;
		h *= -2;
		h = h*1000;
		h -= 3000+2000;
		server=new Thread(new Runnable() {
			@Override
			public void run() {
				try {
				if(x>5000||x<-500||y>650||y<-650||h>100000||h<-50000||
					Double.isInfinite(x)||Double.isNaN(x)||
					Double.isInfinite(y)||Double.isNaN(y)||
					Double.isInfinite(h)||Double.isNaN(h)){
		    		System.out.println("HELLO"+"  :  "+x+" : "+y+" : "+h);
					if(Test.newCount++ >= 5)
					{
						Test.avgX.newPoint();
						Test.avgY.newPoint();
						Test.avgZ.newPoint();
					}
					Test.tracker=0;
					x=xlast;
		    		y=ylast;
		    		h=zlast;
		    	}
				else
				{
					Test.newCount = 0;
					xlast=x;
					ylast=y;
					zlast=h;
					Test.avgX.updateData(x);
					Test.avgY.updateData(y);
					Test.avgZ.updateData(h);
					Test.tracker=1;
					System.out.println("Someth : "+Test.avgX.getAvg()+" : "+Test.avgY.getAvg()+" : "+Test.avgZ.getAvg());
					
				}	
//				httpObj=new HttpURLConnectionExample(Test.avgX.getAvg(),Test.avgY.getAvg(),Test.avgZ.getAvg(),Test.tracker);
//				httpObj.sendPost();
				} catch (Exception e) {
//					System.out.println(e.getMessage());				
					}
			}
		});
		server.start();
	}
	
	@Override
	public void sendDataServer(String s) {
		// TODO Auto-generated method stub
		server.stop();;
	}
}
