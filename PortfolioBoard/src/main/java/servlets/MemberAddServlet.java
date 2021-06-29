package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import vo.Member;


@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		RequestDispatcher rd = request.getRequestDispatcher("/member/MemberForm.jsp");
		rd.include(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PreparedStatement stmt = null; //db에 보낼 sql문을 담을 객체
	
		try {
			
			ServletContext sc = this.getServletContext();
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
			
			memberDao.insert(
					new Member().setName(request.getParameter("name")).setEmail(request.getParameter("email")).setPassword(request.getParameter("password")));
			
			
			response.sendRedirect("list");
			
		}catch(Exception e) {
			request.getRequestDispatcher("/Error.jsp");
			e.printStackTrace();
		}finally {
			try {if(stmt!=null)stmt.close();}catch(Exception e) {}
		}
		
	}
	
}
