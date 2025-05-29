package com.hsjeong.tutorial01.boundedContext.member.controller;

import com.hsjeong.tutorial01.boundedContext.base.rq.Rq;
import com.hsjeong.tutorial01.boundedContext.base.rsData.RsData;
import com.hsjeong.tutorial01.boundedContext.member.entity.Member;
import com.hsjeong.tutorial01.boundedContext.member.service.MemberService;
import com.sun.jdi.LongType;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
@RequestMapping("/member")
public class MemberController {

    /**
     *     // 필드 주입
     *     @Autowired
     *     private MemberService memberService;
     */

    // 생성자 주입
    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }



    @GetMapping("/login")
    @ResponseBody
    public RsData login(String username, String password, HttpServletRequest req, HttpServletResponse res){

        Rq rq = new Rq(req, res);
        /**
         *         Map<String,Object> rsData = new LinkedHashMap<>(){{
         *            put("resultCode","success");
         *            put("msg","%s님 환영합니다".formatted(username));
         *         }};
         *         return rsData;
         */
        if(username == null){
            return RsData.of("F-1","로그인 아이디를 입력해주세요");
        }

        if(password == null){
            return RsData.of("F-2","로그인 비밀번호를 입력해주세요");
        }

        RsData rsData = memberService.tryLogin(username, password);

        if(rsData.isSuccess()){
            Member member = (Member)rsData.getData();
            rq.setCookie("loginedMemberId",member.getId());
        }
        return rsData;
    }

    @GetMapping("/logout")
    @ResponseBody
    public RsData logout(HttpServletRequest req, HttpServletResponse res) {

        Rq rq = new Rq(req, res);

        boolean cookieRemoved = rq.removedCookie("loginedMemberId");

        if(!cookieRemoved){
            return RsData.of("F-1","이미 로그아웃 상태입니다");
        }

        return RsData.of("S-1","로그아웃되었습니다");
    }

    @GetMapping("/me")
    @ResponseBody
    public RsData showMe(HttpServletRequest req, HttpServletResponse res){
        Rq rq = new Rq(req,res);

        boolean isLogined = false;

        long loginedMemberId = rq.getCookieAsLong("loginedMemberId",0);
        System.out.print("loginedMemberId : "  + loginedMemberId);

        isLogined = loginedMemberId >0;

        if(!isLogined){
            return RsData.of("F-1","로그인 후 이용해주세요");
        }

        Member member = memberService.findById(loginedMemberId);
        return RsData.of("success","당신의 username은 %s 입니다".formatted(member.getUsername()));
    }

}