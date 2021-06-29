package servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class DBConnectionPool {
	String url;
	String username;
	String password;
	ArrayList<Connection> connList = new ArrayList<Connection>();

	public DBConnectionPool(String driver, String url, String username, String password) throws Exception {
		this.url = url;
		this.username = username;
		this.password = password;

		Class.forName(driver);
	}

	public Connection getConnection() throws Exception {
		if (connList.size() > 0) {
			Connection conn = connList.get(0);
			if (conn.isValid(10)) {	//DB커넥션 객체도 일정 시간이 지나면 서버와의 연결이 끊어지기 때문에, 유효성 체크를 한 다음에 반
				return conn;
			}
		}
		return DriverManager.getConnection(url, username, password);
	}
	
	public void returnConnection(Connection conn) throws Exception{
		connList.add(conn);
	}
	
	public void closeAll() {
		for(Connection conn : connList) {
			try {conn.close();}catch(Exception e) {}
		}
	}

}
