package digimodel.mars.iitr;

public class RunningAvg {
	int length;
	float avg = 0;
	int point = 0;
	boolean first = true;
	float[] list;
	public RunningAvg(int length)
	{
		this.length = length;
		list = new float[length];
		for(int i=0;i<length;i++)
		{
			list[i] = 0;
		}
	}
	public float getAvg()
	{
		return avg;
	}
	public void updateData(float next)
	{
		list[point] = next;
		if(++point >= length)
		{
			point = 0;
			first = false;
		}
		avg = 0;
		int tmp = length;
		if(first)
		{
			tmp = point;
		}
		for(int i=0;i<tmp;i++)
		{
			avg += list[i];
		}
		avg /= tmp;
	}
	public void newPoint() {
		first = true;
		point = 0;
	}
//	public static void main(String[] args) {
//		RunningAvg avg = new RunningAvg(5);
//		avg.updateData(1);
//		System.out.println(avg.getAvg());
//		avg.updateData(2);
//		System.out.println(avg.getAvg());
//		avg.updateData(3);
//		System.out.println(avg.getAvg());
//		avg.updateData(4);
//		System.out.println(avg.getAvg());
//		avg.updateData(5);
//		System.out.println(avg.getAvg());
//		avg.updateData(6);
//		System.out.println(avg.getAvg());
//		avg.newPoint();
//		avg.updateData(7);
//		System.out.println(avg.getAvg());
//		avg.updateData(8);
//		System.out.println(avg.getAvg());
//		avg.updateData(9);
//		System.out.println(avg.getAvg());
//		avg.updateData(10);
//		System.out.println(avg.getAvg());
//	}
}
