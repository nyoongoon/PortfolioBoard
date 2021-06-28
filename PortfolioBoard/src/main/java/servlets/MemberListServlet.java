package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.Member;

@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
		
			ServletContext sc = this.getServletContext();
			conn = (Connection)sc.getAttribute("conn");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"select MNO, MNAME, EMAIL, CRE_DATE" + " from MEMBERS " 
			+ "order by MNO ASC");
			response.setContentType("text/html; charset=UTF-8");
			ArrayList<Member> members = new ArrayList<Member>();
			while(rs.next()) {
				members.add(new Member().setNo(rs.getInt("MNO"))
													.setName(rs.getString("MNAME"))
													.setEmail(rs.getString("EMAIL"))
													.setCreatedDate(rs.getDate("CRE_DATE")));
			}
			//request에 회원 목록 데이터 보관
			request.setAttribute("members", members);
			
			//JSP로 출력을 위임.
			RequestDispatcher rd = request.getRequestDispatcher(
						"/member/MemberList.jsp");
			rd.include(request, response);
			
		}catch(Exception e) {
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
			
		}finally {
			try {if(rs!=null)rs.close();}catch(Exception e) {}
			try {if(stmt!=null)stmt.close();}catch(Exception e) {}
		}
	}
}
