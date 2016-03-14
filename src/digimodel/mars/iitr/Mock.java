package digimodel.mars.iitr;

public class Mock {
	public static RunningAvg avgX,avgY,avgZ;
	public Mock()
	{
		avgX = new RunningAvg(1);
		avgY = new RunningAvg(1);
		avgZ = new RunningAvg(1);
		for(int i=0;i<100;i++)
		{
			float dat = (float)Math.sin(i*0.1f);
			avgX.updateData(i*0.1f);
			avgY.updateData(dat);
			System.out.println((int)(dat*100));
			avgZ.updateData(0.0f);
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
