package dbconnection;

import alarm.SychronAlarm;

import sendInfo.SendAlarm;
import sendInfo.SendInfo;


public class DmWrite {
	

	public static SychronAlarm synchronAlarmHuaBei = new SychronAlarm();
	public static SychronAlarm synchronAlarmHuaDong = new SychronAlarm();
	public static SychronAlarm synchronAlarmData = new SychronAlarm();
	private DmConnection conn = null;
	private String dmDbname ;
	private SendInfo sendInfo;
	private SendAlarm sendAlarm;

	public DmWrite(DmConnection conn,String dmDbname,SendInfo sendInfo,SendAlarm sendAlarm){
		this.conn = conn;
		this.dmDbname = dmDbname;
		this.sendInfo = sendInfo;
		this.sendAlarm = sendAlarm;
	}
	
	public void synchronzationWrite( String status,String delta,double speed1,double speed2,String time) throws Exception {
		
		String sqlFormat = "INSERT INTO %s.sysdba.synchronzation(status,delta,speed_local,speed_net,time) VALUES('%s','%s',%s,%s,'%s')";
		String sql = String.format(sqlFormat,this.dmDbname,status,delta,String.valueOf(speed1),String.valueOf(speed2),time);
		System.out.println(sql);
		String deleteSql = "delete from "+ this.dmDbname+".sysdba.synchronzation where time = (select min(time) from "+this.dmDbname +".sysdba.synchronzation)";
		sql =  deleteSql + ";" + sql;
		sendInfo.sendSql(sql);
		System.out.println("33333");
		
		String data = "";
	
		if(status.equalsIgnoreCase("HuaBei Exception")) {
			if(synchronAlarmHuaBei.getMark() == 0) {
				data = "横向同步: 华北异常";
				byte [] str = data.getBytes("UTF-8");
				data = new String(str,"UTF-8");
				synchronAlarmHuaBei.setInstance(sendInfo.getNodeId(),data, time, status);
				sendAlarm.sendAlarm(synchronAlarmHuaBei);
				synchronAlarmHuaBei.setMark(1);
			}
			if(synchronAlarmHuaDong.getMark() == 1) {
				synchronAlarmHuaDong.setEndTime(time);
				sendAlarm.sendDisAlarm(synchronAlarmHuaDong);
				synchronAlarmHuaDong.setMark(0);
			}
			if(synchronAlarmData.getMark() == 1) {
				synchronAlarmData.setEndTime(time);
				sendAlarm.sendDisAlarm(synchronAlarmData);
				synchronAlarmData.setMark(0);
			}
		}
		
		if(status.equalsIgnoreCase("HuaDong Exception")) {
			if(synchronAlarmHuaBei.getMark() == 1) {
				synchronAlarmHuaBei.setEndTime(time);
				sendAlarm.sendDisAlarm(synchronAlarmHuaBei);
				synchronAlarmHuaBei.setMark(0);
			}
			if(synchronAlarmHuaDong.getMark() == 0) {
				data = "横向同步:华东异常";
				byte [] str = data.getBytes("UTF-8");
				data = new String(str,"UTF-8");
				synchronAlarmHuaDong.setInstance(sendInfo.getNodeId(),data, time, status);
				sendAlarm.sendAlarm(synchronAlarmHuaDong);
				synchronAlarmHuaDong.setMark(1);
			}
			if(synchronAlarmData.getMark() == 1) {
				synchronAlarmData.setEndTime(time);
				sendAlarm.sendDisAlarm(synchronAlarmData);
				synchronAlarmData.setMark(0);
			}
		}
		if(status.equalsIgnoreCase("All Exception")) {
			if(synchronAlarmHuaBei.getMark() == 0) {
				data = "横向同步: 华北异常";
				byte [] str = data.getBytes("UTF-8");
				data = new String(str,"UTF-8");
				synchronAlarmHuaBei.setInstance(sendInfo.getNodeId(),data, time, status);
				sendAlarm.sendAlarm(synchronAlarmHuaBei);
				synchronAlarmHuaBei.setMark(1);
			}
			if(synchronAlarmHuaDong.getMark() == 0) {
				data = "横向同步:华东异常";
				byte [] str = data.getBytes("UTF-8");
				data = new String(str,"UTF-8");
				synchronAlarmHuaDong.setInstance(sendInfo.getNodeId(),data, time, status);
				sendAlarm.sendAlarm(synchronAlarmHuaDong);
				synchronAlarmHuaDong.setMark(1);
			}
			if(synchronAlarmData.getMark() == 1) {
				synchronAlarmData.setEndTime(time);
				sendAlarm.sendDisAlarm(synchronAlarmData);
				synchronAlarmData.setMark(0);
			}
		}
		if(status.equalsIgnoreCase("Exception")) {  //data Exception
			if(synchronAlarmHuaBei.getMark() == 1) {
				synchronAlarmHuaBei.setEndTime(time);
				sendAlarm.sendDisAlarm(synchronAlarmHuaBei);
				synchronAlarmHuaBei.setMark(0);
			}
			if(synchronAlarmHuaDong.getMark() == 1) {
				synchronAlarmHuaDong.setEndTime(time);
				sendAlarm.sendDisAlarm(synchronAlarmHuaDong);
				synchronAlarmHuaDong.setMark(0);
			}
			if(synchronAlarmData.getMark() == 0) {
				data = "横向同步: 同步差值 "+ delta;
				byte [] str = data.getBytes("UTF-8");
				data = new String(str,"UTF-8");
				synchronAlarmData.setInstance(sendInfo.getNodeId(),data, time, status);
				sendAlarm.sendAlarm(synchronAlarmData);
				synchronAlarmData.setMark(1);
			}
		}
		if(status.equalsIgnoreCase("normal")) {
			if(synchronAlarmHuaBei.getMark() == 1) {
				synchronAlarmHuaBei.setEndTime(time);
				sendAlarm.sendDisAlarm(synchronAlarmHuaBei);
				synchronAlarmHuaBei.setMark(0);
			}
			if(synchronAlarmHuaDong.getMark() == 1) {
				synchronAlarmHuaDong.setEndTime(time);
				sendAlarm.sendDisAlarm(synchronAlarmHuaDong);
				synchronAlarmHuaDong.setMark(0);
			}
			if(synchronAlarmData.getMark() == 1) {
				synchronAlarmData.setEndTime(time);
				sendAlarm.sendDisAlarm(synchronAlarmData);
				synchronAlarmData.setMark(0);
			}
		}
		System.out.println("write ok");
	
	}
	
	public void statusWrite( int status1,int status2,String time) throws Exception {
		
		String sqlFormat = "INSERT INTO %s.sysdba.database_Status(status_local,status_net,time) VALUES(%s,%s,'%s')";
		String sql = String.format(sqlFormat,this.dmDbname,String.valueOf(status1),String.valueOf(status2),time);
		System.out.println(sql);
		String deleteSql = "delete from "+ this.dmDbname+".sysdba.database_Status where time = (select min(time) from "+ this.dmDbname+".sysdba.database_Status) ";
		sql =  deleteSql + ";" + sql;
		sendInfo.sendSql(sql);
	}
}