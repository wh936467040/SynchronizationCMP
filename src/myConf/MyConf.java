package myConf;

import java.io.BufferedReader;
import java.io.FileReader;

public class MyConf {
	private long nodeId = 0L;
	private String IP;
	private int port;
	private String alarmIp;
	private int alarmPort;
	
	private String dbUrlBJ;
	private String dbUserBJ;
	private String dbPasswordBJ;
	
	private String dbUrlSH;
	private String dbUserSH;
	private String dbPasswordSH;
	private String dmDbName;
	
	

	public int read(String path) {
		//String path = getHomePath() + "/Desktop/conf.txt";
		System.out.println(path);
		try {
			// read file content from file
			FileReader reader = new FileReader(path);
			BufferedReader br = new BufferedReader(reader);

			String str = null;

			while ((str = br.readLine()) != null) {
				if(str.indexOf("id:") >= 0 || str.indexOf("ID:") > 0){
					str = str.replace("id:", "");
					str = str.trim();
					System.out.println(str);
					this.nodeId = Long.parseLong(str);
				} else if(str.indexOf("serverip:") >= 0 || str.indexOf("SERVERIP:") > 0){
					str = str.replace("serverip:", "");
					str = str.trim();
					System.out.println(str);
					this.IP = str;
				} else if(str.indexOf("serverport:") >= 0 || str.indexOf("SERVERIP:") > 0){
					str = str.replace("serverport:", "");
					str = str.trim();
					System.out.println(str);
					this.port = Integer.parseInt(str);
				} else if(str.indexOf("dburlbj:") >= 0 || str.indexOf("DBURLBJ:") > 0) {
					str = str.replace("dburlbj:", "");
					str = str.trim();
					System.out.println(str);
					this.dbUrlBJ = str;
				} else if(str.indexOf("dbuserbj:") >= 0 || str.indexOf("DBUSERBJ:") > 0) {
					str = str.replace("dbuserbj:", "");
					str = str.trim();
					System.out.println(str);
					this.dbUserBJ = str;
				} else if(str.indexOf("dbpasswordbj:") >= 0 || str.indexOf("dbpasswordBJ:") > 0) {
					str = str.replace("dbpasswordbj:", "");
					str = str.trim();
					System.out.println(str);
					this.dbPasswordBJ = str;
				}else if(str.indexOf("dburlsh:") >= 0 || str.indexOf("DBURLSH:") > 0) {
					str = str.replace("dburlsh:", "");
					str = str.trim();
					System.out.println(str);
					this.dbUrlSH = str;
				} else if(str.indexOf("dbusersh:") >= 0 || str.indexOf("DBUSERSH:") > 0) {
					str = str.replace("dbusersh:", "");
					str = str.trim();
					System.out.println(str);
					this.dbUserSH = str;
				} else if(str.indexOf("dbpasswordsh:") >= 0 || str.indexOf("dbpasswordSH:") > 0) {
					str = str.replace("dbpasswordsh:", "");
					str = str.trim();
					System.out.println(str);
					this.dbPasswordSH = str;
				} else if(str.indexOf("dmdbname:") >= 0 || str.indexOf("dmdbname:") > 0) {
					str = str.replace("dmdbname:", "");
					str = str.trim();
					System.out.println(str);
					this.dmDbName = str;
				} else if(str.indexOf("alarmip:") >= 0 || str.indexOf("alarmip:") > 0){
					str = str.replace("alarmip:", "");
					str = str.trim();
					System.out.println(str);
					this.alarmIp = str;
				} else if(str.indexOf("alarmport:") >= 0 || str.indexOf("alarmport:") > 0){
					str = str.replace("alarmport:", "");
					str = str.trim();
					System.out.println(str);
					this.alarmPort = Integer.parseInt(str);
				}
			}
			br.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}



	public long getNodeId() {
		return nodeId;
	}



	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
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



	public String getDbUrlBJ() {
		return dbUrlBJ;
	}



	public void setDbUrlBJ(String dbUrlBJ) {
		this.dbUrlBJ = dbUrlBJ;
	}



	public String getDbUserBJ() {
		return dbUserBJ;
	}



	public void setDbUserBJ(String dbUserBJ) {
		this.dbUserBJ = dbUserBJ;
	}



	public String getDbPasswordBJ() {
		return dbPasswordBJ;
	}



	public void setDbPasswordBJ(String dbPasswordBJ) {
		this.dbPasswordBJ = dbPasswordBJ;
	}



	public String getDbUrlSH() {
		return dbUrlSH;
	}



	public void setDbUrlSH(String dbUrlSH) {
		this.dbUrlSH = dbUrlSH;
	}



	public String getDbUserSH() {
		return dbUserSH;
	}



	public void setDbUserSH(String dbUserSH) {
		this.dbUserSH = dbUserSH;
	}



	public String getDbPasswordSH() {
		return dbPasswordSH;
	}



	public void setDbPasswordSH(String dbPasswordSH) {
		this.dbPasswordSH = dbPasswordSH;
	}



	public String getDmDbName() {
		return dmDbName;
	}



	public void setDmDbName(String dmDbName) {
		this.dmDbName = dmDbName;
	}



	public String getAlarmIp() {
		return alarmIp;
	}



	public void setAlarmIp(String alarmIp) {
		this.alarmIp = alarmIp;
	}



	public int getAlarmPort() {
		return alarmPort;
	}



	public void setAlarmPort(int alarmPort) {
		this.alarmPort = alarmPort;
	}

	
	
}
