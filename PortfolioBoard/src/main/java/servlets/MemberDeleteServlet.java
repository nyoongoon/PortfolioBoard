package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/delete")
public class MemberDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement stmt = null;
		String mno = request.getParameter("no");		
		String SQL = "DELETE FROM MEMBERS WHERE MNO =" + mno; 
		try {	
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
							sc.getInitParameter("url"),
							sc.getInitParameter("username"),
							sc.getInitParameter("password")
					);
			stmt = conn.prepareStatement(SQL);
			stmt.executeUpdate();
			response.sendRedirect("list");
		}catch(Exception e) {
			
		}finally {
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(conn != null) stmt.close();} catch(Exception e) {}
		}
		

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement stmt = null;
		String mno = request.getParameter("no");		
		String SQL = "DELETE FROM MEMBERS WHERE MNO =" + mno; 
		try {	
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
							sc.getInitParameter("url"),
							sc.getInitParameter("username"),
							sc.getInitParameter("password")
					);
			stmt = conn.prepareStatement(SQL);
			stmt.executeUpdate();
			response.sendRedirect("list");
		}catch(Exception e) {
			
		}finally {
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(conn != null) stmt.close();} catch(Exception e) {}
		}
		

	}
}
