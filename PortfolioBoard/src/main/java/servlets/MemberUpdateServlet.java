package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.Member;

public class MemberUpdateServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
			try {
				response.setContentType("text/html; charset=UTF-8");
				ServletContext sc = this.getServletContext();
				Class.forName(sc.getInitParameter("driver"));
				conn = DriverManager.getConnection(sc.getInitParameter("url"),
												sc.getInitParameter("username"),
												sc.getInitParameter("password"));
				stmt = conn.createStatement();
				rs = stmt.executeQuery(
							"select MNO, EMAIL, MNAME, CRE_DATE from MEMBERS" +
							" where MNO=" + request.getParameter("no"));
				rs.next();
				Member member = new Member().setNo(rs.getInt("MNO"))
						.setName(rs.getString("MNAME"))
						.setEmail(rs.getString("EMAIL"))
						.setCreatedDate(rs.getDate("CRE_DATE"));
				RequestDispatcher rd = request.getRequestDispatcher("/member/MemberUpdateForm.jsp");
				request.setAttribute("member", member);
				rd.forward(request, response);
			}catch(Exception e) {
				request.getRequestDispatcher("/Error.jsp");
			}finally {
				try {if(rs != null) rs.close();}catch(Exception e) {}
				try {if(stmt != null) stmt.close();}catch(Exception e) {}
				try {if(conn != null) conn.close();}catch(Exception e) {}
			}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Connection conn = null;
		PreparedStatement stmt = null;
		
			try {
				ServletContext sc = this.getServletContext();
				Class.forName(sc.getInitParameter("driver"));
				conn = DriverManager.getConnection(sc.getInitParameter("url"),
												sc.getInitParameter("username"),
												sc.getInitParameter("password"));
				stmt = conn.prepareStatement(
							"UPDATE MEMBERS SET EMAIL=?, MNAME=?, MOD_DATE=now()"
							+ " WHERE MNO=?");
				stmt.setString(1, request.getParameter("email"));
				stmt.setString(2, request.getParameter("name"));
				stmt.setInt(3, Integer.parseInt(request.getParameter("no")));
				stmt.executeUpdate();
				response.sendRedirect("list");
			}catch(Exception e) {
				request.getRequestDispatcher("/Error.jsp");
			}finally {
				try {if(stmt != null) stmt.close();} catch(Exception e) {}
				try {if(conn != null) stmt.close();} catch(Exception e) {}
			}
	}
}
