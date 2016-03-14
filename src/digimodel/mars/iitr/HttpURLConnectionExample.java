package digimodel.mars.iitr;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import digimodel.mars.iitr.Cam1Coords.ObtainCoordinates1;

public class HttpURLConnectionExample {

    String x,y,z,tracker;
    static SendData sendData;
    private final String USER_AGENT = "Mozilla/5.0";
    
    public HttpURLConnectionExample(double x,double y,double z,int track) {
		// TODO Auto-generated constructor stub
    	
    		
        	this.x=((int)(x))+"";
           	this.y=((int)(y))+"";
        	this.z=((int)(z))+"";
        	this.tracker=track+"";
        	
    	
	}
    
    public interface SendData{
		void sendDataServer(String s);
	}
    
    public static void setOnDataSent(SendData listener) {
		sendData=listener;
	}
    // HTTP POST request
    public void sendPost() throws Exception {

        String url = "http://192.168.0.10:8080/data";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
      con.setRequestMethod("POST");
      con.setRequestProperty("User-Agent", USER_AGENT);
      con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "x="+x+"&y="+y+"&z="+z+"&track="+tracker;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
        
        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        if(sendData!= null)
        sendData.sendDataServer(response.toString());

    }

}