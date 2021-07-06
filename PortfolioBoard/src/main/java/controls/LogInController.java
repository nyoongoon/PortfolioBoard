package controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import dao.MemberDao;
import vo.Member;

public class LogInController implements Controller {
	MemberDao memberDao;

	public LogInController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {

		if (model.containsKey("loginInfo")) {// 로그인 내용이 담겨 있다면

			Member loginInfo = (Member) model.get("loginInfo");

			Member member = memberDao.exist(loginInfo.getEmail(), loginInfo.getPassword());

			// 세션 처리 하기
			if (member != null) {
				HttpSession session = (HttpSession) model.get("session");
				session.setAttribute("sessionMember", member);
				return "redirect:../member/list.do";
			} else {
				return "/auth/LogInFail.jsp";
			}
		} else {
			return "/auth/LogInForm.jsp";

		}
	}
}
