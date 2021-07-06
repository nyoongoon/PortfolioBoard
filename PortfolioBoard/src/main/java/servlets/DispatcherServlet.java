package servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controls.Controller;
import controls.LogInController;
import controls.LogOutController;
import controls.MemberAddController;
import controls.MemberDeleteController;
import controls.MemberListController;
import controls.MemberUpdateController;
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
			ServletContext sc = this.getServletContext();
			HashMap<String, Object> model = new HashMap<String, Object>();
			model.put("session", request.getSession());
			
			Controller pageController = (Controller) sc.getAttribute(servletPath);
					
			// 요청 URL에서 서블릿 경로 알아내기
			if("/member/list.do".equals(servletPath)) {
			}else if("/member/add.do".equals(servletPath)) {
				if(request.getParameter("email") != null) {
					model.put("member", new Member()
						.setEmail(request.getParameter("email"))
						.setPassword(request.getParameter("password"))
						.setName(request.getParameter("name")));
				}
			}else if("/member/update.do".equals(servletPath)) {
				if(request.getParameter("email")!=null) {
					model.put("member", new Member()
							.setEmail(request.getParameter("email"))
							.setNo(Integer.parseInt(request.getParameter("no")))
							.setName(request.getParameter("name")));
				}else {
					model.put("no", new Integer(request.getParameter("no")));
				}
			}else if("/member/delete.do".equals(servletPath)) {
				model.put("no", new Integer(request.getParameter("no")));
			}else if("/auth/login.do".equals(servletPath)) {
				if(request.getParameter("email")!=null) {
					model.put("loginInfo", new Member()
							.setEmail(request.getParameter("email"))
							.setPassword(request.getParameter("password")));
				}
			}
			String viewUrl = pageController.execute(model);
			
			for(String key : model.keySet()) {
				request.setAttribute(key,  model.get(key));
			}
			if(viewUrl.startsWith("redirect:")) {
				response.sendRedirect(viewUrl.substring(9));
				return;
			}else {
				RequestDispatcher rd = request.getRequestDispatcher(viewUrl);
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