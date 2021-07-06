package controls;

import java.util.Map;

import dao.MemberDao;

public class MemberDeleteController implements Controller{
	MemberDao memberDao;
	
	public MemberDeleteController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	@Override
	public String execute(Map<String, Object> model) throws Exception{
		int no = (Integer)model.get("no");
		memberDao.delete(no);
		return "redirect:list.do";
	}
}
