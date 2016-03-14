package digimodel.mars.iitr;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DataServ {
    public DataServ()
    {
        ServerSocket listener;
		try {
			listener = new ServerSocket(9090);
			try {
	            while (true) {
	                Socket socket = listener.accept();
	                try {
	                    PrintWriter out =
	                        new PrintWriter(socket.getOutputStream(), true);
	                    out.println((int)(Test.avgX.getAvg()*100)+","+(int)(Test.avgY.getAvg()*100)+","+(int)(Test.avgZ.getAvg()*100));
	                } finally {
	                    socket.close();
	                }
	            }
	        }
	        finally {
	            listener.close();
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}