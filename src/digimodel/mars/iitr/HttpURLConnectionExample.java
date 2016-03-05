package digimodel.mars.iitr;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import digimodel.mars.iitr.Cam1Coords.ObtainCoordinates1;

public class HttpURLConnectionExample {

    String x,y,z;
    SendData sendData;
    
    public HttpURLConnectionExample(double x,double y,double z) {
		// TODO Auto-generated constructor stub
    	this.x=x+"";
    	this.y=y+"";
    	this.z=z+"";
	}
    
    public interface SendData{
		void sendDataServer(String s);
	}
    
    public void setOnDataSent(SendData listener) {
		sendData=listener;
	}
    // HTTP POST request
    public void sendPost() throws Exception {

        String url = "https://192.168.0.30/";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");
//      con.setRequestProperty("User-Agent", USER_AGENT);
//      con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = x+""+y+"";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
        
        int responseCode = con.getResponseCode();
        System.out.println("URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        sendData.sendDataServer(response.toString());

    }

}