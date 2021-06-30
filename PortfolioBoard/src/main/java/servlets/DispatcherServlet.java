package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.Member;

@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException{
		response.setContentType("text/html; charset=UTF-8");
		//요청 URL에서 서블릿 경로 추출하기
		String servletPath = request.getServletPath();
		try {
			String pageControllerPath = null;
			// 요청 URL에서 서블릿 경로 알아내기
			if("/member/list.do".equals(servletPath)) {
				pageControllerPath = "/member/list";
			}else if("/member/add.do".equals(servletPath)) {
				pageControllerPath = "/member/add";
				if(request.getParameter("email") != null) {
					request.setAttribute("member", new Member()
						.setEmail(request.getParameter("email"))
						.setPassword(request.getParameter("password"))
						.setName(request.getParameter("name")));
				}
			}else if("/member/update.do".equals(servletPath)) {
				pageControllerPath = "/member/update";
				if(request.getParameter("mail")!=null) {
					request.setAttribute("member", new Member()
							.setEmail(request.getParameter("email"))
							.setNo(Integer.parseInt(request.getParameter("no")))
							.setName(request.getParameter("name")));
				}
			}else if("/member/delete.do".equals(servletPath)) {
				pageControllerPath = "/member/delete";
			}else if("/auth/login.do".equals(servletPath)) {
				pageControllerPath = "auth/login";
			}else if("/auth/logout.do".equals(servletPath)) {
				pageControllerPath = "auth/logout";
			}
			//페이지 컨트롤러로 위임	
			RequestDispatcher rd = request.getRequestDispatcher(pageControllerPath);
			rd.include(request, response);
			//JSP로 위임
			String viewUrl = (String) request.getAttribute("viewUrl");
			if(viewUrl.startsWith("redirect:")) {
				response.sendRedirect(viewUrl.substring(9));
				return;
			}else {
				rd = request.getRequestDispatcher(viewUrl);
				rd.include(request, response);
			}
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}
}