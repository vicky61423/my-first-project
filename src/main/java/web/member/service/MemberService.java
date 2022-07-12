package web.member.service;

import web.member.vo.Member;

public interface MemberService {
	Integer register(Member member);

	Member login(Member member);

	Integer modify(Member member);

	Integer remove(Member member);
}
