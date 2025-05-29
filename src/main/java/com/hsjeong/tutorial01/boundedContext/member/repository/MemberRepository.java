package com.hsjeong.tutorial01.boundedContext.member.repository;

import com.hsjeong.tutorial01.boundedContext.member.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepository {
    private List<Member> members;

    public MemberRepository(){
        members = new ArrayList<>();

        members.add(new Member("user1","11111"));
        members.add(new Member("user2","22222"));
        members.add(new Member("user3","33333"));
        members.add(new Member("user4","44444"));
        members.add(new Member("user5","55555"));
        members.add(new Member("love","love1"));
        members.add(new Member("test","test1"));
        members.add(new Member("spring","spring1"));
        members.add(new Member("summer","summer1"));
        members.add(new Member("fall","fall1"));
        members.add(new Member("winter","winter1"));

    }

    public Member findByUserName(String username) {
        return members.stream()
                .filter(member -> member.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public Member findById(long id) {
        return members.stream()
                .filter(member -> (long)member.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
