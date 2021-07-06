package controls;

import java.util.Map;

import dao.MemberDao;
import vo.Member;

public class MemberUpdateController implements Controller{
	MemberDao memberDao;
	
	public MemberUpdateController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	@Override
	public String execute(Map<String, Object> model) throws Exception{
		Member member = (Member)model.get("member");
		if(member == null) {//입력 폼을 요청할 때
			// selectone() 매개변수를어떻게 담지?
			int no = (Integer)model.get("no");
			member = memberDao.selectOne(no);
			model.put("member", member);
			return "/member/MemberUpdateForm.jsp";
		}else {
			
			member = (Member) model.get("member");
			memberDao.update(member);
			
			return "redirect:list.do";
		}
	}
}
