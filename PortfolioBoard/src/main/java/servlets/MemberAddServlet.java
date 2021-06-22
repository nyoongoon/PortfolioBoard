package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

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
		PrintWriter out = response.getWriter();
		out.println("<html><head><tittle>회원등록</title></head>");
		out.println("<body><h1>회원등록</h1>");
		out.println("<form action='add' method='post'>");
		out.println("이름: <input type='text' name='name'><br>");
		out.println("이메일: <input type='text' name='email'><br>");
		out.println("암호: <input type='password' name='password'><br>");
		out.println("<input type = 'submit' value='추가'>");
		out.println("<input type = 'reset' value='취소'>");
		out.println("</form>");
		out.println("</body></html>");
			
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		Connection conn = null; //DB 접속 정보 --createStatement()를 통해 Statement 인터페이스 구현체 반환받음.
		PreparedStatement stmt = null; //db에 보낼 sql문을 담을 객체
	
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver()); //드라이버 객체 생성
			conn = DriverManager.getConnection(//생성한 sql드라이버로 dbms에 연결하고, 연결정보를 반환받음
							"jdbc:mysql://localhost/studydb",
							"root",
							"tjrqls29");
			stmt = conn.prepareStatement(
							"INSERT INTO MEMBERS(EMAIL, PWD, MNAME, CRE_DATE, MOD_DATE)"
						+	" VALUES(?, ?, ?, NOW(), NOW())");
			stmt.setString(1,  request.getParameter("email"));
			stmt.setString(2,  request.getParameter("password"));
			stmt.setString(3,  request.getParameter("name"));
			stmt.executeUpdate(); //서버에 sql문 날리기
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원등록결과</title></head>");
			out.println("<body>");
			out.println("<p> 등록 성공입니다! </p>");
			out.println("</body></html>");
		}catch(Exception e) {
			throw new ServletException(e);
		}finally {
			try {if(stmt!=null)stmt.close();}catch(Exception e) {}
			try {if(conn!=null)conn.close();}catch(Exception e) {}
		}
		
	}
	
}
