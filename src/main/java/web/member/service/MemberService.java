package web.member.service;

import web.member.vo.Member;

public interface MemberService {
	Integer register(Member member);
	Member login(String account, String password);
}