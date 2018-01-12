package sendInfo;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import model.*;


public class SendInfo {
	private String IP;
	private int port;
	private String nodeId;
	
	public SendInfo(String IP,int port,long nodeId){
		this.IP =IP;
		this.port = port;
		this.nodeId = String.valueOf(nodeId);
	}
	
	public int sendSql(String sql){
		List<String> list = new ArrayList<String> ();
		list.add(sql);
		//此处time 无作用;
		sendXml("5000","2017-06-16 11:30:30",this.nodeId,this.IP,this.port,list);
		System.out.println("send ok");
		return 1;
	}

	public int sendXml(String type, String time, String nodeid, String ip,int port,List<String> list) {
		try {
			Socket socket = new Socket(ip, port);
			OutputStream outputStream = socket.getOutputStream();

			PrintWriter printWriter = new PrintWriter(outputStream);

			String str = "";
			StringBuilder builder = new StringBuilder();

			builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			builder.append("<type id=\"" + type + "\">");
			builder.append("<nodeid id=\"" + nodeid + "\" />");
			builder.append("<time value=\"" + time + "\" />");
			//builder.append("<ip value=\"" + url + "\" />");
			for(int i=0;i<list.size();i++){
				builder.append("<data value=\"" + list.get(i) + "\" />");
			}
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
		} // 连接服务器
		return 1;

	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	

}
