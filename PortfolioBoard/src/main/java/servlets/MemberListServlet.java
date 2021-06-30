package servlets;

import java.io.IOException;
import java.sql.Connection;
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

import dao.MemberDao;
import vo.Member;

@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Statement stmt = null;
		ResultSet rs = null;
		try {
		
			ServletContext sc = this.getServletContext();
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
			
			request.setAttribute("members", memberDao.selectList());
			// JSP URL 정보를 프론트 컨트롤러에 알려주
			request.setAttribute("viewUrl", "/member/MemberList.jsp");
		}catch(Exception e) {
			throw new ServletException(e);
		}finally {
			try {if(rs!=null)rs.close();}catch(Exception e) {}
			try {if(stmt!=null)stmt.close();}catch(Exception e) {}
		}
	}
}
