package com.hsjeong.tutorial01.boundedContext.member.service;

import com.hsjeong.tutorial01.boundedContext.base.rsData.RsData;
import com.hsjeong.tutorial01.boundedContext.member.entity.Member;
import com.hsjeong.tutorial01.boundedContext.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(){
        memberRepository = new MemberRepository();
    }

    public RsData tryLogin(String username, String password) {
        Member member = memberRepository.findByUserName(username);

        if(!username.equals(member.getUsername())){
            return RsData.of("F-3","%s(은)는 존재하지 않습니다");
        }
        if(!password.equals(member.getPassword())){
            return RsData.of("F-4","%s비밀번호가 일치하지 않습니다");
        }

        return RsData.of("S-1","%s님 환영합니다".formatted(username), member.getId());
    }

    public Member findById(long id) {
        return memberRepository.findById(id);
    }
}
