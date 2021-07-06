package controls;

import java.util.Map;

import dao.MemberDao;

public class MemberListController implements Controller{
	MemberDao memberDao;
	
	public MemberListController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	//회원목록을 처리하는 페이지 컨트롤러
	@Override
	public String execute(Map<String, Object> model) throws Exception{
		model.put("members", memberDao.selectList());
		return "/member/MemberList.jsp";
		}
}
