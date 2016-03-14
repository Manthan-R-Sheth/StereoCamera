package digimodel.mars.iitr;

import org.opencv.core.Core;

public class Test2 {
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new DataServ();
			}
		}).start();
		new Test();
//		new Mock();
	}
}
