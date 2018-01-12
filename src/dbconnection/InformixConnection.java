package dbconnection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class InformixConnection {
	
	public Connection connection = null;
	private  String driver = "com.informix.jdbc.IfxDriver";
	
	private  Statement statement = null;

	private  String url;
	private  String userName;
	private  String passWord;
	

	public void destoryConnection() {
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public InformixConnection(String URL,String usrname,String pwd) {
		this.url=URL;
		this.userName=usrname;
		this.passWord=pwd;
	}
	
	public ResultSet ExecuteQuery(String sql) {
		ResultSet rs=null;
		//����statement����
		try{
			rs = statement.executeQuery(sql);
		}catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}
	
	public boolean ExecuteSql(String Sql) throws SQLException{
		
		boolean bIsTrue = false;
		
		try {
			int i = statement.executeUpdate(Sql);
			if (i == 0){
				bIsTrue = true;
			}
		} catch (SQLException e) {
			 throw e;
		}
		
		
		return bIsTrue;
	}
	

	public 	int initJdbcConnection() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, userName, passWord);
			statement = connection.createStatement();
			System.out.println("connect success");
			return 1;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return -1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} 
	}

	public  String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public  String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
			
}
