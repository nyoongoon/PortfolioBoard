package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		RequestDispatcher rd = request.getRequestDispatcher("/member/MemberForm.jsp");
		rd.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = null; //DB 접속 정보 --createStatement()를 통해 Statement 인터페이스 구현체 반환받음.
		PreparedStatement stmt = null; //db에 보낼 sql문을 담을 객체
	
		try {
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));//드라이버 객체 생성
			conn = DriverManager.getConnection(//생성한 sql드라이버로 dbms에 연결하고, 연결정보를 반환받음
							sc.getInitParameter("url"),
							sc.getInitParameter("username"),
							sc.getInitParameter("password"));
			stmt = conn.prepareStatement(
							"INSERT INTO MEMBERS(EMAIL, PWD, MNAME, CRE_DATE, MOD_DATE)"
						+	" VALUES(?, ?, ?, NOW(), NOW())");
			stmt.setString(1,  request.getParameter("email"));
			stmt.setString(2,  request.getParameter("password"));
			stmt.setString(3,  request.getParameter("name"));
			stmt.executeUpdate(); //서버에 sql문 날리기
			response.sendRedirect("list");
			
		}catch(Exception e) {
			request.getRequestDispatcher("/Error.jsp");
		}finally {
			try {if(stmt!=null)stmt.close();}catch(Exception e) {}
			try {if(conn!=null)conn.close();}catch(Exception e) {}
		}
		
	}
	
}
