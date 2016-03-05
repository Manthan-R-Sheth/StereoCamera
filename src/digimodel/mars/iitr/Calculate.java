package digimodel.mars.iitr;

import digimodel.mars.iitr.HttpURLConnectionExample.SendData;

public class Calculate implements SendData{
	
	double h,d1,d2,m1,m2,m3,m4,x,y,z;
	Thread server;
	HttpURLConnectionExample httpObj;
	
	public void calc(int x1,int y1,int x2,int y2){
		//System.out.println(x1+" : "+y1+" : "+x2+" : "+y2);
		//httpObj.setOnDataSent(this);
		this.z=y1;
		m1=x1/1325.0;
		m2=x2/1325.0;
		m3=y1/1325.0;
		m4=y2/1325.0;
		h=0.3/(m1-m2);
		d1=h*m1;
		d2=h*m2;
		x=(d1+d2)/2.0;
		y=((y1*1.78)+(y2*1.78))/(1325.0*2.0);
		System.out.println((x-0.41)+" : "+(-1*(y-0.317))+" : "+h);
		//System.out.println((x-0.41));//+" : "+(h)+" : "+(z-205.2));
//		server=new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				try {
//					new HttpURLConnectionExample(x, h, z).sendPost();
//				} catch (Exception e) {
//					System.out.println("Error in sending post request");				}
//			}
//		});
//		server.start();
	}
	
	@Override
	public void sendDataServer(String s) {
		// TODO Auto-generated method stub
		System.out.println(s+"   ");
		server.destroy();
	}
}
