package model;

import java.sql.ResultSet;
import java.util.LinkedList;

import dbconnection.InformixConnection;

public class SyslogsSelect {

	// private ResultSet rs = null;
	private InformixConnection conn;

	private String sql = "select number,uniqid,size,used,is_current from syslogs;";

	public SyslogsSelect(InformixConnection conn) {
		super();
		this.conn = conn;
	}

	public LinkedList<SyslogsBean> select() {
		LinkedList<SyslogsBean> list = new LinkedList<SyslogsBean>();
		System.out.println(sql);
		ResultSet rs = conn.ExecuteQuery(sql);
		int myId = 1 ;
		if (null == rs) {
			return null;
		} else {
			try {
				while (rs.next()) {
					SyslogsBean bean = new SyslogsBean();
					bean.setId(rs.getInt(1));
					bean.setUniqid(rs.getInt(2));
					bean.setSize(rs.getInt(3));
					bean.setUsed(rs.getInt(4));
					bean.setIsCurrent(rs.getInt(5));
					bean.setMyId(myId);
					myId ++ ;
					list.add(bean);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}
		return list;
	}
}
