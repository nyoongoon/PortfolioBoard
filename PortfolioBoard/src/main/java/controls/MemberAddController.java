package controls;

import java.util.Map;

import dao.MemberDao;
import vo.Member;

public class MemberAddController implements Controller{
	MemberDao memberDao;
	
	public MemberAddController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	@Override
	public String execute(Map<String, Object> model) throws Exception{
		if(model.get("member") == null) {//입력 폼을 요청할 때
			return "/member/MemberForm.jsp";
		}else {
			Member member = (Member) model.get("member");
			memberDao.insert(member);
			
			return "redirect:list.do";
		}
	}
}
