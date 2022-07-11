package web.member.dao;

import web.member.vo.Member;

public interface MemberDao {
	Integer insert (Member member);
	Member selectByMemberIdAndPassword(Member member);
	Integer update (Member member);
	Integer delete (Member member);
}
