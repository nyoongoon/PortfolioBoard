package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;
import vo.Member;

@WebServlet("/auth/login")
public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		RequestDispatcher rd = request.getRequestDispatcher("/auth/LogInForm.jsp");
		rd.include(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ServletContext sc = this.getServletContext();
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
			Member member = memberDao.exist(request.getParameter("email"), request.getParameter("password"));
			
			HttpSession session = request.getSession();
			session.setAttribute("member", member);
			response.sendRedirect("../member/list");
			
		} catch (Exception e) {
			RequestDispatcher rd = request.getRequestDispatcher("/auth/LogInFail.jsp");
			rd.forward(request, response);
			throw new ServletException(e);
		}		
	}
}
