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

import dao.MemberDao;

@WebServlet("/member/delete")
public class MemberDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn;
		try {
			ServletContext sc = this.getServletContext();
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
			memberDao.delete(Integer.parseInt(request.getParameter("no")));
			response.sendRedirect("list");
		} catch (Exception e) {
			request.getRequestDispatcher("/Error.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn;
		try {
			ServletContext sc = this.getServletContext();
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
			memberDao.delete(Integer.parseInt(request.getParameter("no")));
			response.sendRedirect("list");
		} catch (Exception e) {
			request.getRequestDispatcher("/Error.jsp");
		}
	}
}
