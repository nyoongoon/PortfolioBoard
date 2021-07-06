package listeners;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import controls.LogInController;
import controls.LogOutController;
import controls.MemberAddController;
import controls.MemberDeleteController;
import controls.MemberListController;
import controls.MemberUpdateController;
import dao.MemberDao;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			ServletContext sc = event.getServletContext();
			InitialContext initialContext = new InitialContext();
			DataSource ds = (DataSource)initialContext.lookup(
					//서버 자원의 JNDI이름
					"java:comp/env/jdbc/studydb");
			
			MemberDao memberDao = new MemberDao();
			memberDao.setDataSource(ds);
			
			sc.setAttribute("/auth/login.do",
					new LogInController().setMemberDao(memberDao));
			sc.setAttribute("/auth/logout.do", 
					new LogOutController());
			sc.setAttribute("/member/list.do", 
					new MemberListController().setMemberDao(memberDao));
			sc.setAttribute("/member/add.do",
					new MemberAddController().setMemberDao(memberDao));
			sc.setAttribute("/member/update.do",
					new MemberUpdateController().setMemberDao(memberDao));
			sc.setAttribute("/member/delete.do", 
					new MemberDeleteController().setMemberDao(memberDao));
			
			
		}catch(Throwable e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}
}
