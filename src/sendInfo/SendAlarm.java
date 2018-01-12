package sendInfo;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import alarm.SychronAlarm;
import model.*;


public class SendAlarm {
	private String IP;
	private int port;
	private String nodeId;
	
	public SendAlarm(String IP,int port,long nodeId){
		this.IP =IP;
		this.port = port;
		this.nodeId = String.valueOf(nodeId);
	}
	
	public int sendAlarm(SychronAlarm synchronAlarm) {
		String type = "46";
		String itemId =synchronAlarm.getItemId();
		String startTime = synchronAlarm.getStartTime();
		String endTime = synchronAlarm.getEndTime();
		endTime ="0";
		String data = synchronAlarm.getData();
		sendXml(type,itemId,startTime,endTime,data,nodeId,IP,port);
		System.out.println("sendAlarm:" + IP +":"+port);
		return 1;
	}
	
	public int sendDisAlarm(SychronAlarm synchronAlarm) {
		String type = "47";
		String itemId =synchronAlarm.getItemId();
		String startTime = synchronAlarm.getStartTime();
		String endTime = synchronAlarm.getEndTime();
		String data = synchronAlarm.getData();
		sendDisXml(type,itemId,startTime,endTime,data,nodeId,IP,port);
		return 1;
	}
	
	public int sendXml(String type,String itemId,String startTime,String endTime,String data, String nodeid, String ip,int port) {
		try {


			String str = "";
			StringBuilder builder = new StringBuilder();

			builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			builder.append("<type id=\"" + type + "\">");
			builder.append("<Node id=\"" + nodeid + "\" />");
			builder.append("<time value=\"" + startTime + "\" />");
			builder.append("<itemid value=\"" + itemId + "\" />");
			builder.append("<flag value=\"" + data + "\" />");

			//builder.append("<runtime value=\"" + runTime + "\" />");
			builder.append("</type>");
			str = builder.toString();
			System.out.println(str);
			
			Socket socket = new Socket(ip, port);
			OutputStream outputStream = socket.getOutputStream();

			PrintWriter printWriter = new PrintWriter(outputStream);
			
			printWriter.println(str);
			printWriter.flush();
			socket.shutdownOutput();
			printWriter.close();
			outputStream.close();
			socket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 杩炴帴鏈嶅姟鍣�
		return 1;

	}
	
	public int sendDisXml(String type,String itemId,String startTime,String endTime,String data, String nodeid, String ip,int port) {
		try {
			Socket socket = new Socket(ip, port);
			OutputStream outputStream = socket.getOutputStream();

			PrintWriter printWriter = new PrintWriter(outputStream);

			String str = "";
			StringBuilder builder = new StringBuilder();

			builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			builder.append("<type id=\"" + type + "\">");
			builder.append("<Node id=\"" + nodeid + "\" />");
			builder.append("<itemid value=\"" + itemId + "\" />");
			builder.append("<starttime value=\"" + startTime + "\" />");
			builder.append("<endtime value=\"" + endTime + "\" />");
			builder.append("<flag value=\"" + data + "\" />");

			//builder.append("<runtime value=\"" + runTime + "\" />");
			builder.append("</type>");
			str = builder.toString();
			System.out.println(str);
			printWriter.println(str);
			printWriter.flush();
			socket.shutdownOutput();
			printWriter.close();
			outputStream.close();
			socket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 杩炴帴鏈嶅姟鍣�
		return 1;

	}


}
