package com.hsjeong.tutorial01.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

@Controller
/**
 * 개발자가 스프링부트에게 말하는 것
 * 이 클래스는 웹 요청을 받아서 작업을 한다.
 * 이 클래스가 컨트롤러야
 */
public class HomeController {
    private int num;
    List<Person> personList;

    public HomeController() {
        num = -1;
        personList = new ArrayList<>();
    }

    @GetMapping("/home/addPerson")
    @ResponseBody
    public String addPerson(String name, int age){
        Person person = new Person(name, age);
        personList.add(person);
        //++id;
        //#1, return id +"번 사람이 추가되었습니다";
        //#2, return "%d번 사람이 추가되었습니다.".formatted(id);
        System.out.println(person);
        return "%d번 사람이 추가되었습니다.".formatted(person.getId());
    }

    @GetMapping("/home/showPeople")
    @ResponseBody
    public List<Person> showPeople() {
        return personList;
    }

    @GetMapping("/home/personTestCase")
    @ResponseBody
    public String personTestCase() {
        personList.add(new Person("둘리",22));
        personList.add(new Person("도우너",23));
        personList.add(new Person("고길동",51));
        return "테스트케이스 추가 완료되었습니다";
    }

    @GetMapping("/home/removePerson")
    @ResponseBody
    public String removePerson(int id) {
        /**
         * 일반 자바문법
         *         Person target = null;
         *         for(Person p : personList){
         *             if(p.getId() == id){
         *                  target = p;
         *                  break;
         *             }
         *         }
         *
         *         if(target==null){
         *             return "%d번 사람은 존재하지 않습니다.".formatted(id);
         *         }
         *         personList.remove(target);
         *         return "%d번 사람이 삭제되었습니다.".formatted(target.getId());
         */
        boolean flag = personList.removeIf(person -> person.getId()==id);
        /**
         * 리스트에서 해당요소가 있으면 삭제
         * 삭제가 성공이 되면 true를 반환, 실패를 반환하면 false를 반환
         */
        
        if(flag){
            return "%d번 사람이 삭제되었습니다.".formatted(id);
        }else{
            return "%d번 사람이 존재하지 않습니다.".formatted(id);
        }
    }

    @GetMapping("/home/modifyPerson")
    @ResponseBody
    public String modifyPerson(int id, String name, int age){
        /**
         * 자바 방식
         * Person target = null;
         *         for(Person p : personList){
         *             if(p.getId() == id){
         *                 target = p;
         *             }
         *         }
         */
        Person target = personList.stream()
                            .filter(person -> person.getId()==id)   // filter 메서드 내 조건이 참인 것만 스트림조회
                            .findFirst()    //찾은 것 중에 첫번째것 하나만 남도록 필터링
                            .orElse(null);  // 없으면 null 반환

        if(target != null){
            target.setName(name);
            target.setAge(age);
            return "%d번 사람 수정완료했습니다".formatted(id);
        }else{
            return "%d번 사람 존재하지 않습니다".formatted(id);
        }
    }


    /**
     * GetMapping
     * 개발자가 /home/main이라는 요청을 보내면 아래 메서드를 실행시켜줘
     */
    @GetMapping("/home/main")
    @ResponseBody
    /**
     * 아래 메서드를 실행 후 리턴값을 응답으로 삼아
     * body에 출력해줘
     */
    public String showHome() {
        return "어서오세요";
    }

    @GetMapping("/home/main2")
    @ResponseBody
    public String showHome2() {
        return "환영합니다456";
    }

    @GetMapping("/home/main3")
    @ResponseBody
    public String showHome3() {
        return "스프링부트는 획기적이다";
    }

    @GetMapping("/home/increase")
    @ResponseBody
    public int showIncrease() {
        ++num;
        return num;
    }

    @GetMapping("/home/cookie/increase")
    @ResponseBody
    public int showCookieIncrease(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int countInCookie = 0;

        if(req.getCookies() != null){
            countInCookie = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("count"))
                    .map(Cookie::getValue)
                    .mapToInt(Integer::parseInt)
                    .findFirst()
                    .orElse(0);
        }

        int newCountInCookie = countInCookie +1;

        res.addCookie(new Cookie("count",newCountInCookie+""));

        return newCountInCookie;
    }

    @GetMapping("/home/getReqAndResp")
    @ResponseBody
    public void showReqAndResp(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int age = Integer.parseInt(req.getParameter("age"));
        res.getWriter().append("Hello. I'm %d years old.".formatted(age));
    }

    private static String getReqAndResp() {
        return "reqAndResp";
    }


    @GetMapping("/home/plus")
    @ResponseBody
    public int showPlus(int a, @RequestParam(defaultValue = "0") int b) {
        /**
         * 컨트롤러 내부에 메서드의 매개변수
         * URL 요청 시 넘겨준 파라미터 값이 매개변수에 들어간다
         */
        return a + b;
    }

    @GetMapping("/home/returnBoolean")
    @ResponseBody
    public Boolean showReturnBoolean() {
        return true;
    }

    @GetMapping("/home/returnDouble")
    @ResponseBody
    public double showReturnDouble() {
        return Math.PI;
    }

    @GetMapping("/home/returnArray")
    @ResponseBody
    public int[] showReturnArray() {
        /**
         * 스프링부트 내부 객체가 객체 조회 시 주소값이 아닌
         * 원소값을 조회할 수 있도록 도와줌
         */

        int[] arr = new int[]{10, 20, 30};
        System.out.println(arr);
        return arr;
    }

    @GetMapping("/home/returnList")
    @ResponseBody
    public List<Integer> showReturnList() {
        List<Integer> list = new ArrayList<>() {{
            add(10);
            add(20);
            add(30);
        }};

        return list;
    }

    @GetMapping("/home/returnMap")
    @ResponseBody
    public Map<String, Object> showReturnMap() {
        Map<String, Object> map = new LinkedHashMap<>() {{
            put("id", 1);
            put("subject", "제목");
            put("content", "내용");
            put("articleNo", new ArrayList<>() {{
                add(1);
                add(2);
                add(3);
            }});
        }};
        return map;
    }

    @GetMapping("/home/returnArticle")
    @ResponseBody
    public Article showReturnArticle() {
        Article article = new Article(1, "subject1", "content2", new ArrayList<>() {{
            add(1);
            add(2);
            add(3);
        }});

        return article;
    }

    @GetMapping("/home/returnArticle2")
    @ResponseBody
    public Article2 showReturnArticle2() {
        Article2 article2 = new Article2(2, "subsub1", "concon2", new ArrayList<>() {{
            add(10);
            add(20);
            add(30);
        }});

        return article2;
    }

    @GetMapping("/home/returnArticleMapList")
    @ResponseBody
    public List<Map<String,Object>> showReturnMapList() {
        Map<String,Object> articleMap1 = new LinkedHashMap<>() {{
                put("id", 1);
                put("subject", "제목1");
                put("content", "내용1");
                put("articleNo", new ArrayList<>() {{
                    add(1);
                    add(2);
                    add(3);
                }});
        }};

        Map<String,Object> articleMap2 = new LinkedHashMap<>() {{
            put("id", 2);
            put("subject", "제목2");
            put("content", "내용2");
            put("articleNo", new ArrayList<>() {{
                add(10);
                add(20);
                add(30);
            }});
        }};

        List<Map<String,Object>> list = new ArrayList<>();
        list.add(articleMap1);
        list.add(articleMap2);

        return list;

    }
}

class Article{
    private final int id;
    private String subject;
    private String content;
    private List<Integer> articleNo;

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public List<Integer> getArticleNo() {
        return articleNo;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setArticleNo(List<Integer> articleNo) {
        this.articleNo = articleNo;
    }

    public Article(int id, String subject, String content, List<Integer> articleNo) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.articleNo = articleNo;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", articleNo=" + articleNo +
                '}';
    }
}

@Getter @Setter
@AllArgsConstructor
@ToString
class Article2 {
    private final int id;
    private String subject;
    private String content;
    private List<Integer> articleNo;
}

@AllArgsConstructor
@Getter @Setter
@ToString
class Person {
    private static int lastId;
    private final int id;
    private String name;
    private int age;

    static{
        lastId = 0;
    }

    public Person(String name, int age){
        this(++lastId, name, age);
    }
}
