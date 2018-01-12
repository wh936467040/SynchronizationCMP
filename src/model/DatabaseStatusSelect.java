package model;

import java.sql.ResultSet;
import java.util.LinkedList;

import dbconnection.InformixConnection;

public class DatabaseStatusSelect {

	// private ResultSet rs = null;
	private InformixConnection conn;

	private String sql = "select ha_type from sysha_type;";

	public DatabaseStatusSelect(InformixConnection conn) {
		super();
		this.conn = conn;
	}

	public DatabaseStatusBean select() {
		DatabaseStatusBean bean= new DatabaseStatusBean();
		System.out.println(sql);
		ResultSet rs = conn.ExecuteQuery(sql);
		int myId = 1 ;
		if (null == rs) {
			return null;
		} else {
			try {
				while (rs.next()) {
					bean.setType(rs.getInt(1));
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}
		return bean;
	}
}
