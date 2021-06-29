package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import vo.Member;

public class MemberUpdateServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			try {
				ServletContext sc = this.getServletContext();
				MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
				Member member = memberDao.selectOne(Integer.parseInt(request.getParameter("no")));
				request.setAttribute("member", member);
				RequestDispatcher rd = request.getRequestDispatcher("/member/MemberUpdateForm.jsp");
				rd.forward(request, response);
				
			}catch(Exception e) {
				request.getRequestDispatcher("/Error.jsp");
			}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PreparedStatement stmt = null;
		
			try {
				response.setContentType("text/html; charset=UTF-8");
				ServletContext sc = this.getServletContext();
				MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
				memberDao.update(new Member()
					      .setNo(Integer.parseInt(request.getParameter("no")))
					      .setName(request.getParameter("name"))
					      .setEmail(request.getParameter("email")));

				response.sendRedirect("list");
			}catch(Exception e) {
				request.getRequestDispatcher("/Error.jsp");
			}finally {
				try {if(stmt != null) stmt.close();} catch(Exception e) {}
			}
	}
}
