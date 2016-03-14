package digimodel.mars.iitr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DataServer {
    String x,y,z,tracker;
    public DataServer(double x,double y,double z,int track) throws IOException {
    	this.x=((int)(x))+"";
       	this.y=((int)(y))+"";
    	this.z=((int)(z))+"";
    	this.tracker=track+"";
   
    }
    
    public void sendCoordi()throws IOException{
    	ServerSocket listener = new ServerSocket(9898);
        try {
            while (true) {
                new SendData(listener.accept(),(x+","+y+","+z+","+tracker)).start();
                 }
        }
        finally {
            listener.close();
        }
    }
    private static class SendData extends Thread{
    	String s;
    	Socket socket;
    	public SendData(Socket socket,String s){
    		this.s=s;
    		this.socket=socket;
    	}
    	
    	 public void run() {
             try {
            	 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 out.println(s);
         }
             catch (Exception e) {
            	 System.out.println("Error handling client: " + e);
             } finally {
                 try {
                     socket.close();
                 } catch (IOException e) {
                     System.out.println("s");
                 }
          }
    }
}
}
