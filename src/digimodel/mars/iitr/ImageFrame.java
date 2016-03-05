package digimodel.mars.iitr;

import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import org.opencv.core.Mat;


public class ImageFrame 
{	
	public JFrame Window;
	private ImageIcon image;
	private JLabel label;
	
	public ImageFrame()
	{
		Window = new JFrame();
		image = new ImageIcon();
		label = new JLabel();
		label.setIcon(image);
		Window.getContentPane().add(label);
		Window.setResizable(false);
		Window.setTitle("Image");
		Window.setVisible(true);
	}
	
	void setTitle(String str)
	{
		Window.setTitle(str);
	}
	
	public void addKeyListener(KeyListener listener)
	{
		Window.addKeyListener(listener);
	}
	
	public BufferedImage toBufferedImage(Mat m) {
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (m.channels() > 1) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		int bufferSize = m.channels() * m.cols() * m.rows();
		byte[] b = new byte[bufferSize];
		m.get(0, 0, b); // get all the pixels
		BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster()
				.getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);
		return image;

	}
	
	public void showImage(Mat img) {
		BufferedImage bufImage = null;
		try 
		{
			bufImage = toBufferedImage(img);
			image.setImage(bufImage);
			Window.pack();
			label.updateUI();
			//Window.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void show(Mat mat) 
	{
		Window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		showImage(mat);
	}

}
