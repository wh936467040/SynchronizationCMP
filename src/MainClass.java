import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import alarm.SychronAlarm;
import sendInfo.SendAlarm;
import sendInfo.SendInfo;
import cal.CalSpeed;
import cal.SynchronzationCMP;
import dbconnection.DmConnection;
import dbconnection.DmWrite;
import dbconnection.InformixConnection;
import model.DatabaseStatusBean;
import model.DatabaseStatusSelect;
import model.SyslogsBean;
import model.SyslogsSelect;
import myConf.MyConf;

public class MainClass {
	
	static String informixUrl1 = "jdbc:informix-sqli://18.18.21.1:9088/sysmaster:informixserver=bj1";
	static String informixUser1 = "sg_readonly";
	static String informixPwd1 = "sg_readonly";
	
	//back informix
	static String informixUrl2 = "jdbc:informix-sqli://30.10.38.11:9088/sysmaster:informixserver=sh1;"
			+ "IFX_USE_STRENC=true";
	static String informixUser2 = "sg_readonly";
	static String informixPwd2 = "sg_readonly";
	
	static String dmUrl = "jdbc:dm://18.18.27.10:12345/MONITOR";
	static String dmUser = "SYSDBA";
	static String dmPwd = "SYSDBA";
	
	static String dmDbname = "MONITOR";
	
	/*
	static String informixUrl1 = "jdbc:informix-sqli://11.11.11.101:9088/sysmaster:informixserver=gbase1;";
	static String informixUser1 = "informix";
	static String informixPwd1 = "d5k.2017";

	// back informix
	static String informixUrl2 = "jdbc:informix-sqli://11.11.11.201:9088/sysmaster:informixserver=gbase2";
	static String informixUser2 = "informix";
	static String informixPwd2 = "d5k.2017";

	static String dmUrl = "jdbc:dm://11.11.11.250:12345/FUJIAN_MONITOR";
	static String dmUser = "SYSDBA";
	static String dmPwd = "SYSDBA";
	*/
	
	
	//实际没有建立连接
	static DmConnection dmConn = new DmConnection(dmUrl, dmUser, dmPwd);
	static SendInfo  sendInfo;
	static SendAlarm sendAlarm;
	static DmWrite dmWriter ;
	
	public static void main(String[] args) throws InterruptedException {
		while(true) {
			try{
				
				String path = System.getenv("D5000_HOME");
				path = path + "/conf/hisMonitor.conf";
				//String path = "C:\\Users\\WH\\Desktop\\hisMonitor.conf";
				System.out.println(path);
				MyConf conf = new MyConf();
				
				conf.read(path);
				dmDbname = conf.getDmDbName();

				sendInfo = new SendInfo(conf.getIP(),conf.getPort(),conf.getNodeId());
				sendAlarm = new SendAlarm(conf.getAlarmIp(),conf.getAlarmPort(),conf.getNodeId());
				dmWriter = new DmWrite(dmConn,dmDbname,sendInfo,sendAlarm);
				//dmConn.initJdbcConnection();
				
				InformixConnection informixConn1 = new InformixConnection(conf.getDbUrlBJ(),
						conf.getDbUserBJ(), conf.getDbPasswordBJ());
				int ret1 = informixConn1.initJdbcConnection();

				InformixConnection informixConn2 = new InformixConnection(conf.getDbUrlSH(),
						conf.getDbUserSH(), conf.getDbPasswordSH());
				int ret2 = informixConn2.initJdbcConnection();
				if (ret1 > 0 && ret2 > 0 && informixIsException(informixConn1, informixConn2) > 0) {
					getData(informixConn1,informixConn2,dmConn);
				}else {
					doException(informixConn1,informixConn2,dmConn);
				}
			} catch (Exception e) {
				System.out.println("111112222"); 
			}
			Thread.sleep(30*1000);
		}
		
	}
	
	public static void getData(InformixConnection informixConn1,
			InformixConnection informixConn2,DmConnection dmConn) {
		// main infomix


		
		SyslogsSelect informixReader1 = new SyslogsSelect(informixConn1);
		LinkedList<SyslogsBean> list1 = informixReader1.select();
		System.out.println(list1.size());

		SyslogsSelect informixReader2 = new SyslogsSelect(informixConn2);
		LinkedList<SyslogsBean> list2 = informixReader2.select();
		System.out.println(list2.size());
		
		DatabaseStatusSelect databaseStatusReader1 = new DatabaseStatusSelect(
				informixConn1);
		DatabaseStatusSelect databaseStatusReader2 = new DatabaseStatusSelect(
				informixConn2);

		SynchronzationCMP compartor = new SynchronzationCMP();
		// compartor.compare(list1,list2);
		CalSpeed calSpeedMachine = new CalSpeed();

		LinkedList<SyslogsBean> oldList1 = list1;
		LinkedList<SyslogsBean> oldList2 = list2;
		LinkedList<SyslogsBean> newList1 = null;
		LinkedList<SyslogsBean> newList2 = null;
		double speed1 = 0;
		double speed2 = 0;
		try {
			while (true) {
			
				Thread.sleep(30 * 1000);
				
				newList1 = informixReader1.select();
				newList2 = informixReader2.select();
				
				double sub = compartor.compare(newList1, newList2);
				speed1 = calSpeedMachine.getSpeed(newList1, oldList1);
				speed2 = calSpeedMachine.getSpeed(newList2, oldList2);
				
				speed1 = speed1*2; //KB;
				speed2 = speed2*2; //KB

				oldList1 = newList1;
				oldList2 = newList2;

				System.out.println("gbase1 speed " + speed1);
				System.out.println("gbase2 speed " + speed2);
				String status = null;
				String delta = null;
				
				sub =  (sub * 2.0/1024/1024); //GBytes;
				String tmp = String.format("%.2f",sub);
				sub = Double.parseDouble(tmp);
				
				if(Math.abs(sub) > 2) {
					status = "exception";
				} else {
					status = "normal";
				}
				delta = " " + sub + " GB ";

				
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String time = formatter.format(date);
				dmWriter.synchronzationWrite(status, delta, speed1, speed2,
						time);

				DatabaseStatusBean statusBean1 = databaseStatusReader1.select();
				DatabaseStatusBean statusBean2 = databaseStatusReader2.select();
				dmWriter.statusWrite(statusBean1.getType(),
						statusBean2.getType(), time);
			}
		} catch (Exception e) {
			System.out.println(e);

		}
		informixConn1.destoryConnection();
		informixConn2.destoryConnection();
		dmConn.destoryConnection();
	}
	
	public static int doException(InformixConnection informixConn1,
			InformixConnection informixConn2,DmConnection dmConn){
		
		CalSpeed calSpeedMachine = new CalSpeed();
		LinkedList<SyslogsBean> oldList = null;
		LinkedList<SyslogsBean> newList = null;;
		//DmWrite dmWriter = new DmWrite(dmConn,dmDbname);
		double speed1 = 0;
		double speed2 = 0;
		String status = "Exception";
		if(informixConn1.getStatement() !=null && informixConn2.getStatement() == null) {
			SyslogsSelect informixReader1 = new SyslogsSelect(informixConn1);
			oldList = informixReader1.select();
			try {
				Thread.sleep(30 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			newList = informixReader1.select();
			speed1 = calSpeedMachine.getSpeed(newList, oldList);
			speed2 = 0;	
			status = "HuaDong Exception";
		} else if (informixConn2.getStatement() !=null && informixConn1.getStatement() == null){
			SyslogsSelect informixReader2 = new SyslogsSelect(informixConn2);
			oldList = informixReader2.select();
			try {
				Thread.sleep(30 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			newList = informixReader2.select();
			speed2 = calSpeedMachine.getSpeed(newList, oldList);
			speed1 = 0;	
			status = "HuaBei Exception";
			
		} else if(informixConn2.getStatement() == null && informixConn1.getStatement() == null){
			speed1 = 0;
			speed2 = 0;
			status ="All Exception";
			System.out.println("all informix db connection failed ");
		} else {
			SyslogsSelect informixReader1 = new SyslogsSelect(informixConn1);
			oldList = informixReader1.select();
			try {
				Thread.sleep(30 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			newList = informixReader1.select();
			speed1 = calSpeedMachine.getSpeed(newList, oldList);
			speed2 = 0;	
			status = "HuaDong Exception";
		}
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String time = formatter.format(date);
		
		try {
			dmWriter.synchronzationWrite(status, "-1", speed1, speed2, time);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			DatabaseStatusSelect databaseStatusReader1 = new DatabaseStatusSelect(
					informixConn1);
			DatabaseStatusSelect databaseStatusReader2 = new DatabaseStatusSelect(
					informixConn2);
			DatabaseStatusBean statusBean1 = databaseStatusReader1.select();
			DatabaseStatusBean statusBean2 = databaseStatusReader2.select();
			dmWriter.statusWrite(statusBean1.getType(),
					statusBean2.getType(), time);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		informixConn1.destoryConnection();
		informixConn2.destoryConnection();
		dmConn.destoryConnection();
		return 0;	
	}
	
	public static int informixIsException(InformixConnection informixConn1,
			InformixConnection informixConn2){

		if(informixConn1.getStatement() !=null && informixConn2.getStatement() != null) {
			SyslogsSelect informixReader1 = new SyslogsSelect(informixConn1);
			SyslogsSelect informixReader2 = new SyslogsSelect(informixConn2);
			LinkedList<SyslogsBean> list = null;
			try {
				//Thread.sleep(3 * 1000);
				 list = informixReader2.select();
				 if(list.equals(null)) {
					 return -1;
				 }
				 return 1;
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		} else {
			return -1;
		}
	}
}
